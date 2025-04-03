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

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        System.out.println(user.getFull_name());
        User newUser = userService.addUser(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<String, Object>();

        //System.out.println(user.getEmail());
       // System.out.println(user.getPassword());

        // Validate user by email and password
        User loginUser = userService.loginUserEmail(user.getEmail(), user.getPassword());

        if (loginUser != null) {
            response.put("message", "Login Successfully");
           // response.put("store_type", user.getStore_type()); // Fetch store type from DB
            System.out.println("Login Successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid email or password");
            System.out.println("Invalid Email and Password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
