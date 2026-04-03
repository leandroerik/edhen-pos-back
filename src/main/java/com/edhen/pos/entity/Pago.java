package com.edhen.pos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Pago {

    @Id
    @GeneratedValue
    private Long id;

    private Double monto;
    private String metodo; // EFECTIVO, TRANSFERENCIA, TARJETA
    private String estado;
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "venta_id")
    @JsonIgnore
    private Venta venta;

    // getters y setters

    public Long getId() {
        return id;
    }

    public Pago setId(Long id) {
        this.id = id;
        return this;
    }

    public Double getMonto() {
        return monto;
    }

    public Pago setMonto(Double monto) {
        this.monto = monto;
        return this;
    }

    public String getMetodo() {
        return metodo;
    }

    public Pago setMetodo(String metodo) {
        this.metodo = metodo;
        return this;
    }

    public String getEstado() {
        return estado;
    }

    public Pago setEstado(String estado) {
        this.estado = estado;
        return this;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Pago setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
        return this;
    }

    public Venta getVenta() {
        return venta;
    }

    public Pago setVenta(Venta venta) {
        this.venta = venta;
        return this;
    }
}