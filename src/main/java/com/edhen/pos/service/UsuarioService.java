package com.edhen.pos.service;

import com.edhen.pos.entity.Usuario;
import com.edhen.pos.exception.BusinessException;
import com.edhen.pos.exception.ResourceNotFoundException;
import com.edhen.pos.repository.TiendaRepository;
import com.edhen.pos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    public Usuario crearUsuario(Usuario usuario) {
        // asegurar que la tienda existe
        if (usuario.getTienda() != null && usuario.getTienda().getId() != null) {
            usuario.setTienda(
                    tiendaRepository.findById(usuario.getTienda().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Tienda", "id", usuario.getTienda().getId()))
            );
        }
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
    }

    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario", "id", id);
        }
        usuario.setId(id);

        // asegurar que la tienda existe
        if (usuario.getTienda() != null && usuario.getTienda().getId() != null) {
            usuario.setTienda(
                    tiendaRepository.findById(usuario.getTienda().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Tienda", "id", usuario.getTienda().getId()))
            );
        }

        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario", "id", id);
        }
        usuarioRepository.deleteById(id);
    }
}
