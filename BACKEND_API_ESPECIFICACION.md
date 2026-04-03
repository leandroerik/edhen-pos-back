# 📋 ESPECIFICACIÓN DEL BACKEND - SISTEMA POS EDHEN

## 🔗 INFORMACIÓN GENERAL

- **Base URL**: `http://localhost:8080`
- **Base de datos**: SQLite (pos.db)
- **Framework**: Spring Boot 3.5.13
- **Java Version**: 21

---

## 📊 MODELOS DE DATOS (ENTIDADES)

### 1. **Cliente**
```json
{
  "id": 1,
  "nombre": "Juan García",
  "telefono": "555-1234",
  "email": "juan@example.com",
  "tipo": "LOCAL"  // LOCAL o ONLINE
}
```
**Campos**:
- `id`: Long (autoincremental)
- `nombre`: String
- `telefono`: String
- `email`: String
- `tipo`: String (LOCAL / ONLINE)

---

### 2. **Producto**
```json
{
  "id": 1,
  "nombre": "Zapatos",
  "descripcion": "Zapatos de cuero",
  "activo": true,
  "skus": [
    {
      "id": 1,
      "precio": 99.99,
      "costo": 50.00,
      "stock": 15,
      "codigoBarra": "7501234567890",
      "fechaActualizacionPrecio": "2026-04-03T10:30:00"
    }
  ]
}
```
**Campos**:
- `id`: Long (autoincremental)
- `nombre`: String
- `descripcion`: String
- `activo`: Boolean
- `skus`: List<SKU> (relación OneToMany)

---

### 3. **SKU** (Stock Keeping Unit)
```json
{
  "id": 1,
  "precio": 99.99,
  "costo": 50.00,
  "stock": 15,
  "codigoBarra": "7501234567890",
  "fechaActualizacionPrecio": "2026-04-03T10:30:00",
  "producto": { }
}
```
**Campos**:
- `id`: Long (autoincremental)
- `precio`: Double
- `costo`: Double
- `stock`: Integer
- `codigoBarra`: String
- `fechaActualizacionPrecio`: LocalDateTime
- `producto`: Producto (relación ManyToOne)

---

### 4. **Venta**
```json
{
  "id": 1,
  "fecha": "2026-04-03T15:30:00",
  "total": 299.97,
  "tipoVenta": "LOCAL",  // LOCAL o ONLINE
  "estado": "COMPLETADA",
  "cliente": { "id": 1, "nombre": "Juan García" },
  "usuario": { "id": 1 },
  "tienda": { "id": 1 },
  "detalles": [ { } ],
  "pagos": [ { } ]
}
```
**Campos**:
- `id`: Long (autoincremental)
- `fecha`: LocalDateTime
- `total`: Double
- `tipoVenta`: String (LOCAL / ONLINE)
- `estado`: String (PENDIENTE / COMPLETADA / CANCELADA)
- `cliente`: Cliente (relación ManyToOne)
- `usuario`: Usuario (relación ManyToOne)
- `tienda`: Tienda (relación ManyToOne)
- `detalles`: List<VentaDetalle>
- `pagos`: List<Pago>

---

### 5. **VentaDetalle**
```json
{
  "id": 1,
  "cantidad": 3,
  "precioOriginal": 99.99,
  "precioUnitario": 99.99,
  "costoUnitario": 50.00,
  "sku": { "id": 1 },
  "venta": { "id": 1 }
}
```
**Campos**:
- `id`: Long (autoincremental)
- `cantidad`: Integer
- `precioOriginal`: Double
- `precioUnitario`: Double
- `costoUnitario`: Double
- `sku`: SKU (relación ManyToOne)
- `venta`: Venta (relación ManyToOne)

---

### 6. **Pago**
```json
{
  "id": 1,
  "monto": 299.97,
  "metodo": "EFECTIVO",  // EFECTIVO, TRANSFERENCIA, TARJETA
  "estado": "CONFIRMADO",
  "fecha": "2026-04-03T15:30:00",
  "venta": { "id": 1 }
}
```
**Campos**:
- `id`: Long (autoincremental)
- `monto`: Double
- `metodo`: String (EFECTIVO / TRANSFERENCIA / TARJETA)
- `estado`: String
- `fecha`: LocalDateTime
- `venta`: Venta (relación ManyToOne)

---

### 7. **Usuario**
- `id`: Long
- (Otros campos según tu implementación)

---

### 8. **Tienda**
- `id`: Long
- (Otros campos según tu implementación)

---

### 9. **DocumentoVenta**
```json
{
  "id": 1,
  "ventaId": 1,
  "tipo": "TICKET",
  "contenido": "Ticket en formato texto...",
  "fechaGeneracion": "2026-04-03T15:30:00"
}
```

---

## 🔌 ENDPOINTS DEL API

### 🔷 CLIENTES (`/clientes`)

| Método | Endpoint | Descripción | Body |
|--------|----------|-------------|------|
| **POST** | `/clientes` | Crear cliente | `Cliente` |
| **GET** | `/clientes` | Listar todos | - |
| **GET** | `/clientes/{id}` | Obtener por ID | - |
| **PUT** | `/clientes/{id}` | Actualizar | `Cliente` |
| **DELETE** | `/clientes/{id}` | Eliminar | - |
| **GET** | `/clientes/buscar/nombre?nombre=Juan` | Buscar por nombre | - |
| **GET** | `/clientes/buscar/email?email=juan@example.com` | Buscar por email | - |
| **GET** | `/clientes/buscar/telefono?telefono=555-1234` | Buscar por teléfono | - |
| **GET** | `/clientes/buscar/tipo/{tipo}` | Buscar por tipo (LOCAL/ONLINE) | - |

---

### 🔷 PRODUCTOS (`/productos`)

| Método | Endpoint | Descripción | Body |
|--------|----------|-------------|------|
| **POST** | `/productos` | Crear producto | `Producto` |
| **GET** | `/productos` | Listar todos | - |
| **GET** | `/productos/{id}` | Obtener por ID | - |
| **PUT** | `/productos/{id}` | Actualizar | `Producto` |
| **DELETE** | `/productos/{id}` | Eliminar | - |
| **GET** | `/productos/buscar/nombre?nombre=Zapatos` | Buscar por nombre | - |
| **GET** | `/productos/buscar/descripcion?descripcion=cuero` | Buscar por descripción | - |
| **GET** | `/productos/activos` | Listar productos activos | - |
| **GET** | `/productos/inactivos` | Listar productos inactivos | - |

---

### 🔷 SKUs (`/skus`)

| Método | Endpoint | Descripción | Body |
|--------|----------|-------------|------|
| **POST** | `/skus` | Crear SKU | `SKU` |
| **GET** | `/skus` | Listar todos | - |
| **GET** | `/skus/{id}` | Obtener por ID | - |
| **PUT** | `/skus/{id}` | Actualizar | `SKU` |
| **DELETE** | `/skus/{id}` | Eliminar | - |

---

### 🔷 VENTAS (`/ventas`)

| Método | Endpoint | Descripción | Body |
|--------|----------|-------------|------|
| **POST** | `/ventas` | Crear venta | `VentaRequest` |
| **GET** | `/ventas` | Listar todas | - |
| **GET** | `/ventas/{id}` | Obtener por ID | - |
| **PUT** | `/ventas/{id}` | Actualizar | `Venta` |
| **DELETE** | `/ventas/{id}` | Eliminar | - |
| **GET** | `/ventas/buscar/fecha?fechaInicio=2026-04-01&fechaFin=2026-04-30` | Buscar por rango de fechas | - |
| **GET** | `/ventas/buscar/cliente/{clienteId}` | Buscar por cliente | - |
| **GET** | `/ventas/buscar/usuario/{usuarioId}` | Buscar por usuario | - |
| **GET** | `/ventas/buscar/tipo/{tipo}` | Buscar por tipo (LOCAL/ONLINE) | - |
| **GET** | `/ventas/buscar/estado/{estado}` | Buscar por estado | - |

---

### 📝 CREAR VENTA - Request DTO

**Endpoint**: `POST /ventas`

```json
{
  "clienteId": 1,
  "usuarioId": 1,
  "tiendaId": 1,
  "tipoVenta": "LOCAL",
  "items": [
    {
      "skuId": 1,
      "cantidad": 3,
      "precioOverride": null
    },
    {
      "skuId": 2,
      "cantidad": 1,
      "precioOverride": 49.99
    }
  ],
  "pagos": [
    {
      "monto": 299.97,
      "metodo": "EFECTIVO"
    }
  ]
}
```

**DTOs Utilizados**:
- `VentaRequest`: contenedor principal
- `ItemVentaRequest`: cada item de la venta
  - `skuId`: Long
  - `cantidad`: Integer
  - `precioOverride`: Double (opcional, para aplicar descuentos)
- `PagoRequest`: cada pago
  - `monto`: Double
  - `metodo`: String (EFECTIVO, TRANSFERENCIA, TARJETA)

**Response**: `Venta` con detalles completos

---

### 🔷 DOCUMENTOS (`/documentos`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| **GET** | `/documentos/ticket/{ventaId}` | Obtener texto del ticket |
| **GET** | `/documentos/ticket/{ventaId}/pdf` | Descargar ticket en PDF |
| **GET** | `/documentos` | Listar todos los documentos |
| **GET** | `/documentos/{id}` | Obtener documento por ID |
| **GET** | `/documentos/venta/{ventaId}` | Listar documentos de una venta |
| **PUT** | `/documentos/{id}` | Actualizar documento |
| **DELETE** | `/documentos/{id}` | Eliminar documento |

---

### 📊 REPORTES (`/reportes`)

| Método | Endpoint | Descripción | Parámetros |
|--------|----------|-------------|------------|
| **GET** | `/reportes/ventas` | Ventas por período | `desde`, `hasta` (fecha) |
| **GET** | `/reportes/productos-mas-vendidos` | Top productos | `desde`, `hasta`, `top` (default: 10) |
| **GET** | `/reportes/ganancias` | Ganancias totales | `desde`, `hasta` |
| **GET** | `/reportes/top-clientes` | Top clientes | `desde`, `hasta`, `top` (default: 10) |
| **GET** | `/reportes/inventario` | Reporte de inventario | - |
| **GET** | `/reportes/total-vendido` | Total vendido histórico | - |

---

## 📤 DTOs DE RESPUESTA (REPORTES)

### VentaPeriodoDTO
```json
{
  "fechaInicio": "2026-04-01",
  "fechaFin": "2026-04-30",
  "totalVentas": 5000.00,
  "cantidadTransacciones": 15
}
```

### ProductoVendidoDTO
```json
{
  "productoId": 1,
  "nombre": "Zapatos",
  "cantidadVendida": 25,
  "totalVendido": 2499.75
}
```

### GananciaDTO
```json
{
  "totalIngresos": 5000.00,
  "totalCostos": 2000.00,
  "gananciaTotal": 3000.00,
  "margenGanancia": 60.0
}
```

### ClienteTopDTO
```json
{
  "clienteId": 1,
  "nombre": "Juan García",
  "totalCompras": 500.00,
  "cantidadTransacciones": 5
}
```

### InventarioDTO
```json
{
  "skuId": 1,
  "codigoBarra": "7501234567890",
  "productoNombre": "Zapatos",
  "stock": 15,
  "precio": 99.99,
  "costo": 50.00
}
```

### TotalVendidoDTO
```json
{
  "totalHistorico": 50000.00,
  "ultimaVenta": "2026-04-03T15:30:00"
}
```

---

## 🔒 CONSIDERACIONES DE IMPLEMENTACIÓN

### Estados de Venta
- `PENDIENTE`: Venta creada pero no finalizada
- `COMPLETADA`: Venta completada con pago
- `CANCELADA`: Venta cancelada

### Métodos de Pago
- `EFECTIVO`
- `TRANSFERENCIA`
- `TARJETA`

### Tipos de Cliente
- `LOCAL`: Cliente presencial
- `ONLINE`: Cliente online

### Tipos de Venta
- `LOCAL`: Venta presencial
- `ONLINE`: Venta online

---

## 📌 INFORMACIÓN IMPORTANTE PARA EL FRONTEND

### Flujo de Creación de Venta:
1. Seleccionar cliente (o crear uno nuevo)
2. Agregar items (SKUs) con cantidades
3. Opcionalmente aplicar `precioOverride` para descuentos
4. Ingresar pagos (uno o múltiples)
5. Enviar `VentaRequest` a POST `/ventas`

### Manejo de Respuestas:
- Si hay error: `status != 200`, revisar el message de error
- Si es exitosa: `status 200` con objeto `Venta` completo

### URLs de Descarga:
- Para descargar PDF de ticket: `GET /documentos/ticket/{ventaId}/pdf`
- El endpoint retorna `Content-Type: application/pdf`
- El header `Content-Disposition` contiene el nombre del archivo

### Búsquedas y Filtros:
- Todas las búsquedas usan `@RequestParam` o `@PathVariable`
- Formato de fecha: `ISO 8601` (YYYY-MM-DD) para `@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)`

### Relaciones Entre Entidades:
- **Venta** contiene **VentaDetalle** (líneas de producto)
- **VentaDetalle** hace referencia a **SKU**
- **SKU** hace referencia a **Producto**
- **Venta** hace referencia a **Cliente**, **Usuario**, **Tienda**

---

## ⚠️ NOTAS IMPORTANTES

1. **Inventario**: El `stock` en SKU se actualiza automáticamente al crear una venta
2. **Eliminar Venta**: Puede afectar inventario y documentos - usar con cuidado
3. **Actualizar Venta**: Puede ser riesgoso - considerar lógica adicional antes de actualizar
4. **Generación de Tickets**: Los tickets se generan automáticamente al crear la venta
5. **Uso de IDs**: Todos los IDs en las relaciones son de tipo `Long`

---

## 📍 PARÁMETROS DE FECHA

### Formato Aceptado:
- ISO 8601: `2026-04-03`
- En URLs: `?desde=2026-04-01&hasta=2026-04-30`

### Ejemplo de Búsqueda por Fechas:
```
GET /ventas/buscar/fecha?fechaInicio=2026-04-01&fechaFin=2026-04-30
```

---

## 🎯 CASOS DE USO TÍPICOS

### Dashboard:
```
GET /reportes/total-vendido (última venta y total)
GET /reportes/ventas?desde=today&hasta=today (ventas del día)
GET /reportes/productos-mas-vendidos?desde=today&hasta=today (top 5)
```

### Crear Venta:
```
1. GET /clientes (listar para seleccionar)
2. GET /productos (listar con SKUs)
3. POST /ventas (crear con VentaRequest)
```

### Reportes:
```
GET /reportes/ganancias?desde=2026-04-01&hasta=2026-04-30
GET /reportes/top-clientes?desde=2026-04-01&hasta=2026-04-30&top=5
GET /reportes/inventario
```

---

## 🔗 EJEMPLOS DE REQUESTS CON CURL

### Crear Cliente:
```bash
curl -X POST http://localhost:8080/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Juan García",
    "telefono": "555-1234",
    "email": "juan@example.com",
    "tipo": "LOCAL"
  }'
```

### Listar Productos Activos:
```bash
curl http://localhost:8080/productos/activos
```

### Crear Venta:
```bash
curl -X POST http://localhost:8080/ventas \
  -H "Content-Type: application/json" \
  -d '{
    "clienteId": 1,
    "usuarioId": 1,
    "tiendaId": 1,
    "tipoVenta": "LOCAL",
    "items": [
      {"skuId": 1, "cantidad": 3}
    ],
    "pagos": [
      {"monto": 299.97, "metodo": "EFECTIVO"}
    ]
  }'
```

### Descargar Ticket PDF:
```bash
curl http://localhost:8080/documentos/ticket/1/pdf -o ticket.pdf
```

### Reportes:
```bash
curl "http://localhost:8080/reportes/ventas?desde=2026-04-01&hasta=2026-04-30"
curl "http://localhost:8080/reportes/productos-mas-vendidos?desde=2026-04-01&hasta=2026-04-30&top=10"
```

---

**Generado el**: 2026-04-03  
**Versión del Backend**: 0.0.1-SNAPSHOT

