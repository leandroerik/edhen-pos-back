package com.edhen.pos.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Producto {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
    private String descripcion;
    private Boolean activo;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SKU> skus = new ArrayList<>();

    public void agregarSku(SKU sku) {
        skus.add(sku);
        sku.setProducto(this);
    }

    public Long getId() {
        return id;
    }

    public Producto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public Producto setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Producto setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public Boolean getActivo() {
        return activo;
    }

    public Producto setActivo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public List<SKU> getSkus() {
        return skus;
    }

    public Producto setSkus(List<SKU> skus) {
        this.skus = skus;
        return this;
    }
}