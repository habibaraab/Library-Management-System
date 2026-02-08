package com.learn.library_management.repository;

import com.learn.library_management.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByCategory_CategoryId(Long categoryId);
    Optional<Book> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);
}
