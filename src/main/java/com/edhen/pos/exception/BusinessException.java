package com.edhen.pos.exception;

/**
 * Excepción para errores de validación de negocio
 */
public class BusinessException extends RuntimeException {
    
    public BusinessException(String message) {
        super(message);
    }
    
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}

