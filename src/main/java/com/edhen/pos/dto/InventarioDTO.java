package com.edhen.pos.dto;

public class InventarioDTO {
    private Long productoId;
    private String nombre;
    private int stockActual;
    private boolean bajoStock;
    // getters y setters

    public Long getProductoId() {
        return productoId;
    }

    public InventarioDTO setProductoId(Long productoId) {
        this.productoId = productoId;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public InventarioDTO setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public int getStockActual() {
        return stockActual;
    }

    public InventarioDTO setStockActual(int stockActual) {
        this.stockActual = stockActual;
        return this;
    }

    public boolean isBajoStock() {
        return bajoStock;
    }

    public InventarioDTO setBajoStock(boolean bajoStock) {
        this.bajoStock = bajoStock;
        return this;
    }
}

