package com.agenda.Contactos.Tienda.config.MailConfiguration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:email.properties")
public class MailConfiguration {
    @Value("${email.sender}")
    private String EMAIL_SENDER;
    @Value("${email.sender.password}")
    private  String EMAIl_SENDER_PASSWORD;
    @Value("${email.host}")
    private String HOST;
    @Value("${email.port}")
    private int PORT;
    @Bean
    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties props = new Properties();

        mailSender.setHost(HOST);
        mailSender.setPort(PORT);//  Puerto SMTP
        mailSender.setUsername(EMAIL_SENDER);
        mailSender.setPassword(EMAIl_SENDER_PASSWORD);
        //Props
        props.put("mail.transport.protocol","smtp");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug","true");
        //Set properties
        mailSender.setJavaMailProperties(props);

        return mailSender;
    }
}
