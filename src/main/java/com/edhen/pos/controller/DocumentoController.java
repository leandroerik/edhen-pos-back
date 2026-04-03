package com.edhen.pos.controller;

import com.edhen.pos.entity.DocumentoVenta;
import com.edhen.pos.service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documentos")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    @GetMapping("/ticket/{ventaId}")
    public String verTicket(@PathVariable Long ventaId) {
        return documentoService.obtenerTextoTicketPorVentaId(ventaId);
    }

    @GetMapping("/venta/{ventaId}")
    public List<DocumentoVenta> listarDocumentosPorVenta(@PathVariable Long ventaId) {
        return documentoService.listarDocumentosPorVenta(ventaId);
    }

    @GetMapping
    public List<DocumentoVenta> listar() {
        return documentoService.listarDocumentos();
    }

    @GetMapping("/{id}")
    public DocumentoVenta obtener(@PathVariable Long id) {
        return documentoService.obtenerDocumentoPorId(id);
    }

    @PutMapping("/{id}")
    public DocumentoVenta actualizar(@PathVariable Long id, @RequestBody DocumentoVenta documento) {
        if (!documentoService.obtenerDocumentoPorId(id).equals(documento)) {
            throw new RuntimeException("Documento no encontrado");
        }
        documento.setId(id);
        return documentoService.actualizarDocumento(documento);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        documentoService.eliminarDocumento(id);
    }

    @GetMapping("/ticket/{ventaId}/pdf")
    public ResponseEntity<byte[]> descargarTicketPdf(@PathVariable Long ventaId) throws Exception {
        byte[] pdfBytes = documentoService.obtenerPdfTicketPorVentaId(ventaId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "ticket_" + ventaId + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}