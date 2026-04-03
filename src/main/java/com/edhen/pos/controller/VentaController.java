package com.edhen.pos.controller;

import com.edhen.pos.dto.VentaRequest;
import com.edhen.pos.entity.Venta;
import com.edhen.pos.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping
    public Venta crear(@RequestBody VentaRequest request) {
        return ventaService.crearVenta(request);
    }

    @GetMapping
    public List<Venta> listar() {
        return ventaService.listarVentas();
    }
}