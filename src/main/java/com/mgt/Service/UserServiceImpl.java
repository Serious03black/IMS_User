package com.mgt.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mgt.Model.User;
import com.mgt.Repository.UserRepo;

import java.util.List;
import java.util.Optional;

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
	public List<User> getAllUser(){
		return  userRepo.findAll();
	}

	public boolean deleteUser(Integer id) {

		boolean status = false;
		if (id != null) {
			userRepo.deleteById(id);
			status = true;
		}
		return status;
	}

	public User updatUser(User user) {
		Optional<User> existingUser = userRepo.findById(user.getId());
		if(existingUser.isPresent()){
			return userRepo.save(user);
		}else{
			return null;
		}
	}
}
