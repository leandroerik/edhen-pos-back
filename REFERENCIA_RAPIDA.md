# 🚀 GUÍA RÁPIDA - ENDPOINTS PARA POSTMAN

## ⚡ LOS 10 ENDPOINTS MÁS IMPORTANTES

```
1. GET http://localhost:8080/clientes
   └─ Ver todos los clientes

2. GET http://localhost:8080/productos
   └─ Ver todos los productos

3. GET http://localhost:8080/ventas
   └─ Ver todas las ventas

4. GET http://localhost:8080/ventas/1
   └─ Ver venta de prueba ($36,000)

5. GET http://localhost:8080/documentos/ticket/1
   └─ Ver ticket en texto

6. GET http://localhost:8080/documentos/ticket/1/pdf
   └─ Descargar PDF del ticket ⭐

7. POST http://localhost:8080/ventas
   └─ Crear nueva venta

8. GET http://localhost:8080/reportes/total-vendido
   └─ Ver total de ventas

9. GET http://localhost:8080/reportes/inventario
   └─ Ver stock actual

10. GET http://localhost:8080/reportes/productos-mas-vendidos?top=5
    └─ Ver top 5 productos vendidos
```

---

## 📮 CÓMO IMPORTAR EN POSTMAN

**3 PASOS:**

1. Abre Postman
2. Import → Postman_Collection.json
3. ¡Listo!

---

## 👥 CLIENTES - 9 ENDPOINTS

| Método | URL | Descripción |
|--------|-----|------------|
| GET | `/clientes` | Listar todos |
| GET | `/clientes/1` | Obtener por ID |
| POST | `/clientes` | Crear cliente |
| PUT | `/clientes/1` | Actualizar cliente |
| DELETE | `/clientes/1` | Eliminar cliente |
| GET | `/clientes/buscar/nombre?nombre=María` | Buscar por nombre |
| GET | `/clientes/buscar/email?email=gmail` | Buscar por email |
| GET | `/clientes/buscar/telefono?telefono=123` | Buscar por teléfono |
| GET | `/clientes/buscar/tipo/LOCAL` | Filtrar por tipo |

---

## 👕 PRODUCTOS - 9 ENDPOINTS

| Método | URL | Descripción |
|--------|-----|------------|
| GET | `/productos` | Listar todos |
| GET | `/productos/1` | Obtener por ID |
| POST | `/productos` | Crear producto |
| PUT | `/productos/1` | Actualizar producto |
| DELETE | `/productos/1` | Eliminar producto |
| GET | `/productos/activos` | Solo activos |
| GET | `/productos/inactivos` | Solo inactivos |
| GET | `/productos/buscar/nombre?nombre=Musculosa` | Buscar por nombre |
| GET | `/productos/buscar/descripcion?descripcion=algodón` | Buscar por descripción |

---

## 💰 VENTAS - 10 ENDPOINTS

| Método | URL | Descripción |
|--------|-----|------------|
| GET | `/ventas` | Listar todas |
| GET | `/ventas/1` | Obtener por ID |
| POST | `/ventas` | Crear venta |
| PUT | `/ventas/1` | Actualizar venta |
| DELETE | `/ventas/1` | Eliminar venta |
| GET | `/ventas/buscar/fecha?fechaInicio=2026-04-01&fechaFin=2026-04-30` | Por fecha |
| GET | `/ventas/buscar/cliente/1` | Por cliente |
| GET | `/ventas/buscar/usuario/1` | Por usuario |
| GET | `/ventas/buscar/tipo/LOCAL` | Por tipo |
| GET | `/ventas/buscar/estado/COMPLETADA` | Por estado |

---

## 📄 DOCUMENTOS - 8 ENDPOINTS

| Método | URL | Descripción |
|--------|-----|------------|
| GET | `/documentos` | Listar todos |
| GET | `/documentos/1` | Obtener por ID |
| POST | `/documentos` | Crear documento |
| PUT | `/documentos/1` | Actualizar |
| DELETE | `/documentos/1` | Eliminar |
| GET | `/documentos/ticket/1` | Ticket en texto |
| GET | `/documentos/ticket/1/pdf` | Descargar PDF ⭐ |
| GET | `/documentos/venta/1` | Por venta |

---

## 📊 REPORTES - 6 ENDPOINTS

| Método | URL | Descripción |
|--------|-----|------------|
| GET | `/reportes/total-vendido` | Total histórico |
| GET | `/reportes/inventario` | Stock actual |
| GET | `/reportes/ventas?desde=2026-04-01&hasta=2026-04-30` | Ventas período |
| GET | `/reportes/productos-mas-vendidos?desde=2026-04-01&hasta=2026-04-30&top=5` | Top 5 productos |
| GET | `/reportes/ganancias?desde=2026-04-01&hasta=2026-04-30` | Ganancias totales |
| GET | `/reportes/top-clientes?desde=2026-04-01&hasta=2026-04-30&top=10` | Top 10 clientes |

---

## 📦 SKUs - 5 ENDPOINTS

| Método | URL | Descripción |
|--------|-----|------------|
| GET | `/skus` | Listar todos |
| GET | `/skus/1` | Obtener por ID |
| POST | `/skus` | Crear SKU |
| PUT | `/skus/1` | Actualizar SKU |
| DELETE | `/skus/1` | Eliminar SKU |

---

## 👨‍💼 USUARIOS - 5 ENDPOINTS

| Método | URL | Descripción |
|--------|-----|------------|
| GET | `/usuarios` | Listar todos |
| GET | `/usuarios/1` | Obtener por ID |
| POST | `/usuarios` | Crear usuario |
| PUT | `/usuarios/1` | Actualizar |
| DELETE | `/usuarios/1` | Eliminar |

---

## 🏪 TIENDAS - 5 ENDPOINTS

| Método | URL | Descripción |
|--------|-----|------------|
| GET | `/tiendas` | Listar todas |
| GET | `/tiendas/1` | Obtener por ID |
| POST | `/tiendas` | Crear tienda |
| PUT | `/tiendas/1` | Actualizar |
| DELETE | `/tiendas/1` | Eliminar |

---

## 💾 ADMIN - 2 ENDPOINTS

| Método | URL | Descripción |
|--------|-----|------------|
| GET | `/admin/exportar-bdd` | Descargar BD |
| POST | `/admin/importar-bdd` | Subir BD |

---

## 🔥 FLUJO DE PRUEBA RECOMENDADO

**Día 1 - GET (Leer datos):**
```
1. /clientes
2. /productos
3. /ventas
4. /ventas/1
5. /reportes/total-vendido
6. /reportes/inventario
```

**Día 2 - Búsquedas y Filtros:**
```
1. /clientes/buscar/nombre?nombre=María
2. /productos/buscar/nombre?nombre=Musculosa
3. /ventas/buscar/cliente/1
4. /ventas/buscar/fecha?fechaInicio=2026-04-01&fechaFin=2026-04-30
5. /productos/activos
```

**Día 3 - Reportes:**
```
1. /reportes/total-vendido
2. /reportes/inventario
3. /reportes/productos-mas-vendidos?top=5
4. /reportes/ganancias?desde=2026-04-01&hasta=2026-04-30
5. /reportes/top-clientes?top=10
```

**Día 4 - Crear (POST):**
```
1. POST /clientes → Crear nuevo cliente
2. POST /productos → Crear nuevo producto
3. POST /ventas → Crear nueva venta
4. GET /documentos/ticket/{id}/pdf → Descargar PDF
```

**Día 5 - Actualizar y Eliminar:**
```
1. PUT /clientes/{id} → Modificar cliente
2. DELETE /clientes/{id} → Eliminar cliente
3. PUT /productos/{id} → Modificar producto
4. DELETE /productos/{id} → Eliminar producto
```

---

## 💡 HEADERS NECESARIOS

Para **GET**: Ninguno requerido

Para **POST/PUT**: Agregar:
```
Content-Type: application/json
```

En Postman ya está configurado en la colección.

---

## 📋 BODY EJEMPLOS

### Crear Cliente:
```json
{
  "nombre": "Juan Pérez",
  "email": "juan@gmail.com",
  "telefono": "1123456789",
  "tipo": "LOCAL"
}
```

### Crear Venta:
```json
{
  "clienteId": 1,
  "usuarioId": 1,
  "tiendaId": 1,
  "tipoVenta": "LOCAL",
  "items": [
    {"skuId": 4, "cantidad": 2}
  ],
  "pagos": [
    {"monto": 24000.0, "metodo": "EFECTIVO"}
  ]
}
```

### Crear Producto:
```json
{
  "nombre": "Pantalón",
  "descripcion": "Pantalón de algodón",
  "activo": true,
  "skus": [
    {
      "codigoBarra": "PANT-NEGRO-M",
      "precio": 25000.0,
      "costo": 12000.0,
      "stock": 10
    }
  ]
}
```

---

## ✅ LISTA DE VERIFICACIÓN

Después de importar:

- [ ] Ver colección cargada en Postman
- [ ] Probar GET /clientes
- [ ] Probar GET /ventas/1
- [ ] Probar GET /documentos/ticket/1/pdf
- [ ] Probar /reportes/total-vendido
- [ ] Crear un cliente con POST
- [ ] Crear una venta con POST
- [ ] Descargar un PDF
- [ ] Buscar con parámetros
- [ ] Filtrar por fecha

---

## 🆘 PROBLEMAS COMUNES

**"Connection refused"**
→ Asegúrate que la app corre en puerto 8080

**"Cannot send request"**
→ Verifica la URL exacta

**"Invalid JSON"**
→ Verifica el body (sin comas extras)

**"ID not found" (404)**
→ Usa un ID que exista

---

**¡Todos los endpoints en una página! 📮**

Para más detalles, ver:
- ENDPOINTS_POSTMAN.md (referencia completa)
- COMO_USAR_POSTMAN.md (instrucciones detalladas)

