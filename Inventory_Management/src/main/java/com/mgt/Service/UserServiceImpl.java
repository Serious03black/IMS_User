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
}
