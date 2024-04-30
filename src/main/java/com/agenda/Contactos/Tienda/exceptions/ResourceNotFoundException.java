package com.agenda.Contactos.Tienda.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResourceNotFoundException extends  RuntimeException{
    private String message;
    private HttpStatus httpStatus;
    public ResourceNotFoundException(String message){
        super(message);
        this.message = message;
        this.httpStatus = HttpStatus.NOT_FOUND;
    }
}
