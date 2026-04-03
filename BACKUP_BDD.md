# Backup y restauración de la base de datos (SQLite)

## 1. Backup automático diario
- El sistema realiza una copia del archivo `pos.db` cada día al iniciar la aplicación.
- Los backups se guardan en la carpeta `backups/` dentro del proyecto, con el nombre `pos_backup_YYYY-MM-DD.db`.
- Se conservan los últimos 7 backups. Los más antiguos se eliminan automáticamente.

## 2. Exportar base de datos manualmente
- Endpoint: `GET /admin/exportar-bdd`
- Permite descargar el archivo actual de la base de datos (`pos.db`).

## 3. Importar base de datos manualmente
- Endpoint: `POST /admin/importar-bdd`
- Permite subir un archivo `.db` y reemplazar la base de datos actual.
- Antes de reemplazar, se realiza un backup automático del archivo existente.
- El archivo subido debe ser una base de datos SQLite válida.

## 4. Restaurar desde backup
- Para restaurar manualmente, puedes usar el endpoint de importación y subir uno de los archivos de la carpeta `backups/`.

## 5. Seguridad
- Los endpoints de exportar/importar deben estar protegidos (solo accesibles para usuarios administradores).

---

**Recomendaciones:**
- Descarga periódicamente los backups y guárdalos en un lugar seguro.
- Antes de importar una base de datos, asegúrate de que el sistema esté detenido para evitar corrupción de datos.
- Si tienes dudas, consulta con soporte técnico antes de restaurar.

