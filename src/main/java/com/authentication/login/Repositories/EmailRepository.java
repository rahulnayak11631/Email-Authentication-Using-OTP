package com.authentication.login.Repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authentication.login.Model.EmailModel;



public interface EmailRepository extends  JpaRepository<EmailModel,UUID>{
    EmailModel findByEmail(String email);

     List<EmailModel> findByCreatedAt(LocalDateTime createdAt);
}
