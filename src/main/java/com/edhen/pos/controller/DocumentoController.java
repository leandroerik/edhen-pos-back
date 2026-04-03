package com.edhen.pos.controller;

import com.edhen.pos.entity.Venta;
import com.edhen.pos.repository.VentaRepository;
import com.edhen.pos.service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/documentos")
public class DocumentoController {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private DocumentoService documentoService;

    @GetMapping("/ticket/{ventaId}")
    public String verTicket(@PathVariable Long ventaId) {

        Venta venta = ventaRepository.findById(ventaId).orElseThrow();

        return documentoService.generarTextoTicket(venta);
    }
}