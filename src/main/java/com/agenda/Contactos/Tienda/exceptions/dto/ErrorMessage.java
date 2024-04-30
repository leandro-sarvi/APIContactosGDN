package com.agenda.Contactos.Tienda.exceptions.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorMessage {
    private String message;
    private HttpStatus httpStatus;
}
