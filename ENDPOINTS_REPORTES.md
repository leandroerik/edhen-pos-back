# Documentación de Endpoints de Reportes POS

## 1. Ventas por período
**GET** `/reportes/ventas?desde=YYYY-MM-DD&hasta=YYYY-MM-DD`
- **Descripción:** Devuelve la cantidad de ventas y el monto total en el rango de fechas.
- **Parámetros:**
  - `desde`: Fecha inicio (YYYY-MM-DD)
  - `hasta`: Fecha fin (YYYY-MM-DD)
- **Ejemplo:**
  - `/reportes/ventas?desde=2024-01-01&hasta=2024-12-31`

## 2. Productos más vendidos (top N)
**GET** `/reportes/productos-mas-vendidos?desde=YYYY-MM-DD&hasta=YYYY-MM-DD&top=N`
- **Descripción:** Devuelve el ranking de productos más vendidos en el período.
- **Parámetros:**
  - `desde`: Fecha inicio (YYYY-MM-DD)
  - `hasta`: Fecha fin (YYYY-MM-DD)
  - `top`: (opcional, default 10) cantidad de productos a mostrar
- **Ejemplo:**
  - `/reportes/productos-mas-vendidos?desde=2024-01-01&hasta=2024-12-31&top=5`

## 3. Ganancias totales por período
**GET** `/reportes/ganancias?desde=YYYY-MM-DD&hasta=YYYY-MM-DD`
- **Descripción:** Devuelve la ganancia total (precio - costo) en el rango de fechas.
- **Ejemplo:**
  - `/reportes/ganancias?desde=2024-01-01&hasta=2024-12-31`

## 4. Top clientes
**GET** `/reportes/top-clientes?desde=YYYY-MM-DD&hasta=YYYY-MM-DD&top=N`
- **Descripción:** Devuelve los clientes con mayor monto y cantidad de compras en el período.
- **Parámetros:**
  - `desde`: Fecha inicio (YYYY-MM-DD)
  - `hasta`: Fecha fin (YYYY-MM-DD)
  - `top`: (opcional, default 10) cantidad de clientes a mostrar
- **Ejemplo:**
  - `/reportes/top-clientes?desde=2024-01-01&hasta=2024-12-31&top=10`

## 5. Reporte de inventario
**GET** `/reportes/inventario`
- **Descripción:** Devuelve el stock actual de todos los productos y alerta de bajo stock.
- **Ejemplo:**
  - `/reportes/inventario`

## 6. Total vendido hasta ahora
**GET** `/reportes/total-vendido`
- **Descripción:** Devuelve la cantidad total de ventas y el monto acumulado histórico.
- **Ejemplo:**
  - `/reportes/total-vendido`

---

**Notas:**
- Todos los endpoints son de tipo GET.
- Los parámetros de fecha deben estar en formato `YYYY-MM-DD`.
- Los endpoints devuelven datos en formato JSON listos para usar en dashboards o análisis.

