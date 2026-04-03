package com.edhen.pos.dto;

import java.math.BigDecimal;

public class ProductoVendidoDTO {
    private Long productoId;
    private String nombre;
    private long cantidadVendida;
    private BigDecimal montoTotal;
    // getters y setters

    public Long getProductoId() {
        return productoId;
    }

    public ProductoVendidoDTO setProductoId(Long productoId) {
        this.productoId = productoId;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public ProductoVendidoDTO setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public long getCantidadVendida() {
        return cantidadVendida;
    }

    public ProductoVendidoDTO setCantidadVendida(long cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
        return this;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public ProductoVendidoDTO setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
        return this;
    }
}

