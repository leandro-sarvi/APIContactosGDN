package com.agenda.Contactos.Tienda.services;

import com.agenda.Contactos.Tienda.dto.UserDto;
import com.agenda.Contactos.Tienda.dto.LoginDto;
import com.agenda.Contactos.Tienda.dto.ResponseDto;
import com.nimbusds.jose.JOSEException;
import jakarta.mail.MessagingException;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

public interface IAuthService {
    HashMap<String,String> login(LoginDto loginDto) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, JOSEException, MessagingException;
     ResponseDto register(UserDto userDto) throws Exception;
     ResponseDto deleteUser(Long id,String password);
     HashMap<String,String> confirmEmail(String tokenConfirm);
     Authentication sessionVerifier();
}
