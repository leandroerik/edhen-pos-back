# ✅ Setup Logo y QR - COMPLETADO

## 📋 Estado Actual:

✅ **Dependencias agregadas:**
- iText (para PDF)
- ZXing (para QR)

✅ **Código implementado:**
- `QRCodeUtil.java` - Generador de QR
- `ImagenUtil.java` - Manejador de imágenes  
- `DocumentoService.java` - Integración de logo y QR

✅ **QR automático** - Se genera en cada ticket

🔴 **FALTA:** Guardar tu logo

## 📸 Cómo Guardar el Logo:

### Paso 1: Crear la carpeta
```
D:\edhen\sistema-edhen\edhen-pos-back\src\main\resources\static\
```

### Paso 2: Guardar el logo
Copia el logo EDHEN que compartiste en:
```
D:\edhen\sistema-edhen\edhen-pos-back\src\main\resources\static\logo.jpg
```

### Nombre exacto: `logo.jpg`

## 🚀 Para Probar:

### 1. Guarda el logo en la carpeta

### 2. Compila:
```bash
cd D:\edhen\sistema-edhen\edhen-pos-back
mvn compile -q
```

### 3. Ejecuta:
```bash
mvn spring-boot:run
```

### 4. Prueba:
```bash
# Crear una venta
curl -X POST http://localhost:8080/ventas \
  -H "Content-Type: application/json" \
  -d '{
    "clienteId": 1,
    "usuarioId": 1,
    "tiendaId": 1,
    "tipoVenta": "LOCAL",
    "items": [{"skuId": 4, "cantidad": 2}],
    "pagos": [{"monto": 24000.0, "metodo": "EFECTIVO"}]
  }'

# Descargar el PDF con logo y QR
curl -o ticket_con_logo.pdf http://localhost:8080/documentos/ticket/1/pdf
```

## 📄 El PDF incluirá:

```
┌─────────────────────────────┐
│      [LOGO EDHEN]           │ ← Tu logo aquí
│         EDHEN               │
│      Sistema POS            │
│  Flores - Tel: (000)...     │
├─────────────────────────────┤
│    Datos de la venta        │
│    Items y precios          │
│                             │
│      TOTAL: $24,000         │
│                             │
│       [QR CODE]             │ ← QR automático
│  ¡Gracias por su compra!    │
└─────────────────────────────┘
```

## 🎯 Próximos Pasos:

1. **Guarda el logo** en `src/main/resources/static/logo.jpg`
2. **Compila y ejecuta**
3. **Prueba y verifica** que el logo aparezca en el PDF

¡Listo para usar! 🎉
