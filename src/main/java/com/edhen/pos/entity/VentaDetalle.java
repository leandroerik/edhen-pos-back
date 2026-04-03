package com.edhen.pos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class VentaDetalle {

    @Id
    @GeneratedValue
    private Long id;

    private Integer cantidad;
    private Double precioOriginal;
    private Double precioUnitario;
    private Double costoUnitario;

    @ManyToOne
    @JoinColumn(name = "venta_id")
    @JsonIgnore
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "sku_id")
    private SKU sku;

    // getters y setters


    public Long getId() {
        return id;
    }

    public VentaDetalle setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public VentaDetalle setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public Double getPrecioOriginal() {
        return precioOriginal;
    }

    public VentaDetalle setPrecioOriginal(Double precioOriginal) {
        this.precioOriginal = precioOriginal;
        return this;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public VentaDetalle setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
        return this;
    }

    public Double getCostoUnitario() {
        return costoUnitario;
    }

    public VentaDetalle setCostoUnitario(Double costoUnitario) {
        this.costoUnitario = costoUnitario;
        return this;
    }

    public Venta getVenta() {
        return venta;
    }

    public VentaDetalle setVenta(Venta venta) {
        this.venta = venta;
        return this;
    }

    public SKU getSku() {
        return sku;
    }

    public VentaDetalle setSku(SKU sku) {
        this.sku = sku;
        return this;
    }
}