package com.mgt.Controller;


import com.mgt.Model.User;
import com.mgt.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/user")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Integer id) {
        boolean isDeleted = userService.deleteUser(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", isDeleted ? "User deleted successfully." : "Failed to delete user.");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        User updateUser = userService.updatUser(user);
        if(updateUser != null){
            return ResponseEntity.ok(updateUser);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

