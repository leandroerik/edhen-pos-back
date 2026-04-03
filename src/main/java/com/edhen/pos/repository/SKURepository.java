package com.edhen.pos.repository;

import com.edhen.pos.entity.SKU;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SKURepository extends JpaRepository<SKU, Long> {
    List<SKU> findByCodigoBarra(String codigoBarra);
}