package com.spring.libarary_management_system.Repository;

import com.spring.libarary_management_system.Entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
    @Query("""
    	    SELECT t FROM Token t WHERE t.user.id = :userId 
    	""")
    List<Token> findAllValidTokenByUser(Long userId);
}
