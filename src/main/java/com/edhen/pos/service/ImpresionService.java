package com.edhen.pos.service;

import com.edhen.pos.entity.Venta;
import com.edhen.pos.entity.VentaDetalle;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.print.*;

@Service
public class ImpresionService {

    public void imprimirTicket(Venta venta) {

        PrinterJob job = PrinterJob.getPrinterJob();

        job.setPrintable((graphics, pageFormat, pageIndex) -> {

            if (pageIndex > 0) return Printable.NO_SUCH_PAGE;

            Graphics2D g2d = (Graphics2D) graphics;
            int y = 20;

            g2d.drawString("==== EDHEN ====", 10, y);
            y += 20;

            g2d.drawString("Fecha: " + venta.getFecha(), 10, y);
            y += 20;

            for (VentaDetalle d : venta.getDetalles()) {
                g2d.drawString(d.getSku().getCodigoBarra(), 10, y);
                y += 15;

                g2d.drawString(
                        d.getCantidad() + " x $" + d.getPrecioUnitario(),
                        10,
                        y
                );
                y += 20;
            }

            g2d.drawString("----------------", 10, y);
            y += 20;

            g2d.drawString("TOTAL: $" + venta.getTotal(), 10, y);

            return Printable.PAGE_EXISTS;
        });

        try {
            job.print();
        } catch (PrinterException e) {
            throw new RuntimeException("Error al imprimir ticket", e);
        }
    }
}