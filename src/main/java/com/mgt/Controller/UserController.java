package com.mgt.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgt.Model.User;
import com.mgt.Service.UserServiceImpl;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@GetMapping("message")
	public String getMsg() {
		return "Hi From Server";
	}

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) {
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

		if (user != null) {
			response.put("message", "Login Successfully");
//			response.put("store_type", user.getStore_type()); // Fetch store type from DB
			System.out.println("Login Successfully");
			return ResponseEntity.ok(response);
		} else {
			response.put("message", "Invalid email or password");
			System.out.println("Invalid email or password");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}
	}
}