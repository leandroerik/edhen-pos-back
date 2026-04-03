# 🚀 GUÍA PARA EL FRONTEND REACT - CHECKLIST DE IMPLEMENTACIÓN

**Documento para pasar al otro Copilot**

---

## 📌 REQUISITOS DEL FRONTEND

### Stack Tecnológico Recomendado:
- **React** 18+
- **Axios** (cliente HTTP)
- **React Router DOM** (routing)
- **Tailwind CSS** (estilos)
- **Chart.js** + **React ChartJS 2** (gráficos)
- **LocalStorage** (estado temporal)

---

## ✅ CHECKLIST DE FUNCIONALIDADES

### 🏠 DASHBOARD
- [ ] Mostrar resumen de ventas del día
- [ ] Última venta realizada
- [ ] Total vendido acumulado
- [ ] Productos más vendidos (top 5)
- [ ] Gráfico de tendencias de ventas
- [ ] Mostrar inventario bajo
- [ ] Última actualización en tiempo real

**Endpoints a consumir**:
```
GET /reportes/total-vendido
GET /reportes/ventas?desde=[hoy]&hasta=[hoy]
GET /reportes/productos-mas-vendidos?desde=[hoy]&hasta=[hoy]&top=5
GET /reportes/inventario
```

---

### 👥 GESTIÓN DE CLIENTES
- [ ] Listar todos los clientes
- [ ] Crear nuevo cliente
- [ ] Editar cliente
- [ ] Eliminar cliente
- [ ] Buscar por nombre
- [ ] Buscar por email
- [ ] Buscar por teléfono
- [ ] Filtrar por tipo (LOCAL / ONLINE)
- [ ] Tabla con paginación
- [ ] Modal para crear/editar

**Endpoints a consumir**:
```
GET /clientes
POST /clientes
GET /clientes/{id}
PUT /clientes/{id}
DELETE /clientes/{id}
GET /clientes/buscar/nombre?nombre=...
GET /clientes/buscar/email?email=...
GET /clientes/buscar/telefono?telefono=...
GET /clientes/buscar/tipo/{tipo}
```

**Campos de Formulario**:
- nombre (required)
- telefono
- email
- tipo (select: LOCAL / ONLINE)

---

### 📦 GESTIÓN DE PRODUCTOS
- [ ] Listar todos los productos
- [ ] Crear producto con múltiples SKUs
- [ ] Editar producto
- [ ] Eliminar producto
- [ ] Buscar por nombre
- [ ] Buscar por descripción
- [ ] Filtrar activos/inactivos
- [ ] Mostrar stock por SKU
- [ ] Tabla con paginación
- [ ] Modal para crear/editar
- [ ] Agregar/editar SKUs dentro del producto

**Endpoints a consumir**:
```
GET /productos
POST /productos
GET /productos/{id}
PUT /productos/{id}
DELETE /productos/{id}
GET /productos/buscar/nombre?nombre=...
GET /productos/buscar/descripcion?descripcion=...
GET /productos/activos
GET /productos/inactivos
GET /skus
POST /skus
PUT /skus/{id}
DELETE /skus/{id}
```

**Estructura de Producto**:
```json
{
  "nombre": "string",
  "descripcion": "string",
  "activo": true,
  "skus": [
    {
      "precio": 99.99,
      "costo": 50.00,
      "stock": 15,
      "codigoBarra": "7501234567890"
    }
  ]
}
```

---

### 💰 CREAR VENTA (POS)
- [ ] Selector de cliente (crear si no existe)
- [ ] Búsqueda de productos por nombre/código de barras
- [ ] Agregar items al carrito
- [ ] Cantidad de items
- [ ] Mostrar precio unitario
- [ ] Calcular subtotal por item
- [ ] Opción de descuento por item (precioOverride)
- [ ] Mostrar total acumulado
- [ ] Eliminar item del carrito
- [ ] Selector de tipo de venta (LOCAL / ONLINE)
- [ ] Múltiples métodos de pago
- [ ] Validación de monto pagado >= total
- [ ] Botón procesar venta
- [ ] Confirmación de venta exitosa
- [ ] Opción descargar/ver ticket
- [ ] Limpiar carrito después de venta exitosa

**Endpoints a consumir**:
```
GET /clientes (para selector)
GET /productos/activos (para búsqueda)
GET /skus (para carrito)
POST /ventas (crear venta)
GET /documentos/ticket/{ventaId}
GET /documentos/ticket/{ventaId}/pdf
```

**Estructura VentaRequest**:
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

**Métodos de Pago Disponibles**:
- EFECTIVO
- TRANSFERENCIA
- TARJETA

---

### 📊 REPORTES Y ESTADÍSTICAS
- [ ] Selector de rango de fechas
- [ ] Reporte de ventas por período (gráfico de línea)
- [ ] Top 10 productos más vendidos (gráfico de barras)
- [ ] Top 10 clientes por gasto (gráfico de barras)
- [ ] Ganancias totales (mostrar ingresos, costos, ganancia, margen)
- [ ] Reporte de inventario (tabla)
- [ ] Exportar reportes (opcional)
- [ ] Filtros dinámicos

**Endpoints a consumir**:
```
GET /reportes/ventas?desde=...&hasta=...
GET /reportes/productos-mas-vendidos?desde=...&hasta=...&top=10
GET /reportes/top-clientes?desde=...&hasta=...&top=10
GET /reportes/ganancias?desde=...&hasta=...
GET /reportes/inventario
```

---

### 🎫 VER Y DESCARGAR TICKETS
- [ ] Listar historial de ventas
- [ ] Buscar venta por ID
- [ ] Ver detalle de venta
- [ ] Ver ticket en texto
- [ ] Descargar ticket como PDF
- [ ] Imprimir ticket
- [ ] Mostrar items de la venta
- [ ] Mostrar pagos realizados

**Endpoints a consumir**:
```
GET /ventas
GET /ventas/{id}
GET /documentos/ticket/{ventaId}
GET /documentos/ticket/{ventaId}/pdf
```

---

## 🔧 ESTRUCTURA DE CARPETAS RECOMENDADA

```
edhen-pos-frontend/
├── src/
│   ├── components/
│   │   ├── Navbar.js                 # Menú superior
│   │   ├── Dashboard.js              # Dashboard principal
│   │   ├── Clientes/
│   │   │   ├── ClienteList.js        # Listar clientes
│   │   │   ├── ClienteForm.js        # Crear/editar cliente
│   │   │   └── ClienteBuscar.js      # Búsqueda
│   │   ├── Productos/
│   │   │   ├── ProductoList.js       # Listar productos
│   │   │   ├── ProductoForm.js       # Crear/editar
│   │   │   ├── SKUForm.js            # Crear/editar SKUs
│   │   │   └── ProductoBuscar.js     # Búsqueda
│   │   ├── Ventas/
│   │   │   ├── VentaForm.js          # Crear venta (POS)
│   │   │   ├── CarritoItem.js        # Item en carrito
│   │   │   ├── ClienteSelector.js    # Selector de cliente
│   │   │   ├── ProductoSearcher.js   # Búsqueda de productos
│   │   │   ├── PagoForm.js           # Múltiples pagos
│   │   │   └── ConfirmacionVenta.js  # Confirmación
│   │   ├── Reportes/
│   │   │   ├── ReporteVentas.js      # Reporte ventas
│   │   │   ├── ReporteProductos.js   # Top productos
│   │   │   ├── ReporteClientes.js    # Top clientes
│   │   │   ├── ReporteGanancias.js   # Ganancias
│   │   │   ├── ReporteInventario.js  # Inventario
│   │   │   └── DateRangePicker.js    # Selector fechas
│   │   ├── Tickets/
│   │   │   ├── TicketList.js         # Listar tickets
│   │   │   ├── TicketDetail.js       # Detalle ticket
│   │   │   └── TicketViewer.js       # Ver/descargar
│   │   └── Common/
│   │       ├── Modal.js              # Modal genérica
│   │       ├── Alert.js              # Alertas
│   │       ├── Loading.js            # Loader
│   │       ├── Pagination.js         # Paginación
│   │       └── SearchBar.js          # Barra búsqueda
│   ├── services/
│   │   ├── api.js                    # Cliente Axios base
│   │   ├── clienteService.js         # CRUD clientes
│   │   ├── productoService.js        # CRUD productos
│   │   ├── skuService.js             # CRUD SKUs
│   │   ├── ventaService.js           # CRUD ventas
│   │   ├── reporteService.js         # Reportes
│   │   ├── documentoService.js       # Tickets/PDFs
│   │   └── utilService.js            # Utilities
│   ├── hooks/
│   │   ├── useApi.js                 # Hook para API
│   │   └── useLocalStorage.js        # Hook localStorage
│   ├── context/
│   │   └── AppContext.js             # Contexto global
│   ├── utils/
│   │   ├── formatters.js             # Formateo de datos
│   │   ├── validators.js             # Validaciones
│   │   └── constants.js              # Constantes
│   ├── App.js                        # Componente principal
│   ├── App.css                       # Estilos globales
│   ├── index.js                      # Entrada
│   └── index.css                     # Estilos base
├── public/
│   ├── index.html
│   └── logo.jpg                      # Logo del negocio
├── .env                              # Variables ambiente
├── package.json
└── README.md
```

---

## 🌐 VARIABLES DE AMBIENTE (`.env`)

```env
REACT_APP_API_URL=http://localhost:8080
REACT_APP_API_TIMEOUT=30000
REACT_APP_PAGE_SIZE=10
REACT_APP_DECIMAL_PLACES=2
```

---

## 📡 SERVICIO API BASE (services/api.js)

```javascript
import axios from 'axios';

const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';

const api = axios.create({
  baseURL: API_URL,
  timeout: parseInt(process.env.REACT_APP_API_TIMEOUT) || 30000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Interceptor de respuesta
api.interceptors.response.use(
  response => response,
  error => {
    console.error('API Error:', error);
    // Aquí puedes manejar errores globales
    throw error;
  }
);

export default api;
```

---

## 🎯 HOOKS RECOMENDADOS

### useApi.js
```javascript
// Hook para simplificar llamadas a API
const { data, loading, error, refetch } = useApi(endpoint);
```

### useLocalStorage.js
```javascript
// Hook para localStorage
const [value, setValue] = useLocalStorage(key, initialValue);
```

---

## 🎨 ESTILOS CON TAILWIND

**Clases recomendadas a usar**:
- `flex`, `grid`: layouts
- `bg-blue-600`, `text-white`: colores
- `p-4`, `m-4`: espaciado
- `rounded`, `shadow`: efectos
- `hover:bg-blue-700`: interactividad
- `text-sm`, `text-lg`: tipografía
- `border`, `border-gray-300`: bordes

---

## 🔄 FLUJO DE DATOS RECOMENDADO

```
Frontend → Axios → Backend (Spring Boot)
                   ↓
              API Endpoints
                   ↓
              Base de Datos (SQLite)
```

---

## ⚠️ VALIDACIONES IMPORTANTES EN FRONTEND

### Clientes:
- [x] Nombre es requerido
- [x] Email válido (si se proporciona)
- [x] Tipo debe ser LOCAL o ONLINE

### Productos:
- [x] Nombre es requerido
- [x] Al menos un SKU
- [x] SKU requiere precio y costo > 0
- [x] Stock >= 0

### Ventas:
- [x] Cliente seleccionado
- [x] Al menos un item en carrito
- [x] Cantidad > 0
- [x] Monto pagado >= total
- [x] Usuario y tienda seleccionados

### Reportes:
- [x] fechaInicio <= fechaFin
- [x] Formato de fecha válido (YYYY-MM-DD)

---

## 🔑 VARIABLES GLOBALES SUGERIDAS (Context)

```javascript
{
  usuarioActual: { id, nombre },
  tiendaActual: { id, nombre },
  clienteSeleccionado: null,
  carritoItems: [],
  totalCarrito: 0,
  loading: false,
  error: null,
}
```

---

## 📱 RESPONSIVIDAD

- [ ] Desktop (1280px+)
- [ ] Tablet (768px - 1279px)
- [ ] Mobile (< 768px)
- [ ] Usar Tailwind breakpoints: `sm:`, `md:`, `lg:`, `xl:`

---

## 🧪 TESTING RECOMENDADO

- [ ] Pruebas de componentes con Jest
- [ ] Pruebas E2E con Cypress
- [ ] Validación de formularios
- [ ] Manejo de errores

---

## 📊 GRÁFICOS CON CHART.JS

### Ejemplos de gráficos a implementar:

1. **Línea**: Ventas a lo largo del tiempo
```javascript
import { Line } from 'react-chartjs-2';
```

2. **Barras**: Top productos/clientes
```javascript
import { Bar } from 'react-chartjs-2';
```

3. **Pastel**: Distribución de pagos
```javascript
import { Pie } from 'react-chartjs-2';
```

---

## 🚀 PASOS INICIALES DE DESARROLLO

1. [ ] Crear proyecto React: `npx create-react-app edhen-pos-frontend`
2. [ ] Instalar dependencias: `npm install axios react-router-dom tailwindcss chart.js react-chartjs-2`
3. [ ] Configurar Tailwind CSS
4. [ ] Crear estructura de carpetas
5. [ ] Implementar servicio API base
6. [ ] Crear Context global
7. [ ] Implementar componentes base (Navbar, Layout)
8. [ ] Implementar Dashboard
9. [ ] Implementar CRUD de clientes
10. [ ] Implementar CRUD de productos
11. [ ] Implementar creación de ventas (POS)
12. [ ] Implementar reportes
13. [ ] Implementar visualización de tickets
14. [ ] Testing y refinamientos
15. [ ] Despliegue

---

## 💡 TIPS DE DESARROLLO

- Usar **React Router DOM** para navegación entre páginas
- Mantener componentes pequeños y reutilizables
- Usar **Tailwind CSS** para evitar CSS adicional
- Implementar **loading states** en todas las operaciones async
- Mostrar **error messages** claros al usuario
- Usar **localStorage** para datos no sensibles (filtros, preferencias)
- Validar en frontend ANTES de enviar al backend
- Manejar respuestas de error del backend correctamente
- Implementar debounce en búsquedas

---

## 🔗 RUTAS SUGERIDAS (React Router)

```
/                      → Dashboard
/clientes              → Listar clientes
/clientes/nuevo        → Crear cliente
/clientes/:id/editar   → Editar cliente
/productos             → Listar productos
/productos/nuevo       → Crear producto
/productos/:id/editar  → Editar producto
/ventas/nueva          → Crear venta (POS)
/ventas                → Historial de ventas
/ventas/:id            → Detalle de venta
/reportes              → Panel de reportes
/tickets               → Gestión de tickets
/tickets/:id           → Detalle de ticket
```

---

## 📞 SOPORTE

Si tienes dudas sobre los endpoints o estructura de datos, refiere a `BACKEND_API_ESPECIFICACION.md` en la raíz del proyecto.

**Backend URL**: `http://localhost:8080`  
**Frontend URL**: `http://localhost:3000`

---

**Última actualización**: 2026-04-03

