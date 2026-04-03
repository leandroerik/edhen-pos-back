package com.edhen.pos.dto;

import java.math.BigDecimal;

public class TotalVendidoDTO {
    private long cantidadVentas;
    private BigDecimal montoTotal;
    // getters y setters


    public long getCantidadVentas() {
        return cantidadVentas;
    }

    public TotalVendidoDTO setCantidadVentas(long cantidadVentas) {
        this.cantidadVentas = cantidadVentas;
        return this;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public TotalVendidoDTO setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
        return this;
    }
}

