package com.learn.library_management.service;

import com.learn.library_management.dto.RoleCreateDTO;
import com.learn.library_management.dto.RoleDTO;
import com.learn.library_management.dto.RoleResponseDTO;
import com.learn.library_management.entities.Role;
import com.learn.library_management.exception.RoleAlreadyExistsException;
import com.learn.library_management.repository.RoleMapper;
import com.learn.library_management.repository.RoleRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class RoleService {

	private final RoleRepository roleRepository;
	private final RoleMapper roleMapper;

	public RoleResponseDTO createRole(@Valid RoleCreateDTO dto) {
		String upperName = dto.getName().toUpperCase();

		if (roleRepository.existsByName(upperName)) {
			throw new RoleAlreadyExistsException();
		}

		Role role = Role.builder().name(upperName).description(dto.getDescription()).build();

		Role saved = roleRepository.save(role);

		return roleMapper.toDTO(saved);
	}

	@Cacheable(value = "roles")
	public List<RoleDTO> getAllRoles() {
		return roleMapper.toDTOList(roleRepository.findAll());
	}
}
