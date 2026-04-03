package com.edhen.pos.controller;

import com.edhen.pos.entity.Tienda;
import com.edhen.pos.service.TiendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tiendas")
public class TiendaController {

    @Autowired
    private TiendaService tiendaService;

    @PostMapping
    public Tienda crear(@RequestBody Tienda tienda) {
        return tiendaService.crearTienda(tienda);
    }

    @GetMapping
    public List<Tienda> listar() {
        return tiendaService.listarTiendas();
    }

    @GetMapping("/{id}")
    public Tienda obtener(@PathVariable Long id) {
        return tiendaService.obtenerTiendaPorId(id);
    }

    @PutMapping("/{id}")
    public Tienda actualizar(@PathVariable Long id, @RequestBody Tienda tienda) {
        return tiendaService.actualizarTienda(id, tienda);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        tiendaService.eliminarTienda(id);
    }
}