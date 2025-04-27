package com.mgt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mgt.model.User;



@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    
    static Optional<User> findById(Long id) {
      
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }



   
    
}
