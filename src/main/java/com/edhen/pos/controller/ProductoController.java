package com.edhen.pos.controller;

import com.edhen.pos.entity.Producto;
import com.edhen.pos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    public Producto crear(@RequestBody Producto producto) {
        return productoService.crearProducto(producto);
    }

    @GetMapping
    public List<Producto> listar() {
        return productoService.listarProductos();
    }

    @GetMapping("/{id}")
    public Producto obtener(@PathVariable Long id) {
        return productoService.obtenerProductoPorId(id);
    }

    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        return productoService.actualizarProducto(id, producto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        productoService.eliminarProducto(id);
    }

    // 🔍 BÚSQUEDAS Y FILTROS

    @GetMapping("/buscar/nombre")
    public List<Producto> buscarPorNombre(@RequestParam String nombre) {
        return productoService.buscarPorNombre(nombre);
    }

    @GetMapping("/buscar/descripcion")
    public List<Producto> buscarPorDescripcion(@RequestParam String descripcion) {
        return productoService.buscarPorDescripcion(descripcion);
    }

    @GetMapping("/activos")
    public List<Producto> listarProductosActivos() {
        return productoService.listarProductosActivos();
    }

    @GetMapping("/inactivos")
    public List<Producto> listarProductosInactivos() {
        return productoService.listarProductosInactivos();
    }
}