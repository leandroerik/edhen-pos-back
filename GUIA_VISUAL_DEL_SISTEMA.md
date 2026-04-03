# 🎨 GUÍA VISUAL DEL SISTEMA

**Diagramas y flows visuales para entender el sistema**

---

## 📐 ARQUITECTURA DEL SISTEMA

```
┌─────────────────────────────────────────────────────────────────────┐
│                         FRONTEND REACT                              │
│  (React 18 + Axios + Tailwind + Chart.js + React Router)           │
│                                                                     │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐              │
│  │  Dashboard   │  │  Clientes    │  │  Productos   │              │
│  └──────────────┘  └──────────────┘  └──────────────┘              │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐              │
│  │   POS/Venta  │  │  Reportes    │  │   Tickets    │              │
│  └──────────────┘  └──────────────┘  └──────────────┘              │
└─────────────────────────────────────────────────────────────────────┘
                              ↕
                     (AXIOS HTTP CLIENT)
                              ↕
┌─────────────────────────────────────────────────────────────────────┐
│                    BACKEND SPRING BOOT                              │
│              (Spring Boot 3.5.13 + Java 21)                        │
│                                                                     │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐              │
│  │ Controllers  │  │   Services   │  │  Repositories│              │
│  │  (REST API)  │  │   (Lógica)   │  │   (JPA)      │              │
│  └──────────────┘  └──────────────┘  └──────────────┘              │
│           ↕                ↕                ↕                       │
│  /clientes           Negocio            BD Queries                 │
│  /productos                                                         │
│  /ventas             Validaciones                                  │
│  /reportes           Cálculos                                      │
│  /documentos         Transformaciones                              │
└─────────────────────────────────────────────────────────────────────┘
                              ↕
┌─────────────────────────────────────────────────────────────────────┐
│                     BASE DE DATOS SQLITE                            │
│                         (pos.db)                                    │
│                                                                     │
│  ┌─────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐             │
│  │ Cliente  │  │ Producto │  │ Venta    │  │ Pago     │             │
│  └─────────┘  └──────────┘  └──────────┘  └──────────┘             │
│  ┌─────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐             │
│  │   SKU   │  │ Usuario  │  │ Tienda   │  │VentaDetal│             │
│  └─────────┘  └──────────┘  └──────────┘  └──────────┘             │
└─────────────────────────────────────────────────────────────────────┘
```

---

## 🔄 FLUJO DE UNA VENTA COMPLETA

```
┌─────────────────────────────────────────────────────────────────┐
│ USUARIO EN PANTALLA POS                                         │
└─────────────────────────────────────────────────────────────────┘
                           ↓
        ┌──────────────────────────────────────┐
        │ 1. SELECCIONAR CLIENTE               │
        │    GET /clientes (cargar selector)   │
        │    + click en cliente o crear nuevo  │
        └──────────────────────────────────────┘
                           ↓
        ┌──────────────────────────────────────┐
        │ 2. AGREGAR PRODUCTOS                 │
        │    GET /productos/activos (búsqueda) │
        │    + agregar al carrito              │
        │    + aumentar cantidad               │
        └──────────────────────────────────────┘
                           ↓
        ┌──────────────────────────────────────┐
        │ 3. CALCULAR TOTAL                    │
        │    sum(item.precio * item.cantidad)  │
        │    + aplicar descuentos (opcional)   │
        └──────────────────────────────────────┘
                           ↓
        ┌──────────────────────────────────────┐
        │ 4. INGRESA DATOS DE PAGO             │
        │    - Monto                           │
        │    - Método (EFECTIVO/TARJETA/etc)   │
        │    - Validar: monto >= total         │
        └──────────────────────────────────────┘
                           ↓
        ┌──────────────────────────────────────┐
        │ 5. PROCESAR VENTA                    │
        │                                      │
        │    POST /ventas                      │
        │    {                                 │
        │      clienteId, usuarioId,           │
        │      tiendaId, tipoVenta,            │
        │      items[], pagos[]                │
        │    }                                 │
        └──────────────────────────────────────┘
                           ↓
        ┌──────────────────────────────────────┐
        │ 6. BACKEND PROCESA                   │
        │    - Valida datos                    │
        │    - Crea Venta                      │
        │    - Crea VentaDetalles              │
        │    - Crea Pagos                      │
        │    - Actualiza inventario (stock)    │
        │    - Genera Ticket/Documento         │
        └──────────────────────────────────────┘
                           ↓
        ┌──────────────────────────────────────┐
        │ 7. RESPUESTA EXITOSA                 │
        │    Venta { id, fecha, total, ... }   │
        └──────────────────────────────────────┘
                           ↓
        ┌──────────────────────────────────────┐
        │ 8. FRONTEND MUESTRA CONFIRMACIÓN     │
        │    - ¡Venta creada exitosamente!    │
        │    - Opciones:                       │
        │      • Descargar PDF                 │
        │      • Imprimir                      │
        │      • Crear nueva venta             │
        └──────────────────────────────────────┘
                           ↓
        ┌──────────────────────────────────────┐
        │ 9. LIMPIAR INTERFAZ                  │
        │    - Vaciar carrito                  │
        │    - Resetear formulario             │
        │    - Deseleccionar cliente           │
        └──────────────────────────────────────┘
```

---

## 📊 ESTRUCTURA DE DATOS - VENTA COMPLETA

```
VENTA
  ├─ id: 1
  ├─ fecha: 2026-04-03T15:30:00
  ├─ total: 299.97
  ├─ tipoVenta: "LOCAL"
  ├─ estado: "COMPLETADA"
  ├─ cliente
  │   ├─ id: 5
  │   ├─ nombre: "Juan García"
  │   ├─ email: "juan@mail.com"
  │   ├─ telefono: "555-1234"
  │   └─ tipo: "LOCAL"
  ├─ usuario
  │   ├─ id: 1
  │   └─ nombre: "Vendedor"
  ├─ tienda
  │   ├─ id: 1
  │   └─ nombre: "Principal"
  ├─ detalles[]
  │   ├─ [0]
  │   │   ├─ id: 101
  │   │   ├─ cantidad: 2
  │   │   ├─ precioUnitario: 99.99
  │   │   ├─ precioOriginal: 99.99
  │   │   ├─ costoUnitario: 50.00
  │   │   └─ sku: { id: 10, codigoBarra: "750..." }
  │   └─ [1]
  │       ├─ id: 102
  │       ├─ cantidad: 1
  │       ├─ precioUnitario: 99.99
  │       ├─ precioOriginal: 99.99
  │       ├─ costoUnitario: 50.00
  │       └─ sku: { id: 11 }
  └─ pagos[]
      ├─ [0]
      │   ├─ id: 201
      │   ├─ monto: 300.00
      │   ├─ metodo: "EFECTIVO"
      │   ├─ estado: "CONFIRMADO"
      │   └─ fecha: 2026-04-03T15:30:00
      └─ (más pagos si los hay)
```

---

## 🎯 MATRIZ DE ENDPOINTS POR PANTALLA

```
┌─────────────────────────────────────────────────────────────────┐
│ DASHBOARD                                                       │
├─────────────────────────────────────────────────────────────────┤
│ GET /reportes/total-vendido                                     │
│ GET /reportes/ventas?desde=hoy&hasta=hoy                       │
│ GET /reportes/productos-mas-vendidos?desde=hoy&hasta=hoy&top=5 │
│ GET /reportes/inventario                                        │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│ CLIENTES                                                        │
├─────────────────────────────────────────────────────────────────┤
│ GET /clientes                                                   │
│ POST /clientes                                                  │
│ PUT /clientes/{id}                                              │
│ DELETE /clientes/{id}                                           │
│ GET /clientes/buscar/nombre?nombre=...                         │
│ GET /clientes/buscar/email?email=...                           │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│ PRODUCTOS                                                       │
├─────────────────────────────────────────────────────────────────┤
│ GET /productos                                                  │
│ GET /productos/activos                                          │
│ POST /productos                                                 │
│ PUT /productos/{id}                                             │
│ DELETE /productos/{id}                                          │
│ GET /skus                                                       │
│ POST /skus                                                      │
│ PUT /skus/{id}                                                  │
│ DELETE /skus/{id}                                               │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│ POS (CREAR VENTA) - ⭐ MÁS IMPORTANTE                           │
├─────────────────────────────────────────────────────────────────┤
│ GET /clientes                  (selector cliente)               │
│ GET /productos/activos         (búsqueda)                       │
│ GET /productos/{id}            (detalles con SKUs)              │
│ ★ POST /ventas                 (CREAR VENTA)                   │
│ GET /documentos/ticket/{id}    (ver ticket)                     │
│ GET /documentos/ticket/{id}/pdf (descargar PDF)                 │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│ REPORTES                                                        │
├─────────────────────────────────────────────────────────────────┤
│ GET /reportes/ventas?desde=...&hasta=...                       │
│ GET /reportes/productos-mas-vendidos?desde=...&hasta=...&top=10│
│ GET /reportes/top-clientes?desde=...&hasta=...&top=10          │
│ GET /reportes/ganancias?desde=...&hasta=...                    │
│ GET /reportes/inventario                                        │
│ GET /reportes/total-vendido                                     │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│ TICKETS                                                         │
├─────────────────────────────────────────────────────────────────┤
│ GET /ventas                    (historial)                      │
│ GET /ventas/{id}               (detalle)                        │
│ GET /documentos/ticket/{id}    (ver ticket)                     │
│ GET /documentos/ticket/{id}/pdf (descargar PDF)                 │
└─────────────────────────────────────────────────────────────────┘
```

---

## 📈 FLUJO DE DATOS EN UNA OPERACIÓN TÍPICA

```
┌────────────────────┐
│ Usuario hace click │
└────────────────────┘
         ↓
┌────────────────────────────────────────┐
│ Event Handler en React (onClick)       │
└────────────────────────────────────────┘
         ↓
┌────────────────────────────────────────┐
│ Validación en Frontend                 │
│ - Datos requeridos                     │
│ - Formatos válidos                     │
│ - Lógica específica                    │
└────────────────────────────────────────┘
         ↓ (Si pasa validación)
┌────────────────────────────────────────┐
│ Llamada a Servicio (clienteService)    │
│ - Método axios.get/post/put/delete     │
│ - URL construida dinámicamente         │
│ - Headers y body incluidos             │
└────────────────────────────────────────┘
         ↓
┌────────────────────────────────────────┐
│ HTTP Request a Backend                 │
│ GET /ruta?param=value                  │
│ POST /ruta con body JSON               │
└────────────────────────────────────────┘
         ↓
┌────────────────────────────────────────┐
│ Backend Spring Boot                    │
│ - Controller mapea ruta                │
│ - Service ejecuta lógica               │
│ - Repository accede a BD               │
│ - Retorna Response JSON                │
└────────────────────────────────────────┘
         ↓
┌────────────────────────────────────────┐
│ HTTP Response                          │
│ Status 200 + data JSON                 │
└────────────────────────────────────────┘
         ↓
┌────────────────────────────────────────┐
│ Frontend recibe respuesta              │
│ - Promise se resuelve (then/await)     │
│ - Estado se actualiza (setState)       │
│ - Componente re-renderiza              │
└────────────────────────────────────────┘
         ↓
┌────────────────────────────────────────┐
│ Usuario ve resultado en pantalla       │
│ - Modal de éxito                       │
│ - Tabla actualizada                    │
│ - Formulario limpio                    │
└────────────────────────────────────────┘
```

---

## 🎨 LAYOUT SUGERIDO - PANTALLA POS

```
┌─────────────────────────────────────────────────────────────────────┐
│ NAVBAR - Sistema POS Edhen                                          │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│ ┌──────────────────────────┐ ┌────────────────────────────────────┐│
│ │ BÚSQUEDA DE PRODUCTOS    │ │ RESUMEN DE VENTA                   ││
│ │                          │ │                                    ││
│ │ Buscar:  [___________]   │ │ Cliente: [ Juan García    ▼]      ││
│ │                          │ │ Tipo:    [ LOCAL         ▼]      ││
│ │ [Resultado 1] $99.99     │ │                                    ││
│ │ [Resultado 2] $49.99     │ │ ────────────────────────────────  ││
│ │ [Resultado 3] $150.00    │ │ Carrito (3 items)                 ││
│ │                          │ │ ────────────────────────────────  ││
│ │ Productos del día:       │ │ [x] Zapatos     qty:2  $199.98    ││
│ │ • Zapatos                │ │ [x] Shirt       qty:1  $49.99     ││
│ │ • Shirt                  │ │                                    ││
│ │ • Jeans                  │ │ Subtotal:                $249.97   ││
│ │ • Accesories             │ │                                    ││
│ │                          │ │ ────────────────────────────────  ││
│ │                          │ │ Pagos:                             ││
│ │                          │ │ $ [________] [EFECTIVO ▼]         ││
│ │                          │ │                                    ││
│ │                          │ │ TOTAL: $249.97                    ││
│ │                          │ │                                    ││
│ │                          │ │ [PROCESAR VENTA]                  ││
│ │                          │ │                                    ││
│ └──────────────────────────┘ └────────────────────────────────────┘│
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
```

---

## 🔄 CICLO DE COMPONENTE REACT CON API

```
1. MONTAJE (useEffect)
   └─ Llamar API
   └─ Actualizar state con datos
   └─ Re-renderizar

2. USUARIO INTERACTÚA
   └─ onClick / onChange / onSubmit
   └─ Llamar función handler

3. VALIDACIÓN
   └─ Verificar datos
   └─ Si error → mostrar alerta
   └─ Si ok → siguiente paso

4. ENVIAR A API
   └─ Loading = true
   └─ Llamar servicio (axios)
   └─ Esperar respuesta

5. PROCESAR RESPUESTA
   └─ Si error (catch)
   │   └─ Mostrar error
   │   └─ Loading = false
   └─ Si éxito (then)
       └─ Actualizar state
       └─ Mostrar éxito
       └─ Refetch datos si necesario
       └─ Limpiar formulario

6. DESMONTAR
   └─ Cleanup si es necesario
```

---

## 💾 RELACIONES EN BASE DE DATOS

```
        CLIENTE               USUARIO              TIENDA
          (1)                  (1)                  (1)
           ↓                   ↓                    ↓
        ┌─────┐────────────┬────────────────────┬─────┐
        │     │            │                    │     │
        │   VENTA (N)      │                    │     │
        │     │            │                    │     │
        └─────┘────────────┴────────────────────┴─────┘
            │
            ↓ (1 a N)
        ┌─────────────────┐        ┌──────────────┐
        │ VENTADETALLE    │        │ PAGO         │
        │                 │        │              │
        │ - id            │        │ - id         │
        │ - cantidad      │        │ - monto      │
        │ - precio        │        │ - metodo     │
        │ - costo         │        │ - estado     │
        └─────────────────┘        └──────────────┘
            │
            ↓ (1 a N)
        ┌─────┐
        │ SKU │
        │     │
        └─────┘
            │
            ↓ (N a 1)
       ┌──────────┐
       │ PRODUCTO │
       │          │
       └──────────┘
```

---

## 📞 MATRIZ DE VALIDACIÓN

```
CAMPO           │ TIPO      │ REQUERIDO │ VALIDACIÓN              │ ERROR
────────────────┼───────────┼───────────┼─────────────────────────┼─────────────────
Cliente         │ Select    │ SÍ        │ != null                 │ "Selecciona cliente"
Producto        │ Search    │ SÍ        │ stock > 0               │ "Sin stock"
Cantidad        │ Number    │ SÍ        │ > 0 y <= stock          │ "Cantidad inválida"
Tipo Venta      │ Select    │ SÍ        │ LOCAL o ONLINE          │ "Tipo inválido"
Monto Pago      │ Number    │ SÍ        │ > 0                     │ "Monto inválido"
Método Pago     │ Select    │ SÍ        │ EFECTIVO/TARJETA/etc    │ "Método inválido"
Total Pagado    │ Calc      │ SÍ        │ >= Total Venta          │ "Monto insuficiente"
Correo Cliente  │ Text      │ NO        │ Formato email si existe │ "Email inválido"
Nombre Cliente  │ Text      │ SÍ        │ != vacío                │ "Nombre requerido"
```

---

**Última actualización**: 2026-04-03

