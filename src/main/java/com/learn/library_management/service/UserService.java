package com.learn.library_management.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.learn.library_management.config.Messages;
import com.learn.library_management.dto.UpdateUserRoleDTO;
import com.learn.library_management.dto.UserResponseDTO;
import com.learn.library_management.dto.UserUpdateDTO;
import com.learn.library_management.exception.EmailAlreadyExistsException;
import com.learn.library_management.exception.RoleNotFoundException;
import com.learn.library_management.exception.UserNotFoundException;
import com.learn.library_management.exception.UsernameAlreadyExistsException;
import com.learn.library_management.mapper.UserMapper;
import com.learn.library_management.entities.User;
import com.learn.library_management.entities.Role;
import com.learn.library_management.repository.RoleRepository;
import com.learn.library_management.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Validated
public class UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final RoleRepository roleRepository;
	private final ImageService imageService;
	private final AuthService authService;

	@Transactional
	@CachePut(value = "users", key = "#userId")
	public UserResponseDTO updateUser(Long userId, @Valid UserUpdateDTO dto, String token) {
		User existingUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());

		existingUser.setPhone(dto.getPhone());
		existingUser.setUsername(dto.getUsername());

		userRepository.findByUsername(dto.getUsername()).filter(user -> !user.getId().equals(userId))
				.ifPresent(user -> {
					throw new UsernameAlreadyExistsException();
				});

		if (!existingUser.getEmail().equals(dto.getEmail())) {
			userRepository.findByEmail(dto.getEmail()).ifPresent(u -> {
				throw new EmailAlreadyExistsException();
			});
			authService.logout(token);
			existingUser.setEmail(dto.getEmail());
		}

		User updatedUser = userRepository.save(existingUser);

		return userMapper.toDTO(updatedUser);
	}

	@Transactional
	@CachePut(value = "users", key = "#userId")
	public UserResponseDTO updateUserRole(Long userId, @Valid UpdateUserRoleDTO dto) {
		User existingUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
		Role role = roleRepository.findById(dto.getRoleId()).orElseThrow(() -> new RoleNotFoundException());

		existingUser.setRole(role);

		User updatedUser = userRepository.save(existingUser);
		return userMapper.toDTO(updatedUser);
	}

	@Transactional
	@CachePut(value = "users", key = "#userId")
	public UserResponseDTO updateUserImage(Long userId, MultipartFile image) throws Exception {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
		if (image == null || image.isEmpty()) {
			throw new IllegalArgumentException(Messages.EMPTY_IMAGE);
		}
		try {
			String imageUrl = imageService.uploadImage(image);
			user.setImage(imageUrl);
			userRepository.save(user);
		} catch (Exception e) {
			throw new RuntimeException(Messages.UPLOAD_IMAGE_FAILED + e.getMessage(), e);
		}

		User updatedUser = userRepository.save(user);

		return userMapper.toDTO(updatedUser);
	}

	@Cacheable(value = "users", key = "#userId")
	public UserResponseDTO findUserById(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
		return userMapper.toDTO(user);
	}

	@CacheEvict(value = "users", allEntries = true)
	public Page<UserResponseDTO> findAllUsers(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<User> usersPage = userRepository.findAll(pageable);

		return usersPage.map(userMapper::toDTO);
	}

	@Transactional
	@CacheEvict(value = "users", key = "#userId")
	public void deleteUser(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
		userRepository.delete(user);
	}
	
	@Transactional
	@Cacheable(value = "users", key = "'title_' + #title")
	public List<UserResponseDTO> searchUserByUsername(String title) {
		return userRepository.findByUsernameContainingIgnoreCase(title).stream().map(userMapper::toDTO)
				.collect(Collectors.toList());
	}
}