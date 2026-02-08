package com.learn.library_management.repository;

import com.learn.library_management.dto.RoleDTO;
import com.learn.library_management.dto.RoleResponseDTO;
import com.learn.library_management.entities.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
	
   // RoleDTO toDTO(Role role);
    RoleResponseDTO toDTO(Role role);

    List<RoleDTO> toDTOList(List<Role> roles);
    
}