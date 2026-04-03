package com.edhen.pos.config;

import com.edhen.pos.entity.*;
import com.edhen.pos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

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

    @Autowired
    private VentaRepository ventaRepository;

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
        // 🧍 MÁS CLIENTES
        // =====================
        Cliente cliente2 = new Cliente();
        cliente2.setNombre("María García");
        cliente2.setTelefono("123456789");
        cliente2.setEmail("maria@email.com");
        cliente2.setTipo("LOCAL");
        clienteRepository.save(cliente2);

        Cliente cliente3 = new Cliente();
        cliente3.setNombre("Carlos López");
        cliente3.setTelefono("987654321");
        cliente3.setEmail("carlos@email.com");
        cliente3.setTipo("ONLINE");
        clienteRepository.save(cliente3);

        // =====================
        // 👨‍💼 MÁS USUARIOS
        // =====================
        Usuario cajero = new Usuario();
        cajero.setNombre("Cajero");
        cajero.setEmail("cajero@edhen.com");
        cajero.setPassword("1234");
        cajero.setRol("CAJERO");
        cajero.setActivo(true);
        cajero.setTienda(tienda);
        usuarioRepository.save(cajero);

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

        // =====================
        // 👕 PRODUCTO 3: Musculosa Morely
        // =====================
        Producto p3 = new Producto();
        p3.setNombre("Musculosa Morely");
        p3.setDescripcion("Musculosa de algodón premium");
        p3.setActivo(true);

        String[] colores = {"Negro", "Blanco", "Rojo", "Azul"};
        String[] talles = {"1", "2", "3", "4"};
        double precio = 12000.0;
        double costo = 6000.0;

        for (String color : colores) {
            for (String talle : talles) {
                SKU sku = new SKU();
                sku.setPrecio(precio);
                sku.setCosto(costo);
                sku.setStock(20); // stock inicial
                sku.setCodigoBarra("MORELY-" + color.toUpperCase() + "-" + talle);
                p3.agregarSku(sku);
            }
        }

        productoRepository.save(p3);

        // =====================
        // 👖 PRODUCTO 4: Otro producto de ejemplo
        // =====================
        Producto p4 = new Producto();
        p4.setNombre("Remera Lisa");
        p4.setDescripcion("Remera básica de algodón");
        p4.setActivo(true);

        SKU skuRemera = new SKU();
        skuRemera.setPrecio(9000.0);
        skuRemera.setCosto(4500.0);
        skuRemera.setStock(15);
        skuRemera.setCodigoBarra("REMERA-LISA-UNICA");

        p4.agregarSku(skuRemera);

        productoRepository.save(p4);

        // =====================
        // 🧾 VENTA MOCKEADA DE PRUEBA
        // =====================
        Venta ventaPrueba = new Venta();
        ventaPrueba.setFecha(LocalDateTime.now());
        ventaPrueba.setTipoVenta("LOCAL");
        ventaPrueba.setEstado("COMPLETADA");
        ventaPrueba.setCliente(cliente);
        ventaPrueba.setUsuario(admin);
        ventaPrueba.setTienda(tienda);

        // Agregar items a la venta
        VentaDetalle detalle1 = new VentaDetalle();
        detalle1.setSku(p3.getSkus().get(0)); // Musculosa Morely Negro 1
        detalle1.setCantidad(2);
        detalle1.setPrecioOriginal(12000.0);
        detalle1.setPrecioUnitario(12000.0);
        detalle1.setCostoUnitario(6000.0);
        ventaPrueba.agregarDetalle(detalle1);

        VentaDetalle detalle2 = new VentaDetalle();
        detalle2.setSku(p3.getSkus().get(4)); // Musculosa Morely Blanco 1
        detalle2.setCantidad(1);
        detalle2.setPrecioOriginal(12000.0);
        detalle2.setPrecioUnitario(12000.0);
        detalle2.setCostoUnitario(6000.0);
        ventaPrueba.agregarDetalle(detalle2);

        ventaPrueba.setTotal(36000.0);

        // Agregar pagos
        Pago pago1 = new Pago();
        pago1.setMonto(24000.0);
        pago1.setMetodo("EFECTIVO");
        pago1.setEstado("CONFIRMADO");
        pago1.setFecha(LocalDateTime.now());
        ventaPrueba.agregarPago(pago1);

        Pago pago2 = new Pago();
        pago2.setMonto(12000.0);
        pago2.setMetodo("TARJETA");
        pago2.setEstado("CONFIRMADO");
        pago2.setFecha(LocalDateTime.now());
        ventaPrueba.agregarPago(pago2);

        ventaRepository.save(ventaPrueba);

        System.out.println("🔥 DATA MOCK CARGADA");
    }
}