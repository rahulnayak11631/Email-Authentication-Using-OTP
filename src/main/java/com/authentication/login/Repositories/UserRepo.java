package com.authentication.login.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authentication.login.Model.UserModel;
import java.util.List;


public interface UserRepo extends  JpaRepository<UserModel,UUID>{
    List<UserModel> findByUserName(String userName);
}
