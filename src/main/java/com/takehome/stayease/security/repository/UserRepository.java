package com.takehome.stayease.security.repository;

import com.takehome.stayease.security.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    
    Optional<Users> findByEmail(String email);
}
