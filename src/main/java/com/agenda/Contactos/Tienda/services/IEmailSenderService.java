package com.agenda.Contactos.Tienda.services;

import jakarta.mail.MessagingException;

import java.io.File;

public interface IEmailSenderService {
void sendEMail(String toUser, String timeStamp,String details) throws MessagingException;
void sendEmailConfirmation(String toUser, String token) throws MessagingException;
void sendEmailWithFile(String toUser, String subjetc, String message, File file);
}
