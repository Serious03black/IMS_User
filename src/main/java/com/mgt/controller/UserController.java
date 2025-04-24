package com.mgt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.HTML;

import com.mgt.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mgt.model.Status;
import com.mgt.model.User;
import com.mgt.serviceimpl.UserServiceImpl;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private UserRepo userRepo;

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		if (user.getStatus() == null) {
	        user.setStatus(Status.PENDING);  // force set if null
	    }
		User newUser = userService.addUser(user);
		return ResponseEntity.ok(newUser);
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> loginUser(@RequestBody User u) {
	    Map<String, Object> response = new HashMap<String, Object>();

	    System.out.println(u.getEmail());
	    System.out.println(u.getPassword()); 

	    // Validate user by email and password
	    User user = userService.loginUserEmail(u.getEmail(), u.getPassword());

	    if (user != null && user.getStatus() == Status.ACTIVE) {
	        response.put("message", "Login Successfully");
			response.put("user_id" , user.getId());
			response.put("name" , user.getFull_name());
	        response.put("store_type", user.getStore_type()); // Include store_type in response
	        System.out.println("Login Successfully with store_type: " + user.getStore_type());
	        return ResponseEntity.ok(response);
	    }
      else if (user != null && user.getStatus() == Status.PENDING) {
        response.put("message", "Your account is Pending for Admin approval.");
        System.out.println("Inactive user tried to log in.");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response); 
      }
      else if (user != null && user.getStatus() == Status.REJECTED) {
        response.put("message", "Your account has been Rejected");
        System.out.println("Rejected User Tried to loin");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
      }
      else {
	        response.put("message", "Invalid email or password");
	        System.out.println("Invalid email or password");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	    }
	}

	@GetMapping("/getAllUser")
	public List<User> getUser(){
		return userRepo.findAll();
	}



}