package com.agenda.Contactos.Tienda.services.impl;

import com.agenda.Contactos.Tienda.persistence.entities.User;
import com.agenda.Contactos.Tienda.services.IJwtUtilityService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;
@Service
public class JwtUtilityService implements IJwtUtilityService {
    @Value("classpath:jwtKeys/private_key.pem")
    private Resource privateKeyResource;
    @Value("classpath:jwtKeys/public_key.pem")
    private Resource publicKeyResource;
    @Value("${expiration.token}")
    private int EXPIRATION_TOKEN;
    @Override
    public String generateToken(User user) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, JOSEException {
        // Cargar la clave privada
        PrivateKey privateKey = loadPrivateKey(privateKeyResource);
        // Crear las reclamaciones (claims)
        Date now = new Date();
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .claim("role", user.getRole())
                .issueTime(now)
                .expirationTime(new Date(now.getTime() + EXPIRATION_TOKEN))
                .build();

        // Creamos el encabezado (Header) donde especificamos el algoritmo RS256
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(new JOSEObjectType("JWT"))
                .build();

        // Crear el JWT firmado
        SignedJWT signedJWT = new SignedJWT(header, claimsSet);

        // Firmar el JWT con la clave privada
        JWSSigner signer = new RSASSASigner(privateKey);
        signedJWT.sign(signer);

        // Serializar el JWT a una cadena de caracteres
        return signedJWT.serialize();
    }

    @Override
    public JWTClaimsSet parseJwt(String jwt) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, ParseException, JOSEException {
        PublicKey publicKey = loadPublicKey(publicKeyResource);
         SignedJWT signedJWT = SignedJWT.parse(jwt);
        JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey);
        if(!signedJWT.verify(verifier)){
            throw  new JOSEException("Invalid signature");
        }
        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
        if(claimsSet.getExpirationTime().before(new Date())){
            throw new JOSEException("Expired token");
        }
        return claimsSet;
    }


    private PrivateKey loadPrivateKey(Resource resource) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        //Este código lee todos los bytes del archivo especificado por ruta_del_archivo.txt utilizando Files.readAllBytes(Path path).
        // La ruta del archivo se crea utilizando Paths.get(String path)
        byte[] keyBytes = Files.readAllBytes(Paths.get(resource.getURI()));
        String privateKeyPEM = new String(keyBytes, StandardCharsets.UTF_8)
                .replace("-----BEGIN PRIVATE KEY-----","")
                .replace("-----END PRIVATE KEY-----","")
                .replaceAll("\\s","");
        //Este código decodifica la cadena Base64 almacenada en privateKeyPEM utilizando Base64.getDecoder().decode(String)
        // y almacena el resultado en un array de bytes llamado decodeKey
        byte[] decodeKey = Base64.getDecoder().decode(privateKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return  keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodeKey));

    }
    private PublicKey loadPublicKey(Resource resource) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Files.readAllBytes(Paths.get(resource.getURI()));
        String publicKeyPEM = new String(keyBytes, StandardCharsets.UTF_8)
                .replace("-----BEGIN PUBLIC KEY-----","")
                .replace("-----END PUBLIC KEY-----","")
                .replaceAll("\\s","");
        byte[] decodeKey = Base64.getDecoder().decode(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(new X509EncodedKeySpec(decodeKey));
    }
}
/*

Este método loadPrivateKey/loadPublicKey carga una clave privada desde un archivo en formato PEM.
Aquí tienes una explicación paso a paso de lo que hace:

Lee todos los bytes del archivo utilizando Files.readAllBytes().
Convierte los bytes en una cadena utilizando UTF-8.
Elimina las marcas de inicio y fin del bloque PEM de la cadena.
Elimina todos los espacios en blanco de la cadena.
Decodifica la cadena Base64 resultante en un arreglo de bytes.
Utiliza KeyFactory para generar una instancia de PrivateKey a partir de los bytes decodificados, usando PKCS8EncodedKeySpec.
Este método asume que el archivo contiene una clave privada RSA en formato PEM.
Asegúrate de que el archivo que estás cargando realmente contenga una clave privada RSA y
de que esté en el formato esperado (incluyendo las marcas de inicio y fin del bloque PEM).

Recuerda manejar adecuadamente las excepciones que puedan ocurrir durante la lectura del archivo y la generación de la clave privada.
Además, ten en cuenta que este método carga la clave privada desde un archivo local,
así que asegúrate de tener los permisos necesarios para acceder al archivo en tiempo de ejecución.

 */