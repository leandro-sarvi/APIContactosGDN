package com.agenda.Contactos.Tienda.exceptions;

import com.agenda.Contactos.Tienda.exceptions.dto.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHanlder {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(ResourceNotFoundException ex){
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setHttpStatus(ex.getHttpStatus());
        return ResponseEntity.status(ex.getHttpStatus()).body(errorMessage);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessage> handleBadRequestException(BadRequestException ex){
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setHttpStatus(ex.getHttpStatus());
        return ResponseEntity.status(ex.getHttpStatus()).body(errorMessage);
    }
}
