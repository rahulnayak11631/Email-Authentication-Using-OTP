package com.authentication.login.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.login.Model.UserModel;
import com.authentication.login.Service.UserService;


@RestController
// @RequestMapping("api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public String SignUp(@RequestBody UserModel user) {
        return userService.signUp(user);
        
    }

    @PostMapping("/auth")
    public boolean auth(@RequestBody UserModel user) {
        return userService.authenticate(user.getUserName(), user.getPassword());
    }
    

    
}
