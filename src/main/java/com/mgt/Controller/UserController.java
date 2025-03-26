package com.mgt.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
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

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		User newUser = userService.addUser(user);
		return ResponseEntity.ok(newUser);
	}

	@PostMapping("/login")
	public String loginUser(@RequestBody User u) {
		User user = null;

		// Check if username is provided and try login with username
		if (u.getFull_name() != null && !u.getFull_name().isEmpty()) {
			user = userService.loginUserName(u.getFull_name(), u.getPassword());
		}
		// If username not provided or login failed, try with email
		if (user == null && u.getEmail() != null && !u.getEmail().isEmpty()) {
			user = userService.loginUserEmail(u.getEmail(), u.getPassword());
		}

		if (user != null) {
			return "Login Successfully";
		} else {
			return "Login Failed ... Please enter valid username/email and password";
		}
	}


//	@PostMapping("/forgetPassword")
//	public String resetPass(@RequestBody ResetPassword resetPassword) {
//
//		System.out.println("Email : " + resetPassword.getEmail());
//		System.out.println("Old Password : " + resetPassword.getNewPassword());
//		System.out.println("New Password : " + resetPassword.getConfirmPassword());
//
//		User user = userService.forgetPass(resetPassword.getEmail(), resetPassword.getNewPassword(),
//				resetPassword.getConfirmPassword());
//
//		if (user != null) {
//			return "Password updated successfully";
//		}
//		return "Invalid email or password";
//	}
}
