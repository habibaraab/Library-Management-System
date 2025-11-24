package com.spring.libarary_management_system.Repository;

import com.spring.libarary_management_system.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
