package com.edhen.pos.controller;

import com.edhen.pos.entity.Producto;
import com.edhen.pos.entity.SKU;
import com.edhen.pos.repository.ProductoRepository;
import com.edhen.pos.repository.SKURepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skus")
@RequiredArgsConstructor
public class SKUController {

    @Autowired
    private  SKURepository skuRepository;

    @GetMapping
    public List<SKU> listar() {
        return skuRepository.findAll();
    }

    @PostMapping
    public SKU crear(@RequestBody SKU sku) {
        return skuRepository.save(sku);
    }
}