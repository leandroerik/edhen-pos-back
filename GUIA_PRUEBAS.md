# 🧪 GUÍA COMPLETA DE PRUEBAS - SISTEMA POS EDHEN

## 🚀 PASO 1: EJECUTAR LA APLICACIÓN

### Opción A: Con Maven (recomendado)
```bash
cd D:\edhen\sistema-edhen\edhen-pos-back
mvn spring-boot:run
```

### Opción B: Ejecutar JAR directamente
```bash
cd D:\edhen\sistema-edhen\edhen-pos-back
java -jar target/pos-0.0.1-SNAPSHOT.jar
```

**Espera a que veas:**
```
Started PosApplication in X seconds
```

La aplicación estará en: `http://localhost:8080`

---

## 📋 PASO 2: PROBAR ENDPOINTS

Abre **PowerShell** en otra ventana y ejecuta estos comandos:

### ✅ TEST 1: Ver todas las ventas
```powershell
curl http://localhost:8080/ventas
```

### ✅ TEST 2: Ver la venta de prueba (ID=1)
```powershell
curl http://localhost:8080/ventas/1
```

### ✅ TEST 3: Ver el ticket en TEXTO
```powershell
curl http://localhost:8080/documentos/ticket/1
```

### ✅ TEST 4: Descargar el TICKET en PDF
```powershell
curl -o ticket_prueba.pdf http://localhost:8080/documentos/ticket/1/pdf
```
_Se guardará como `ticket_prueba.pdf` en tu carpeta actual_

### ✅ TEST 5: Ver todos los SKUs (productos)
```powershell
curl http://localhost:8080/skus
```

### ✅ TEST 6: Ver todos los clientes
```powershell
curl http://localhost:8080/clientes
```

### ✅ TEST 7: Ver todos los usuarios
```powershell
curl http://localhost:8080/usuarios
```

---

## 🔍 PASO 3: PROBAR BÚSQUEDAS Y FILTROS

### ✅ Buscar productos activos
```powershell
curl "http://localhost:8080/productos/activos"
```

### ✅ Buscar musculosas
```powershell
curl "http://localhost:8080/productos/buscar/nombre?nombre=Musculosa"
```

### ✅ Buscar cliente por nombre
```powershell
curl "http://localhost:8080/clientes/buscar/nombre?nombre=María"
```

### ✅ Filtrar ventas de hoy (2026-04-03)
```powershell
curl "http://localhost:8080/ventas/buscar/fecha?fechaInicio=2026-04-03&fechaFin=2026-04-03"
```

### ✅ Ventas por cliente
```powershell
curl "http://localhost:8080/ventas/buscar/cliente/1"
```

---

## 📊 PASO 4: PROBAR REPORTES

### ✅ Total vendido histórico
```powershell
curl http://localhost:8080/reportes/total-vendido
```

### ✅ Inventario actual
```powershell
curl http://localhost:8080/reportes/inventario
```

### ✅ Ventas de un período
```powershell
curl "http://localhost:8080/reportes/ventas?desde=2026-04-01&hasta=2026-04-30"
```

### ✅ Productos más vendidos
```powershell
curl "http://localhost:8080/reportes/productos-mas-vendidos?desde=2026-04-01&hasta=2026-04-30&top=5"
```

### ✅ Ganancias totales
```powershell
curl "http://localhost:8080/reportes/ganancias?desde=2026-04-01&hasta=2026-04-30"
```

### ✅ Top clientes
```powershell
curl "http://localhost:8080/reportes/top-clientes?desde=2026-04-01&hasta=2026-04-30&top=10"
```

---

## ✍️ PASO 5: CREAR NUEVOS DATOS

### ✅ Crear un nuevo cliente
```powershell
curl -X POST http://localhost:8080/clientes `
  -H "Content-Type: application/json" `
  -d '{
    "nombre": "Juan Pérez",
    "email": "juan@gmail.com",
    "telefono": "1234567890",
    "tipo": "LOCAL"
  }'
```

### ✅ Crear una nueva venta
```powershell
curl -X POST http://localhost:8080/ventas `
  -H "Content-Type: application/json" `
  -d '{
    "clienteId": 1,
    "usuarioId": 1,
    "tiendaId": 1,
    "tipoVenta": "LOCAL",
    "items": [
      {"skuId": 4, "cantidad": 1}
    ],
    "pagos": [
      {"monto": 12000.0, "metodo": "EFECTIVO"}
    ]
  }'
```

### ✅ Crear un nuevo producto con SKU
```powershell
curl -X POST http://localhost:8080/productos `
  -H "Content-Type: application/json" `
  -d '{
    "nombre": "Pantalón Premium",
    "descripcion": "Pantalón de algodón premium",
    "activo": true,
    "skus": [
      {
        "codigoBarra": "PANT-GRIS-M",
        "precio": 25000.0,
        "costo": 12000.0,
        "stock": 10
      },
      {
        "codigoBarra": "PANT-GRIS-L",
        "precio": 25000.0,
        "costo": 12000.0,
        "stock": 15
      }
    ]
  }'
```

---

## 📊 DATOS DE PRUEBA DISPONIBLES

### Clientes:
- ID 1: Cliente General
- ID 2: María García
- ID 3: Carlos López

### Usuarios:
- ID 1: Admin (password: 1234)
- ID 2: Cajero (password: 1234)

### Productos principales:
- ID 1: Musculosa (2 SKUs)
- ID 2: Pantalón (1 SKU)
- ID 3: Musculosa Morely (16 SKUs - 4 colores × 4 talles)
- ID 4: Remera Lisa (1 SKU)

### Venta de prueba:
- ID 1: Venta con 2 Musculosas Morely Negro Talle 1 + 1 Musculosa Morely Blanco
- Total: $36,000
- Pagos: $24,000 Efectivo + $12,000 Tarjeta

---

## 🛠️ VALIDACIONES AUTOMÁTICAS

El sistema **rechazará**:
- ❌ Crear venta sin cliente válido
- ❌ Crear venta sin stock suficiente
- ❌ Pagos que no coincidan con el total
- ❌ Crear usuario sin tienda válida
- ❌ Acceder a recursos que no existen (404)

---

## 📤 IMPORTAR/EXPORTAR BASE DE DATOS

### Exportar BDD actual
```powershell
curl -o backup_manual.db http://localhost:8080/admin/exportar-bdd
```

### Importar una BDD (archivo .db)
```powershell
# Primero crea un FormData con el archivo
$file = Get-Item "C:\ruta\a\tu\backup.db"
curl -X POST -F "file=@$file" http://localhost:8080/admin/importar-bdd
```

---

## 💡 TIPS ÚTILES

1. **Ver logs en la consola** - La aplicación muestra todas las consultas SQL
2. **La BD se crea automáticamente** - En `pos.db` en la raíz del proyecto
3. **Backup automático** - Se guarda en `backups/` cada vez que inicia
4. **Formatos de fechas** - Usa `YYYY-MM-DD`
5. **Colores de texto en PDF** - Edita `DocumentoService.java`

---

## 🚀 PRÓXIMOS PASOS (OPCIONAL)

Una vez que pruebes todo, puedo ayudarte con:

1. 🔐 **Autenticación con JWT** - Proteger endpoints con login
2. 📱 **Frontend con React/Vue** - Interfaz gráfica
3. 🐳 **Docker** - Containerizar la aplicación
4. ☁️ **Despliegue** - AWS, Azure, Heroku
5. 🧪 **Tests automáticos** - Unit tests y integration tests
6. 📖 **Documentación API** - Swagger/OpenAPI

---

## ❓ TROUBLESHOOTING

### Error: Port 8080 already in use
```powershell
# Encuentra qué proceso usa el puerto
Get-Process | Where-Object {$_.Name -like "*java*"}

# O usa otro puerto
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

### Error: Database locked
- Cierra todas las otras ventanas de PowerShell ejecutando curl
- Asegúrate que no hay otra instancia de la app corriendo

### Error: Could not build class path
```powershell
mvn clean install
mvn spring-boot:run
```

---

## 📞 NOTAS

- La aplicación usa **SQLite** (archivo local `pos.db`)
- Los endpoints están en **REST JSON**
- Todas las fechas son en formato `YYYY-MM-DD`
- Los IDs se generan automáticamente

**¡A probar ahora! 🎉**

