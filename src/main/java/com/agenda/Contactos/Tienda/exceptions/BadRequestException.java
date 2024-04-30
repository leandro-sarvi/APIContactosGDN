package com.agenda.Contactos.Tienda.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class BadRequestException extends RuntimeException{
    private String message;
    private HttpStatus httpStatus;
    public BadRequestException(String message){
        super(message);
        this.message = message;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }
}
