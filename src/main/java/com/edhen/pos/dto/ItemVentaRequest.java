package com.edhen.pos.dto;

import java.util.List;

public class ItemVentaRequest {

    public Long skuId;

    public Integer cantidad;

    public Double precioOverride; // opcional

    public Long getSkuId() {
        return skuId;
    }

    public ItemVentaRequest setSkuId(Long skuId) {
        this.skuId = skuId;
        return this;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public ItemVentaRequest setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public Double getPrecioOverride() {
        return precioOverride;
    }

    public ItemVentaRequest setPrecioOverride(Double precioOverride) {
        this.precioOverride = precioOverride;
        return this;
    }
}