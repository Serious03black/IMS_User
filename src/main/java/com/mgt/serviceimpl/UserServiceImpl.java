package com.mgt.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mgt.model.User;
import com.mgt.repository.UserRepo;

@Service
public class UserServiceImpl {

	@Autowired
	private UserRepo userRepo;

	public User addUser(User user) {
		return userRepo.save(user);
	}

	public User loginUserName(String email, String password) {
		User validUser = userRepo.findByEmail(email);
		if (validUser != null && validUser.getPassword().matches(password)) {
			return validUser;
		}
		return null;
	}

	public User loginUserEmail(String email, String password) {
		User validUser = userRepo.findByEmail(email);
		if (validUser != null && validUser.getPassword().matches(password)) {
			return validUser;
		}
		return null;
	}

	public User findById(Long id) {
        Optional<User> user = userRepo.findById(id);
        return user.orElse(null);  // Return the user if present, otherwise null
    }

	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}

}
