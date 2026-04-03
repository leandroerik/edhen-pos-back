# Personalización de Tickets PDF - POS EDHEN

## 🎨 Diseño Actual del PDF

El ticket PDF actual incluye:
- **Header**: Logo "EDHEN" y subtítulo
- **Información**: Dirección y teléfono
- **Datos de venta**: Fecha, número de ticket, cliente, usuario
- **Tabla de items**: Cantidad, descripción, precio, total
- **Total**: Monto total destacado
- **Pagos**: Detalle de métodos de pago
- **Footer**: Mensaje de agradecimiento

## 🛠️ Cómo Personalizar el Diseño

### 1. **Modificar Información de Empresa**
En `DocumentoService.generarPdfTicket()`:

```java
// Cambiar nombre de empresa
Paragraph header = new Paragraph("TU EMPRESA")
    .setTextAlignment(TextAlignment.CENTER)
    .setFontSize(16)
    .setBold();

// Cambiar dirección y teléfono
Paragraph info = new Paragraph("Tu Dirección - Tel: (123) 456-7890")
    .setTextAlignment(TextAlignment.CENTER)
    .setFontSize(8);
```

### 2. **Agregar Logo**
Para agregar un logo, necesitas:
```java
// Agregar import
import com.itextpdf.layout.element.Image;
import com.itextpdf.io.image.ImageDataFactory;

// En el método generarPdfTicket()
Image logo = new Image(ImageDataFactory.create("ruta/a/tu/logo.png"));
logo.setWidth(100); // ajustar tamaño
document.add(logo);
```

### 3. **Cambiar Colores y Estilos**
```java
// Cambiar color del texto
Paragraph header = new Paragraph("EDHEN")
    .setTextAlignment(TextAlignment.CENTER)
    .setFontSize(16)
    .setBold()
    .setFontColor(com.itextpdf.kernel.colors.ColorConstants.BLUE); // cambiar color

// Agregar fondo
header.setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY);
```

### 4. **Modificar Layout de Items**
El formato tabular actual usa `String.format()` para alinear columnas.
Puedes cambiarlo a una tabla real de iText:

```java
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;

// Crear tabla
Table table = new Table(4); // 4 columnas
table.addCell(new Cell().add(new Paragraph("Cant")));
table.addCell(new Cell().add(new Paragraph("Descripción")));
table.addCell(new Cell().add(new Paragraph("Precio")));
table.addCell(new Cell().add(new Paragraph("Total")));

// Agregar filas
for (VentaDetalle d : venta.getDetalles()) {
    table.addCell(new Cell().add(new Paragraph(String.valueOf(d.getCantidad()))));
    table.addCell(new Cell().add(new Paragraph(d.getSku().getProducto().getNombre())));
    table.addCell(new Cell().add(new Paragraph("$" + d.getPrecioUnitario())));
    table.addCell(new Cell().add(new Paragraph("$" + (d.getPrecioUnitario() * d.getCantidad()))));
}

document.add(table);
```

### 5. **Personalizar Mensajes**
```java
// Cambiar mensaje de footer
Paragraph footer = new Paragraph("¡Gracias por elegirnos!\nVisítanos en www.tuempresa.com")
    .setTextAlignment(TextAlignment.CENTER)
    .setFontSize(9)
    .setBold();
```

### 6. **Agregar Información Adicional**
```java
// Agregar CUIT/RUC
Paragraph cuit = new Paragraph("CUIT: 12-34567890-1")
    .setTextAlignment(TextAlignment.CENTER)
    .setFontSize(8);
document.add(cuit);

// Agregar condiciones de venta
Paragraph condiciones = new Paragraph("Condiciones: Cambios dentro de 30 días")
    .setFontSize(7);
document.add(condiciones);
```

### 7. **Cambiar Tamaño de Página**
```java
// Para diferentes anchos de impresora térmica
pdfDoc.setDefaultPageSize(new PageSize(200, 1000)); // 58mm
pdfDoc.setDefaultPageSize(new PageSize(250, 1000)); // 80mm estándar
pdfDoc.setDefaultPageSize(new PageSize(300, 1000)); // 100mm
```

## 📋 Ejemplos de Diseños Comunes

### **Ticket Básico (58mm)**
- Logo pequeño o solo texto
- Información esencial
- Formato compacto

### **Ticket Estándar (80mm)**
- Logo más grande
- Más información (CUIT, dirección completa)
- Mejor formato tabular

### **Remito Completo (100mm+)**
- Logo grande
- Información detallada del cliente
- Tabla completa con códigos
- Condiciones de venta
- Espacio para firma

## 🔧 Próximos Pasos

1. **Comparte tus ejemplos** de tickets que te gusten
2. **Indica qué elementos** quieres agregar/modificar
3. **Especifica el ancho** de tu impresora térmica
4. **Menciona colores** o estilos específicos

¡Puedo adaptar el diseño a tus necesidades exactas! 🎨
