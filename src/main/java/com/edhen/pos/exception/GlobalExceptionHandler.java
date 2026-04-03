package com.edhen.pos.exception;

import com.edhen.pos.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/**
 * Controlador global para manejar excepciones en toda la aplicación
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja ResourceNotFoundException
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "Recurso no encontrado",
            ex.getMessage()
        );
        errorResponse.setRuta(request.getDescription(false).replace("uri=", ""));
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja BusinessException
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Error en la operación",
            ex.getMessage()
        );
        errorResponse.setRuta(request.getDescription(false).replace("uri=", ""));
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja excepciones generales
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Error interno del servidor",
            ex.getMessage() != null ? ex.getMessage() : "Error desconocido"
        );
        errorResponse.setRuta(request.getDescription(false).replace("uri=", ""));
        
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Maneja IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Argumento inválido",
            ex.getMessage()
        );
        errorResponse.setRuta(request.getDescription(false).replace("uri=", ""));
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

