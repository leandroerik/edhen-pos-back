package com.edhen.pos.entity;

import jakarta.persistence.*;

@Entity
public class Usuario {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
    private String email;
    private String password;
    private String rol; // ADMIN / VENDEDOR
    private Boolean activo;

    @ManyToOne
    @JoinColumn(name = "tienda_id")
    private Tienda tienda;

    // getters y setters

    public Long getId() {
        return id;
    }

    public Usuario setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public Usuario setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Usuario setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Usuario setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRol() {
        return rol;
    }

    public Usuario setRol(String rol) {
        this.rol = rol;
        return this;
    }

    public Boolean getActivo() {
        return activo;
    }

    public Usuario setActivo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public Usuario setTienda(Tienda tienda) {
        this.tienda = tienda;
        return this;
    }
}