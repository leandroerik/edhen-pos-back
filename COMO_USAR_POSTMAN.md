# 📮 CÓMO IMPORTAR LA COLECCIÓN EN POSTMAN

## Opción 1: Importar el archivo JSON

### Pasos:
1. **Abre Postman**
2. **Click en "Import"** (esquina superior izquierda)
3. **Selecciona "File"**
4. **Busca y selecciona:** `Postman_Collection.json`
5. **Click en "Import"**
6. ¡Listo! Ya tendrás toda la colección

---

## Opción 2: Crear la colección manualmente

Si prefieres crear la colección tú mismo:

1. **New → Collection** → Dale nombre: "EDHEN POS System"
2. **Dentro de la colección, crear carpetas:**
   - 👥 CLIENTES
   - 👕 PRODUCTOS
   - 📦 SKUs
   - 👨‍💼 USUARIOS
   - 🏪 TIENDAS
   - 💰 VENTAS
   - 📄 DOCUMENTOS
   - 📊 REPORTES
   - 💾 ADMIN

3. **Dentro de cada carpeta, agregar los requests según ENDPOINTS_POSTMAN.md**

---

## Organización de la Colección

```
📦 EDHEN POS System
├── 👥 CLIENTES
│   ├── Listar todos
│   ├── Obtener por ID
│   ├── Crear cliente
│   ├── Actualizar cliente
│   ├── Eliminar cliente
│   └── Buscar por nombre
├── 👕 PRODUCTOS
│   ├── Listar todos
│   ├── Obtener por ID
│   ├── Crear producto con SKUs
│   ├── Actualizar producto
│   ├── Eliminar producto
│   ├── Productos activos
│   ├── Productos inactivos
│   ├── Buscar por nombre
│   └── Buscar por descripción
├── 📦 SKUs
│   ├── Listar todos
│   ├── Obtener por ID
│   ├── Crear SKU
│   ├── Actualizar SKU
│   └── Eliminar SKU
├── 👨‍💼 USUARIOS
│   ├── Listar todos
│   ├── Obtener por ID
│   ├── Crear usuario
│   ├── Actualizar usuario
│   └── Eliminar usuario
├── 🏪 TIENDAS
│   ├── Listar todas
│   ├── Obtener por ID
│   ├── Crear tienda
│   ├── Actualizar tienda
│   └── Eliminar tienda
├── 💰 VENTAS
│   ├── Listar todas
│   ├── Obtener por ID
│   ├── Crear venta
│   ├── Actualizar venta
│   ├── Eliminar venta
│   ├── Buscar por fecha
│   ├── Buscar por cliente
│   ├── Buscar por usuario
│   ├── Filtrar por tipo
│   └── Filtrar por estado
├── 📄 DOCUMENTOS
│   ├── Listar todos
│   ├── Obtener por ID
│   ├── Ver ticket en texto
│   ├── Descargar PDF
│   ├── Listar por venta
│   ├── Crear documento
│   ├── Actualizar documento
│   └── Eliminar documento
├── 📊 REPORTES
│   ├── Total vendido
│   ├── Inventario
│   ├── Ventas por período
│   ├── Productos más vendidos
│   ├── Ganancias
│   └── Top clientes
└── 💾 ADMIN
    ├── Exportar BD
    └── Importar BD
```

---

## 📋 CHECKLIST DESPUÉS DE IMPORTAR

Una vez importada la colección:

- [ ] Verificar que todas las carpetas aparecen
- [ ] Hacer clic en un request (ej: "Listar todos" de CLIENTES)
- [ ] Cambiar la URL si usas otro puerto (ej: 8081)
- [ ] Hacer click en "Send" para probar

---

## 🔧 CONFIGURAR VARIABLES DE ENTORNO (Opcional)

Para evitar escribir la URL completa cada vez:

1. **Click en "Environments"** (arriba a la izquierda)
2. **Click en "Create"**
3. **Nombre:** `EDHEN POS`
4. **Agregar variable:**
   - **Key:** `base_url`
   - **Value:** `http://localhost:8080`
5. **Click en "Save"**
6. **Seleccionar el environment** (esquina superior derecha)
7. Ahora en los requests puedes usar: `{{base_url}}/clientes`

---

## 🚀 FLUJO DE PRUEBAS RECOMENDADO

### Día 1: Pruebas Básicas
1. **GET** `/clientes` → Listar
2. **GET** `/productos` → Listar
3. **GET** `/ventas` → Listar
4. **GET** `/ventas/1` → Ver venta de prueba
5. **GET** `/documentos/ticket/1` → Ver ticket en texto

### Día 2: Búsquedas y Reportes
1. **GET** `/clientes/buscar/nombre?nombre=María`
2. **GET** `/productos/buscar/nombre?nombre=Musculosa`
3. **GET** `/reportes/total-vendido`
4. **GET** `/reportes/inventario`
5. **GET** `/reportes/productos-mas-vendidos?top=5`

### Día 3: Crear Datos
1. **POST** `/clientes` → Crear nuevo cliente
2. **POST** `/productos` → Crear nuevo producto con SKUs
3. **POST** `/ventas` → Crear nueva venta
4. **GET** `/documentos/ticket/{id}/pdf` → Descargar PDF

### Día 4: Validaciones
1. **GET** `/clientes/999` → Debe dar 404
2. **POST** `/ventas` → Stock insuficiente → Debe dar 400
3. **POST** `/ventas` → Pagos incorrectos → Debe dar 400

---

## 💡 TIPS

### Copiar URL desde navegador
```
Abre en navegador: http://localhost:8080/clientes
Postman detectará automáticamente
Click en "Capture requests"
```

### Guardar respuestas
```
Cada respuesta en Postman tiene un botón "Save response"
Útil para comparar cambios
```

### Tests automáticos
```
En la pestaña "Tests", puedes agregar scripts:

pm.test("Status is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Response has data", function () {
    pm.expect(pm.response.json()).to.be.an('array');
});
```

### Historial
```
Click en "History" (izquierda)
Ver todos los requests realizados
Copiar requests anteriores
```

---

## ⚠️ ERRORES COMUNES

### "Connection refused"
→ Asegúrate que la app esté corriendo en puerto 8080

### "Cannot find module"
→ Verifica que la URL es correcta (http:// no https://)

### "Invalid JSON in body"
→ Verifica que el JSON esté bien formado (sin comas extras)

### "ID not found" (404)
→ Usa IDs que existen (prueba primero con GET)

---

## 📚 DOCUMENTOS DE REFERENCIA

- **ENDPOINTS_POSTMAN.md** → Todos los endpoints con detalles
- **COMANDOS_RAPIDOS.md** → Para probar con curl
- **GUIA_PRUEBAS.md** → Guía completa
- **CHECKLIST_PRUEBAS.md** → Checklist de pruebas

---

¡**La colección está lista para usar! 🚀**

