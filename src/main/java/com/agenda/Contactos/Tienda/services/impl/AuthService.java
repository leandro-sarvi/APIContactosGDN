package com.agenda.Contactos.Tienda.services.impl;

import com.agenda.Contactos.Tienda.config.security.ValidateCredentials;
import com.agenda.Contactos.Tienda.mappers.impl.MapperUser;
import com.agenda.Contactos.Tienda.persistence.entities.User;
import com.agenda.Contactos.Tienda.dto.UserDto;
import com.agenda.Contactos.Tienda.persistence.repositories.UserRepository;
import com.agenda.Contactos.Tienda.services.IAuthService;
import com.agenda.Contactos.Tienda.services.IEmailSenderService;
import com.agenda.Contactos.Tienda.services.IJwtUtilityService;
import com.agenda.Contactos.Tienda.dto.LoginDto;
import com.agenda.Contactos.Tienda.dto.ResponseDto;
import com.nimbusds.jose.JOSEException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService implements IAuthService {
    private UserRepository userRepository;
    private IJwtUtilityService jwtUtilityService;
    private MapperUser mapperUser;
    private ValidateCredentials validateCredentials;
    private IEmailSenderService emailService;
    public AuthService(UserRepository userRepository, IJwtUtilityService jwtUtilityService, MapperUser mapperUser, ValidateCredentials validateCredentials, EmailServiceImpl emailService){
        this.userRepository = userRepository;
        this.jwtUtilityService=jwtUtilityService;
        this.validateCredentials = validateCredentials;
        this.mapperUser=mapperUser;
        this.emailService = emailService;
    }
    @Override
    public HashMap<String, String> login(LoginDto loginDto) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, JOSEException {
        HashMap<String, String> jwt = new HashMap<>();
        Date timeStamp = new Date();
        Optional<User> userOptional = this.userRepository.findByEmail(loginDto.getEmail());
        if(loginDto.getEmail()==null || !validateCredentials.validateEmail(loginDto.getEmail())){
            jwt.put("Error","INVALID O NULL EMAIL");
            jwt.put("TimeStamp",timeStamp.toString());
            return jwt;
        }
        if(EmailNotExists(loginDto.getEmail())){
            jwt.put("Error","User is not registered!");
            jwt.put("TimeStamp",timeStamp.toString());
            return jwt;
        }
        User user = userOptional.get();
        if(user.getConfirm()==false){
            jwt.put("Error","unconfirmed email, confirmation email is sent");
            jwt.put("TimeStamp",timeStamp.toString());
            emailService.sendEMail(user.getEmail(),"GDN-API: Confirmación de correo electronico",
                    "¡Gracias por registrarte "+ user.getEmail() +"!\n\n" +
                            "Para completar el registro, por favor haz click en el siguiente enlace para confirmar tu dirección de correo electrónico:\n\n"
                            + "http://localhost:8080/auth/confirm-mail/" + user.getToken_confirm()+"\n\n"+"Muchas gracias!!!");
            return jwt;
        }
        if(VerifyPassword(loginDto.getPassword(),user.getPassword())){
            jwt.put("jwt",jwtUtilityService.generateToken(user));
            jwt.put("TimeStamp",timeStamp.toString());
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            emailService.sendEMail("leandrosarvi@gmail.com","GDN-API: Alerta de inicio de sesion",
                    "El usuario "+ loginDto.getEmail()+" ha iniciado sesion con exito \n\n"
                            + "Horario: " + timeStamp.toString()+"\n\n"+ auth.getDetails().toString());
        }
        else {
            jwt.put("Error","Authentication failed!");
            jwt.put("TimeStamp",timeStamp.toString());
            return jwt;
        }
        return jwt;
    }
    @Override
    public ResponseDto register(UserDto userDto) throws Exception {
        Date now = new Date();
        Optional<User> userOptional = this.userRepository.findByEmail(userDto.getEmail());
        if(!EmailNotExists(userDto.getEmail())){
            ResponseDto responseDto = new ResponseDto();
            responseDto.setError(1);
            responseDto.setMessage("REGISTERED_USER!!!");
            responseDto.setTimeStamp(now);
            return responseDto;
        }
        ResponseDto response = this.validateCredentials.validate(userDto);
        if(response.getError()==0){
            String token = generateStringTokenUID();
            User user = this.mapperUser.map(userDto);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            user.setPassword(encoder.encode(userDto.getPassword()));
            user.setRole(userDto.getRole());
            user.setCreateAT(now);
            user.setToken_confirm(token);
            userRepository.save(user);
            response.setMessage("User created correctly, email was sent to confirm the email!!!");
            response.setTimeStamp(now);
            // Send mail confirmation
            emailService.sendEMail(user.getEmail(), "GDN-API: Confirmación de correo electronico",
                    "¡Gracias por registrarte "+ user.getEmail() +"!\n\n" +
                              "Para completar el registro, por favor haz click en el siguiente enlace para confirmar tu dirección de correo electrónico:\n\n"
                            + "http://localhost:8080/auth/confirm-mail/" +token+"\n\n"+"Muchas gracias!!!");
        }
        return response;
    }
    @Override
    public HashMap<String, String> confirmEmail(String tokenConfirm) {
        Date now = new Date();
        HashMap<String, String> response = new HashMap<>();
        Optional<User> userOptional = this.userRepository.findByTokenConfirm(tokenConfirm);
        if(userOptional.isEmpty()){
            response.put("Error","INVALID_TOKEN_CONFIRM");
            response.put("TimeStamp",now.toString());
            return response;
        }
        this.userRepository.confirmEmail(tokenConfirm);
        response.put("response:","confirmed email!!!");
        response.put("timestamp",now.toString());
        return response;
    }
    @Override
    public ResponseDto deleteUser(Long id, String password) {
        Date now = new Date();
        ResponseDto response = new ResponseDto();
        Optional<User> user = this.userRepository.findUserById(id);
        // Verificamos el contexto de la seguridad
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> userOptional = this.userRepository.findByEmail(auth.getPrincipal().toString());
        User userSession = userOptional.get();
        if(user.isEmpty()){
            response.setError(response.getError() + 1);
            response.setMessage("USER NOT REGISTERED!!!");
            response.setTimeStamp(now);
            return response;
        }
        if(!VerifyPassword(password,userSession.getPassword())){
            response.setError(response.getError() + 1);
            response.setMessage("ACCESS_DENIED!!!");
            response.setTimeStamp(now);
            return response;
        }
        this.userRepository.deleteById(id);
        response.setMessage("DELETED_USER!!!");
        response.setError(0);
        response.setTimeStamp(now);
        return response;
    }
    @Override
    public Authentication sessionVerifier() {
        Authentication session = SecurityContextHolder.getContext().getAuthentication();
        return session;
    }
    private boolean VerifyPassword(String enteredPassword, String storePassword){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(enteredPassword,storePassword);
    }
    private boolean EmailNotExists(String email){
        Optional<User> optionalUser = this.userRepository.findByEmail(email);
        return optionalUser.isEmpty();
    }
    private String generateStringTokenUID(){
        UUID token =  UUID.randomUUID();
        return token.toString();
    }
}
