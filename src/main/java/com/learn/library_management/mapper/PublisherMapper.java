package com.learn.library_management.mapper;

import com.learn.library_management.dto.*;
import com.learn.library_management.entities.*;
import org.mapstruct.*;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PublisherMapper {

    @Mapping(target = "bookIds", source = "books", qualifiedByName = "mapBookIds")
    PublisherResponseDTO toDTO(Publisher publisher);

    @Mapping(target = "publisherId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "books", ignore = true)
    Publisher toEntity(PublisherCreateDTO dto);
    
    @Named("mapBookIds")
    default List<BookInfo> mapBookIds(List<Book> books) {
        return books.stream()
                    .map(book -> {
                        BookInfo info = new BookInfo();
                        info.setId(book.getBookId());
                        info.setTitle(book.getTitle());
                        return info;
                    })
                    .collect(Collectors.toList());
    }
}
