package com.authentication.login.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authentication.login.Model.UserModel;


public interface UserRepo extends  JpaRepository<UserModel,UUID>{
    UserModel findByUserName(String userName);
}
