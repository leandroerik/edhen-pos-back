package com.edhen.pos.entity;

import jakarta.persistence.*;

@Entity
public class Cliente {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
    private String telefono;
    private String email;
    private String tipo; // LOCAL / ONLINE

    // getters y setters


    public Long getId() {
        return id;
    }

    public Cliente setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public Cliente setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getTelefono() {
        return telefono;
    }

    public Cliente setTelefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Cliente setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getTipo() {
        return tipo;
    }

    public Cliente setTipo(String tipo) {
        this.tipo = tipo;
        return this;
    }
}