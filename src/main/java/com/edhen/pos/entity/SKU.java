package com.edhen.pos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class SKU {

    @Id
    @GeneratedValue
    private Long id;

    private Double precio;
    private Double costo;

    private Integer stock;

    private String codigoBarra;

    private LocalDateTime fechaActualizacionPrecio;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    @JsonIgnore
    private Producto producto;

    public Long getId() {
        return id;
    }

    public SKU setId(Long id) {
        this.id = id;
        return this;
    }

    public Double getPrecio() {
        return precio;
    }

    public SKU setPrecio(Double precio) {
        this.precio = precio;
        return this;
    }

    public Double getCosto() {
        return costo;
    }

    public SKU setCosto(Double costo) {
        this.costo = costo;
        return this;
    }

    public Integer getStock() {
        return stock;
    }

    public SKU setStock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public SKU setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
        return this;
    }

    public LocalDateTime getFechaActualizacionPrecio() {
        return fechaActualizacionPrecio;
    }

    public SKU setFechaActualizacionPrecio(LocalDateTime fechaActualizacionPrecio) {
        this.fechaActualizacionPrecio = fechaActualizacionPrecio;
        return this;
    }

    public Producto getProducto() {
        return producto;
    }

    public SKU setProducto(Producto producto) {
        this.producto = producto;
        return this;
    }
}