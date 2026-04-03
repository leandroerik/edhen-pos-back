package com.edhen.pos.dto;

import java.math.BigDecimal;

public class GananciaDTO {
    private BigDecimal gananciaTotal;
    // getters y setters

    public BigDecimal getGananciaTotal() {
        return gananciaTotal;
    }

    public GananciaDTO setGananciaTotal(BigDecimal gananciaTotal) {
        this.gananciaTotal = gananciaTotal;
        return this;
    }
}

