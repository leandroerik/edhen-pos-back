package com.edhen.pos.service;

import com.edhen.pos.dto.*;
import com.edhen.pos.entity.*;
import com.edhen.pos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
public class ReporteServiceImpl implements ReporteService {
    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public VentaPeriodoDTO ventasPorPeriodo(LocalDate desde, LocalDate hasta) {
        LocalDateTime desdeDT = desde.atStartOfDay();
        LocalDateTime hastaDT = hasta.atTime(23,59,59);
        List<Venta> ventas = ventaRepository.findByFechaBetween(desdeDT, hastaDT);
        VentaPeriodoDTO dto = new VentaPeriodoDTO();
        dto.setDesde(desde);
        dto.setHasta(hasta);
        dto.setCantidadVentas(ventas.size());
        dto.setMontoTotal(ventas.stream().map(v -> BigDecimal.valueOf(v.getTotal())).reduce(BigDecimal.ZERO, BigDecimal::add));
        return dto;
    }

    @Override
    public List<ProductoVendidoDTO> productosMasVendidos(LocalDate desde, LocalDate hasta, int topN) {
        LocalDateTime desdeDT = desde.atStartOfDay();
        LocalDateTime hastaDT = hasta.atTime(23,59,59);
        List<Object[]> results = ventaRepository.findProductosMasVendidos(desdeDT, hastaDT);
        return results.stream().limit(topN).map(row -> {
            ProductoVendidoDTO dto = new ProductoVendidoDTO();
            dto.setProductoId((Long) row[0]);
            dto.setNombre((String) row[1]);
            dto.setCantidadVendida(row[2] instanceof Long ? (Long) row[2] : ((Number) row[2]).longValue());
            dto.setMontoTotal(BigDecimal.valueOf(row[3] instanceof Double ? (Double) row[3] : ((Number) row[3]).doubleValue()));
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public GananciaDTO gananciasTotales(LocalDate desde, LocalDate hasta) {
        LocalDateTime desdeDT = desde.atStartOfDay();
        LocalDateTime hastaDT = hasta.atTime(23,59,59);
        Double ganancia = ventaRepository.findGananciaTotal(desdeDT, hastaDT);
        GananciaDTO dto = new GananciaDTO();
        dto.setGananciaTotal(BigDecimal.valueOf(ganancia != null ? ganancia : 0.0));
        return dto;
    }

    @Override
    public List<ClienteTopDTO> topClientes(LocalDate desde, LocalDate hasta, int topN) {
        LocalDateTime desdeDT = desde.atStartOfDay();
        LocalDateTime hastaDT = hasta.atTime(23,59,59);
        List<Object[]> results = ventaRepository.findTopClientes(desdeDT, hastaDT);
        return results.stream().limit(topN).map(row -> {
            ClienteTopDTO dto = new ClienteTopDTO();
            dto.setClienteId((Long) row[0]);
            dto.setNombre((String) row[1]);
            dto.setCantidadCompras(row[2] instanceof Long ? (Long) row[2] : ((Number) row[2]).longValue());
            dto.setMontoTotal(BigDecimal.valueOf(row[3] instanceof Double ? (Double) row[3] : ((Number) row[3]).doubleValue()));
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<InventarioDTO> reporteInventario() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream().map(p -> {
            int stock = p.getSkus() != null ? p.getSkus().stream().mapToInt(s -> s.getStock() != null ? s.getStock() : 0).sum() : 0;
            InventarioDTO dto = new InventarioDTO();
            dto.setProductoId(p.getId());
            dto.setNombre(p.getNombre());
            dto.setStockActual(stock);
            dto.setBajoStock(stock <= 5); // umbral configurable
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public TotalVendidoDTO totalVendidoHastaAhora() {
        List<Venta> ventas = ventaRepository.findAll();
        TotalVendidoDTO dto = new TotalVendidoDTO();
        dto.setCantidadVentas(ventas.size());
        dto.setMontoTotal(ventas.stream().map(v -> BigDecimal.valueOf(v.getTotal())).reduce(BigDecimal.ZERO, BigDecimal::add));
        return dto;
    }
}
