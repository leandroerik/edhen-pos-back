package com.edhen.pos.entity;

import jakarta.persistence.*;

@Entity
public class Tienda {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    // getters y setters

    public Long getId() {
        return id;
    }

    public Tienda setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public Tienda setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }
}