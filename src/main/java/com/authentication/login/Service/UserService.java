package com.authentication.login.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authentication.login.Model.UserModel;
import com.authentication.login.Repositories.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepository;

    public void signUp(UserModel userModel) {
        
        try {
            userRepository.save(userModel);
        } catch (Exception e) {
           System.out.println(e.getMessage());
        }
    }

    public boolean authenticate(String userName,String password)
    {
        List<UserModel> userModel = userRepository.findByUserName(userName);
        if (userModel.isEmpty()) {
            return false;
        }
        else
        {
            if (password.equals(userModel.get(0).getPassword())) {
                return true;
            }
            else
            {
                return false;
            }
        }
    } 
}