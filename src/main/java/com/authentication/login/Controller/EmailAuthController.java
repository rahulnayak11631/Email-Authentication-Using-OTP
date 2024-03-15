package com.authentication.login.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.login.Service.EmailAuthService;

import jakarta.mail.MessagingException;

@RestController
public class EmailAuthController {
    @Autowired
    private EmailAuthService service;

    public EmailAuthController(EmailAuthService emailAuthService) {
        this.service = emailAuthService;
    }

    @PostMapping("/sendOTP")
    public ResponseEntity<String> sendOTP(@RequestHeader String email) {
        try {
            service.sendOTP(email);
            return ResponseEntity.ok("OTP sent to " + email);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send OTP, Please try again!");
        }

    }

    @PostMapping("/verifyOTP")
    public String verifyOTP(@RequestHeader int OTP, @RequestHeader String email) {
        return service.verifyOTP(OTP, email);
    }

}
