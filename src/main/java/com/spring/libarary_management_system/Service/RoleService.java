package com.spring.libarary_management_system.Service;

import com.spring.libarary_management_system.DTOs.RoleCreateDTO;
import com.spring.libarary_management_system.DTOs.RoleDTO;
import com.spring.libarary_management_system.DTOs.RoleResponseDTO;
import com.spring.libarary_management_system.Entity.Role;
import com.spring.libarary_management_system.Exception.RoleAlreadyExistsException;
import com.spring.libarary_management_system.Mapper.RoleMapper;
import com.spring.libarary_management_system.Repository.RoleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

        Role role = Role.builder().name(upperName).build();

        Role saved = roleRepository.save(role);

        return roleMapper.toDTO(saved);
    }

    public List<RoleDTO> getAllRoles() {
        return roleMapper.toDTOList(roleRepository.findAll());
    }
}
