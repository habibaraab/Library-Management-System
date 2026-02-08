package com.learn.library_management.mapper;

import com.learn.library_management.dto.*;
import com.learn.library_management.entities.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roleName", source = "role.name")
    UserResponseDTO toDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "requestCode", ignore = true)
    @Mapping(target = "role", ignore = true) 
    User toEntity(UserCreateDTO dto);
}
