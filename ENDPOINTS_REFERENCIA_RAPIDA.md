# 🎯 REFERENCIA RÁPIDA - ENDPOINTS DEL BACKEND

**Documento resumido para referencia rápida del Frontend**

---

## 🔷 CLIENTES

| Acción | Método | URL | Body |
|--------|--------|-----|------|
| Listar todos | GET | `/clientes` | - |
| Obtener uno | GET | `/clientes/{id}` | - |
| Crear | POST | `/clientes` | `{nombre, telefono, email, tipo}` |
| Actualizar | PUT | `/clientes/{id}` | `{nombre, telefono, email, tipo}` |
| Eliminar | DELETE | `/clientes/{id}` | - |
| Buscar por nombre | GET | `/clientes/buscar/nombre?nombre=Juan` | - |
| Buscar por email | GET | `/clientes/buscar/email?email=test@mail.com` | - |
| Buscar por teléfono | GET | `/clientes/buscar/telefono?telefono=555-1234` | - |
| Buscar por tipo | GET | `/clientes/buscar/tipo/LOCAL` | - |

---

## 🔷 PRODUCTOS

| Acción | Método | URL | Body |
|--------|--------|-----|------|
| Listar todos | GET | `/productos` | - |
| Listar activos | GET | `/productos/activos` | - |
| Listar inactivos | GET | `/productos/inactivos` | - |
| Obtener uno | GET | `/productos/{id}` | - |
| Crear | POST | `/productos` | `{nombre, descripcion, activo, skus:[]}` |
| Actualizar | PUT | `/productos/{id}` | `{nombre, descripcion, activo, skus:[]}` |
| Eliminar | DELETE | `/productos/{id}` | - |
| Buscar por nombre | GET | `/productos/buscar/nombre?nombre=Zapatos` | - |
| Buscar por descripción | GET | `/productos/buscar/descripcion?descripcion=cuero` | - |

---

## 🔷 SKUs

| Acción | Método | URL | Body |
|--------|--------|-----|------|
| Listar todos | GET | `/skus` | - |
| Obtener uno | GET | `/skus/{id}` | - |
| Crear | POST | `/skus` | `{precio, costo, stock, codigoBarra, productoId}` |
| Actualizar | PUT | `/skus/{id}` | `{precio, costo, stock, codigoBarra}` |
| Eliminar | DELETE | `/skus/{id}` | - |

---

## 🔷 VENTAS

| Acción | Método | URL | Body |
|--------|--------|-----|------|
| Listar todas | GET | `/ventas` | - |
| Obtener una | GET | `/ventas/{id}` | - |
| **Crear** | **POST** | **`/ventas`** | **`{clienteId, usuarioId, tiendaId, tipoVenta, items, pagos}`** |
| Actualizar | PUT | `/ventas/{id}` | `{...ventaData}` |
| Eliminar | DELETE | `/ventas/{id}` | - |
| Por rango de fechas | GET | `/ventas/buscar/fecha?fechaInicio=2026-04-01&fechaFin=2026-04-30` | - |
| Por cliente | GET | `/ventas/buscar/cliente/{clienteId}` | - |
| Por usuario | GET | `/ventas/buscar/usuario/{usuarioId}` | - |
| Por tipo | GET | `/ventas/buscar/tipo/LOCAL` | - |
| Por estado | GET | `/ventas/buscar/estado/COMPLETADA` | - |

### Estructura de Venta (POST):
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

---

## 🔷 DOCUMENTOS (Tickets/PDFs)

| Acción | Método | URL | Retorna |
|--------|--------|-----|---------|
| Ver ticket (texto) | GET | `/documentos/ticket/{ventaId}` | String (HTML/texto) |
| Descargar PDF | GET | `/documentos/ticket/{ventaId}/pdf` | **Binary (PDF)** |
| Listar todos | GET | `/documentos` | Array of DocumentoVenta |
| Obtener uno | GET | `/documentos/{id}` | DocumentoVenta |
| Listar por venta | GET | `/documentos/venta/{ventaId}` | Array of DocumentoVenta |
| Actualizar | PUT | `/documentos/{id}` | DocumentoVenta |
| Eliminar | DELETE | `/documentos/{id}` | - |

---

## 📊 REPORTES

| Reporte | Método | URL | Parámetros | Retorna |
|---------|--------|-----|------------|---------|
| **Ventas por período** | GET | `/reportes/ventas` | `desde`, `hasta` (YYYY-MM-DD) | `VentaPeriodoDTO` |
| **Productos más vendidos** | GET | `/reportes/productos-mas-vendidos` | `desde`, `hasta`, `top=10` | Array `ProductoVendidoDTO[]` |
| **Top clientes** | GET | `/reportes/top-clientes` | `desde`, `hasta`, `top=10` | Array `ClienteTopDTO[]` |
| **Ganancias totales** | GET | `/reportes/ganancias` | `desde`, `hasta` | `GananciaDTO` |
| **Inventario** | GET | `/reportes/inventario` | - | Array `InventarioDTO[]` |
| **Total vendido histórico** | GET | `/reportes/total-vendido` | - | `TotalVendidoDTO` |

### Ejemplo de URLs:
```
GET /reportes/ventas?desde=2026-04-01&hasta=2026-04-30
GET /reportes/productos-mas-vendidos?desde=2026-04-01&hasta=2026-04-30&top=5
GET /reportes/ganancias?desde=2026-04-01&hasta=2026-04-30
GET /reportes/top-clientes?desde=2026-04-01&hasta=2026-04-30&top=10
GET /reportes/inventario
GET /reportes/total-vendido
```

---

## 📋 DTOs DE RESPUESTA (Reportes)

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

### ClienteTopDTO
```json
{
  "clienteId": 1,
  "nombre": "Juan García",
  "totalCompras": 500.00,
  "cantidadTransacciones": 5
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

## 🔑 TIPOS DE DATOS IMPORTANTES

### Estados de Venta
- `PENDIENTE`
- `COMPLETADA`
- `CANCELADA`

### Métodos de Pago
- `EFECTIVO`
- `TRANSFERENCIA`
- `TARJETA`

### Tipos de Cliente
- `LOCAL`
- `ONLINE`

### Tipos de Venta
- `LOCAL`
- `ONLINE`

---

## 🔴 CÓDIGOS DE ERROR HTTP

| Código | Significado |
|--------|-------------|
| `200` | ✅ OK - Éxito |
| `201` | ✅ Created - Recurso creado |
| `400` | ❌ Bad Request - Datos inválidos |
| `404` | ❌ Not Found - Recurso no encontrado |
| `500` | ❌ Server Error - Error en el servidor |

---

## ✅ CHECKLIST DE DATOS EN CADA OPERACIÓN

### Para crear CLIENTE:
- [ ] `nombre` (requerido)
- [ ] `email` (opcional, pero validar si se proporciona)
- [ ] `telefono` (opcional)
- [ ] `tipo` (requerido: LOCAL o ONLINE)

### Para crear PRODUCTO:
- [ ] `nombre` (requerido)
- [ ] `descripcion` (opcional)
- [ ] `activo` (true/false)
- [ ] `skus` array con al menos 1 SKU

### Para crear SKU:
- [ ] `precio` (requerido, > 0)
- [ ] `costo` (requerido, >= 0)
- [ ] `stock` (requerido, >= 0)
- [ ] `codigoBarra` (opcional)

### Para crear VENTA:
- [ ] `clienteId` (requerido)
- [ ] `usuarioId` (requerido)
- [ ] `tiendaId` (requerido)
- [ ] `tipoVenta` (requerido: LOCAL o ONLINE)
- [ ] `items` array con al menos 1 item
  - [ ] `skuId` (requerido)
  - [ ] `cantidad` (requerido, > 0)
  - [ ] `precioOverride` (opcional)
- [ ] `pagos` array con al menos 1 pago
  - [ ] `monto` (requerido, > 0)
  - [ ] `metodo` (requerido: EFECTIVO/TRANSFERENCIA/TARJETA)

---

## 🎯 CASOS DE USO COMUNES

### Dashboard - Cargar datos iniciales:
```javascript
// 1. Total vendido histórico
GET /reportes/total-vendido

// 2. Ventas del día
GET /reportes/ventas?desde=2026-04-03&hasta=2026-04-03

// 3. Top 5 productos del día
GET /reportes/productos-mas-vendidos?desde=2026-04-03&hasta=2026-04-03&top=5

// 4. Reporte de inventario
GET /reportes/inventario
```

### Crear Venta - Flujo completo:
```javascript
// 1. Listar clientes para selector
GET /clientes

// 2. Listar productos activos para búsqueda
GET /productos/activos

// 3. Buscar producto específico
GET /productos/buscar/nombre?nombre=Zapatos

// 4. Obtener detalle de producto con SKUs
GET /productos/{id}

// 5. Crear venta
POST /ventas
Body: { clienteId, usuarioId, tiendaId, tipoVenta, items, pagos }

// 6. Descargar ticket PDF
GET /documentos/ticket/{ventaId}/pdf
```

### Reportes - Flujo:
```javascript
// 1. Dashboard de reportes (seleccionar rango de fechas)
GET /reportes/ventas?desde=2026-04-01&hasta=2026-04-30
GET /reportes/productos-mas-vendidos?desde=2026-04-01&hasta=2026-04-30&top=10
GET /reportes/top-clientes?desde=2026-04-01&hasta=2026-04-30&top=10
GET /reportes/ganancias?desde=2026-04-01&hasta=2026-04-30
GET /reportes/inventario
```

---

## 🚀 PRUEBA RÁPIDA CON POSTMAN/CURL

### Crear un cliente:
```bash
curl -X POST http://localhost:8080/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Test User",
    "email": "test@mail.com",
    "telefono": "555-1234",
    "tipo": "LOCAL"
  }'
```

### Listar clientes:
```bash
curl http://localhost:8080/clientes
```

### Obtener reportes de hoy:
```bash
curl "http://localhost:8080/reportes/ventas?desde=2026-04-03&hasta=2026-04-03"
```

---

**Base URL**: `http://localhost:8080`  
**Última actualización**: 2026-04-03

