package com.edhen.pos.service;

import com.edhen.pos.entity.Cliente;
import com.edhen.pos.exception.BusinessException;
import com.edhen.pos.exception.ResourceNotFoundException;
import com.edhen.pos.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente crearCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente obtenerClientePorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", id));
    }

    public Cliente actualizarCliente(Long id, Cliente cliente) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente", "id", id);
        }
        cliente.setId(id);
        return clienteRepository.save(cliente);
    }

    public void eliminarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente", "id", id);
        }
        clienteRepository.deleteById(id);
    }

    // 🔍 BÚSQUEDAS Y FILTROS

    /**
     * Buscar clientes por nombre (búsqueda parcial)
     */
    public List<Cliente> buscarPorNombre(String nombre) {
        return clienteRepository.findAll().stream()
                .filter(c -> c.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .toList();
    }

    /**
     * Buscar clientes por email
     */
    public List<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findAll().stream()
                .filter(c -> c.getEmail() != null && c.getEmail().toLowerCase().contains(email.toLowerCase()))
                .toList();
    }

    /**
     * Buscar clientes por teléfono
     */
    public List<Cliente> buscarPorTelefono(String telefono) {
        return clienteRepository.findAll().stream()
                .filter(c -> c.getTelefono() != null && c.getTelefono().contains(telefono))
                .toList();
    }

    /**
     * Buscar clientes por tipo (LOCAL, ONLINE, MAYORISTA)
     */
    public List<Cliente> buscarPorTipo(String tipo) {
        return clienteRepository.findAll().stream()
                .filter(c -> c.getTipo().equals(tipo))
                .toList();
    }
}
