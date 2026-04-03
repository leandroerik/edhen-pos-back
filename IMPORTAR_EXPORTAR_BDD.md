# 💾 GUÍA COMPLETA - IMPORTAR/EXPORTAR BASE DE DATOS

## 🎯 DESCRIPCIÓN GENERAL

El sistema tiene dos endpoints para gestionar la base de datos:

1. **EXPORTAR** - Descargar la BD actual (hacer backup)
2. **IMPORTAR** - Subir una BD (restaurar backup)

---

## 📤 EXPORTAR BASE DE DATOS (DESCARGAR)

### En Postman:

**Método:** GET
**URL:** `http://localhost:8080/admin/exportar-bdd`
**Headers:** Ninguno
**Body:** Ninguno

### Pasos en Postman:

1. **Abre Postman**
2. **Selecciona "GET"** en el dropdown
3. **Pega la URL:** `http://localhost:8080/admin/exportar-bdd`
4. **Click en "Send"**
5. **Click en "Save response"** (abajo a la derecha)
6. **Dale un nombre** (ej: `backup_2026-04-03.db`)
7. **Selecciona "Save to file"**
8. **Elige ubicación** y **"Save"**

✅ **¡Archivo descargado!** Se guardará como `pos.db`

---

## 📥 IMPORTAR BASE DE DATOS (SUBIR)

### En Postman:

**Método:** POST
**URL:** `http://localhost:8080/admin/importar-bdd`
**Content-Type:** `multipart/form-data`

### Pasos en Postman:

1. **Crea un nuevo request** (New → HTTP)
2. **Selecciona "POST"**
3. **Pega la URL:** `http://localhost:8080/admin/importar-bdd`
4. **Ve a la pestaña "Body"**
5. **Selecciona "form-data"**
6. **Agregar campo:**
   - **Key:** `file` (exactamente así)
   - **Value:** Click en el icono de carpeta y selecciona tu archivo `.db`
   - **Type:** Cambia a "File" (automático cuando seleccionas archivo)
7. **Click en "Send"**
8. **Verifica la respuesta:** Debe decir "Base de datos importada correctamente"

✅ **¡Base de datos importada!**

---

## 📋 EJEMPLO PASO A PASO - EXPORTAR

### 1. Abre Postman y crea un request:

```
Método: GET
URL: http://localhost:8080/admin/exportar-bdd
```

### 2. Click en "Send"

Verás:
```
Status: 200 OK
Content-Type: application/octet-stream
```

### 3. Descarga el archivo:

- Postman mostrará "Download" automáticamente
- Click en "Download" o "Save response"
- Elige dónde guardar
- Se descargará como `pos.db`

### 4. Verifica el archivo:

```
Tamaño: Algunos KB (mínimo 50KB)
Tipo: Archivo SQLite
Ubicación: Donde lo guardaste
```

---

## 📋 EJEMPLO PASO A PASO - IMPORTAR

### 1. Abre Postman y crea un nuevo request:

```
Método: POST
URL: http://localhost:8080/admin/importar-bdd
```

### 2. Ve a "Body"

```
Selecciona: form-data
```

### 3. Agrega el archivo

```
Key:   file
Value: [click en carpeta y selecciona tu backup.db]
Type:  File (automático)
```

### 4. Click en "Send"

Verás respuesta:
```json
{
  "mensaje": "Base de datos importada correctamente. Reinicie la aplicación para aplicar los cambios."
}
```

### 5. Reinicia la aplicación

```
1. Cierra la app (Ctrl+C en la consola)
2. Vuelve a ejecutar: .\run.ps1
3. Selecciona opción 1
```

✅ **¡Base de datos restaurada!**

---

## 🔄 FLUJO COMPLETO DE BACKUP Y RESTORE

### CREAR BACKUP:

```
1. GET /admin/exportar-bdd
   ↓
2. Guardar archivo como backup_2026-04-03.db
   ↓
3. Guardar en carpeta segura (USB, Google Drive, etc)
```

### RESTAURAR BACKUP:

```
1. Tener archivo backup.db listo
   ↓
2. POST /admin/importar-bdd
   ↓
3. Subir archivo (form-data: file)
   ↓
4. Reiniciar la aplicación
   ↓
5. ✅ BD restaurada
```

---

## 💡 CUÁNDO USAR CADA UNO

### EXPORTAR (Descargar):
- ✅ Hacer backup de la BD antes de cambios importantes
- ✅ Guardar progreso en otro lugar
- ✅ Compartir datos con otros
- ✅ Migrar a otra máquina

### IMPORTAR (Subir):
- ✅ Restaurar desde un backup anterior
- ✅ Cargar datos de otra máquina
- ✅ Recuperarse de errores
- ✅ Restaurar estado conocido

---

## ⚠️ INFORMACIÓN IMPORTANTE

### ANTES DE IMPORTAR:

1. **Cierra la app** (Ctrl+C)
   - Evita corrupción de datos
   - Asegura que no está usando la BD

2. **Haz un backup actual** (opcional pero recomendado)
   - GET /admin/exportar-bdd
   - Guarda como `backup_antes_restore.db`

3. **Asegúrate que el archivo es válido**
   - Debe ser un archivo SQLite (.db)
   - Tamaño > 50KB
   - No debe estar corrompido

### DESPUÉS DE IMPORTAR:

1. **Reinicia la aplicación**
   - Cmd: `Ctrl+C`
   - Ejecuta: `.\run.ps1`

2. **Verifica que los datos están**
   - GET /clientes
   - GET /productos
   - GET /ventas

3. **Haz las pruebas necesarias**
   - Verifica que todo está OK
   - Si hay problemas, reimporta el anterior

---

## 🆘 TROUBLESHOOTING

### "El archivo no se sube"

**Solución:**
1. Verifica que el archivo existe
2. Asegúrate de seleccionar "File" en "Type"
3. El archivo debe ser un .db válido
4. Intenta con otro archivo

### "Recibo error 500 al importar"

**Solución:**
1. Cierra la aplicación completamente
2. Espera 2-3 segundos
3. Vuelve a ejecutar la app
4. Intenta de nuevo

### "Los datos no cambiaron después de importar"

**Solución:**
1. Asegúrate de haber reiniciado la app
2. Verifica que el POST devolvió status 200
3. Prueba con: GET /clientes (debe haber datos nuevos)
4. Si sigue sin funcionar, reimporta

### "Base de datos corrupta"

**Solución:**
1. No intentes importar archivos corruptos
2. Usa un backup anterior conocido como válido
3. Si no tienes backup, elimina pos.db (se recreará con datos de prueba)
4. Considera usar git para versionar

---

## 📊 ESTRUCTURA DEL BACKUP

Cuando exportas, obtienes un archivo SQLite con:

```
pos.db
├── Tabla: cliente
├── Tabla: producto
├── Tabla: sku
├── Tabla: usuario
├── Tabla: tienda
├── Tabla: venta
├── Tabla: venta_detalle
├── Tabla: pago
└── Tabla: documento_venta
```

Todos los datos, relaciones y secuencias se mantienen.

---

## 🔐 SEGURIDAD

### Recomendaciones:

1. **Hacer backups regularmente**
   - Diarios si hay muchas ventas
   - Semanales si hay pocos cambios

2. **Guardar en múltiples ubicaciones**
   - Carpeta local
   - USB externo
   - Google Drive / OneDrive
   - Servidor externo

3. **Proteger archivos sensibles**
   - Descargarlos a carpeta privada
   - No compartir con personas no autorizadas
   - Considerar encriptación

4. **Verificar integridad**
   - Antes de importar, abrir con SQLite Browser
   - Verificar que el archivo tiene datos
   - Comprobar tamaño coherente

---

## 📝 GUÍA RÁPIDA

### Exportar (Descargar):
```
GET http://localhost:8080/admin/exportar-bdd
```

### Importar (Subir):
```
POST http://localhost:8080/admin/importar-bdd
Form-data: file = [tu archivo .db]
```

### Después de importar:
```
Reiniciar la app y verificar con GET /clientes
```

---

## ✅ CHECKLIST

- [ ] La app está corriendo
- [ ] Puedo acceder a Postman
- [ ] Puedo hacer GET /admin/exportar-bdd
- [ ] El archivo se descargó correctamente
- [ ] Verifico que el archivo tiene datos
- [ ] Puedo hacer POST /admin/importar-bdd
- [ ] El archivo se subió correctamente
- [ ] Reinicié la aplicación
- [ ] Verifico con GET /clientes que hay datos
- [ ] Los datos son los esperados

---

## 🎯 CASOS DE USO COMUNES

### Caso 1: Hacer backup diario
```
1. GET /admin/exportar-bdd
2. Guardar con nombre: backup_YYYY-MM-DD.db
3. Guardar en carpeta backups/
```

### Caso 2: Restaurar desde ayer
```
1. Tengo archivo: backup_2026-04-02.db
2. POST /admin/importar-bdd con ese archivo
3. Reinicio app
4. ¡Datos de ayer restaurados!
```

### Caso 3: Transferir datos a otro servidor
```
1. Server A: GET /admin/exportar-bdd → archivo.db
2. Copiar archivo.db a Server B
3. Server B: POST /admin/importar-bdd con archivo.db
4. ¡Datos sincronizados!
```

### Caso 4: Descartar cambios malos
```
1. Hice cambios que no quiero
2. Tengo backup anterior: backup_bueno.db
3. POST /admin/importar-bdd con backup_bueno.db
4. ¡Volvemos al estado anterior!
```

---

## 📚 DOCUMENTOS RELACIONADOS

- ENDPOINTS_POSTMAN.md (busca "admin" para detalles)
- COMO_USAR_POSTMAN.md (importar colección)
- BACKUP_BDD.md (información del backup automático)

---

**¡Ya tienes todo para gestionar tu base de datos! 💾**

