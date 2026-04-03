package com.edhen.pos.controller;

import com.edhen.pos.dto.VentaRequest;
import com.edhen.pos.entity.Venta;
import com.edhen.pos.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/{id}")
    public Venta obtener(@PathVariable Long id) {
        return ventaService.obtenerVentaPorId(id);
    }

    @PutMapping("/{id}")
    public Venta actualizar(@PathVariable Long id, @RequestBody Venta venta) {
        if (!ventaService.obtenerVentaPorId(id).equals(venta)) {
            throw new RuntimeException("Venta no encontrada");
        }
        venta.setId(id);
        // Nota: Actualizar venta puede ser riesgoso, considera lógica adicional
        return ventaService.actualizarVenta(venta);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        // Nota: Eliminar venta puede afectar inventario y documentos
        ventaService.eliminarVenta(id);
    }

    // 🔍 BÚSQUEDAS Y FILTROS

    @GetMapping("/buscar/fecha")
    public List<Venta> buscarPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ventaService.buscarVentasPorFecha(fechaInicio, fechaFin);
    }

    @GetMapping("/buscar/cliente/{clienteId}")
    public List<Venta> buscarPorCliente(@PathVariable Long clienteId) {
        return ventaService.buscarVentasPorCliente(clienteId);
    }

    @GetMapping("/buscar/usuario/{usuarioId}")
    public List<Venta> buscarPorUsuario(@PathVariable Long usuarioId) {
        return ventaService.buscarVentasPorUsuario(usuarioId);
    }

    @GetMapping("/buscar/tipo/{tipo}")
    public List<Venta> buscarPorTipo(@PathVariable String tipo) {
        return ventaService.buscarVentasPorTipo(tipo);
    }

    @GetMapping("/buscar/estado/{estado}")
    public List<Venta> buscarPorEstado(@PathVariable String estado) {
        return ventaService.buscarVentasPorEstado(estado);
    }
}