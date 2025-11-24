package com.spring.libarary_management_system.Repository;

import com.spring.libarary_management_system.Entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
