# Datos de Prueba - POS EDHEN

Estos son los datos que se cargan automáticamente al iniciar la aplicación.

## 🏪 Tiendas

| ID | Nombre | Dirección |
|----|--------|-----------|
| 1 | EDHEN Flores | - |

## 👥 Usuarios

| ID | Nombre | Email | Password | Rol | Activo | Tienda |
|----|--------|-------|----------|-----|--------|--------|
| 1 | Admin | admin@edhen.com | 1234 | ADMIN | true | EDHEN Flores |
| 2 | Cajero | cajero@edhen.com | 1234 | CAJERO | true | EDHEN Flores |

## 🧍 Clientes

| ID | Nombre | Teléfono | Email | Tipo |
|----|--------|----------|-------|------|
| 1 | Cliente General | 000000 | cliente@edhen.com | LOCAL |
| 2 | María García | 123456789 | maria@email.com | LOCAL |
| 3 | Carlos López | 987654321 | carlos@email.com | ONLINE |

## 📦 Productos y SKUs

### Producto 1: Musculosa
**Descripción:** Básica algodón  
**Activo:** true

| SKU ID | Código de Barra | Precio | Costo | Stock |
|--------|-----------------|--------|-------|-------|
| 1 | MUSCU-NEGRO-M | 8000.0 | 4000.0 | 10 |
| 2 | MUSCU-BLANCO-L | 8000.0 | 4000.0 | 5 |

### Producto 2: Pantalón
**Descripción:** Darlon premium  
**Activo:** true

| SKU ID | Código de Barra | Precio | Costo | Stock |
|--------|-----------------|--------|-------|-------|
| 3 | PANT-NEGRO-42 | 15000.0 | 8000.0 | 8 |

### Producto 3: Musculosa Morely
**Descripción:** Musculosa de algodón premium  
**Activo:** true

| Color | Talle | Código de Barra | Precio | Costo | Stock |
|-------|-------|-----------------|--------|-------|-------|
| Negro | 1 | MORELY-NEGRO-1 | 12000.0 | 6000.0 | 20 |
| Negro | 2 | MORELY-NEGRO-2 | 12000.0 | 6000.0 | 20 |
| Negro | 3 | MORELY-NEGRO-3 | 12000.0 | 6000.0 | 20 |
| Negro | 4 | MORELY-NEGRO-4 | 12000.0 | 6000.0 | 20 |
| Blanco | 1 | MORELY-BLANCO-1 | 12000.0 | 6000.0 | 20 |
| Blanco | 2 | MORELY-BLANCO-2 | 12000.0 | 6000.0 | 20 |
| Blanco | 3 | MORELY-BLANCO-3 | 12000.0 | 6000.0 | 20 |
| Blanco | 4 | MORELY-BLANCO-4 | 12000.0 | 6000.0 | 20 |
| Rojo | 1 | MORELY-ROJO-1 | 12000.0 | 6000.0 | 20 |
| Rojo | 2 | MORELY-ROJO-2 | 12000.0 | 6000.0 | 20 |
| Rojo | 3 | MORELY-ROJO-3 | 12000.0 | 6000.0 | 20 |
| Rojo | 4 | MORELY-ROJO-4 | 12000.0 | 6000.0 | 20 |
| Azul | 1 | MORELY-AZUL-1 | 12000.0 | 6000.0 | 20 |
| Azul | 2 | MORELY-AZUL-2 | 12000.0 | 6000.0 | 20 |
| Azul | 3 | MORELY-AZUL-3 | 12000.0 | 6000.0 | 20 |
| Azul | 4 | MORELY-AZUL-4 | 12000.0 | 6000.0 | 20 |

### Producto 4: Remera Lisa
**Descripción:** Remera básica de algodón  
**Activo:** true

| SKU ID | Código de Barra | Precio | Costo | Stock |
|--------|-----------------|--------|-------|-------|
| 20 | REMERA-LISA-UNICA | 9000.0 | 4500.0 | 15 |

## 📊 Resumen
- **Total Tiendas:** 1
- **Total Usuarios:** 2
- **Total Clientes:** 3
- **Total Productos:** 4
- **Total SKUs:** 20

## 🔑 Credenciales de Acceso
- **Admin:** admin@edhen.com / 1234
- **Cajero:** cajero@edhen.com / 1234

## 💡 Notas
- Los datos se cargan solo si la base de datos está vacía (verificación por tiendaRepository.count() > 0)
- Todos los productos están activos
- Los códigos de barra siguen el patrón: [PRODUCTO]-[COLOR]-[TALLE]
- Precios en pesos argentinos
