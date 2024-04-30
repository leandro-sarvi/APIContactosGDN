package com.agenda.Contactos.Tienda.config.security;

import com.agenda.Contactos.Tienda.dto.UserDto;
import com.agenda.Contactos.Tienda.dto.ResponseDto;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Component
public class ValidateCredentials {
    private  final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private  final  String PASSWORD_REGEX =
            "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$.%^&*-]).{8,}$";
    private final Pattern patternEmail = Pattern.compile(EMAIL_REGEX);
    private final Pattern patterPassword = Pattern.compile(PASSWORD_REGEX);
    public ResponseDto validate(UserDto userDto){
        ResponseDto responseDto = new ResponseDto();
        Date timeStamp = new Date();
        if(userDto.getEmail()==null ||!validateEmail(userDto.getEmail())){
            responseDto.setMessage("INVALID_EMAIL");
            responseDto.setTimeStamp(timeStamp);
            responseDto.setError(responseDto.getError()+1);
        }
        if(userDto.getPassword()==null || !validatePassword(userDto.getPassword())){
            responseDto.setMessage("INVALID_PASSWORD");
            responseDto.setTimeStamp(timeStamp);
            responseDto.setError(responseDto.getError()+1);
        }
        if(!validateEmail(userDto.getEmail()) && !validatePassword(userDto.getPassword())){
            responseDto.setMessage("INVALID EMAIL AND PASSWORD");
            responseDto.setTimeStamp(timeStamp);
        }
        return responseDto;
    }
    public boolean validateEmail(String email){
        Matcher matcher = patternEmail.matcher(email);
        return matcher.matches();
    }
    private boolean validatePassword(String password){
        Matcher matcher = patterPassword.matcher(password);
        return matcher.matches();
    }
}
