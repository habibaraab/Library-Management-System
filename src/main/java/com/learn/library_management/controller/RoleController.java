package com.learn.library_management.controller;

import com.learn.library_management.config.Messages;
import com.learn.library_management.dto.BasicResponse;
import com.learn.library_management.dto.RoleCreateDTO;
import com.learn.library_management.dto.RoleDTO;
import com.learn.library_management.dto.RoleResponseDTO;
import com.learn.library_management.service.RoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Role Controller", description = "API for managing user roles.")
@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "Create a new role", description = "Allows admin to create a new role (name will be uppercased)")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BasicResponse> createRole(@RequestBody RoleCreateDTO dto) {
        RoleResponseDTO response = roleService.createRole(dto);
        return ResponseEntity.ok(new BasicResponse(Messages.ADD_ROLE, response));
    }

    @Operation(summary = "Get all roles", description = "Retrieve a list of all roles")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BasicResponse> getAllRoles() {
        List<RoleDTO> response = roleService.getAllRoles();
        return ResponseEntity.ok(new BasicResponse(Messages.FETCH_ROLES, response));
    }
}
