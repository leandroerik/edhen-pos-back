package com.edhen.pos.controller;

import com.edhen.pos.entity.Cliente;
import com.edhen.pos.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public Cliente crear(@RequestBody Cliente cliente) {
        return clienteService.crearCliente(cliente);
    }

    @GetMapping
    public List<Cliente> listar() {
        return clienteService.listarClientes();
    }

    @GetMapping("/{id}")
    public Cliente obtener(@PathVariable Long id) {
        return clienteService.obtenerClientePorId(id);
    }

    @PutMapping("/{id}")
    public Cliente actualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        return clienteService.actualizarCliente(id, cliente);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
    }

    // 🔍 BÚSQUEDAS Y FILTROS

    @GetMapping("/buscar/nombre")
    public List<Cliente> buscarPorNombre(@RequestParam String nombre) {
        return clienteService.buscarPorNombre(nombre);
    }

    @GetMapping("/buscar/email")
    public List<Cliente> buscarPorEmail(@RequestParam String email) {
        return clienteService.buscarPorEmail(email);
    }

    @GetMapping("/buscar/telefono")
    public List<Cliente> buscarPorTelefono(@RequestParam String telefono) {
        return clienteService.buscarPorTelefono(telefono);
    }

    @GetMapping("/buscar/tipo/{tipo}")
    public List<Cliente> buscarPorTipo(@PathVariable String tipo) {
        return clienteService.buscarPorTipo(tipo);
    }
}