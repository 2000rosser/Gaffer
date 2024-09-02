package com.example.gaffer.services;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.example.gaffer.models.UserEntity;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class UserMailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public UserMailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine){
        this.mailSender=mailSender;
        this.templateEngine=templateEngine;
    }

    private String getVerificationMailContent(UserEntity entity) {
        Context context = new Context();
        String verificationUrl = String.format("http://localhost:8080/verify?code=%s", entity.getVerificationCode());
        context.setVariable("applicationUrl", verificationUrl);
        return templateEngine.process("user-verify", context);
    }

    private MimeMessage createMessage(UserEntity entity, String content) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
        message.setText(content, true);
        message.setSubject("Welcome to MyApp XYZ");
        message.setFrom("noreply@myapp.xyz");
        message.setTo(entity.getUsername());
        return mimeMessage;
    }

    public void sendVerificationMail(UserEntity entity) {
        String content = getVerificationMailContent(entity);
        try {
            mailSender.send(createMessage(entity, content));
        } catch (MessagingException ex) {
            // throw new Exception("Could not send e-mail to verify user with e-mail '" + entity.getUsername() + "'", ex);
            System.out.println("failed to send message" + ex.toString());
        }
    }
}