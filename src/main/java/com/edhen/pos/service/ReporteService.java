package com.edhen.pos.service;

import com.edhen.pos.dto.*;
import java.time.LocalDate;
import java.util.List;

public interface ReporteService {
    VentaPeriodoDTO ventasPorPeriodo(LocalDate desde, LocalDate hasta);
    List<ProductoVendidoDTO> productosMasVendidos(LocalDate desde, LocalDate hasta, int topN);
    GananciaDTO gananciasTotales(LocalDate desde, LocalDate hasta);
    List<ClienteTopDTO> topClientes(LocalDate desde, LocalDate hasta, int topN);
    List<InventarioDTO> reporteInventario();
    TotalVendidoDTO totalVendidoHastaAhora();
}

