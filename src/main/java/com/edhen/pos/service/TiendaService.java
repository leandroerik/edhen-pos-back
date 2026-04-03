package com.edhen.pos.service;

import com.edhen.pos.entity.Tienda;
import com.edhen.pos.exception.BusinessException;
import com.edhen.pos.exception.ResourceNotFoundException;
import com.edhen.pos.repository.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TiendaService {

    @Autowired
    private TiendaRepository tiendaRepository;

    public Tienda crearTienda(Tienda tienda) {
        return tiendaRepository.save(tienda);
    }

    public List<Tienda> listarTiendas() {
        return tiendaRepository.findAll();
    }

    public Tienda obtenerTiendaPorId(Long id) {
        return tiendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tienda", "id", id));
    }

    public Tienda actualizarTienda(Long id, Tienda tienda) {
        if (!tiendaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tienda", "id", id);
        }
        tienda.setId(id);
        return tiendaRepository.save(tienda);
    }

    public void eliminarTienda(Long id) {
        if (!tiendaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tienda", "id", id);
        }
        tiendaRepository.deleteById(id);
    }
}
