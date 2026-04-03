package com.edhen.pos.service;

import com.edhen.pos.entity.DocumentoVenta;
import com.edhen.pos.entity.Venta;
import com.edhen.pos.entity.VentaDetalle;
import com.edhen.pos.repository.DocumentoVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoVentaRepository documentoRepository;



    public DocumentoVenta crearDocumento(Venta venta) {

        DocumentoVenta doc = new DocumentoVenta();

        doc.setVenta(venta);
        doc.setFecha(LocalDateTime.now());
        doc.setTotal(venta.getTotal());

        // tipo automático
        if ("ONLINE".equals(venta.getTipoVenta())) {
            doc.setTipo("REMITO");
        } else {
            doc.setTipo("TICKET");
        }

        doc.setNumero(generarNumero(doc.getTipo()));

        return documentoRepository.save(doc);
    }

    private String generarNumero(String tipo) {
        return tipo.substring(0, 3) + "-" + System.currentTimeMillis();
    }

    public String generarTextoTicket(Venta venta) {

        StringBuilder sb = new StringBuilder();

        sb.append("==== EDHEN ====\n");
        sb.append("Fecha: ").append(venta.getFecha()).append("\n\n");

        for (VentaDetalle d : venta.getDetalles()) {
            sb.append("SKU: ").append(d.getSku().getCodigoBarra()).append("\n");
            sb.append("Cant: ").append(d.getCantidad())
                    .append(" x $").append(d.getPrecioUnitario()).append("\n\n");
        }

        sb.append("----------------\n");
        sb.append("TOTAL: $").append(venta.getTotal()).append("\n");
        sb.append("================\n");

        return sb.toString();
    }
}