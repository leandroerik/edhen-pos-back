# 🚀 FRONTEND - TODO LO QUE NECESITAS

**Un solo documento. Copia, pega y haz el frontend.**

---

## 🔗 BACKEND INFO

- **URL**: `http://localhost:8080`
- **DB**: SQLite (pos.db)
- **Stack**: Spring Boot 3.5.13

---

## 📊 ENTIDADES Y ENDPOINTS

### CLIENTES
```
GET    /clientes                    → Listar
POST   /clientes                    → Crear: {nombre, email, telefono, tipo}
PUT    /clientes/{id}               → Actualizar
DELETE /clientes/{id}               → Eliminar
GET    /clientes/buscar/nombre?nombre=X
```

### PRODUCTOS
```
GET    /productos                   → Listar todos
GET    /productos/activos           → Solo activos
POST   /productos                   → Crear: {nombre, descripcion, activo, skus[]}
PUT    /productos/{id}              → Actualizar
DELETE /productos/{id}              → Eliminar
```

### SKUs
```
GET    /skus                        → Listar
POST   /skus                        → Crear: {precio, costo, stock, codigoBarra, productoId}
PUT    /skus/{id}                   → Actualizar
DELETE /skus/{id}                   → Eliminar
```

### ⭐ VENTAS (LO MÁS IMPORTANTE)
```
GET    /ventas                      → Listar
POST   /ventas                      → CREAR VENTA (ver JSON abajo)
GET    /ventas/{id}                 → Obtener
```

### REPORTES
```
GET    /reportes/total-vendido                          → {totalHistorico, ultimaVenta}
GET    /reportes/ventas?desde=X&hasta=X                 → {fechaInicio, fechaFin, totalVentas, cantidadTransacciones}
GET    /reportes/productos-mas-vendidos?desde=X&hasta=X&top=10  → [{productoId, nombre, cantidadVendida, totalVendido}]
GET    /reportes/top-clientes?desde=X&hasta=X&top=10    → [{clienteId, nombre, totalCompras}]
GET    /reportes/ganancias?desde=X&hasta=X              → {totalIngresos, totalCostos, gananciaTotal, margenGanancia}
GET    /reportes/inventario                             → [{skuId, codigoBarra, productoNombre, stock, precio, costo}]
```

### DOCUMENTOS
```
GET    /documentos/ticket/{ventaId}                     → Ver ticket (HTML)
GET    /documentos/ticket/{ventaId}/pdf                 → Descargar PDF
```

---

## 🎯 CREAR VENTA - JSON (CRÍTICO)

```json
POST /ventas

{
  "clienteId": 1,
  "usuarioId": 1,
  "tiendaId": 1,
  "tipoVenta": "LOCAL",
  "items": [
    {"skuId": 1, "cantidad": 2, "precioOverride": null},
    {"skuId": 2, "cantidad": 1, "precioOverride": 49.99}
  ],
  "pagos": [
    {"monto": 249.97, "metodo": "EFECTIVO"}
  ]
}
```

**Respuesta**: Venta completa con detalles y pagos

---

## 💻 SERVICIOS A CREAR

### 1. `services/api.js` - Base HTTP
```javascript
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 30000,
});

export default api;
```

### 2. `services/clienteService.js`
```javascript
import api from './api';

export const clienteService = {
  listar: () => api.get('/clientes'),
  crear: (data) => api.post('/clientes', data),
  obtener: (id) => api.get(`/clientes/${id}`),
  actualizar: (id, data) => api.put(`/clientes/${id}`, data),
  eliminar: (id) => api.delete(`/clientes/${id}`),
  buscar: (nombre) => api.get('/clientes/buscar/nombre', {params: {nombre}}),
};
```

### 3. `services/productoService.js`
```javascript
import api from './api';

export const productoService = {
  listar: () => api.get('/productos'),
  activos: () => api.get('/productos/activos'),
  crear: (data) => api.post('/productos', data),
  obtener: (id) => api.get(`/productos/{id}`),
  actualizar: (id, data) => api.put(`/productos/${id}`, data),
  eliminar: (id) => api.delete(`/productos/{id}`),
};
```

### 4. `services/ventaService.js`
```javascript
import api from './api';

export const ventaService = {
  listar: () => api.get('/ventas'),
  obtener: (id) => api.get(`/ventas/${id}`),
  crear: (data) => api.post('/ventas', data),
};
```

### 5. `services/reporteService.js`
```javascript
import api from './api';

export const reporteService = {
  totalVendido: () => api.get('/reportes/total-vendido'),
  ventasPeriodo: (desde, hasta) => 
    api.get('/reportes/ventas', {params: {desde, hasta}}),
  productosMasVendidos: (desde, hasta, top=10) =>
    api.get('/reportes/productos-mas-vendidos', {params: {desde, hasta, top}}),
  topClientes: (desde, hasta, top=10) =>
    api.get('/reportes/top-clientes', {params: {desde, hasta, top}}),
  ganancias: (desde, hasta) =>
    api.get('/reportes/ganancias', {params: {desde, hasta}}),
  inventario: () => api.get('/reportes/inventario'),
};
```

### 6. `services/documentoService.js`
```javascript
import api from './api';

export const documentoService = {
  descargarPDF: (ventaId) =>
    api.get(`/documentos/ticket/${ventaId}/pdf`, {responseType: 'blob'}),
};
```

---

## 📱 PÁGINAS A HACER

### 1. DASHBOARD
- GET `/reportes/total-vendido`
- GET `/reportes/ventas?desde=hoy&hasta=hoy`
- GET `/reportes/productos-mas-vendidos?desde=hoy&hasta=hoy&top=5`
- GET `/reportes/inventario`
- Mostrar: tarjetas, gráficos (Chart.js)

### 2. CLIENTES
- GET `/clientes` → tabla
- POST `/clientes` → modal crear
- PUT `/clientes/{id}` → modal editar
- DELETE `/clientes/{id}` → botón

### 3. PRODUCTOS
- GET `/productos/activos` → tabla
- POST `/productos` → modal crear (con SKUs)
- PUT `/productos/{id}` → modal editar
- DELETE `/productos/{id}` → botón

### 4. ⭐ CREAR VENTA (POS) - PRIORIDAD 1
```
1. Selector cliente (GET /clientes)
2. Búsqueda producto (GET /productos/activos)
3. Carrito items (localmente)
4. Calcular total (suma items)
5. Inputs pagos (monto, metodo)
6. Botón procesar → POST /ventas
7. Modal confirmación + descargar PDF
```

### 5. REPORTES
- DatePicker (desde, hasta)
- GET `/reportes/ventas?desde=X&hasta=X` → gráfico línea
- GET `/reportes/productos-mas-vendidos` → gráfico barras
- GET `/reportes/top-clientes` → gráfico barras
- GET `/reportes/ganancias` → cards

### 6. TICKETS
- GET `/ventas` → tabla
- GET `/documentos/ticket/{id}/pdf` → descargar

---

## 🎨 ESTRUCTURA CARPETAS MÍNIMA

```
src/
├── services/
│   ├── api.js
│   ├── clienteService.js
│   ├── productoService.js
│   ├── ventaService.js
│   ├── reporteService.js
│   └── documentoService.js
├── pages/
│   ├── Dashboard.js
│   ├── Clientes.js
│   ├── Productos.js
│   ├── Venta.js          ← POS
│   ├── Reportes.js
│   └── Tickets.js
├── components/
│   ├── Navbar.js
│   ├── Modal.js
│   └── Loading.js
├── App.js
└── index.js
```

---

## ⚙️ .env

```env
REACT_APP_API_URL=http://localhost:8080
```

---

## 🔑 VALIDACIONES (FRONTEND ANTES DE ENVIAR)

### Crear Venta
```
✓ clienteId != null
✓ items.length >= 1
✓ items[].cantidad > 0
✓ pagos.length >= 1
✓ sum(pagos.monto) >= total
✓ metodo ∈ [EFECTIVO, TARJETA, TRANSFERENCIA]
```

### Crear Cliente
```
✓ nombre != vacío
✓ tipo ∈ [LOCAL, ONLINE]
```

### Crear Producto
```
✓ nombre != vacío
✓ skus.length >= 1
✓ sku.precio > 0
✓ sku.stock >= 0
```

---

## 🧪 PRUEBA CON CURL

```bash
# Crear cliente
curl -X POST http://localhost:8080/clientes \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Test","email":"test@mail.com","telefono":"555-1234","tipo":"LOCAL"}'

# Listar clientes
curl http://localhost:8080/clientes

# Total vendido
curl http://localhost:8080/reportes/total-vendido

# Crear venta
curl -X POST http://localhost:8080/ventas \
  -H "Content-Type: application/json" \
  -d '{"clienteId":1,"usuarioId":1,"tiendaId":1,"tipoVenta":"LOCAL","items":[{"skuId":1,"cantidad":2}],"pagos":[{"monto":200,"metodo":"EFECTIVO"}]}'
```

---

## 🚀 SETUP RÁPIDO

```bash
# 1. Crear proyecto
npx create-react-app edhen-pos-frontend
cd edhen-pos-frontend

# 2. Instalar dependencias
npm install axios react-router-dom tailwindcss chart.js react-chartjs-2

# 3. Configurar Tailwind
npm install -D tailwindcss postcss autoprefixer
npx tailwindcss init -p

# 4. Crear .env
echo "REACT_APP_API_URL=http://localhost:8080" > .env

# 5. Iniciar
npm start
```

---

## 📋 CONSTANTES

```javascript
export const METODOS_PAGO = {
  EFECTIVO: 'EFECTIVO',
  TARJETA: 'TARJETA',
  TRANSFERENCIA: 'TRANSFERENCIA'
};

export const TIPOS = {
  LOCAL: 'LOCAL',
  ONLINE: 'ONLINE'
};

export const ESTADOS_VENTA = {
  PENDIENTE: 'PENDIENTE',
  COMPLETADA: 'COMPLETADA',
  CANCELADA: 'CANCELADA'
};
```

---

## 📅 FORMATO FECHAS

```javascript
// Hoy en formato YYYY-MM-DD
const hoy = new Date().toISOString().split('T')[0];

// URLs con fechas
GET /reportes/ventas?desde=2026-04-03&hasta=2026-04-03
```

---

## 🎯 FLUJO CREAR VENTA

```
1. GET /clientes → selector
2. GET /productos/activos → búsqueda
3. Click producto → agregar carrito
4. Cambiar cantidad
5. Ingresar pagos (monto + metodo)
6. Validar total pagado >= subtotal
7. POST /ventas
8. Si OK → descargar PDF (/documentos/ticket/{id}/pdf)
9. Limpiar carrito
```

---

## 🎨 TAILWIND BASICS

```
Flex: flex, flex-col, gap-4, justify-between, items-center
Padding: p-4, px-2, py-4, m-2
Colors: bg-blue-600, text-white, border-gray-300, text-red-600
Text: font-bold, text-lg, text-center, text-sm
Effects: rounded, shadow, hover:bg-blue-700
Responsive: md:, lg:, xl:
```

---

## 📊 RESPUESTA VENTA

```json
{
  "id": 1,
  "fecha": "2026-04-03T15:30:00",
  "total": 249.97,
  "cliente": {
    "id": 1,
    "nombre": "Juan García"
  },
  "detalles": [
    {
      "id": 101,
      "cantidad": 2,
      "precioUnitario": 99.99,
      "costoUnitario": 50.00,
      "sku": { "id": 1 }
    }
  ],
  "pagos": [
    {
      "id": 201,
      "monto": 249.97,
      "metodo": "EFECTIVO",
      "estado": "CONFIRMADO"
    }
  ]
}
```

---

## ✅ CHECKLIST ANTES DE EMPEZAR

- [ ] Backend corriendo en http://localhost:8080
- [ ] Node.js 16+ instalado
- [ ] Proyecto React creado
- [ ] Dependencias instaladas
- [ ] .env configurado
- [ ] Servicios creados
- [ ] Rutas configuradas
- [ ] Componentes principal listos

---

## 🔗 TODO FUNCIONA CON ESTOS SERVICIOS

Los 6 servicios arriba cubren 100% de lo que necesitas. No necesitas nada más.

**Copialos, úsalos y haz el frontend sobre ellos.**

---

**Backend**: http://localhost:8080  
**Frontend**: http://localhost:3000  
**Generado**: 2026-04-03

