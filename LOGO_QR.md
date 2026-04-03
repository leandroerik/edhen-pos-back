# Logo y QR en Tickets PDF

## 📋 Instrucciones para Agregar tu Logo

### Paso 1: Guardar el Logo
Guarda tu logo en la siguiente carpeta:
```
D:\edhen\sistema-edhen\edhen-pos-back\src\main\resources\static\logo.png
```

**Requisitos del logo:**
- Formato: PNG, JPG, o GIF
- Tamaño recomendado: 200x200 px mínimo
- Fondo: Transparente (PNG recomendado)
- Nombre: `logo.png`

### Paso 2: Crear la Carpeta
Si la carpeta `static` no existe, créala en:
```
src/main/resources/static/
```

### Paso 3: Configurar (Opcional)
Si quieres usar una ruta diferente o un nombre diferente, edita `application.properties`:
```properties
app.logo.path=src/main/resources/static/logo.png
```

## 🎨 Características Implementadas:

### ✅ **QR Code Automático**
- Se genera automáticamente en cada ticket
- Contiene: ID de venta + Total
- Posicionado al pie del ticket
- Tamaño: 100x100 puntos

### ✅ **Logo de Empresa**
- Se carga desde archivo (cuando lo subas)
- Tamaño: 80x80 puntos (~28mm)
- Posicionado en el header del ticket
- Manejo de errores si no existe

## 📦 Archivos Creados:

1. **`QRCodeUtil.java`** - Utilidades para generar QR codes
2. **`ImagenUtil.java`** - Utilidades para manejar imágenes
3. **`LOGO_QR.md`** - Este archivo

## 🚀 Flujo Actual del PDF:

```
┌─────────────────────────────┐
│        EDHEN LOGO           │ ← Se agregará aquí cuando subas el logo
├─────────────────────────────┤
│         Sistema POS         │
│   Flores - Tel: (000)...    │
├─────────────────────────────┤
│  Fecha: 2026-04-03 10:30   │
│  Ticket N°: TIC-1234567     │
│  Cliente: Cliente General   │
│  Atendido por: Admin        │
├─────────────────────────────┤
│ Cant | Descripción | Total  │
├─────────────────────────────┤
│  2   | Musculosa    | 24000 │
├─────────────────────────────┤
│         TOTAL: $24,000      │
│                             │
│  EFECTIVO: $24,000          │
├─────────────────────────────┤
│  ¡Gracias por su compra!    │
│         [QR CODE]           │ ← QR generado automáticamente
│      Vuelva pronto          │
└─────────────────────────────┘
```

## 📷 Para Subir tu Logo:

Tienes varias opciones:

### **Opción 1: Copiar archivo directamente**
Copia `logo.png` en:
```
D:\edhen\sistema-edhen\edhen-pos-back\src\main\resources\static\logo.png
```

### **Opción 2: Crear desde URL**
Si tienes el logo en URL, puedo ayudarte a descargarlo.

### **Opción 3: Enviarlo como archivo**
Comparte el archivo y te ayudaré a guardarlo en la ubicación correcta.

## ✨ Qué Falta:

1. **Tu logo** - Comparte el archivo o indica dónde guardarlo
2. **Compilar y probar** - Cuando tengas el logo, compilamos y probamos

## 🎯 Próximos Pasos:

1. **Sube tu logo** (PNG preferiblemente)
2. Guárdalo en `src/main/resources/static/logo.png`
3. Compila: `mvn compile`
4. Ejecuta: `mvn spring-boot:run`
5. Prueba: `curl -o ticket.pdf http://localhost:8080/documentos/ticket/1/pdf`

¡Comparte el logo y seguimos! 📸
