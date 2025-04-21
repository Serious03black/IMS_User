package com.mgt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mgt.model.Status;
import com.mgt.model.User;
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
  @PutMapping("/approve/{id}")
    public ResponseEntity<Map<String, Object>> approveUser(@PathVariable("id") Integer id) {
        User user = userServiceImpl.findById(id);
        if (user == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        user.setStatus(Status.ACTIVE); // Change status to ACTIVE
        userServiceImpl.addUser(user);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User approved and can now login.");
        return ResponseEntity.ok(response);
    }

    // Admin reject endpoint
    @PutMapping("/reject/{id}")
    public ResponseEntity<Map<String, Object>> rejectUser(@PathVariable("id") Integer id) {
        User user = userServiceImpl.findById(id);
        if (user == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        user.setStatus(Status.REJECTED); // Change status to REJECTED
        userServiceImpl.addUser(user);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "User rejected.");
        return ResponseEntity.ok(response);
    }
}