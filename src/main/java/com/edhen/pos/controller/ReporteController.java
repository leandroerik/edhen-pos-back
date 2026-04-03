package com.edhen.pos.controller;

import com.edhen.pos.dto.*;
import com.edhen.pos.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReporteController {
    @Autowired
    private ReporteService reporteService;

    @GetMapping("/ventas")
    public VentaPeriodoDTO ventasPorPeriodo(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return reporteService.ventasPorPeriodo(desde, hasta);
    }

    @GetMapping("/productos-mas-vendidos")
    public List<ProductoVendidoDTO> productosMasVendidos(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
                                                         @RequestParam(defaultValue = "10") int top) {
        return reporteService.productosMasVendidos(desde, hasta, top);
    }

    @GetMapping("/ganancias")
    public GananciaDTO gananciasTotales(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return reporteService.gananciasTotales(desde, hasta);
    }

    @GetMapping("/top-clientes")
    public List<ClienteTopDTO> topClientes(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
                                           @RequestParam(defaultValue = "10") int top) {
        return reporteService.topClientes(desde, hasta, top);
    }

    @GetMapping("/inventario")
    public List<InventarioDTO> reporteInventario() {
        return reporteService.reporteInventario();
    }

    @GetMapping("/total-vendido")
    public TotalVendidoDTO totalVendidoHastaAhora() {
        return reporteService.totalVendidoHastaAhora();
    }
}

