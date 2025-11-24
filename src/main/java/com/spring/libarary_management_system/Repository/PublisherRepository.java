package com.spring.libarary_management_system.Repository;

import com.spring.libarary_management_system.Entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher,Long> {
}
