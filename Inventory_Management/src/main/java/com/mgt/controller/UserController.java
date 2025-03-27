package com.mgt.controller;

import com.mgt.model.User;
import com.mgt.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

        // Validate user by email and password
        User user = userService.loginUserEmail(u.getEmail(), u.getPassword());

        if (user != null) {
            response.put("message", "Login Successfully");
            response.put("store_type", user.getStore_type()); // Fetch store type from DB
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}