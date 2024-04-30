package com.agenda.Contactos.Tienda.services;

import java.io.File;

public interface IEmailSenderService {
void sendEMail(String toUser, String subjetc, String message);
void sendEmailWithFile(String toUser, String subjetc, String message, File file);
}
