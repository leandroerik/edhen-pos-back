# ✅ CHECKLIST DE PRUEBAS - SISTEMA POS EDHEN

## 🚀 ANTES DE EMPEZAR

- [ ] PowerShell abierto en `D:\edhen\sistema-edhen\edhen-pos-back`
- [ ] Ejecutaste `.\run.ps1` y seleccionaste opción 1
- [ ] Ves "Started PosApplication in X seconds" en la consola
- [ ] La app está corriendo en `http://localhost:8080`

---

## 📋 PRUEBAS BÁSICAS

### Acceso a datos
- [ ] `curl http://localhost:8080/ventas` → Ver todas las ventas
- [ ] `curl http://localhost:8080/ventas/1` → Ver venta de prueba (ID 1)
- [ ] `curl http://localhost:8080/clientes` → Ver todos los clientes
- [ ] `curl http://localhost:8080/productos` → Ver todos los productos
- [ ] `curl http://localhost:8080/usuarios` → Ver todos los usuarios
- [ ] `curl http://localhost:8080/tiendas` → Ver todas las tiendas
- [ ] `curl http://localhost:8080/skus` → Ver todos los SKUs

### Documentos
- [ ] `curl http://localhost:8080/documentos/ticket/1` → Ver ticket en texto
- [ ] `curl -o ticket.pdf http://localhost:8080/documentos/ticket/1/pdf` → Descargar PDF
  - [ ] Verifica que el archivo `ticket.pdf` se creó
  - [ ] Abrelo con Adobe Reader o navegador
  - [ ] Verifica que tiene: logo, datos de venta, items, total, QR

---

## 🔍 PRUEBAS DE BÚSQUEDA Y FILTROS

- [ ] `curl http://localhost:8080/productos/activos` → Productos activos
- [ ] `curl http://localhost:8080/productos/buscar/nombre?nombre=Musculosa` → Buscar musculosas
- [ ] `curl http://localhost:8080/clientes/buscar/nombre?nombre=María` → Buscar cliente María
- [ ] `curl http://localhost:8080/ventas/buscar/cliente/1` → Ventas del cliente 1
- [ ] `curl "http://localhost:8080/ventas/buscar/fecha?fechaInicio=2026-04-03&fechaFin=2026-04-03"` → Ventas de hoy

---

## 📊 PRUEBAS DE REPORTES

- [ ] `curl http://localhost:8080/reportes/total-vendido` → Total histórico
  - Debe mostrar: cantidad de ventas, monto total
  
- [ ] `curl http://localhost:8080/reportes/inventario` → Inventario actual
  - Debe mostrar: todos los SKUs con stock actual
  
- [ ] `curl "http://localhost:8080/reportes/ventas?desde=2026-04-01&hasta=2026-04-30"` → Ventas del período
  - Debe mostrar: cantidad y total
  
- [ ] `curl "http://localhost:8080/reportes/productos-mas-vendidos?desde=2026-04-01&hasta=2026-04-30&top=5"` → Top 5 productos
  - Debe mostrar: nombre, cantidad vendida
  
- [ ] `curl "http://localhost:8080/reportes/ganancias?desde=2026-04-01&hasta=2026-04-30"` → Ganancias
  - Debe mostrar: ganancia total (precio - costo)
  
- [ ] `curl "http://localhost:8080/reportes/top-clientes?desde=2026-04-01&hasta=2026-04-30&top=5"` → Top 5 clientes
  - Debe mostrar: nombre cliente, cantidad de compras, monto total

---

## ➕ PRUEBAS DE CREACIÓN (OPCIONAL)

### Crear nuevo cliente
```powershell
curl -X POST http://localhost:8080/clientes `
  -H "Content-Type: application/json" `
  -d '{
    "nombre": "Test Cliente",
    "email": "test@gmail.com",
    "telefono": "1123456789",
    "tipo": "LOCAL"
  }'
```
- [ ] La respuesta debe incluir el ID generado (4 o superior)
- [ ] El cliente debe aparecer en `curl http://localhost:8080/clientes`

### Crear nueva venta
```powershell
curl -X POST http://localhost:8080/ventas `
  -H "Content-Type: application/json" `
  -d '{
    "clienteId": 1,
    "usuarioId": 1,
    "tiendaId": 1,
    "tipoVenta": "LOCAL",
    "items": [{"skuId": 5, "cantidad": 2}],
    "pagos": [{"monto": 24000.0, "metodo": "EFECTIVO"}]
  }'
```
- [ ] La respuesta debe incluir el ID de la nueva venta
- [ ] El total debe ser: 12000 * 2 = 24000
- [ ] La venta debe aparecer en `curl http://localhost:8080/ventas`

---

## ⚠️ PRUEBAS DE VALIDACIONES (DEBEN FALLAR)

### Cliente inexistente (404)
```powershell
curl http://localhost:8080/clientes/999
```
- [ ] Respuesta: Error 404
- [ ] Mensaje: "Cliente no encontrado"

### Venta sin cliente válido (400)
```powershell
curl -X POST http://localhost:8080/ventas `
  -H "Content-Type: application/json" `
  -d '{
    "clienteId": 999,
    "usuarioId": 1,
    "tiendaId": 1,
    "tipoVenta": "LOCAL",
    "items": [{"skuId": 1, "cantidad": 1}],
    "pagos": [{"monto": 10000.0, "metodo": "EFECTIVO"}]
  }'
```
- [ ] Respuesta: Error 400 (Business Exception)
- [ ] Mensaje: "Cliente no encontrado"

### Stock insuficiente (400)
```powershell
curl -X POST http://localhost:8080/ventas `
  -H "Content-Type: application/json" `
  -d '{
    "clienteId": 1,
    "usuarioId": 1,
    "tiendaId": 1,
    "tipoVenta": "LOCAL",
    "items": [{"skuId": 1, "cantidad": 9999}],
    "pagos": [{"monto": 99999999.0, "metodo": "EFECTIVO"}]
  }'
```
- [ ] Respuesta: Error 400
- [ ] Mensaje: "Stock insuficiente"

### Pagos no coinciden (400)
```powershell
curl -X POST http://localhost:8080/ventas `
  -H "Content-Type: application/json" `
  -d '{
    "clienteId": 1,
    "usuarioId": 1,
    "tiendaId": 1,
    "tipoVenta": "LOCAL",
    "items": [{"skuId": 1, "cantidad": 1}],
    "pagos": [{"monto": 5000.0, "metodo": "EFECTIVO"}]
  }'
```
- [ ] Respuesta: Error 400
- [ ] Mensaje: "El total de pagos no coincide con el total"

---

## 💾 PRUEBAS DE BACKUP/RESTORE

### Exportar BD
```powershell
curl -o backup_test.db http://localhost:8080/admin/exportar-bdd
```
- [ ] Se crea el archivo `backup_test.db`
- [ ] El tamaño es > 0 bytes
- [ ] Es un archivo SQLite válido

---

## 🎯 PRUEBA AUTOMÁTICA (FÁCIL)

En otra ventana PowerShell, ejecuta:
```powershell
cd D:\edhen\sistema-edhen\edhen-pos-back
.\test.ps1
```

Esto ejecutará TODAS las pruebas automáticamente y te mostrará resultados:
- [ ] Todos los GETs devuelven status 200
- [ ] Los errores devuelven 404 o 400
- [ ] El PDF se descarga correctamente
- [ ] Las búsquedas devuelven resultados

---

## 📊 RESULTADOS ESPERADOS

### Datos de prueba:
- **Clientes:** 3 (Cliente General, María García, Carlos López)
- **Usuarios:** 2 (Admin, Cajero)
- **Tiendas:** 1 (EDHEN Flores)
- **Productos:** 4 (Musculosa, Pantalón, Musculosa Morely, Remera)
- **SKUs:** 20+ (varios por producto)
- **Ventas:** 1 (venta de prueba con $36,000)

### Venta de prueba (ID 1):
- **Cliente:** Cliente General
- **Items:** 
  - 2x Musculosa Morely Negro Talle 1 ($12,000 c/u)
  - 1x Musculosa Morely Blanco Talle 1 ($12,000)
- **Total:** $36,000
- **Pagos:** $24,000 Efectivo + $12,000 Tarjeta

### PDF esperado:
- Logo EDHEN en header
- Datos: ticket #1, fecha, cliente, usuario
- Tabla con items vendidos
- Total destacado: $36,000
- Detalles de pagos
- QR con código
- Footer con agradecimiento

---

## 🆘 TROUBLESHOOTING

### "Connection refused"
```powershell
# Verifica que la app esté corriendo
curl http://localhost:8080/ventas
```
Si falla, reinicia:
```powershell
.\run.ps1
# Selecciona opción 1 o 2
```

### "Port 8080 already in use"
```powershell
# Cambia de puerto
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

### "Database locked"
Cierra la app (Ctrl+C) y vuelve a ejecutar.

### PDF no se descarga
Verifica que puedas acceder a:
```powershell
curl "http://localhost:8080/documentos/ticket/1"
```
Si eso funciona, el PDF debería funcionar también.

---

## 📝 NOTAS

- La BD se crea automáticamente en `pos.db`
- Los backups se guardan en `backups/` automáticamente
- Todos los datos de prueba se crean al iniciar (DataLoader)
- Las fechas están en formato YYYY-MM-DD
- El sistema acepta JSON en requests POST/PUT

---

## ✅ RESUMEN FINAL

Cuando termines, deberías haber verificado:

```
✓ CRUD completo (GET todos, GET por ID, POST crear)
✓ Búsquedas y filtros funcionando
✓ Reportes generando datos correctos
✓ Validaciones rechazando datos inválidos
✓ PDF generándose correctamente
✓ Backup funcionando
✓ Errores con HTTP status correcto
✓ Datos de prueba precargados
✓ Todos los endpoints respondiendo
```

---

**🎉 ¡LISTO! Tu sistema POS está completamente operativo.**

Si quieres continuar, podemos trabajar en:
- 🔐 Autenticación JWT
- 🎨 Frontend con React
- 🐳 Docker
- ☁️ Despliegue en la nube

¿Qué necesitas hacer ahora?

