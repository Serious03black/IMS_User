package com.mgt.Service;

import com.mgt.Model.User;
import com.mgt.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public User addUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public User loginUser(String email, String password) {
        User validUser = userRepo.findByEmail(email);
        if (validUser != null && validUser.getPassword().matches(password)) {
            return validUser;
        }
        return null;
    }

    @Override
    public boolean updateProfile(User user) {
        boolean status = false;
        /*
        try {
            userRepo.save(user);
            status = true;
        }catch(Exception e){
            e.printStackTrace();
            status = false;
        }
        */

        if (user != null) {
            userRepo.save(user);
            status = true;
        } else {
            status = false;
        }
        return status;

    }

    @Override
    public User forgetPass(String email, String oldPassword, String newPassword) {

        User validUser = userRepo.findByEmail(email);
        if(validUser != null && validUser.getPassword().matches(oldPassword)){
            validUser.setPassword(newPassword);   // set new password to old password
            return userRepo.save(validUser);      // save the update user
        }
        return null;   //  return null if email and password not found
    }

}
