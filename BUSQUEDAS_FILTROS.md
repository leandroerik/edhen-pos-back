# 🔍 Búsquedas y Filtros - IMPLEMENTADOS

## ✅ Lo que Agregué:

### 1. **Búsquedas en Ventas**
- ✅ Filtrar por rango de fechas
- ✅ Filtrar por cliente
- ✅ Filtrar por usuario (cajero)
- ✅ Filtrar por tipo (LOCAL, ONLINE)
- ✅ Filtrar por estado (COMPLETADA, PENDIENTE, etc.)

### 2. **Búsquedas en Clientes**
- ✅ Buscar por nombre (búsqueda parcial)
- ✅ Buscar por email
- ✅ Buscar por teléfono
- ✅ Filtrar por tipo (LOCAL, ONLINE, MAYORISTA)

### 3. **Búsquedas en Productos**
- ✅ Buscar por nombre (búsqueda parcial)
- ✅ Buscar por descripción
- ✅ Listar productos activos
- ✅ Listar productos inactivos

---

## 📡 Nuevos Endpoints:

### **VENTAS - Búsquedas y Filtros:**

#### **Buscar ventas por fecha**
```
GET /ventas/buscar/fecha?fechaInicio=2026-04-01&fechaFin=2026-04-30
```
Respuesta: Lista de ventas en el rango de fechas

#### **Buscar ventas por cliente**
```
GET /ventas/buscar/cliente/1
```
Respuesta: Todas las ventas del cliente con ID 1

#### **Buscar ventas por usuario (cajero)**
```
GET /ventas/buscar/usuario/1
```
Respuesta: Todas las ventas procesadas por el usuario

#### **Filtrar ventas por tipo**
```
GET /ventas/buscar/tipo/LOCAL
GET /ventas/buscar/tipo/ONLINE
```

#### **Filtrar ventas por estado**
```
GET /ventas/buscar/estado/COMPLETADA
GET /ventas/buscar/estado/PENDIENTE
```

---

### **CLIENTES - Búsquedas y Filtros:**

#### **Buscar clientes por nombre**
```
GET /clientes/buscar/nombre?nombre=María
```
Respuesta: Clientes cuyo nombre contiene "María"

#### **Buscar clientes por email**
```
GET /clientes/buscar/email?email=gmail.com
```
Respuesta: Clientes con email que contiene "gmail.com"

#### **Buscar clientes por teléfono**
```
GET /clientes/buscar/telefono?telefono=123456789
```
Respuesta: Clientes con ese teléfono

#### **Filtrar clientes por tipo**
```
GET /clientes/buscar/tipo/LOCAL
GET /clientes/buscar/tipo/ONLINE
```

---

### **PRODUCTOS - Búsquedas y Filtros:**

#### **Buscar productos por nombre**
```
GET /productos/buscar/nombre?nombre=Musculosa
```
Respuesta: Productos que contienen "Musculosa" en el nombre

#### **Buscar productos por descripción**
```
GET /productos/buscar/descripcion?descripcion=algodón
```
Respuesta: Productos con descripción que contiene "algodón"

#### **Listar productos activos**
```
GET /productos/activos
```
Respuesta: Solo productos activos

#### **Listar productos inactivos**
```
GET /productos/inactivos
```
Respuesta: Solo productos inactivos

---

## 🧪 Ejemplos de Uso con CURL:

### **Test 1: Buscar ventas de hoy**
```bash
curl "http://localhost:8080/ventas/buscar/fecha?fechaInicio=2026-04-03&fechaFin=2026-04-03"
```

### **Test 2: Buscar clientes que se llamen "María"**
```bash
curl "http://localhost:8080/clientes/buscar/nombre?nombre=María"
```

### **Test 3: Buscar productos "Musculosa"**
```bash
curl "http://localhost:8080/productos/buscar/nombre?nombre=Musculosa"
```

### **Test 4: Ver ventas del cliente 1**
```bash
curl "http://localhost:8080/ventas/buscar/cliente/1"
```

### **Test 5: Ver ventas procesadas por usuario 1**
```bash
curl "http://localhost:8080/ventas/buscar/usuario/1"
```

### **Test 6: Listar productos activos**
```bash
curl "http://localhost:8080/productos/activos"
```

### **Test 7: Buscar clientes por email**
```bash
curl "http://localhost:8080/clientes/buscar/email?email=edhen.com"
```

---

## 🎯 Características de las Búsquedas:

✅ **Case-insensitive** - Búsquedas sin importar mayúsculas/minúsculas
✅ **Búsqueda parcial** - Busca por coincidencia parcial (no requiere texto exacto)
✅ **Validación** - Verifica que el recurso existe (ej: cliente ID válido)
✅ **Mensajes de error** - Errores claros si no se encuentra
✅ **Filtros múltiples** - Puedes usar varios criterios

---

## 📋 Resumen de Métodos en Servicios:

### VentaService:
- `buscarVentasPorFecha(fechaInicio, fechaFin)`
- `buscarVentasPorCliente(clienteId)`
- `buscarVentasPorUsuario(usuarioId)`
- `buscarVentasPorTipo(tipo)`
- `buscarVentasPorEstado(estado)`

### ClienteService:
- `buscarPorNombre(nombre)`
- `buscarPorEmail(email)`
- `buscarPorTelefono(telefono)`
- `buscarPorTipo(tipo)`

### ProductoService:
- `buscarPorNombre(nombre)`
- `buscarPorDescripcion(descripcion)`
- `listarProductosActivos()`
- `listarProductosInactivos()`

---

## 🚀 Para Probar:

```bash
# Ejecuta la app
mvn spring-boot:run

# Prueba las búsquedas
curl "http://localhost:8080/ventas/buscar/fecha?fechaInicio=2026-04-03&fechaFin=2026-04-03"
curl "http://localhost:8080/clientes/buscar/nombre?nombre=Cliente"
curl "http://localhost:8080/productos/activos"
```

---

## 🎊 ¡Sistema Completo!

Ahora tienes:
- ✅ CRUD completo
- ✅ Validaciones y excepciones
- ✅ **Búsquedas y filtros** ← NUEVO
- ✅ PDF con QR
- ✅ Datos mockeados

**¡Próximo paso: Reportes y Estadísticas!** 📊
