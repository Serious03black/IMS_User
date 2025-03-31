package com.mgt.service;

import com.mgt.model.User;
import com.mgt.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepo userRepo;

    public User addUser(User user) {
        return userRepo.save(user);
    }
/*
    public User loginUserName(String email, String password) {
        User validUser = userRepo.findByEmail(email);
        if (validUser != null && validUser.getPassword().matches(password)) {
            return validUser;
        }
        return null;
    }
*/
    public User loginUserEmail(String email, String password) {
        User validUser = userRepo.findByEmail(email);
        if (validUser != null && validUser.getPassword().matches(password)) {
            return validUser;
        }
        return null;
    }


}