package com.learn.library_management.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.learn.library_management.dto.AuthorCreateDTO;
import com.learn.library_management.dto.AuthorResponseDTO;
import com.learn.library_management.entities.Author;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

	AuthorResponseDTO toDTO(Author author);
	
    @Mapping(target = "authorId", ignore = true)
    @Mapping(target = "books", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Author toEntity(AuthorCreateDTO dto);
}
