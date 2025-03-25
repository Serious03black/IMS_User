package com.mgt.Controller;

import com.mgt.Model.User;
import com.mgt.Repository.UserRepo;
import com.mgt.Service.UserService;
import com.mgt.dto.LoginRequest;
import com.mgt.dto.ResetPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User newUser = userService.addUser(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginRequest loginRequest) {
        //System.out.println("Email: " + loginRequest.getEmail());
        //System.out.println("Password: " + loginRequest.getPassword());
        User user = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());

        if (user != null) {
            return "Login Successfully";
        } else {
            return "Login Failed ... Plz Enter valid email and password";
        }
    }

    @PutMapping("/update")
    public String updatePro(@RequestBody User user){
        /*
        System.out.println("Name : " + user.getName());
        System.out.println("Phone No :" + user.getPhone_no());
        System.out.println("Email : " + user.getEmail());
        System.out.println("Password : " + user.getPassword());
        */
        boolean result = userService.updateProfile(user);
        if(result) {
            return "Profile update successfully";
        }else{
            return "Profile does not update";
        }
    }

    @PostMapping("/forgetPassword")
    public String resetPass(@RequestBody ResetPassword resetPassword){

        System.out.println("Email : " + resetPassword.getEmail());
        System.out.println("Old Password : " + resetPassword.getNewPassword());
        System.out.println("New Password : " + resetPassword.getConfirmPassword());

        User user = userService.forgetPass(resetPassword.getEmail() , resetPassword.getNewPassword() , resetPassword.getConfirmPassword());

        if(user != null){
            return "Password updated successfully";
        }
        return "Invalid email or password";
    }
}
