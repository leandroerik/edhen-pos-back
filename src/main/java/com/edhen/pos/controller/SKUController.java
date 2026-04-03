package com.edhen.pos.controller;

import com.edhen.pos.entity.SKU;
import com.edhen.pos.service.SKUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skus")
public class SKUController {

    @Autowired
    private SKUService skuService;

    @GetMapping
    public List<SKU> listar() {
        return skuService.listarSKUs();
    }

    @PostMapping
    public SKU crear(@RequestBody SKU sku) {
        return skuService.crearSKU(sku);
    }

    @GetMapping("/{id}")
    public SKU obtener(@PathVariable Long id) {
        return skuService.obtenerSKUPorId(id);
    }

    @PutMapping("/{id}")
    public SKU actualizar(@PathVariable Long id, @RequestBody SKU sku) {
        return skuService.actualizarSKU(id, sku);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        skuService.eliminarSKU(id);
    }
}