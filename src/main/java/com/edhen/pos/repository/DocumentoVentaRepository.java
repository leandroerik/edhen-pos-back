package com.edhen.pos.repository;

import com.edhen.pos.entity.Cliente;
import com.edhen.pos.entity.DocumentoVenta;
import com.edhen.pos.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DocumentoVentaRepository extends JpaRepository<DocumentoVenta, Long> {

}