package com.edhen.pos.controller;

import com.edhen.pos.entity.Usuario;
import com.edhen.pos.repository.UsuarioRepository;
import com.edhen.pos.repository.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    @PostMapping
    public Usuario crear(@RequestBody Usuario usuario) {

        // asegurar que la tienda existe
        if (usuario.getTienda() != null && usuario.getTienda().getId() != null) {
            usuario.setTienda(
                    tiendaRepository.findById(usuario.getTienda().getId()).orElseThrow()
            );
        }

        return usuarioRepository.save(usuario);
    }

    @GetMapping
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }
}