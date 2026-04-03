package com.edhen.pos.config;

import com.edhen.pos.entity.*;
import com.edhen.pos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader implements CommandLineRunner {

    @Autowired
    private TiendaRepository tiendaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void run(String... args) {

        // 🔥 evitar duplicados
        if (tiendaRepository.count() > 0) return;

        // =====================
        // 🏪 TIENDA
        // =====================
        Tienda tienda = new Tienda();
        tienda.setNombre("EDHEN Flores");
        tiendaRepository.save(tienda);

        // =====================
        // 👨‍💼 USUARIO
        // =====================
        Usuario admin = new Usuario();
        admin.setNombre("Admin");
        admin.setEmail("admin@edhen.com");
        admin.setPassword("1234");
        admin.setRol("ADMIN");
        admin.setActivo(true);
        admin.setTienda(tienda);

        usuarioRepository.save(admin);

        // =====================
        // 🧍 CLIENTE
        // =====================
        Cliente cliente = new Cliente();
        cliente.setNombre("Cliente General");
        cliente.setTelefono("000000");
        cliente.setEmail("cliente@edhen.com");
        cliente.setTipo("LOCAL");

        clienteRepository.save(cliente);

        // =====================
        // 👕 PRODUCTO 1
        // =====================
        Producto p1 = new Producto();
        p1.setNombre("Musculosa");
        p1.setDescripcion("Básica algodón");
        p1.setActivo(true);

        SKU sku1 = new SKU();
        sku1.setPrecio(8000.0);
        sku1.setCosto(4000.0);
        sku1.setStock(10);
        sku1.setCodigoBarra("MUSCU-NEGRO-M");

        SKU sku2 = new SKU();
        sku2.setPrecio(8000.0);
        sku2.setCosto(4000.0);
        sku2.setStock(5);
        sku2.setCodigoBarra("MUSCU-BLANCO-L");

        p1.agregarSku(sku1);
        p1.agregarSku(sku2);

        productoRepository.save(p1);

        // =====================
        // 👖 PRODUCTO 2
        // =====================
        Producto p2 = new Producto();
        p2.setNombre("Pantalón");
        p2.setDescripcion("Darlon premium");
        p2.setActivo(true);

        SKU sku3 = new SKU();
        sku3.setPrecio(15000.0);
        sku3.setCosto(8000.0);
        sku3.setStock(8);
        sku3.setCodigoBarra("PANT-NEGRO-42");

        p2.agregarSku(sku3);

        productoRepository.save(p2);

        System.out.println("🔥 DATA MOCK CARGADA");
    }
}