package com.authentication.login.Service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.authentication.login.Model.EmailModel;
import com.authentication.login.Repositories.EmailRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;

@Service
public class EmailAuthService {

    @Autowired
    private EmailRepository EmailRepository;

    @Autowired
    private JavaMailSender javaMailSender; // Autowire JavaMailSender

    @Value("${spring.mail.username}")
    private String sender;
    @Value("${spring.mail.password}")
    private String password;


    private int generateOTP() {
        Random random = new Random();
        int min = 100000;
        int max = 999999;
        return random.nextInt(max - min + 1) + min;
    }

    public void sendOTP(String email, HttpSession session) throws MessagingException {
        try {
            int OTP = generateOTP();
            session.setAttribute("email", email);
            session.setAttribute("OTP", OTP);
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("OTP for Email Authentication");
            helper.setFrom(sender);
            helper.setText("Your OTP is " + OTP);
            javaMailSender.send(message);
        } catch (Exception e) {
            // TODO: handle exception
            // System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean verifyOTP(int OTP, HttpSession session) {
        int sessionOTP = (int) session.getAttribute("OTP");
        if (sessionOTP!=0 && (sessionOTP==OTP)) {
            return true;
        } else {
            return false;
        }

    }

    public void updateVerificationStatus(String email) {
        try {
            EmailModel model = EmailRepository.findByEmail(email);
            if (model != null) {
                model.setVerified(true);
                model.setEmail(email);
                EmailRepository.save(model);
            }
            else
            {
                System.out.println("Lowda");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
