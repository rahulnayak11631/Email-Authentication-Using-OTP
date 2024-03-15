package com.authentication.login.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.authentication.login.Model.EmailModel;
import com.authentication.login.Repositories.EmailRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;

@Service
public class EmailAuthService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private JavaMailSender javaMailSender; // Autowire JavaMailSender

    @Value("${spring.mail.username}")
    private String sender;

    private int generateOTP() {
        Random random = new Random();
        int min = 100000;
        int max = 999999;
        return random.nextInt(max - min + 1) + min;
    }

    @Scheduled(fixedRate = 120000)
    public void deleteExpiredRecords() {
        LocalDateTime expiryTime = LocalDateTime.now().minusMinutes(2).truncatedTo(ChronoUnit.MINUTES);
        List<EmailModel> expiredRecords = emailRepository.findByCreatedAt(expiryTime);
        if (expiredRecords.size() != 0) {
            emailRepository.deleteAll(expiredRecords);
        }
    }

    public void sendOTP(String email) throws MessagingException {
        try {
            int OTP = generateOTP();
            EmailModel otp = new EmailModel();

            otp.setOTP(OTP);
            otp.setEmail(email);
            otp.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));

            emailRepository.save(otp);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("OTP for Email Authentication");
            helper.setFrom(sender);
            helper.setText("Your OTP is " + OTP);
            javaMailSender.send(message);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String verifyOTP(int otp, String email) {
        EmailModel otpModel = emailRepository.findByEmail(email);
        if (otpModel != null) {
            int otpFromDB = otpModel.getOTP();
            if (otp == otpFromDB) {
                return "OTP verified";
            } else {
                return "OTP not verified";
            }
        } else {
            return "OTP not verified";
        }

    }

}
