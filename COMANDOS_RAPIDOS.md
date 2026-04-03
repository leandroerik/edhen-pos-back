# ⚡ COMANDOS RÁPIDOS - COPIAR Y PEGAR EN POWERSHELL

## 🚀 EJECUTAR LA APLICACIÓN

### Opción 1: Usar el script (RECOMENDADO)
```powershell
cd D:\edhen\sistema-edhen\edhen-pos-back
.\run.ps1
# Selecciona opción 1 o 2
```

### Opción 2: Comando directo
```powershell
cd D:\edhen\sistema-edhen\edhen-pos-back
mvn spring-boot:run
```

---

## 🧪 PROBAR LA APP (en otra ventana PowerShell)

### Test automático completo
```powershell
cd D:\edhen\sistema-edhen\edhen-pos-back
.\test.ps1
```

### O pruebas manuales:

#### Ver venta de prueba
```powershell
curl http://localhost:8080/ventas/1
```

#### Ver ticket en texto
```powershell
curl http://localhost:8080/documentos/ticket/1
```

#### Descargar PDF del ticket
```powershell
curl -o ticket.pdf http://localhost:8080/documentos/ticket/1/pdf
```

#### Ver todos los productos
```powershell
curl http://localhost:8080/productos
```

#### Ver clientes
```powershell
curl http://localhost:8080/clientes
```

#### Ver reportes de ventas
```powershell
curl "http://localhost:8080/reportes/total-vendido"
```

---

## ➕ CREAR NUEVA VENTA DE PRUEBA

```powershell
curl -X POST http://localhost:8080/ventas `
  -H "Content-Type: application/json" `
  -d '{
    "clienteId": 2,
    "usuarioId": 1,
    "tiendaId": 1,
    "tipoVenta": "LOCAL",
    "items": [
      {"skuId": 5, "cantidad": 2},
      {"skuId": 10, "cantidad": 1}
    ],
    "pagos": [
      {"monto": 50000.0, "metodo": "EFECTIVO"}
    ]
  }'
```

---

## ➕ CREAR NUEVO CLIENTE

```powershell
curl -X POST http://localhost:8080/clientes `
  -H "Content-Type: application/json" `
  -d '{
    "nombre": "Ana Martínez",
    "email": "ana@gmail.com",
    "telefono": "1123456789",
    "tipo": "LOCAL"
  }'
```

---

## 📊 VER REPORTES

```powershell
# Total vendido
curl "http://localhost:8080/reportes/total-vendido"

# Inventario
curl "http://localhost:8080/reportes/inventario"

# Ventas del período
curl "http://localhost:8080/reportes/ventas?desde=2026-04-01&hasta=2026-04-30"

# Productos más vendidos
curl "http://localhost:8080/reportes/productos-mas-vendidos?desde=2026-04-01&hasta=2026-04-30&top=5"

# Ganancias
curl "http://localhost:8080/reportes/ganancias?desde=2026-04-01&hasta=2026-04-30"

# Top clientes
curl "http://localhost:8080/reportes/top-clientes?desde=2026-04-01&hasta=2026-04-30&top=5"
```

---

## 🔍 BÚSQUEDAS

```powershell
# Productos activos
curl "http://localhost:8080/productos/activos"

# Buscar musculosas
curl "http://localhost:8080/productos/buscar/nombre?nombre=Musculosa"

# Buscar cliente por nombre
curl "http://localhost:8080/clientes/buscar/nombre?nombre=María"

# Ventas de hoy
curl "http://localhost:8080/ventas/buscar/fecha?fechaInicio=2026-04-03&fechaFin=2026-04-03"

# Ventas del cliente 1
curl "http://localhost:8080/ventas/buscar/cliente/1"
```

---

## 💾 IMPORTAR/EXPORTAR BASE DE DATOS

```powershell
# Exportar (descargar la BD actual)
curl -o backup_manual.db http://localhost:8080/admin/exportar-bdd

# Importar (subir una BD)
# (Primero necesita estar compilado)
$file = Get-Item "C:\ruta\a\tu\backup.db"
curl -X POST -F "file=@$file" http://localhost:8080/admin/importar-bdd
```

---

## ⚙️ LIMPIAR Y RECOMPILAR

```powershell
cd D:\edhen\sistema-edhen\edhen-pos-back
mvn clean install
```

---

## 📊 ESTRUCTURA DE LA BD

### Tablas principales:
- **CLIENTE** - Información de clientes
- **PRODUCTO** - Productos
- **SKU** - Variantes de productos (talla, color, etc.)
- **USUARIO** - Usuarios del sistema (admin, cajero, etc.)
- **TIENDA** - Sucursales
- **VENTA** - Ventas realizadas
- **VENTA_DETALLE** - Items de cada venta
- **PAGO** - Métodos de pago por venta
- **DOCUMENTO_VENTA** - Tickets y remitos

---

## 📍 ARCHIVOS IMPORTANTES

```
D:\edhen\sistema-edhen\edhen-pos-back\
├── pos.db                      # Base de datos (se crea automáticamente)
├── backups/                    # Respaldos automáticos
├── src/
│   └── main/
│       ├── java/com/edhen/pos/
│       │   ├── entity/         # Entidades JPA
│       │   ├── repository/     # Repositorios
│       │   ├── service/        # Servicios de negocio
│       │   ├── controller/     # Controladores REST
│       │   └── util/           # Utilidades (backup, PDF, etc.)
│       └── resources/
│           ├── application.properties
│           └── static/logo.jpg # Logo para PDFs
├── run.ps1                     # Script para ejecutar
├── test.ps1                    # Script para probar
├── GUIA_PRUEBAS.md            # Guía completa de pruebas
└── pom.xml                     # Configuración Maven
```

---

## 🎯 FLUJO DE PRUEBA RECOMENDADO

1. **Ejecuta** la app con `.\run.ps1`
2. **Abre** otra ventana PowerShell
3. **Ejecuta** `.\test.ps1` para probar todo automáticamente
4. **Verifica** que todos los endpoints respondan correctamente
5. **Descarga** el PDF con: `curl -o ticket.pdf http://localhost:8080/documentos/ticket/1/pdf`
6. **Abre** el PDF en Adobe Reader para ver el ticket

---

## 💡 TIPS

- El sistema crea datos de prueba automáticamente
- Los backups se crean automáticamente en la carpeta `backups/`
- La BD se resetea si eliminas `pos.db` (se recrea con datos de prueba)
- Los logs SQL aparecen en la consola (útil para debugging)
- Todos los errores devuelven respuestas JSON con status HTTP correcto

---

## ❓ PROBLEMAS COMUNES

### "Port 8080 already in use"
```powershell
# Ver qué proceso usa el puerto
Get-Process | Where-Object {$_.Name -like "*java*"}

# Cambia de puerto
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

### "Could not build class path"
```powershell
mvn clean install
mvn spring-boot:run
```

### "Database locked"
- Cierra todas las instancias de la app
- Elimina `pos.db`
- Vuelve a ejecutar

---

¡**A PROBAR! 🚀**

