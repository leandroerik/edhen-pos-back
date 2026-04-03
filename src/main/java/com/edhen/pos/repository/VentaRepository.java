package com.edhen.pos.repository;

import com.edhen.pos.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    // Productos más vendidos (top N)
    @Query("""
        SELECT p.id, p.nombre, SUM(d.cantidad) as cantidadVendida, SUM(d.cantidad * d.precioUnitario) as montoTotal
        FROM Venta v
        JOIN v.detalles d
        JOIN d.sku s
        JOIN s.producto p
        WHERE v.fecha BETWEEN :desde AND :hasta
        GROUP BY p.id, p.nombre
        ORDER BY cantidadVendida DESC
    """)
    List<Object[]> findProductosMasVendidos(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);

    // Ganancia total (suma de (precio - costo) * cantidad)
    @Query("""
        SELECT SUM((d.precioUnitario - d.costoUnitario) * d.cantidad)
        FROM Venta v
        JOIN v.detalles d
        WHERE v.fecha BETWEEN :desde AND :hasta
    """)
    Double findGananciaTotal(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);

    // Top clientes (por monto y cantidad)
    @Query("""
        SELECT c.id, c.nombre, COUNT(v.id) as cantidadCompras, SUM(v.total) as montoTotal
        FROM Venta v
        JOIN v.cliente c
        WHERE v.fecha BETWEEN :desde AND :hasta
        GROUP BY c.id, c.nombre
        ORDER BY montoTotal DESC
    """)
    List<Object[]> findTopClientes(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);
}