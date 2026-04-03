# 📮 GUÍA COMPLETA DE ENDPOINTS PARA POSTMAN

**Base URL:** `http://localhost:8080`

---

## 🔐 HEADERS REQUERIDOS

Para todos los requests POST/PUT, agrega estos headers:
```
Content-Type: application/json
```

---

## 👥 CLIENTES

### 1. Listar todos los clientes
- **Método:** GET
- **URL:** `http://localhost:8080/clientes`
- **Headers:** Ninguno
- **Body:** Ninguno

### 2. Obtener cliente por ID
- **Método:** GET
- **URL:** `http://localhost:8080/clientes/1`
- **Headers:** Ninguno
- **Body:** Ninguno

### 3. Crear cliente
- **Método:** POST
- **URL:** `http://localhost:8080/clientes`
- **Headers:** `Content-Type: application/json`
- **Body:**
```json
{
  "nombre": "Juan Pérez",
  "email": "juan@gmail.com",
  "telefono": "1123456789",
  "tipo": "LOCAL"
}
```

### 4. Actualizar cliente
- **Método:** PUT
- **URL:** `http://localhost:8080/clientes/1`
- **Headers:** `Content-Type: application/json`
- **Body:**
```json
{
  "nombre": "Juan Pérez Actualizado",
  "email": "juan.actualizado@gmail.com",
  "telefono": "1123456789",
  "tipo": "LOCAL"
}
```

### 5. Eliminar cliente
- **Método:** DELETE
- **URL:** `http://localhost:8080/clientes/1`
- **Headers:** Ninguno
- **Body:** Ninguno

### 6. Buscar clientes por nombre
- **Método:** GET
- **URL:** `http://localhost:8080/clientes/buscar/nombre?nombre=María`
- **Headers:** Ninguno
- **Body:** Ninguno

### 7. Buscar clientes por email
- **Método:** GET
- **URL:** `http://localhost:8080/clientes/buscar/email?email=edhen`
- **Headers:** Ninguno
- **Body:** Ninguno

### 8. Buscar clientes por teléfono
- **Método:** GET
- **URL:** `http://localhost:8080/clientes/buscar/telefono?telefono=1123456789`
- **Headers:** Ninguno
- **Body:** Ninguno

### 9. Filtrar clientes por tipo
- **Método:** GET
- **URL:** `http://localhost:8080/clientes/buscar/tipo/LOCAL`
- **Headers:** Ninguno
- **Body:** Ninguno

---

## 👕 PRODUCTOS

### 1. Listar todos los productos
- **Método:** GET
- **URL:** `http://localhost:8080/productos`
- **Headers:** Ninguno
- **Body:** Ninguno

### 2. Obtener producto por ID
- **Método:** GET
- **URL:** `http://localhost:8080/productos/1`
- **Headers:** Ninguno
- **Body:** Ninguno

### 3. Crear producto con SKUs
- **Método:** POST
- **URL:** `http://localhost:8080/productos`
- **Headers:** `Content-Type: application/json`
- **Body:**
```json
{
  "nombre": "Pantalón Premium",
  "descripcion": "Pantalón de algodón premium",
  "activo": true,
  "skus": [
    {
      "codigoBarra": "PANT-NEGRO-M",
      "precio": 25000.0,
      "costo": 12000.0,
      "stock": 10
    },
    {
      "codigoBarra": "PANT-NEGRO-L",
      "precio": 25000.0,
      "costo": 12000.0,
      "stock": 15
    }
  ]
}
```

### 4. Actualizar producto
- **Método:** PUT
- **URL:** `http://localhost:8080/productos/1`
- **Headers:** `Content-Type: application/json`
- **Body:**
```json
{
  "nombre": "Pantalón Premium Actualizado",
  "descripcion": "Pantalón de algodón premium de alta calidad",
  "activo": true
}
```

### 5. Eliminar producto
- **Método:** DELETE
- **URL:** `http://localhost:8080/productos/1`
- **Headers:** Ninguno
- **Body:** Ninguno

### 6. Listar productos activos
- **Método:** GET
- **URL:** `http://localhost:8080/productos/activos`
- **Headers:** Ninguno
- **Body:** Ninguno

### 7. Listar productos inactivos
- **Método:** GET
- **URL:** `http://localhost:8080/productos/inactivos`
- **Headers:** Ninguno
- **Body:** Ninguno

### 8. Buscar productos por nombre
- **Método:** GET
- **URL:** `http://localhost:8080/productos/buscar/nombre?nombre=Musculosa`
- **Headers:** Ninguno
- **Body:** Ninguno

### 9. Buscar productos por descripción
- **Método:** GET
- **URL:** `http://localhost:8080/productos/buscar/descripcion?descripcion=algodón`
- **Headers:** Ninguno
- **Body:** Ninguno

---

## 📦 SKUs

### 1. Listar todos los SKUs
- **Método:** GET
- **URL:** `http://localhost:8080/skus`
- **Headers:** Ninguno
- **Body:** Ninguno

### 2. Obtener SKU por ID
- **Método:** GET
- **URL:** `http://localhost:8080/skus/1`
- **Headers:** Ninguno
- **Body:** Ninguno

### 3. Crear SKU
- **Método:** POST
- **URL:** `http://localhost:8080/skus`
- **Headers:** `Content-Type: application/json`
- **Body:**
```json
{
  "codigoBarra": "REMERA-ROJA-M",
  "precio": 15000.0,
  "costo": 7500.0,
  "stock": 20,
  "productoId": 4
}
```

### 4. Actualizar SKU
- **Método:** PUT
- **URL:** `http://localhost:8080/skus/1`
- **Headers:** `Content-Type: application/json`
- **Body:**
```json
{
  "codigoBarra": "REMERA-ROJA-M-UPDATED",
  "precio": 16000.0,
  "costo": 8000.0,
  "stock": 25
}
```

### 5. Eliminar SKU
- **Método:** DELETE
- **URL:** `http://localhost:8080/skus/1`
- **Headers:** Ninguno
- **Body:** Ninguno

---

## 👨‍💼 USUARIOS

### 1. Listar todos los usuarios
- **Método:** GET
- **URL:** `http://localhost:8080/usuarios`
- **Headers:** Ninguno
- **Body:** Ninguno

### 2. Obtener usuario por ID
- **Método:** GET
- **URL:** `http://localhost:8080/usuarios/1`
- **Headers:** Ninguno
- **Body:** Ninguno

### 3. Crear usuario
- **Método:** POST
- **URL:** `http://localhost:8080/usuarios`
- **Headers:** `Content-Type: application/json`
- **Body:**
```json
{
  "nombre": "Vendedor",
  "email": "vendedor@edhen.com",
  "password": "1234",
  "rol": "VENDEDOR",
  "activo": true,
  "tiendaId": 1
}
```

### 4. Actualizar usuario
- **Método:** PUT
- **URL:** `http://localhost:8080/usuarios/1`
- **Headers:** `Content-Type: application/json`
- **Body:**
```json
{
  "nombre": "Admin Actualizado",
  "email": "admin.actualizado@edhen.com",
  "password": "5678",
  "rol": "ADMIN",
  "activo": true,
  "tiendaId": 1
}
```

### 5. Eliminar usuario
- **Método:** DELETE
- **URL:** `http://localhost:8080/usuarios/1`
- **Headers:** Ninguno
- **Body:** Ninguno

---

## 🏪 TIENDAS

### 1. Listar todas las tiendas
- **Método:** GET
- **URL:** `http://localhost:8080/tiendas`
- **Headers:** Ninguno
- **Body:** Ninguno

### 2. Obtener tienda por ID
- **Método:** GET
- **URL:** `http://localhost:8080/tiendas/1`
- **Headers:** Ninguno
- **Body:** Ninguno

### 3. Crear tienda
- **Método:** POST
- **URL:** `http://localhost:8080/tiendas`
- **Headers:** `Content-Type: application/json`
- **Body:**
```json
{
  "nombre": "EDHEN San Isidro"
}
```

### 4. Actualizar tienda
- **Método:** PUT
- **URL:** `http://localhost:8080/tiendas/1`
- **Headers:** `Content-Type: application/json`
- **Body:**
```json
{
  "nombre": "EDHEN Flores Actualizado"
}
```

### 5. Eliminar tienda
- **Método:** DELETE
- **URL:** `http://localhost:8080/tiendas/1`
- **Headers:** Ninguno
- **Body:** Ninguno

---

## 💰 VENTAS

### 1. Listar todas las ventas
- **Método:** GET
- **URL:** `http://localhost:8080/ventas`
- **Headers:** Ninguno
- **Body:** Ninguno

### 2. Obtener venta por ID
- **Método:** GET
- **URL:** `http://localhost:8080/ventas/1`
- **Headers:** Ninguno
- **Body:** Ninguno

### 3. Crear venta
- **Método:** POST
- **URL:** `http://localhost:8080/ventas`
- **Headers:** `Content-Type: application/json`
- **Body:**
```json
{
  "clienteId": 1,
  "usuarioId": 1,
  "tiendaId": 1,
  "tipoVenta": "LOCAL",
  "items": [
    {
      "skuId": 4,
      "cantidad": 2
    },
    {
      "skuId": 5,
      "cantidad": 1
    }
  ],
  "pagos": [
    {
      "monto": 24000.0,
      "metodo": "EFECTIVO"
    },
    {
      "monto": 15000.0,
      "metodo": "TARJETA"
    }
  ]
}
```

### 4. Actualizar venta
- **Método:** PUT
- **URL:** `http://localhost:8080/ventas/1`
- **Headers:** `Content-Type: application/json`
- **Body:**
```json
{
  "clienteId": 2,
  "usuarioId": 1,
  "tiendaId": 1,
  "tipoVenta": "ONLINE",
  "estado": "COMPLETADA"
}
```

### 5. Eliminar venta
- **Método:** DELETE
- **URL:** `http://localhost:8080/ventas/1`
- **Headers:** Ninguno
- **Body:** Ninguno

### 6. Buscar ventas por fecha
- **Método:** GET
- **URL:** `http://localhost:8080/ventas/buscar/fecha?fechaInicio=2026-04-01&fechaFin=2026-04-30`
- **Headers:** Ninguno
- **Body:** Ninguno

### 7. Buscar ventas por cliente
- **Método:** GET
- **URL:** `http://localhost:8080/ventas/buscar/cliente/1`
- **Headers:** Ninguno
- **Body:** Ninguno

### 8. Buscar ventas por usuario
- **Método:** GET
- **URL:** `http://localhost:8080/ventas/buscar/usuario/1`
- **Headers:** Ninguno
- **Body:** Ninguno

### 9. Filtrar ventas por tipo
- **Método:** GET
- **URL:** `http://localhost:8080/ventas/buscar/tipo/LOCAL`
- **Headers:** Ninguno
- **Body:** Ninguno

### 10. Filtrar ventas por estado
- **Método:** GET
- **URL:** `http://localhost:8080/ventas/buscar/estado/COMPLETADA`
- **Headers:** Ninguno
- **Body:** Ninguno

---

## 📄 DOCUMENTOS

### 1. Listar todos los documentos
- **Método:** GET
- **URL:** `http://localhost:8080/documentos`
- **Headers:** Ninguno
- **Body:** Ninguno

### 2. Obtener documento por ID
- **Método:** GET
- **URL:** `http://localhost:8080/documentos/1`
- **Headers:** Ninguno
- **Body:** Ninguno

### 3. Ver ticket en texto
- **Método:** GET
- **URL:** `http://localhost:8080/documentos/ticket/1`
- **Headers:** Ninguno
- **Body:** Ninguno
- **Respuesta:** Texto formateado para impresora térmica

### 4. Descargar ticket en PDF
- **Método:** GET
- **URL:** `http://localhost:8080/documentos/ticket/1/pdf`
- **Headers:** Ninguno
- **Body:** Ninguno
- **Respuesta:** Archivo PDF descargable

### 5. Listar documentos por venta
- **Método:** GET
- **URL:** `http://localhost:8080/documentos/venta/1`
- **Headers:** Ninguno
- **Body:** Ninguno

### 6. Crear documento
- **Método:** POST
- **URL:** `http://localhost:8080/documentos`
- **Headers:** `Content-Type: application/json`
- **Body:**
```json
{
  "ventaId": 1,
  "tipo": "TICKET",
  "contenido": "Documento generado automáticamente"
}
```

### 7. Actualizar documento
- **Método:** PUT
- **URL:** `http://localhost:8080/documentos/1`
- **Headers:** `Content-Type: application/json`
- **Body:**
```json
{
  "ventaId": 1,
  "tipo": "REMITO",
  "contenido": "Documento actualizado"
}
```

### 8. Eliminar documento
- **Método:** DELETE
- **URL:** `http://localhost:8080/documentos/1`
- **Headers:** Ninguno
- **Body:** Ninguno

---

## 📊 REPORTES

### 1. Total vendido histórico
- **Método:** GET
- **URL:** `http://localhost:8080/reportes/total-vendido`
- **Headers:** Ninguno
- **Body:** Ninguno
- **Respuesta:**
```json
{
  "totalVentas": 1,
  "montoTotal": 36000.0
}
```

### 2. Inventario actual
- **Método:** GET
- **URL:** `http://localhost:8080/reportes/inventario`
- **Headers:** Ninguno
- **Body:** Ninguno
- **Respuesta:** Lista de SKUs con stock actual

### 3. Ventas por período
- **Método:** GET
- **URL:** `http://localhost:8080/reportes/ventas?desde=2026-04-01&hasta=2026-04-30`
- **Headers:** Ninguno
- **Body:** Ninguno
- **Respuesta:**
```json
{
  "totalVentas": 1,
  "montoTotal": 36000.0
}
```

### 4. Productos más vendidos
- **Método:** GET
- **URL:** `http://localhost:8080/reportes/productos-mas-vendidos?desde=2026-04-01&hasta=2026-04-30&top=5`
- **Headers:** Ninguno
- **Body:** Ninguno
- **Respuesta:** Lista de productos con cantidades vendidas

### 5. Ganancias totales
- **Método:** GET
- **URL:** `http://localhost:8080/reportes/ganancias?desde=2026-04-01&hasta=2026-04-30`
- **Headers:** Ninguno
- **Body:** Ninguno
- **Respuesta:**
```json
{
  "gananciaTotal": 18000.0
}
```

### 6. Top clientes
- **Método:** GET
- **URL:** `http://localhost:8080/reportes/top-clientes?desde=2026-04-01&hasta=2026-04-30&top=10`
- **Headers:** Ninguno
- **Body:** Ninguno
- **Respuesta:** Lista de clientes con mayores compras

---

## 💾 ADMIN - BACKUP/RESTORE

### 1. Exportar base de datos
- **Método:** GET
- **URL:** `http://localhost:8080/admin/exportar-bdd`
- **Headers:** Ninguno
- **Body:** Ninguno
- **Respuesta:** Archivo pos.db descargable

### 2. Importar base de datos
- **Método:** POST
- **URL:** `http://localhost:8080/admin/importar-bdd`
- **Headers:** `Content-Type: multipart/form-data`
- **Body:** Form-data con:
  - **Key:** `file`
  - **Value:** Archivo .db a importar
- **Respuesta:**
```json
{
  "mensaje": "Base de datos importada correctamente. Reinicie la aplicación para aplicar los cambios."
}
```

---

## 🧪 EJEMPLOS DE PRUEBAS COMUNES

### Crear cliente y luego una venta
1. **POST** `/clientes` → Obtener el ID del nuevo cliente (ej: 4)
2. **POST** `/ventas` → Usar `clienteId: 4`

### Ver si hay stock antes de crear venta
1. **GET** `/skus/4` → Ver stock disponible
2. **POST** `/ventas` → Crear venta con esa cantidad

### Generar PDF de una venta
1. **GET** `/ventas/1` → Verificar que existe
2. **GET** `/documentos/ticket/1/pdf` → Descargar PDF

### Analizar ganancias
1. **GET** `/reportes/ganancias?desde=2026-01-01&hasta=2026-12-31`
2. **GET** `/reportes/productos-mas-vendidos?top=10`
3. **GET** `/reportes/top-clientes?top=5`

---

## ⚠️ CÓDIGOS DE ERROR ESPERADOS

- **200 OK** - Operación exitosa
- **201 Created** - Recurso creado exitosamente
- **400 Bad Request** - Error de validación (ej: stock insuficiente)
- **404 Not Found** - Recurso no encontrado
- **500 Internal Server Error** - Error del servidor

---

## 💡 TIPS PARA POSTMAN

1. **Crear colección:** File → New → Collection
2. **Crear carpetas:** Clientes, Productos, Ventas, Documentos, Reportes, Admin
3. **Variables de entorno:** 
   - `base_url` = `http://localhost:8080`
   - Usar `{{base_url}}/clientes` en lugar de escribir la URL completa

4. **Pre-request Script** para autenticación (cuando agregues JWT):
   ```javascript
   // Tu código de autenticación aquí
   ```

5. **Tests** para verificar respuestas:
   ```javascript
   pm.test("Status is 200", function () {
       pm.response.to.have.status(200);
   });
   ```

---

**¡Todos los endpoints listos para copiar en Postman! 🚀**

