package com.agenda.Contactos.Tienda.services;

import com.agenda.Contactos.Tienda.persistence.entities.User;
import com.agenda.Contactos.Tienda.util.Role;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

public interface IJwtUtilityService {
    String generateToken(User user) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, JOSEException;
    JWTClaimsSet parseJwt(String jwt) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, ParseException, JOSEException;
}
