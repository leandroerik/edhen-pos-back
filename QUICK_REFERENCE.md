# 🎯 QUICK COPY-PASTE - PARA EL FRONTEND

**Copia y pega directamente en tu proyecto. No necesita modificación.**

---

## 📋 TABLA DE ENDPOINTS

```
BASE_URL: http://localhost:8080

CLIENTES:
  GET    /clientes                                    - Listar todos
  POST   /clientes                                    - Crear
  GET    /clientes/{id}                               - Obtener
  PUT    /clientes/{id}                               - Actualizar
  DELETE /clientes/{id}                               - Eliminar

PRODUCTOS:
  GET    /productos                                   - Listar todos
  GET    /productos/activos                           - Activos
  POST   /productos                                   - Crear
  GET    /productos/{id}                              - Obtener
  PUT    /productos/{id}                              - Actualizar
  DELETE /productos/{id}                              - Eliminar

SKUs:
  GET    /skus                                        - Listar todos
  POST   /skus                                        - Crear
  GET    /skus/{id}                                   - Obtener
  PUT    /skus/{id}                                   - Actualizar
  DELETE /skus/{id}                                   - Eliminar

VENTAS:
  GET    /ventas                                      - Listar todas
  POST   /ventas                                      - ⭐ CREAR VENTA
  GET    /ventas/{id}                                 - Obtener
  PUT    /ventas/{id}                                 - Actualizar
  DELETE /ventas/{id}                                 - Eliminar

DOCUMENTOS:
  GET    /documentos/ticket/{ventaId}                 - Ver ticket
  GET    /documentos/ticket/{ventaId}/pdf             - Descargar PDF

REPORTES:
  GET    /reportes/total-vendido                      - Total histórico
  GET    /reportes/ventas?desde=X&hasta=X             - Ventas período
  GET    /reportes/productos-mas-vendidos?desde=X&hasta=X&top=10
  GET    /reportes/top-clientes?desde=X&hasta=X&top=10
  GET    /reportes/ganancias?desde=X&hasta=X
  GET    /reportes/inventario
```

---

## 🔌 ESTRUCTURA DE REQUEST - CREAR VENTA

```json
POST /ventas

{
  "clienteId": 1,
  "usuarioId": 1,
  "tiendaId": 1,
  "tipoVenta": "LOCAL",
  "items": [
    {
      "skuId": 1,
      "cantidad": 2,
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
      "monto": 249.97,
      "metodo": "EFECTIVO"
    }
  ]
}
```

---

## 📊 ESTRUCTURA DE RESPUESTA - VENTA

```json
{
  "id": 1,
  "fecha": "2026-04-03T15:30:00",
  "total": 249.97,
  "tipoVenta": "LOCAL",
  "estado": "COMPLETADA",
  "cliente": {
    "id": 1,
    "nombre": "Juan García",
    "email": "juan@mail.com",
    "telefono": "555-1234",
    "tipo": "LOCAL"
  },
  "usuario": { "id": 1 },
  "tienda": { "id": 1 },
  "detalles": [
    {
      "id": 101,
      "cantidad": 2,
      "precioOriginal": 99.99,
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
      "estado": "CONFIRMADO",
      "fecha": "2026-04-03T15:30:00"
    }
  ]
}
```

---

## 💡 VARIABLES DE ENTORNO (.env)

```env
REACT_APP_API_URL=http://localhost:8080
REACT_APP_API_TIMEOUT=30000
REACT_APP_PAGE_SIZE=10
REACT_APP_DECIMAL_PLACES=2
```

---

## 🔑 CONSTANTES

```javascript
// Métodos de pago
export const METODOS_PAGO = {
  EFECTIVO: 'EFECTIVO',
  TARJETA: 'TARJETA',
  TRANSFERENCIA: 'TRANSFERENCIA'
};

// Tipos de cliente
export const TIPOS_CLIENTE = {
  LOCAL: 'LOCAL',
  ONLINE: 'ONLINE'
};

// Tipos de venta
export const TIPOS_VENTA = {
  LOCAL: 'LOCAL',
  ONLINE: 'ONLINE'
};

// Estados de venta
export const ESTADOS_VENTA = {
  PENDIENTE: 'PENDIENTE',
  COMPLETADA: 'COMPLETADA',
  CANCELADA: 'CANCELADA'
};
```

---

## 🧪 CASOS DE PRUEBA CON CURL

### Crear cliente
```bash
curl -X POST http://localhost:8080/clientes \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Test","email":"test@mail.com","telefono":"555-1234","tipo":"LOCAL"}'
```

### Listar clientes
```bash
curl http://localhost:8080/clientes
```

### Crear producto
```bash
curl -X POST http://localhost:8080/productos \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Zapatos","descripcion":"Zapatos de cuero","activo":true,"skus":[]}'
```

### Crear SKU
```bash
curl -X POST http://localhost:8080/skus \
  -H "Content-Type: application/json" \
  -d '{"precio":99.99,"costo":50.00,"stock":10,"codigoBarra":"750123","productoId":1}'
```

### Crear venta
```bash
curl -X POST http://localhost:8080/ventas \
  -H "Content-Type: application/json" \
  -d '{
    "clienteId":1,
    "usuarioId":1,
    "tiendaId":1,
    "tipoVenta":"LOCAL",
    "items":[{"skuId":1,"cantidad":2}],
    "pagos":[{"monto":200.00,"metodo":"EFECTIVO"}]
  }'
```

### Descargar ticket PDF
```bash
curl http://localhost:8080/documentos/ticket/1/pdf -o ticket.pdf
```

### Reportes
```bash
# Ventas de hoy
curl "http://localhost:8080/reportes/ventas?desde=2026-04-03&hasta=2026-04-03"

# Productos más vendidos
curl "http://localhost:8080/reportes/productos-mas-vendidos?desde=2026-04-03&hasta=2026-04-03&top=5"

# Total vendido
curl http://localhost:8080/reportes/total-vendido

# Ganancias
curl "http://localhost:8080/reportes/ganancias?desde=2026-04-03&hasta=2026-04-03"

# Inventario
curl http://localhost:8080/reportes/inventario
```

---

## ✅ CHECKLIST DE DATOS

### Crear Cliente - Campos
- nombre (string, requerido)
- email (string, opcional)
- telefono (string, opcional)
- tipo (LOCAL / ONLINE, requerido)

### Crear Producto - Campos
- nombre (string, requerido)
- descripcion (string, opcional)
- activo (boolean, requerido)
- skus (array, mín 1, requerido)

### Crear SKU - Campos
- precio (number, > 0, requerido)
- costo (number, >= 0, requerido)
- stock (number, >= 0, requerido)
- codigoBarra (string, opcional)
- productoId (number, requerido)

### Crear Venta - Campos ⭐
- clienteId (number, requerido)
- usuarioId (number, requerido)
- tiendaId (number, requerido)
- tipoVenta (LOCAL/ONLINE, requerido)
- items (array mín 1, requerido)
  - skuId (number, requerido)
  - cantidad (number, > 0, requerido)
  - precioOverride (number, opcional)
- pagos (array mín 1, requerido)
  - monto (number, > 0, requerido)
  - metodo (EFECTIVO/TARJETA/TRANSFERENCIA, requerido)

---

## 📱 FORMATO DE FECHAS

```
Formato: YYYY-MM-DD
Ejemplo: 2026-04-03

JavaScript:
const hoy = new Date().toISOString().split('T')[0];

Parámetros en URL:
?desde=2026-04-03&hasta=2026-04-03
?fechaInicio=2026-04-01&fechaFin=2026-04-30
```

---

## 🎯 RESPUESTAS DE ERROR

```json
400 Bad Request
{
  "status": 400,
  "message": "Datos inválidos"
}

404 Not Found
{
  "status": 404,
  "message": "Recurso no encontrado"
}

500 Server Error
{
  "status": 500,
  "message": "Error interno del servidor"
}
```

---

## 🔄 FLUJO CREAR VENTA (RESUMEN)

```
1. GET /clientes → Cargar selector
2. GET /productos/activos → Búsqueda
3. Agregar items al carrito (frontend)
4. Calcular total (frontend)
5. Validar datos (frontend)
6. POST /ventas → Crear
7. GET /documentos/ticket/{id} → Ver ticket
8. GET /documentos/ticket/{id}/pdf → Descargar
```

---

## 📊 SERVICIOS RECOMENDADOS

```javascript
// api.js
import axios from 'axios';
const api = axios.create({
  baseURL: process.env.REACT_APP_API_URL || 'http://localhost:8080',
  timeout: 30000,
});
export default api;

// clienteService.js
export const clienteService = {
  listar: () => api.get('/clientes'),
  crear: (data) => api.post('/clientes', data),
  obtener: (id) => api.get(`/clientes/${id}`),
  actualizar: (id, data) => api.put(`/clientes/${id}`, data),
  eliminar: (id) => api.delete(`/clientes/${id}`),
};

// ventaService.js
export const ventaService = {
  listar: () => api.get('/ventas'),
  crear: (data) => api.post('/ventas', data),
  obtener: (id) => api.get(`/ventas/${id}`),
  actualizar: (id, data) => api.put(`/ventas/${id}`, data),
};

// reporteService.js
export const reporteService = {
  ventasPorPeriodo: (desde, hasta) => 
    api.get('/reportes/ventas', { params: { desde, hasta } }),
  productosMasVendidos: (desde, hasta, top = 10) =>
    api.get('/reportes/productos-mas-vendidos', { params: { desde, hasta, top } }),
  totalVendido: () => api.get('/reportes/total-vendido'),
  inventario: () => api.get('/reportes/inventario'),
};

// documentoService.js
export const documentoService = {
  descargarPDF: (ventaId) =>
    api.get(`/documentos/ticket/${ventaId}/pdf`, { responseType: 'blob' }),
};
```

---

## 🚀 INSTALACIÓN RÁPIDA

```bash
# 1. Crear proyecto
npx create-react-app edhen-pos-frontend && cd edhen-pos-frontend

# 2. Instalar dependencias
npm install axios react-router-dom tailwindcss chart.js react-chartjs-2

# 3. Configurar Tailwind
npm install -D tailwindcss postcss autoprefixer && npx tailwindcss init -p

# 4. Crear .env
echo "REACT_APP_API_URL=http://localhost:8080" > .env

# 5. Iniciar
npm start
```

---

## 📁 ESTRUCTURA MÍNIMA DE CARPETAS

```
src/
├── services/
│   ├── api.js
│   ├── clienteService.js
│   ├── productoService.js
│   ├── ventaService.js
│   ├── reporteService.js
│   └── documentoService.js
├── components/
│   ├── Dashboard.js
│   ├── ClienteList.js
│   ├── ProductoList.js
│   ├── VentaForm.js
│   ├── Reportes.js
│   └── Tickets.js
├── App.js
└── index.js
```

---

## 🎨 CLASES TAILWIND ESENCIALES

```
Layout: flex, grid, flex-col, flex-row, gap-4, p-4, m-4
Colors: bg-blue-600, text-white, border-gray-300, text-red-600
Typography: text-lg, font-bold, text-center, text-sm
Effects: rounded, shadow, hover:bg-blue-700, cursor-pointer
Responsive: sm:, md:, lg:, xl:
```

---

## 💾 LOCALSTORAGE (Opcional)

```javascript
// Guardar
localStorage.setItem('usuarioActual', JSON.stringify({id: 1, nombre: 'Juan'}));

// Recuperar
const usuario = JSON.parse(localStorage.getItem('usuarioActual'));

// Limpiar
localStorage.removeItem('usuarioActual');
```

---

## 🔗 LINKS IMPORTANTES

- Backend: http://localhost:8080
- Frontend: http://localhost:3000
- Documentación completa: Ver DOCUMENTACION_FRONTEND_INDICE.md
- Ejemplos: Ver EJEMPLOS_CODIGO_REACT.md
- Endpoints: Ver ENDPOINTS_REFERENCIA_RAPIDA.md

---

**Último update**: 2026-04-03

