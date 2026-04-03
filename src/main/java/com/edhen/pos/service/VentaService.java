package com.edhen.pos.service;

import com.edhen.pos.dto.ItemVentaRequest;
import com.edhen.pos.dto.PagoRequest;
import com.edhen.pos.dto.VentaRequest;
import com.edhen.pos.entity.*;
import com.edhen.pos.exception.BusinessException;
import com.edhen.pos.exception.ResourceNotFoundException;
import com.edhen.pos.repository.SKURepository;
import com.edhen.pos.repository.VentaRepository;
import com.edhen.pos.repository.ClienteRepository;
import com.edhen.pos.repository.UsuarioRepository;
import com.edhen.pos.repository.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        Cliente cliente = clienteRepository.findById(request.clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", request.clienteId));
        Usuario usuario = usuarioRepository.findById(request.usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", request.usuarioId));
        Tienda tienda = tiendaRepository.findById(request.tiendaId)
                .orElseThrow(() -> new ResourceNotFoundException("Tienda", "id", request.tiendaId));

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

            SKU sku = skuRepository.findById(item.skuId)
                    .orElseThrow(() -> new ResourceNotFoundException("SKU", "id", item.skuId));

            // 🔒 validar stock
            if (sku.getStock() < item.cantidad) {
                throw new BusinessException("Stock insuficiente para SKU: " + sku.getCodigoBarra() + 
                        ". Stock disponible: " + sku.getStock() + ", solicitado: " + item.cantidad);
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
            throw new BusinessException(
                    "El total de pagos ($" + totalPagos + ") no coincide con el total ($" + total + ")"
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

    public Venta obtenerVentaPorId(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta", "id", id));
    }

    public Venta actualizarVenta(Venta venta) {
        // TODO: Actualizar venta requiere lógica compleja para stock, pagos, etc.
        // Por simplicidad, solo guardamos
        return ventaRepository.save(venta);
    }

    public void eliminarVenta(Long id) {
        // TODO: Eliminar venta puede requerir restaurar stock, etc.
        ventaRepository.deleteById(id);
    }
    
    // 🔍 BÚSQUEDAS Y FILTROS
    
    /**
     * Buscar ventas por rango de fechas
     */
    public List<Venta> buscarVentasPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(23, 59, 59);
        return ventaRepository.findByFechaBetween(inicio, fin);
    }
    
    /**
     * Buscar ventas por cliente
     */
    public List<Venta> buscarVentasPorCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", clienteId));
        return ventaRepository.findAll().stream()
                .filter(v -> v.getCliente().getId().equals(clienteId))
                .toList();
    }
    
    /**
     * Buscar ventas por usuario (cajero)
     */
    public List<Venta> buscarVentasPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", usuarioId));
        return ventaRepository.findAll().stream()
                .filter(v -> v.getUsuario().getId().equals(usuarioId))
                .toList();
    }
    
    /**
     * Buscar ventas por tipo (LOCAL, ONLINE)
     */
    public List<Venta> buscarVentasPorTipo(String tipo) {
        return ventaRepository.findAll().stream()
                .filter(v -> v.getTipoVenta().equals(tipo))
                .toList();
    }
    
    /**
     * Buscar ventas por estado
     */
    public List<Venta> buscarVentasPorEstado(String estado) {
        return ventaRepository.findAll().stream()
                .filter(v -> v.getEstado().equals(estado))
                .toList();
    }
}