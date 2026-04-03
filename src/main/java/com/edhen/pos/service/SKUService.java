package com.edhen.pos.service;

import com.edhen.pos.entity.SKU;
import com.edhen.pos.exception.BusinessException;
import com.edhen.pos.exception.ResourceNotFoundException;
import com.edhen.pos.repository.SKURepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SKUService {

    @Autowired
    private SKURepository skuRepository;

    public SKU crearSKU(SKU sku) {
        return skuRepository.save(sku);
    }

    public List<SKU> listarSKUs() {
        return skuRepository.findAll();
    }

    public SKU obtenerSKUPorId(Long id) {
        return skuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SKU", "id", id));
    }

    public SKU actualizarSKU(Long id, SKU sku) {
        if (!skuRepository.existsById(id)) {
            throw new ResourceNotFoundException("SKU", "id", id);
        }
        sku.setId(id);
        return skuRepository.save(sku);
    }

    public void eliminarSKU(Long id) {
        if (!skuRepository.existsById(id)) {
            throw new ResourceNotFoundException("SKU", "id", id);
        }
        skuRepository.deleteById(id);
    }
}
