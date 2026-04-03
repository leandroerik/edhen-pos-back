package com.edhen.pos.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Venta {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime fecha;
    private Double total;
    private String tipoVenta;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "tienda_id")
    private Tienda tienda;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VentaDetalle> detalles = new ArrayList<>();

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pago> pagos = new ArrayList<>();

    public void agregarDetalle(VentaDetalle detalle) {
        detalles.add(detalle);
        detalle.setVenta(this);
    }

    public void agregarPago(Pago pago) {
        pagos.add(pago);
        pago.setVenta(this);
    }

    // getters y setters


    public Long getId() {
        return id;
    }

    public Venta setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Venta setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
        return this;
    }

    public Double getTotal() {
        return total;
    }

    public Venta setTotal(Double total) {
        this.total = total;
        return this;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public Venta setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
        return this;
    }

    public String getEstado() {
        return estado;
    }

    public Venta setEstado(String estado) {
        this.estado = estado;
        return this;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Venta setCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Venta setUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public Venta setTienda(Tienda tienda) {
        this.tienda = tienda;
        return this;
    }

    public List<VentaDetalle> getDetalles() {
        return detalles;
    }

    public Venta setDetalles(List<VentaDetalle> detalles) {
        this.detalles = detalles;
        return this;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public Venta setPagos(List<Pago> pagos) {
        this.pagos = pagos;
        return this;
    }
}