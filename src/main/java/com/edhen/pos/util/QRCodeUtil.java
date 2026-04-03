package com.edhen.pos.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class QRCodeUtil {

    /**
     * Genera un QR code como objeto Image de iText
     * @param contenido - Contenido del QR (ej: número de ticket, venta, etc)
     * @param ancho - Ancho en píxeles
     * @param alto - Alto en píxeles
     * @return Image de iText listo para agregar al PDF
     */
    public static Image generarQRImage(String contenido, int ancho, int alto) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(contenido, BarcodeFormat.QR_CODE, ancho, alto);
        
        // Convertir a imagen en bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "png", baos);
        
        // Crear imagen iText desde los bytes
        Image qrImage = new Image(ImageDataFactory.create(baos.toByteArray()));
        qrImage.setWidth(100); // 100 pts (~35mm)
        qrImage.setHeight(100);
        
        return qrImage;
    }

    /**
     * Genera un QR code y lo guarda como archivo
     * @param contenido - Contenido del QR
     * @param ruta - Ruta donde guardar el QR
     * @param ancho - Ancho en píxeles
     * @param alto - Alto en píxeles
     */
    public static void guardarQRCode(String contenido, String ruta, int ancho, int alto) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(contenido, BarcodeFormat.QR_CODE, ancho, alto);
        
        Path path = FileSystems.getDefault().getPath(ruta);
        MatrixToImageWriter.writeToPath(bitMatrix, "png", path);
    }

    /**
     * Genera un QR code y devuelve el archivo como un arreglo de bytes
     * @param contenido - Contenido del QR
     * @param ancho - Ancho en píxeles
     * @param alto - Alto en píxeles
     * @return arreglo de bytes con la imagen del QR
     */
    public static byte[] generarQRCode(String contenido, int ancho, int alto) throws WriterException, java.io.IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(contenido, BarcodeFormat.QR_CODE, ancho, alto);
        
        // Convertir a imagen en bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "png", baos);
        
        return baos.toByteArray();
    }
}
