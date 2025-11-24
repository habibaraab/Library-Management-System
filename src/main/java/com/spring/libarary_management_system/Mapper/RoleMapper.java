package com.spring.libarary_management_system.Mapper;



import com.spring.libarary_management_system.DTOs.RoleDTO;
import com.spring.libarary_management_system.DTOs.RoleResponseDTO;
import com.spring.libarary_management_system.Entity.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponseDTO toDTO(Role role);

    List<RoleDTO> toDTOList(List<Role> roles);

}