package com.edhen.pos.dto;

public class PagoRequest {

    public Double monto;
    public String metodo;

    public Double getMonto() {
        return monto;
    }

    public PagoRequest setMonto(Double monto) {
        this.monto = monto;
        return this;
    }

    public String getMetodo() {
        return metodo;
    }

    public PagoRequest setMetodo(String metodo) {
        this.metodo = metodo;
        return this;
    }
}