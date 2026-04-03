# 📋 RESUMEN EJECUTIVO - QUÉ NECESITA SABER EL FRONTEND

**Documento resumido - Entregable para el otro Copilot**

---

## 🎯 INFORMACIÓN CRÍTICA

### Backend Info
- **URL Base**: `http://localhost:8080`
- **Base Datos**: SQLite (pos.db)
- **Framework**: Spring Boot 3.5.13 + Java 21

### Frontend Stack Recomendado
- React 18+
- Axios (HTTP client)
- React Router DOM
- Tailwind CSS
- Chart.js + React ChartJS 2

---

## 📊 ENTIDADES PRINCIPALES

1. **CLIENTE** - nombre, telefono, email, tipo (LOCAL/ONLINE)
2. **PRODUCTO** - nombre, descripcion, activo, skus[]
3. **SKU** - precio, costo, stock, codigoBarra, fechaActualizacionPrecio
4. **VENTA** - fecha, total, tipoVenta, estado, cliente, usuario, tienda, detalles, pagos
5. **VENTADETALLE** - cantidad, precio, costo (líneas de la venta)
6. **PAGO** - monto, metodo (EFECTIVO/TARJETA/TRANSFERENCIA), estado

---

## 🔌 ENDPOINTS ESENCIALES

### CRUD Básico
```
GET    /clientes               → Listar clientes
POST   /clientes               → Crear cliente
GET    /productos              → Listar productos activos
POST   /productos              → Crear producto
POST   /ventas                 → CREAR VENTA (lo más importante)
GET    /ventas/{id}            → Obtener venta
```

### Búsquedas
```
GET /clientes/buscar/nombre?nombre=Juan
GET /clientes/buscar/email?email=test@mail.com
GET /productos/buscar/nombre?nombre=Zapatos
GET /productos/activos
```

### Reportes (Dashboards)
```
GET /reportes/total-vendido                          → Total histórico
GET /reportes/ventas?desde=2026-04-01&hasta=2026-04-01
GET /reportes/productos-mas-vendidos?desde=X&hasta=X&top=10
GET /reportes/top-clientes?desde=X&hasta=X&top=10
GET /reportes/ganancias?desde=X&hasta=X
GET /reportes/inventario
```

### Documentos
```
GET /documentos/ticket/{ventaId}         → Ver ticket (HTML/texto)
GET /documentos/ticket/{ventaId}/pdf     → Descargar PDF
```

---

## 💰 CREAR VENTA - ESTRUCTURA CRÍTICA

**POST /ventas**

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

## ✅ PÁGINAS A IMPLEMENTAR

| Página | Endpoint | Funcionalidad |
|--------|----------|---------------|
| **Dashboard** | `/reportes/*` | Resumen de ventas, gráficos, KPIs |
| **Clientes** | `GET /clientes`, etc. | CRUD + Búsqueda |
| **Productos** | `GET /productos`, `POST /skus` | CRUD + Gestión de SKUs |
| **POS (Nueva Venta)** | `POST /ventas` | Crear venta, agregar items, pagos |
| **Reportes** | `/reportes/*` | Ventas, productos, clientes, ganancias |
| **Tickets** | `GET /ventas`, `GET /documentos/ticket` | Ver y descargar tickets |

---

## 📱 FUNCIONALIDADES PRIORITARIAS

### 🥇 Prioridad 1 (CRÍTICA)
- [ ] Dashboard con resumen de ventas
- [ ] Crear venta (POS) - **LA MÁS IMPORTANTE**
- [ ] Listar y descargar tickets

### 🥈 Prioridad 2 (MUY IMPORTANTE)
- [ ] CRUD de clientes
- [ ] CRUD de productos con SKUs
- [ ] Reportes de ventas

### 🥉 Prioridad 3 (IMPORTANTE)
- [ ] Reportes avanzados
- [ ] Búsquedas y filtros
- [ ] Gestión de inventario

---

## 🔑 CONSTANTES A USAR

### Métodos de Pago
```javascript
const METODOS_PAGO = ['EFECTIVO', 'TARJETA', 'TRANSFERENCIA'];
```

### Tipos de Cliente
```javascript
const TIPOS_CLIENTE = ['LOCAL', 'ONLINE'];
```

### Tipos de Venta
```javascript
const TIPOS_VENTA = ['LOCAL', 'ONLINE'];
```

### Estados de Venta
```javascript
const ESTADOS_VENTA = ['PENDIENTE', 'COMPLETADA', 'CANCELADA'];
```

---

## 🚀 FLUJO TÍPICO DE UNA VENTA

```
1. Usuario selecciona cliente
   ↓
2. Usuario busca y agrega productos
   ↓
3. Sistema muestra total
   ↓
4. Usuario ingresa datos de pago
   ↓
5. Frontend valida:
   - Cliente seleccionado ✓
   - Carrito no vacío ✓
   - Monto pagado >= Total ✓
   ↓
6. Frontend envía POST /ventas
   ↓
7. Backend crea venta y actualiza inventario
   ↓
8. Sistema muestra confirmación
   ↓
9. Opción: Descargar/Imprimir ticket
```

---

## 📌 VALIDACIONES A IMPLEMENTAR EN FRONTEND

```javascript
// Cliente
- nombre ≠ vacío
- email si se proporciona debe ser válido
- tipo ∈ [LOCAL, ONLINE]

// Producto
- nombre ≠ vacío
- skus.length ≥ 1
- sku.precio > 0
- sku.costo ≥ 0

// Venta (CRÍTICA)
- clienteId ≠ null
- items.length ≥ 1
- items[].cantidad > 0
- sum(pagos[].monto) ≥ total
- usuarioId ≠ null
- tiendaId ≠ null
```

---

## 🎨 COMPONENTES ESENCIALES

```
App
├── Navbar (navegación)
├── Dashboard
│   ├── CardTotal
│   ├── CardVentasHoy
│   ├── ChartVentas
│   └── ChartProductos
├── Clientes
│   ├── ClienteList (tabla)
│   ├── ClienteForm (crear/editar)
│   └── ClienteSearch
├── Productos
│   ├── ProductoList (tabla)
│   ├── ProductoForm (crear/editar)
│   ├── SKUForm
│   └── ProductoSearch
├── Ventas (POS)
│   ├── VentaForm (principal)
│   ├── ProductoBuscador
│   ├── Carrito
│   ├── PagoSelector
│   └── Confirmacion
├── Reportes
│   ├── ReportesPanel
│   ├── DateRangePicker
│   ├── ChartVentas
│   ├── ChartProductos
│   ├── ChartGanancias
│   └── TablaInventario
└── Tickets
    ├── TicketList (tabla de ventas)
    ├── TicketViewer
    └── TicketDownloadPDF
```

---

## 🔗 RELACIONES ENTRE ENTIDADES

```
Cliente
  ↓
  └─→ Venta (1 to Many)
       ├─→ VentaDetalle (1 to Many)
       │    └─→ SKU (Many to 1)
       │         └─→ Producto (Many to 1)
       └─→ Pago (1 to Many)

Producto
  ├─→ SKU (1 to Many)
  └─→ VentaDetalle (Many to Many)
```

---

## 📊 RESPUESTAS DE REPORTES

### VentaPeriodoDTO
```json
{
  "fechaInicio": "2026-04-03",
  "fechaFin": "2026-04-03",
  "totalVentas": 500.00,
  "cantidadTransacciones": 3
}
```

### ProductoVendidoDTO
```json
{
  "productoId": 1,
  "nombre": "Zapatos",
  "cantidadVendida": 10,
  "totalVendido": 999.99
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

---

## 📋 CHECKLISTS RÁPIDOS

### Antes de enviar POST /ventas
- [ ] clienteId seleccionado
- [ ] usuarioId (default: 1)
- [ ] tiendaId (default: 1)
- [ ] tipoVenta ∈ [LOCAL, ONLINE]
- [ ] items.length ≥ 1
- [ ] items[i].skuId valido
- [ ] items[i].cantidad > 0
- [ ] pagos.length ≥ 1
- [ ] pagos[i].monto > 0
- [ ] pagos[i].metodo ∈ [EFECTIVO, TARJETA, TRANSFERENCIA]
- [ ] sum(pagos.monto) ≥ total

### Antes de renderizar tabla de clientes
- [ ] GET /clientes exitoso
- [ ] datos.length > 0
- [ ] mostrar loading mientras carga
- [ ] mostrar error si falla

### Después de crear venta
- [ ] Mostrar confirmación
- [ ] Opción descargar ticket PDF
- [ ] Limpiar carrito
- [ ] Limpiar formulario

---

## 🌐 FORMATO DE FECHAS

**Formato aceptado por backend**: `YYYY-MM-DD` (ISO 8601)

```javascript
const hoy = new Date().toISOString().split('T')[0]; // "2026-04-03"
```

URLs con fechas:
```
GET /reportes/ventas?desde=2026-04-01&hasta=2026-04-30
GET /ventas/buscar/fecha?fechaInicio=2026-04-01&fechaFin=2026-04-30
```

---

## 🎯 VARIABLES DE CONTEXTO GLOBAL SUGERIDAS

```javascript
{
  usuarioActual: {
    id: 1,
    nombre: "Vendedor"
  },
  tiendaActual: {
    id: 1,
    nombre: "Principal"
  },
  clienteEnEdicion: null,
  productoEnEdicion: null,
  ventaEnEdicion: null,
  loading: false,
  error: null,
  success: null
}
```

---

## 🔐 CONFIGURACIÓN INICIAL (`.env`)

```env
REACT_APP_API_URL=http://localhost:8080
REACT_APP_API_TIMEOUT=30000
REACT_APP_PAGE_SIZE=10
REACT_APP_DECIMAL_PLACES=2
REACT_APP_VERSION=1.0.0
```

---

## 📞 DOCUMENTOS DE REFERENCIA

En la carpeta raíz del proyecto backend:

1. **BACKEND_API_ESPECIFICACION.md** - Especificación completa del API
2. **ENDPOINTS_REFERENCIA_RAPIDA.md** - Tabla rápida de endpoints
3. **FRONTEND_CHECKLIST_IMPLEMENTACION.md** - Checklist de funcionalidades
4. **EJEMPLOS_CODIGO_REACT.md** - Ejemplos de código React

---

## 🚀 PASOS PARA EMPEZAR

1. Crear proyecto React
2. Instalar dependencias: `npm install axios react-router-dom tailwindcss chart.js react-chartjs-2`
3. Crear servicios (clienteService, productoService, ventaService, reporteService)
4. Crear componentes base (Layout, Navbar)
5. Implementar Dashboard (usando reportes)
6. Implementar CRUD Clientes
7. Implementar CRUD Productos
8. **Implementar Crear Venta (PRIORIDAD)** ← Aquí
9. Implementar Reportes
10. Implementar Tickets
11. Testing y refinamientos

---

## 💡 TIPS IMPORTANTES

- El **endpoint más importante** es `POST /ventas`
- Siempre validar datos **antes** de enviar
- Usar `loading` states en todas las operaciones async
- Mostrar **error messages** claros
- Usar `localStorage` para datos no sensibles
- Implementar **debounce** en búsquedas
- Usar **React Context** para estado global
- Componentes pequeños y reutilizables

---

**Documento generado**: 2026-04-03  
**Versión Backend**: 0.0.1-SNAPSHOT  
**Base URL**: http://localhost:8080

