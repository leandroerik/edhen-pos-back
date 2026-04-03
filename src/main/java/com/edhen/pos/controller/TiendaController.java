package com.edhen.pos.controller;

import com.edhen.pos.entity.Tienda;
import com.edhen.pos.repository.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tiendas")
public class TiendaController {

    @Autowired
    private TiendaRepository tiendaRepository;

    @PostMapping
    public Tienda crear(@RequestBody Tienda tienda) {
        return tiendaRepository.save(tienda);
    }

    @GetMapping
    public List<Tienda> listar() {
        return tiendaRepository.findAll();
    }
}