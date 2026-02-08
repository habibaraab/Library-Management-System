package com.learn.library_management.mapper;

import com.learn.library_management.dto.UserActivityLogViewDTO;
import com.learn.library_management.entities.UserActivityLog;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserActivityLogMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    UserActivityLogViewDTO toDTO(UserActivityLog log);

    @Mapping(target = "logId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "user", ignore = true) 
    UserActivityLog toEntity(UserActivityLogViewDTO dto);
}
