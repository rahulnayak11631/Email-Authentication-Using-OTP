package com.authentication.login.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.login.Model.EmailModel;
import com.authentication.login.Repositories.EmailRepository;
import com.authentication.login.Service.EmailAuthService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;

@RestController
public class EmailAuthController {
    @Autowired
    private EmailAuthService service;


    @Autowired
    private EmailRepository emailRepository;
    

    public EmailAuthController(EmailAuthService emailAuthService) {
        this.service = emailAuthService;
    }

    @PostMapping("/sendOTP")
    public ResponseEntity<String> sendOTP(@RequestHeader String email, HttpSession session) {
        try {
            EmailModel model = new EmailModel();
            model.setEmail(email);
            emailRepository.save(model);
            service.sendOTP(email, session);
            return ResponseEntity.ok("OTP sent to " + email);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send OTP, Please try again!");
        }

    }

    @PostMapping("/verifyOTP")
    public ResponseEntity<String> verifyOTP(@RequestHeader int OTP, HttpSession session) {
        if (service.verifyOTP(OTP, session)) {
            String email = (String) session.getAttribute("email");
            service.updateVerificationStatus(email);
            return ResponseEntity.ok("Email " + email + " verified successfully!");

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP, Please Try again");
        }
    }

}
