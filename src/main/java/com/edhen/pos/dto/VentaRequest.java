package com.edhen.pos.dto;

import java.util.List;

public class VentaRequest {

    public Long clienteId;
    public Long usuarioId;
    public Long tiendaId;

    public String tipoVenta; // LOCAL / ONLINE

    public List<ItemVentaRequest> items;
    public List<PagoRequest> pagos;

    public Long getClienteId() {
        return clienteId;
    }

    public VentaRequest setClienteId(Long clienteId) {
        this.clienteId = clienteId;
        return this;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public VentaRequest setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
        return this;
    }

    public Long getTiendaId() {
        return tiendaId;
    }

    public VentaRequest setTiendaId(Long tiendaId) {
        this.tiendaId = tiendaId;
        return this;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public VentaRequest setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
        return this;
    }

    public List<ItemVentaRequest> getItems() {
        return items;
    }

    public VentaRequest setItems(List<ItemVentaRequest> items) {
        this.items = items;
        return this;
    }

    public List<PagoRequest> getPagos() {
        return pagos;
    }

    public VentaRequest setPagos(List<PagoRequest> pagos) {
        this.pagos = pagos;
        return this;
    }
}