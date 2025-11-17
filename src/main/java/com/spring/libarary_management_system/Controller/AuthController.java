package com.spring.libarary_management_system.Controller;

import com.spring.libarary_management_system.DTOs.AuthResponse;
import com.spring.libarary_management_system.DTOs.BasicResponse;
import com.spring.libarary_management_system.DTOs.LoginRequestDTO;
import com.spring.libarary_management_system.DTOs.UserCreateDTO;
import com.spring.libarary_management_system.Exception.Messages;
import com.spring.libarary_management_system.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "Auth Controller", description = "API for user authentication and authorization (login, resst password, etc).")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @Operation(
            summary = "User login",
            description = "Authenticate user using username/email and password and return access and refresh tokens."
    )
    @PostMapping("/login")
    public ResponseEntity<BasicResponse> login(@RequestBody LoginRequestDTO loginRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));
        AuthResponse authResponse = authService.login(loginRequest);
        return ResponseEntity.ok(new BasicResponse(Messages.LOGIN_SUCCESS,authResponse));
    }

    @Operation(
            summary = "Refresh access token",
            description = "Generate a new access token using a valid refresh token from the request."
    )
    @PostMapping("/refresh-token")
    public ResponseEntity<BasicResponse> refreshToken(HttpServletRequest request) {
        AuthResponse response = authService.refreshToken(request);
        return ResponseEntity.ok(new BasicResponse(Messages.NEW_TOKEN_GENERATED, response));
    }

    @Operation(
            summary = "Logout user",
            description = "Invalidate the current refresh token to log the user out."
    )
    @PostMapping("/logout")
    public ResponseEntity<BasicResponse> logout(HttpServletRequest request) {
        authService.logoutByRequest(request);
        return ResponseEntity.ok(new BasicResponse(Messages.LOGOUT_SUCCESS));
    }

    @Operation(
            summary = "Create new user",
            description = " creates a new user account with a specific role."
    )
    @PostMapping("/register")
    public ResponseEntity<BasicResponse> createUser(@RequestBody UserCreateDTO user){
        return ResponseEntity.ok(new BasicResponse(Messages.CREATE_NEW_USER,authService.createUser(user)));
    }


}