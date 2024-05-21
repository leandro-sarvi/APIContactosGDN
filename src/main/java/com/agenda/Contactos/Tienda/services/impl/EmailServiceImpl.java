package com.agenda.Contactos.Tienda.services.impl;

import com.agenda.Contactos.Tienda.services.IEmailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.File;

@Service
@PropertySource("classpath:email.properties")
public class EmailServiceImpl implements IEmailSenderService {
    private JavaMailSender mailSender;
    private SpringTemplateEngine templateEngine;
    @Value("${email.sender}")
    private String EMAIL_SENDER;
    public EmailServiceImpl(JavaMailSender mailSender,SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }
    @Override
    public void sendEMail(String toUser, String timeStamp,String details) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Crear el contexto de Thymeleaf y establecer las variables
        Context context = new Context();
        context.setVariable("toUser", toUser);
        context.setVariable("timeStamp", timeStamp);
        context.setVariable("details", details);

        // Procesar la plantilla Thymeleaf
        String html = templateEngine.process("loginAlertTemplate", context);

        helper.setFrom(EMAIL_SENDER);
        helper.setTo(toUser);
        helper.setSubject("GDN-API: Confirmación de correo electronico");
        helper.setText(html, true);  // `true` indica que el contenido es HTML

        mailSender.send(message);
    }
// Plantilla de envio de correo de confirmacion
    @Override
    public void sendEmailConfirmation(String toUser, String token) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Crear el contexto de Thymeleaf y establecer las variables
        Context context = new Context();
        context.setVariable("toUser", toUser);
        context.setVariable("token", token);

        // Procesar la plantilla Thymeleaf
        String html = templateEngine.process("confirmEmailTemplate", context);

        helper.setFrom(EMAIL_SENDER);
        helper.setTo(toUser);
        helper.setSubject("GDN-API: Confirmación de correo electronico");
        helper.setText(html, true);  // `true` indica que el contenido es HTML

        mailSender.send(message);
    }

    @Override
    public void sendEmailWithFile(String toUser, String subjetc, String message, File file) {

    }
}
