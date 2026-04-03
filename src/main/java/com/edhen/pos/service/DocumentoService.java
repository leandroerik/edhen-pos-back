package com.edhen.pos.service;

import com.edhen.pos.entity.DocumentoVenta;
import com.edhen.pos.entity.Pago;
import com.edhen.pos.entity.Venta;
import com.edhen.pos.entity.VentaDetalle;
import com.edhen.pos.repository.DocumentoVentaRepository;
import com.edhen.pos.repository.VentaRepository;
import com.edhen.pos.util.ImagenUtil;
import com.edhen.pos.util.QRCodeUtil;
import com.google.zxing.WriterException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.kernel.geom.PageSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoVentaRepository documentoRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ImagenUtil imagenUtil;



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

    public List<DocumentoVenta> listarDocumentos() {
        return documentoRepository.findAll();
    }

    public DocumentoVenta obtenerDocumentoPorId(Long id) {
        return documentoRepository.findById(id).orElseThrow();
    }

    public List<DocumentoVenta> listarDocumentosPorVenta(Long ventaId) {
        // Assuming DocumentoVenta has a venta field, we can filter by venta.id
        // But since it's not in the repository, I'll use a simple filter
        return documentoRepository.findAll().stream()
                .filter(doc -> doc.getVenta().getId().equals(ventaId))
                .toList();
    }

    public DocumentoVenta actualizarDocumento(DocumentoVenta documento) {
        return documentoRepository.save(documento);
    }

    public void eliminarDocumento(Long id) {
        documentoRepository.deleteById(id);
    }

    public String obtenerTextoTicketPorVentaId(Long ventaId) {
        Venta venta = ventaRepository.findById(ventaId).orElseThrow();
        return generarTextoTicket(venta);
    }

    public byte[] generarPdfTicket(Venta venta) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Configurar página para ticket (ancho reducido)
        pdfDoc.setDefaultPageSize(new PageSize(226, 1000)); // ~80mm ancho

        // 🎨 Intentar cargar y agregar logo
        try {
            if (imagenUtil.logoExiste()) {
                Image logo = imagenUtil.cargarLogo();
                logo.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER);
                document.add(logo);
            }
        } catch (Exception e) {
            System.out.println("⚠️ No se pudo cargar el logo: " + e.getMessage());
        }

        // Header - Logo/Nombre empresa
        Paragraph header = new Paragraph("EDHEN")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(16)
                .setBold();
        document.add(header);

        Paragraph subtitulo = new Paragraph("Sistema POS")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(10);
        document.add(subtitulo);

        // Dirección/Teléfono
        Paragraph info = new Paragraph("Flores - Tel: (000) 000-0000")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(8);
        document.add(info);

        // Separador
        document.add(new Paragraph("========================================")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(8));

        // Fecha y número de ticket
        Paragraph fecha = new Paragraph("Fecha: " + venta.getFecha().toString().substring(0, 19))
                .setFontSize(9);
        document.add(fecha);

        // Buscar documento para obtener número
        DocumentoVenta documento = documentoRepository.findAll().stream()
                .filter(doc -> doc.getVenta().getId().equals(venta.getId()))
                .findFirst().orElse(null);

        if (documento != null) {
            Paragraph numero = new Paragraph("Ticket N°: " + documento.getNumero())
                    .setFontSize(9);
            document.add(numero);
        }

        // Cliente
        Paragraph cliente = new Paragraph("Cliente: " + venta.getCliente().getNombre())
                .setFontSize(9);
        document.add(cliente);

        // Usuario
        Paragraph usuario = new Paragraph("Atendido por: " + venta.getUsuario().getNombre())
                .setFontSize(9);
        document.add(usuario);

        // Separador
        document.add(new Paragraph("----------------------------------------")
                .setTextAlignment(TextAlignment.CENTER));

        // Header de items
        Paragraph headerItems = new Paragraph("Cant    Descripción                    Precio    Total")
                .setFontSize(8)
                .setBold();
        document.add(headerItems);

        document.add(new Paragraph("----------------------------------------")
                .setTextAlignment(TextAlignment.CENTER));

        // Items
        double subtotal = 0;
        for (VentaDetalle d : venta.getDetalles()) {
            String cantidad = String.format("%-3s", d.getCantidad());
            String descripcion = String.format("%-25s", d.getSku().getProducto().getNombre() + " " +
                    (d.getSku().getCodigoBarra().contains("-") ?
                     d.getSku().getCodigoBarra().substring(d.getSku().getCodigoBarra().lastIndexOf("-")) : ""));
            String precio = String.format("%8.0f", d.getPrecioUnitario());
            String total = String.format("%8.0f", d.getPrecioUnitario() * d.getCantidad());

            Paragraph item = new Paragraph(cantidad + " " + descripcion + " " + precio + " " + total)
                    .setFontSize(8);
            document.add(item);

            subtotal += d.getPrecioUnitario() * d.getCantidad();
        }

        // Separador
        document.add(new Paragraph("----------------------------------------")
                .setTextAlignment(TextAlignment.CENTER));

        // Total
        Paragraph total = new Paragraph(String.format("TOTAL: $%.0f", venta.getTotal()))
                .setTextAlignment(TextAlignment.RIGHT)
                .setFontSize(12)
                .setBold();
        document.add(total);

        // Pagos
        if (!venta.getPagos().isEmpty()) {
            document.add(new Paragraph("Pagos:")
                    .setFontSize(9)
                    .setBold());

            for (Pago pago : venta.getPagos()) {
                Paragraph pagoInfo = new Paragraph(pago.getMetodo() + ": $" + String.format("%.0f", pago.getMonto()))
                        .setFontSize(8);
                document.add(pagoInfo);
            }
        }

        // Footer
        document.add(new Paragraph("========================================")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(8));

        Paragraph footer = new Paragraph("¡Gracias por su compra!\nVuelva pronto")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(9)
                .setBold();
        document.add(footer);

        // Timestamp
        Paragraph timestamp = new Paragraph("Generado: " + java.time.LocalDateTime.now().toString().substring(0, 19))
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(6);
        document.add(timestamp);

        // QR Code con detalles completos
        try {
            StringBuilder qrData = new StringBuilder();
            qrData.append("=== EDHEN POS ===\n");
            qrData.append("Venta ID: ").append(venta.getId()).append("\n");
            qrData.append("Fecha: ").append(venta.getFecha().toString().substring(0, 19)).append("\n");
            qrData.append("Cliente: ").append(venta.getCliente().getNombre()).append("\n");
            qrData.append("---\n");
            
            // Detalles de items
            qrData.append("ITEMS:\n");
            for (VentaDetalle d : venta.getDetalles()) {
                qrData.append(d.getCantidad()).append("x ").append(d.getSku().getProducto().getNombre());
                qrData.append(" $").append(String.format("%.0f", d.getPrecioUnitario())).append("\n");
            }
            
            qrData.append("---\n");
            qrData.append("TOTAL: $").append(String.format("%.0f", venta.getTotal())).append("\n");
            
            // Detalles de pagos
            qrData.append("PAGOS:\n");
            for (Pago pago : venta.getPagos()) {
                qrData.append(pago.getMetodo()).append(": $").append(String.format("%.0f", pago.getMonto())).append("\n");
            }
            
            qrData.append("---\n");
            qrData.append("Usuario: ").append(venta.getUsuario().getNombre()).append("\n");
            qrData.append("Tienda: ").append(venta.getTienda().getNombre()).append("\n");
            qrData.append("Estado: ").append(venta.getEstado()).append("\n");
            
            byte[] qrCode = QRCodeUtil.generarQRCode(qrData.toString(), 150, 150);
            Image qrImage = ImagenUtil.convertirByteArrayAImagen(qrCode);
            qrImage.setWidth(120);
            qrImage.setHeight(120);
            qrImage.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER);
            document.add(qrImage);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        document.close();
        return baos.toByteArray();
    }

    public byte[] obtenerPdfTicketPorVentaId(Long ventaId) throws Exception {
        Venta venta = ventaRepository.findById(ventaId).orElseThrow();
        return generarPdfTicket(venta);
    }
}
