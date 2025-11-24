package com.spring.libarary_management_system.Repository;

import com.spring.libarary_management_system.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByName(String name);
    Optional<Category> findFirstByName(String name);
    List<Category> findByNameContainingIgnoreCase(String name);
    List<Category> findByParent_CategoryId(Long parentId);
    List<Category> findByParentIsNull();
}
