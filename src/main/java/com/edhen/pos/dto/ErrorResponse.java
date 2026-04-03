package com.edhen.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO para respuestas de error
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String mensaje;
    private String detalle;
    private LocalDateTime timestamp;
    private String ruta;
    
    public ErrorResponse(int status, String mensaje, String detalle) {
        this.status = status;
        this.mensaje = mensaje;
        this.detalle = detalle;
        this.timestamp = LocalDateTime.now();
    }
}

