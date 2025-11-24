package com.spring.libarary_management_system.Repository;

import com.spring.libarary_management_system.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByCategory_CategoryId(Long categoryId);
    Optional<Book> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);
}
