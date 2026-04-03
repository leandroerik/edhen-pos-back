package com.edhen.pos.dto;

import java.math.BigDecimal;

public class ClienteTopDTO {
    private Long clienteId;
    private String nombre;
    private long cantidadCompras;
    private BigDecimal montoTotal;
    // getters y setters

    public Long getClienteId() {
        return clienteId;
    }

    public ClienteTopDTO setClienteId(Long clienteId) {
        this.clienteId = clienteId;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public ClienteTopDTO setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public long getCantidadCompras() {
        return cantidadCompras;
    }

    public ClienteTopDTO setCantidadCompras(long cantidadCompras) {
        this.cantidadCompras = cantidadCompras;
        return this;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public ClienteTopDTO setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
        return this;
    }
}

