package com.mgt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.mgt.jwtServices.JwtService;
import com.mgt.model.AuthRequest;
import com.mgt.model.User;
import com.mgt.model.UserInfoService;



@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	@Autowired
	private UserInfoService service;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome this endpoint is not secure";
	}

	@PostMapping("/register")
	public String addNewUser(@RequestBody User userInfo) {
		return service.addUser(userInfo);
	}

	// Removed the role checks here as they are already managed in SecurityConfig

	@PostMapping("/login")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUsername());
		} else {
			throw new UsernameNotFoundException("Invalid user request!");
		}
	}

	// Endpoint accessible to users with ROLE_USER
	@GetMapping("/user/userProfile")
	public String userProfile() {
		return "Welcome to the USER profile!";
	}

	// Endpoint accessible to users with ROLE_ADMIN
	@GetMapping("/admin/adminProfile")
	public String adminProfile() {
		return "Welcome to the ADMIN profile!";
	}

	 @GetMapping("/testLanding")
    public String afterLogin(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7); // Remove "Bearer " prefix
        Long userId = jwtService.extractUserId(token);
        return "Welcome to login user " + userId;
    }
	
}