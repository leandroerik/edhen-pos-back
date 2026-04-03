package com.edhen.pos.controller;

import com.edhen.pos.entity.Producto;
import com.edhen.pos.entity.SKU;
import com.edhen.pos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @PostMapping
    public Producto crear(@RequestBody Producto producto) {

        // 🔥 asociar SKUs correctamente
        if (producto.getSkus() != null) {
            for (SKU sku : producto.getSkus()) {
                sku.setProducto(producto);
            }
        }

        return productoRepository.save(producto);
    }

    @GetMapping
    public List<Producto> listar() {
        return productoRepository.findAll();
    }
}