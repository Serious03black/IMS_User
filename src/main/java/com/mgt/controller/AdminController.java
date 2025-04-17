package com.mgt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mgt.model.Admin;
import com.mgt.model.Status;
import com.mgt.model.User;
import com.mgt.serviceimpl.AdminService;
import com.mgt.serviceimpl.UserServiceImpl;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

	@Autowired
	private UserServiceImpl userServiceImpl;

	// View All Users
	@GetMapping("/users")
	public List<User> getAllUser() {
		return userServiceImpl.getAllUser();
	}

	@PutMapping("/users/{id}/status")
	public ResponseEntity<User> updateUserStatus(@PathVariable("id") Long id, @RequestParam("status") Status status) {
	    User user = userServiceImpl.findById(id);

	    user.setStatus(status);
	    userServiceImpl.addUser(user);

	    return ResponseEntity.ok(user);
	}
}
