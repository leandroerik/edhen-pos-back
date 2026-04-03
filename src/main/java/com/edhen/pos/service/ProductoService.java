package com.edhen.pos.service;

import com.edhen.pos.entity.Producto;
import com.edhen.pos.entity.SKU;
import com.edhen.pos.exception.BusinessException;
import com.edhen.pos.exception.ResourceNotFoundException;
import com.edhen.pos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Producto crearProducto(Producto producto) {
        // 🔥 asociar SKUs correctamente
        if (producto.getSkus() != null) {
            for (SKU sku : producto.getSkus()) {
                sku.setProducto(producto);
            }
        }
        return productoRepository.save(producto);
    }

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", "id", id));
    }

    public Producto actualizarProducto(Long id, Producto producto) {
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto", "id", id);
        }
        producto.setId(id);

        // 🔥 asociar SKUs correctamente
        if (producto.getSkus() != null) {
            for (SKU sku : producto.getSkus()) {
                sku.setProducto(producto);
            }
        }

        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto", "id", id);
        }
        productoRepository.deleteById(id);
    }

    // 🔍 BÚSQUEDAS Y FILTROS

    /**
     * Buscar productos por nombre (búsqueda parcial)
     */
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findAll().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .toList();
    }

    /**
     * Buscar productos por descripción
     */
    public List<Producto> buscarPorDescripcion(String descripcion) {
        return productoRepository.findAll().stream()
                .filter(p -> p.getDescripcion() != null &&
                        p.getDescripcion().toLowerCase().contains(descripcion.toLowerCase()))
                .toList();
    }

    /**
     * Listar productos activos
     */
    public List<Producto> listarProductosActivos() {
        return productoRepository.findAll().stream()
                .filter(Producto::getActivo)
                .toList();
    }

    /**
     * Listar productos inactivos
     */
    public List<Producto> listarProductosInactivos() {
        return productoRepository.findAll().stream()
                .filter(p -> !p.isActivo())
                .toList();
    }
}
