package com.edhen.pos.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class VentaPeriodoDTO {
    private LocalDate desde;
    private LocalDate hasta;
    private long cantidadVentas;
    private BigDecimal montoTotal;
    // getters y setters

    public LocalDate getDesde() {
        return desde;
    }

    public VentaPeriodoDTO setDesde(LocalDate desde) {
        this.desde = desde;
        return this;
    }

    public LocalDate getHasta() {
        return hasta;
    }

    public VentaPeriodoDTO setHasta(LocalDate hasta) {
        this.hasta = hasta;
        return this;
    }

    public long getCantidadVentas() {
        return cantidadVentas;
    }

    public VentaPeriodoDTO setCantidadVentas(long cantidadVentas) {
        this.cantidadVentas = cantidadVentas;
        return this;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public VentaPeriodoDTO setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
        return this;
    }
}

