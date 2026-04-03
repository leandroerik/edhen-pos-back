package com.edhen.pos.repository;

import com.edhen.pos.entity.Cliente;
import com.edhen.pos.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Long> {
}