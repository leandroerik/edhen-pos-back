package com.edhen.pos.service;

import com.edhen.pos.dto.ItemVentaRequest;
import com.edhen.pos.dto.PagoRequest;
import com.edhen.pos.dto.VentaRequest;
import com.edhen.pos.entity.*;
import com.edhen.pos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VentaService {

    @Autowired
    private SKURepository skuRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private ImpresionService impresionService;

    public Venta crearVenta(VentaRequest request) {

        // 🔎 buscar relaciones
        Cliente cliente = clienteRepository.findById(request.clienteId).orElseThrow();
        Usuario usuario = usuarioRepository.findById(request.usuarioId).orElseThrow();
        Tienda tienda = tiendaRepository.findById(request.tiendaId).orElseThrow();

        // 🧾 crear venta
        Venta venta = new Venta();
        venta.setFecha(LocalDateTime.now());
        venta.setTipoVenta(request.tipoVenta);
        venta.setEstado("COMPLETADA");
        venta.setCliente(cliente);
        venta.setUsuario(usuario);
        venta.setTienda(tienda);

        double total = 0;

        // ======================
        // 🛒 ITEMS (DETALLES)
        // ======================
        for (ItemVentaRequest item : request.items) {

            SKU sku = skuRepository.findById(item.skuId).orElseThrow();

            // 🔒 validar stock
            if (sku.getStock() < item.cantidad) {
                throw new RuntimeException("Stock insuficiente para SKU " + sku.getId());
            }

            double precioOriginal = sku.getPrecio();

            double precioFinal = (item.precioOverride != null)
                    ? item.precioOverride
                    : precioOriginal;

            // 📦 crear detalle
            VentaDetalle detalle = new VentaDetalle();
            detalle.setSku(sku);
            detalle.setCantidad(item.cantidad);
            detalle.setPrecioOriginal(precioOriginal);
            detalle.setPrecioUnitario(precioFinal);
            detalle.setCostoUnitario(sku.getCosto());

            // 🔗 relación correcta
            venta.agregarDetalle(detalle);

            // 💰 total acumulado
            total += precioFinal * item.cantidad;

            // 📉 descontar stock
            sku.setStock(sku.getStock() - item.cantidad);
        }

        venta.setTotal(total);

        // ======================
        // 💳 PAGOS
        // ======================
        double totalPagos = 0;

        for (PagoRequest p : request.pagos) {

            Pago pago = new Pago();
            pago.setMonto(p.monto);
            pago.setMetodo(p.metodo);
            pago.setEstado("CONFIRMADO");
            pago.setFecha(LocalDateTime.now());

            venta.agregarPago(pago);

            totalPagos += p.monto;
        }

        // 🔒 validar pagos
        if (totalPagos != total) {
            throw new RuntimeException(
                    "El total de pagos (" + totalPagos + ") no coincide con el total (" + total + ")"
            );
        }

        // ======================
        // 💾 GUARDAR
        // ======================
        Venta ventaGuardada = ventaRepository.save(venta);

        // ======================
        // 🧾 DOCUMENTO
        // ======================
        documentoService.crearDocumento(ventaGuardada);

        // 🖨️ imprimir
        impresionService.imprimirTicket(ventaGuardada);

        return ventaGuardada;
    }

    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }
}