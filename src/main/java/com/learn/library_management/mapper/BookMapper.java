package com.learn.library_management.mapper;

import com.learn.library_management.dto.BookCreateDTO;
import com.learn.library_management.dto.BookResponseDTO;
import com.learn.library_management.entities.Author;
import com.learn.library_management.entities.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "publisherId", source = "publisher.publisherId")
    @Mapping(target = "publisherName", source = "publisher.name")
    @Mapping(target = "categoryId", source = "category.categoryId")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "authorNames", source = "authors", qualifiedByName = "mapAuthorsToNames")
    BookResponseDTO toDTO(Book book);
    
    @Mapping(target = "bookId", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "coverImage", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Book toEntity(BookCreateDTO dto);

    @Named("mapAuthorsToNames")
    default List<String> mapAuthorsToNames(List<Author> authors) {
        return authors.stream()
                .map(a -> a.getName())
                .collect(Collectors.toList());
    }
}
