package com.authentication.login.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authentication.login.Model.UserModel;
import com.authentication.login.Repositories.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepository;

    public String signUp(UserModel userModel) {
        
        try {
            UserModel user = userRepository.findByUserName(userModel.getUserName());
            if(user==null){
                String hashedPassword = PasswordUtil.encodePassword(userModel.getPassword());
            userModel.setPassword(hashedPassword);
            userRepository.save(userModel);
            return "Signup Successful";
            }
            else{
                return "User already exists";
            }
            
        } catch (Exception e) {
           return e.getMessage();
        }
    }

    public boolean authenticate(String userName,String password)
    {
        UserModel userModel = userRepository.findByUserName(userName);
        if (userModel==null) {
            return false;
        }
        else
        {
            return PasswordUtil.checkPassword(password,userModel.getPassword());
        }
    } 
}