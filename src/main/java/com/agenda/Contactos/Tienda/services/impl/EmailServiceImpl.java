package com.agenda.Contactos.Tienda.services.impl;

import com.agenda.Contactos.Tienda.services.IEmailSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@PropertySource("classpath:email.properties")
public class EmailServiceImpl implements IEmailSenderService {
    private JavaMailSender mailSender;
    @Value("${email.sender}")
    private String EMAIL_SENDER;
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Override
    public void sendEMail(String toUser, String subjetc, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("leandrosarvi@gmail.com");
        mailMessage.setTo(toUser);
        mailMessage.setSubject(subjetc);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }
    @Override
    public void sendEmailWithFile(String toUser, String subjetc, String message, File file) {

    }
}
