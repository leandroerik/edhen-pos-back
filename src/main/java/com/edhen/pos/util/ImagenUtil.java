package com.edhen.pos.util;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ImagenUtil {

    @Value("${app.logo.path:src/main/resources/static/logo.jpg}")
    private String logoPath;

    /**
     * Carga el logo de la empresa desde el archivo configurado
     * @return Image de iText listo para agregar al PDF
     */
    public Image cargarLogo() throws IOException {
        try {
            Image logo = new Image(ImageDataFactory.create(logoPath));
            logo.setWidth(80); // 80 pts (~28mm)
            logo.setHeight(80);
            return logo;
        } catch (IOException e) {
            System.out.println("⚠️ Logo no encontrado en: " + logoPath);
            throw e;
        }
    }

    /**
     * Verifica si el logo existe
     * @return true si existe, false si no
     */
    public boolean logoExiste() {
        return new java.io.File(logoPath).exists();
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public static com.itextpdf.layout.element.Image convertirByteArrayAImagen(byte[] imageBytes) throws IOException {
        return new com.itextpdf.layout.element.Image(ImageDataFactory.create(imageBytes));
    }
}
