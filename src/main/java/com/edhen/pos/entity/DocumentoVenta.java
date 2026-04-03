package com.edhen.pos.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DocumentoVenta {

    @Id
    @GeneratedValue
    private Long id;

    private String numero;
    private LocalDateTime fecha;

    private String tipo; // TICKET o REMITO

    private Double total;

    @OneToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;

    // getters y setters

    public Long getId() {
        return id;
    }

    public DocumentoVenta setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNumero() {
        return numero;
    }

    public DocumentoVenta setNumero(String numero) {
        this.numero = numero;
        return this;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public DocumentoVenta setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
        return this;
    }

    public String getTipo() {
        return tipo;
    }

    public DocumentoVenta setTipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public Double getTotal() {
        return total;
    }

    public DocumentoVenta setTotal(Double total) {
        this.total = total;
        return this;
    }

    public Venta getVenta() {
        return venta;
    }

    public DocumentoVenta setVenta(Venta venta) {
        this.venta = venta;
        return this;
    }
}