package com.spring.libarary_management_system.Service;



import com.spring.libarary_management_system.DTOs.*;
import com.spring.libarary_management_system.Entity.Role;
import com.spring.libarary_management_system.Entity.User;
import com.spring.libarary_management_system.Exception.*;
import com.spring.libarary_management_system.Repository.RoleRepository;
import com.spring.libarary_management_system.Repository.TokenRepository;
import com.spring.libarary_management_system.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


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
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;

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

        // Manual mapping DTO → Entity
        User user = mapToEntity(dto);

        userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new EmailAlreadyExistsException();
        });

        userRepository.findByUsername(user.getUsername()).ifPresent(u -> {
            throw new UsernameAlreadyExistsException();
        });

        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(RoleNotFoundException::new);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(role);

        User savedUser = userRepository.save(user);

        // Manual mapping Entity → DTO
        return mapToDTO(savedUser);
    }

    public User getUserByEmail(String username) {
        return userRepository.findByUsername(username)
                .or(() -> userRepository.findByEmail(username))
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

    private User mapToEntity(UserCreateDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setPassword(dto.getPassword()); // encoding later
        return user;
    }

    private UserResponseDTO mapToDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRoleName(user.getRole().getName());
        return dto;
    }
}
