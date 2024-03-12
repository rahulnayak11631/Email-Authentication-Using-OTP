package com.authentication.login.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authentication.login.Model.EmailModel;



public interface EmailRepository extends  JpaRepository<EmailModel,UUID>{
    EmailModel findByEmail(String email);
}
