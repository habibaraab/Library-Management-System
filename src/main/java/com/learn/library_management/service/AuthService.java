package com.learn.library_management.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.learn.library_management.config.Messages;
import com.learn.library_management.dto.AuthResponse;
import com.learn.library_management.dto.LoginRequestDTO;
import com.learn.library_management.dto.ResetPasswordDTO;
import com.learn.library_management.dto.UserChangePasswordRequestDTO;
import com.learn.library_management.dto.UserCreateDTO;
import com.learn.library_management.dto.UserResponseDTO;
import com.learn.library_management.exception.EmailAlreadyExistsException;
import com.learn.library_management.exception.InvalidCurrentPasswordException;
import com.learn.library_management.exception.InvalidResetCodeException;
import com.learn.library_management.exception.InvalidTokenException;
import com.learn.library_management.exception.RoleNotFoundException;
import com.learn.library_management.exception.UserNotFoundException;
import com.learn.library_management.exception.UsernameAlreadyExistsException;
import com.learn.library_management.mapper.UserMapper;
import com.learn.library_management.entities.User;
import com.learn.library_management.events.PasswordCodeRegeneratedEvent;
import com.learn.library_management.events.PasswordResetRequestedEvent;
import com.learn.library_management.entities.Role;
import com.learn.library_management.repository.RoleRepository;
import com.learn.library_management.repository.TokenRepository;
import com.learn.library_management.repository.UserRepository;
import static com.learn.library_management.rabbitconfig.RabbitConstants.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMapper;
	private final TokenRepository tokenRepository;
	private final RoleRepository roleRepository;
	private final JwtService jwtService;
	private final RabbitTemplate rabbitTemplate;

	@Transactional
	public AuthResponse login(@Valid LoginRequestDTO loginRequest) {
		User user = userRepository.findByUsername(loginRequest.getUsernameOrEmail())
				.or(() -> userRepository.findByEmail(loginRequest.getUsernameOrEmail()))
				.orElseThrow(UserNotFoundException::new);
		String accessToken = jwtService.generateToken(user);
		String refreshToken = jwtService.generateRefreshToken(user);
		jwtService.revokeAllUserTokens(user);
		jwtService.saveUserToken(user, accessToken);
		return new AuthResponse(accessToken, refreshToken);
	}

	@Transactional
	public UserResponseDTO createUser(@Valid UserCreateDTO dto) {
		User user = userMapper.toEntity(dto);

		userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
			throw new EmailAlreadyExistsException();
		});

		userRepository.findByUsername(user.getUsername()).ifPresent(u -> {
			throw new UsernameAlreadyExistsException();
		});

		Role role = roleRepository.findById(dto.getRoleId()).orElseThrow(() -> new RoleNotFoundException());

		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRole(role);

		User savedUser = userRepository.save(user);

		return userMapper.toDTO(savedUser);
	}

	public void forgotPassword(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
		String resetCode = generateConfirmationCode();
		user.setRequestCode(resetCode);
		userRepository.save(user);

		PasswordResetRequestedEvent event = new PasswordResetRequestedEvent(user.getId(), user.getUsername(),
				user.getEmail(), resetCode, LocalDateTime.now());
		rabbitTemplate.convertAndSend(MAIL_EXCHANGE, MAIL_PASSWORD_RESET_KEY, event);
	}

	public void resetPassword(@Valid ResetPasswordDTO resetPasswodDTO) {
		User user = userRepository.findByEmail(resetPasswodDTO.getEmail())
				.orElseThrow(() -> new UserNotFoundException());
		if (!resetPasswodDTO.getCode().equals(user.getRequestCode())) {
			throw new InvalidResetCodeException();
		}
		user.setPassword(passwordEncoder.encode(resetPasswodDTO.getNewPassword()));
		user.setRequestCode(null);
		userRepository.save(user);

	}

	public void changePassword(String email, @Valid UserChangePasswordRequestDTO request) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
		if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
			throw new InvalidCurrentPasswordException();
		}
		user.setPassword(passwordEncoder.encode(request.getNewPassword()));
		userRepository.save(user);
	}

	public void reGenerateCode(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
		String resetCode = generateConfirmationCode();
		user.setRequestCode(resetCode);
		userRepository.save(user);

		rabbitTemplate.convertAndSend(MAIL_EXCHANGE, MAIL_CODE_GENERATE_KEY,
				new PasswordCodeRegeneratedEvent(user.getEmail(), user.getUsername(), resetCode));

	}

	public User getUserByEmail(String username) {
		return userRepository.findByUsername(username).or(() -> userRepository.findByEmail(username))
				.orElseThrow(UserNotFoundException::new);
	}

	@Transactional
	public void logout(String token) {
		var storedToken = tokenRepository.findByToken(token).orElse(null);
		if (storedToken != null && !storedToken.isExpired()) {
			storedToken.setExpired(true);
			storedToken.setRevoked(true);
			tokenRepository.save(storedToken);
		} else {
			throw new InvalidTokenException(Messages.ALREADY_LOGGED_OUT);
		}
	}

	@Transactional
	public AuthResponse refreshToken(HttpServletRequest request) {
		final String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new InvalidTokenException(Messages.INVALID_REFRESH_TOKEN);
		}

		final String refreshToken = authHeader.substring(7);
		final String username = jwtService.extractUsername(refreshToken);

		if (username == null) {
			throw new InvalidTokenException(Messages.COULD_NOT_EXTRACT_USER);
		}

		User user = getUserByEmail(username);

		if (!jwtService.validateToken(refreshToken, user)) {
			throw new InvalidTokenException(Messages.INVALID_REFRESH_TOKEN);
		}

		String accessToken = jwtService.generateToken(user);
		jwtService.revokeAllUserTokens(user);
		jwtService.saveUserToken(user, accessToken);
		return new AuthResponse(accessToken, refreshToken);
	}

	@Transactional
	public void logoutByRequest(HttpServletRequest request) {
		final String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new InvalidTokenException(Messages.TOKEN_NOT_FOUND);
		}
		String token = authHeader.substring(7);
		logout(token);
	}

	private String generateConfirmationCode() {
		Random random = new Random();
		int code = 1000 + random.nextInt(90000);
		return String.valueOf(code);
	}

}