package com.mgt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mgt.model.User;



@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @SuppressWarnings("null")
    Optional<User> findById(int userId);

    boolean existsByEmail(String email);


    
}
