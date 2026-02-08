package com.learn.library_management.controller;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.learn.library_management.config.Messages;
import com.learn.library_management.dto.BasicResponse;
import com.learn.library_management.dto.UpdateUserRoleDTO;
import com.learn.library_management.dto.UserUpdateDTO;
import com.learn.library_management.dto.UserResponseDTO;
import com.learn.library_management.entities.User;
import com.learn.library_management.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "User Controller", description = "API for managing user profiles and account settings.")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@Operation(summary = "Admin update user Role", description = "Allows admin to update Role of a specific user by ID")
	@PutMapping("/admin/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BasicResponse> adminUpdateUserRole(@PathVariable Long userId,
			@RequestBody UpdateUserRoleDTO dto) {
		UserResponseDTO response = userService.updateUserRole(userId, dto);
		return ResponseEntity.ok(new BasicResponse(Messages.UPDATE_USER, response));
	}

	@Operation(summary = "Update own user profile", description = "Allows authenticated users to update their profile information")
	@PutMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<BasicResponse> updateUser(@AuthenticationPrincipal User user, @RequestBody UserUpdateDTO dto,
			@RequestHeader("Authorization") String authHeader) {
		Long userId = user.getId();
		String token = authHeader.replace("Bearer ", "");
		UserResponseDTO response = userService.updateUser(userId, dto, token);
		return ResponseEntity.ok(new BasicResponse(Messages.UPDATE_USER, response));
	}

	@Operation(summary = "Update profile image", description = "Allows authenticated users to update their profile image")
	@PutMapping("/image")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<BasicResponse> updateUserImage(@AuthenticationPrincipal User user,
			@RequestPart("image") MultipartFile image) throws Exception {
		Long userId = user.getId();
		userService.updateUserImage(userId, image);
		return ResponseEntity.ok(new BasicResponse(Messages.USER_UPDATE_IMAGE));
	}

	@Operation(summary = "Admin get user by ID", description = "Allows admin to retrieve full user details by ID")
	@GetMapping("/admin/view/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BasicResponse> adminFindUserById(@PathVariable Long userId) {
		return ResponseEntity.ok(new BasicResponse(Messages.FETCH_USER ,userService.findUserById(userId)));
	}

	@Operation(summary = "Get own profile", description = "Returns the profile information of the currently authenticated user")
	@GetMapping("/profile")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<BasicResponse> getUserById(@AuthenticationPrincipal User user) {
	    UserResponseDTO userDTO = userService.findUserById(user.getId());
	    return ResponseEntity.ok(new BasicResponse(Messages.FETCH_USER, userDTO));
	}

	@Operation(summary = "Admin get all users", description = "Retrieves a paginated list of all users in the system (admin only)")
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BasicResponse> getAllUsers(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
	    Page<UserResponseDTO> users = userService.findAllUsers(page, size);
	    return ResponseEntity.ok(new BasicResponse(Messages.FETCH_USERS, users));
	}

	@Operation(summary = "Admin delete user", description = "Allows admin to delete a user account by ID")
	@DeleteMapping("/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BasicResponse> deleteUser(@PathVariable Long userId) {
		userService.deleteUser(userId);
		return ResponseEntity.ok(new BasicResponse(Messages.DELETE_USER));
	}

    @Operation(summary = "Search users by username", description = "Find users that contain a given name")
    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> searchUserByUsername(@RequestParam String title) {
        List<UserResponseDTO> response = userService.searchUserByUsername(title);
        return ResponseEntity.ok(new BasicResponse(Messages.SEARCH_USER, response));
    }
}