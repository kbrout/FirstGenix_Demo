package com.firstgenix.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.firstgenix.model.security.User;
import com.firstgenix.security.JwtAuthenticationRequest;
import com.firstgenix.security.JwtUser;

/**
 * Created by stephan on 20.03.16.
 */
public interface UserRepository extends JpaRepository<User, String>{
    User findByUsername(String username);
    Optional<User> findById(String id);   
   
}
