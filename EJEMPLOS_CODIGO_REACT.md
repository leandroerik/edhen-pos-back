# 💻 EJEMPLOS DE CÓDIGO REACT

**Ejemplos prácticos para el Frontend**

---

## 1️⃣ SERVICIO API BASE (services/api.js)

```javascript
import axios from 'axios';

const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';

const api = axios.create({
  baseURL: API_URL,
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Interceptor de error
api.interceptors.response.use(
  response => response,
  error => {
    if (error.response) {
      console.error('Error:', error.response.data);
    }
    throw error;
  }
);

export default api;
```

---

## 2️⃣ SERVICIO DE CLIENTES (services/clienteService.js)

```javascript
import api from './api';

export const clienteService = {
  listar: () => api.get('/clientes'),
  obtener: (id) => api.get(`/clientes/${id}`),
  crear: (data) => api.post('/clientes', data),
  actualizar: (id, data) => api.put(`/clientes/${id}`, data),
  eliminar: (id) => api.delete(`/clientes/${id}`),
  buscarPorNombre: (nombre) => api.get('/clientes/buscar/nombre', { params: { nombre } }),
  buscarPorEmail: (email) => api.get('/clientes/buscar/email', { params: { email } }),
  buscarPorTelefono: (telefono) => api.get('/clientes/buscar/telefono', { params: { telefono } }),
  buscarPorTipo: (tipo) => api.get(`/clientes/buscar/tipo/${tipo}`),
};
```

---

## 3️⃣ SERVICIO DE VENTAS (services/ventaService.js)

```javascript
import api from './api';

export const ventaService = {
  listar: () => api.get('/ventas'),
  obtener: (id) => api.get(`/ventas/${id}`),
  crear: (data) => api.post('/ventas', data),
  actualizar: (id, data) => api.put(`/ventas/${id}`, data),
  eliminar: (id) => api.delete(`/ventas/${id}`),
  buscarPorFechas: (fechaInicio, fechaFin) => 
    api.get('/ventas/buscar/fecha', { params: { fechaInicio, fechaFin } }),
  buscarPorCliente: (clienteId) => api.get(`/ventas/buscar/cliente/${clienteId}`),
  buscarPorUsuario: (usuarioId) => api.get(`/ventas/buscar/usuario/${usuarioId}`),
  buscarPorTipo: (tipo) => api.get(`/ventas/buscar/tipo/${tipo}`),
  buscarPorEstado: (estado) => api.get(`/ventas/buscar/estado/${estado}`),
};
```

---

## 4️⃣ SERVICIO DE REPORTES (services/reporteService.js)

```javascript
import api from './api';

export const reporteService = {
  ventasPorPeriodo: (desde, hasta) => 
    api.get('/reportes/ventas', { params: { desde, hasta } }),
  
  productosMasVendidos: (desde, hasta, top = 10) =>
    api.get('/reportes/productos-mas-vendidos', { params: { desde, hasta, top } }),
  
  topClientes: (desde, hasta, top = 10) =>
    api.get('/reportes/top-clientes', { params: { desde, hasta, top } }),
  
  gananciasTotales: (desde, hasta) =>
    api.get('/reportes/ganancias', { params: { desde, hasta } }),
  
  reporteInventario: () =>
    api.get('/reportes/inventario'),
  
  totalVendidoHastaAhora: () =>
    api.get('/reportes/total-vendido'),
};
```

---

## 5️⃣ SERVICIO DE DOCUMENTOS (services/documentoService.js)

```javascript
import api from './api';

export const documentoService = {
  listar: () => api.get('/documentos'),
  obtener: (id) => api.get(`/documentos/${id}`),
  listarPorVenta: (ventaId) => api.get(`/documentos/venta/${ventaId}`),
  verTicket: (ventaId) => api.get(`/documentos/ticket/${ventaId}`),
  
  descargarPDF: (ventaId) =>
    api.get(`/documentos/ticket/${ventaId}/pdf`, { responseType: 'blob' }),
  
  actualizar: (id, data) => api.put(`/documentos/${id}`, data),
  eliminar: (id) => api.delete(`/documentos/${id}`),
};
```

---

## 6️⃣ HOOK PERSONALIZADO (hooks/useApi.js)

```javascript
import { useState, useEffect } from 'react';

export const useApi = (apiFunction, dependencies = []) => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchData = async () => {
    try {
      setLoading(true);
      const response = await apiFunction();
      setData(response.data);
      setError(null);
    } catch (err) {
      setError(err.message || 'Error al cargar datos');
      setData(null);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, dependencies);

  return { data, loading, error, refetch: fetchData };
};
```

---

## 7️⃣ COMPONENTE: LISTAR CLIENTES

```javascript
import React, { useState } from 'react';
import { clienteService } from '../services/clienteService';
import { useApi } from '../hooks/useApi';

export const ClienteList = () => {
  const { data: clientes, loading, error, refetch } = useApi(
    () => clienteService.listar(),
    []
  );
  const [searchTerm, setSearchTerm] = useState('');

  const handleDelete = async (id) => {
    if (window.confirm('¿Estás seguro de eliminar este cliente?')) {
      try {
        await clienteService.eliminar(id);
        refetch();
      } catch (err) {
        alert('Error al eliminar cliente');
      }
    }
  };

  if (loading) return <div className="text-center p-4">Cargando...</div>;
  if (error) return <div className="text-red-600 p-4">Error: {error}</div>;

  return (
    <div className="p-4">
      <h2 className="text-2xl font-bold mb-4">Clientes</h2>
      
      <input
        type="text"
        placeholder="Buscar cliente..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        className="w-full p-2 border rounded mb-4"
      />

      <table className="w-full border-collapse border">
        <thead className="bg-blue-600 text-white">
          <tr>
            <th className="p-2 text-left">Nombre</th>
            <th className="p-2 text-left">Email</th>
            <th className="p-2 text-left">Teléfono</th>
            <th className="p-2 text-left">Tipo</th>
            <th className="p-2 text-center">Acciones</th>
          </tr>
        </thead>
        <tbody>
          {clientes?.map((cliente) => (
            <tr key={cliente.id} className="border">
              <td className="p-2">{cliente.nombre}</td>
              <td className="p-2">{cliente.email}</td>
              <td className="p-2">{cliente.telefono}</td>
              <td className="p-2">
                <span className={`px-2 py-1 rounded ${
                  cliente.tipo === 'LOCAL' ? 'bg-blue-200' : 'bg-green-200'
                }`}>
                  {cliente.tipo}
                </span>
              </td>
              <td className="p-2 text-center">
                <button
                  onClick={() => handleDelete(cliente.id)}
                  className="bg-red-600 text-white px-2 py-1 rounded hover:bg-red-700"
                >
                  Eliminar
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};
```

---

## 8️⃣ COMPONENTE: CREAR VENTA (POS)

```javascript
import React, { useState, useEffect } from 'react';
import { ventaService } from '../services/ventaService';
import { productoService } from '../services/productoService';
import { clienteService } from '../services/clienteService';

export const VentaForm = () => {
  const [carrito, setCarrito] = useState([]);
  const [total, setTotal] = useState(0);
  const [clienteId, setClienteId] = useState('');
  const [usuarios, setUsuarios] = useState([1]); // Hardcoded por ahora
  const [tiendas, setTiendas] = useState([1]); // Hardcoded por ahora
  const [usuarioId, setUsuarioId] = useState('1');
  const [tiendaId, setTiendaId] = useState('1');
  const [tipoVenta, setTipoVenta] = useState('LOCAL');
  const [pagos, setPagos] = useState([{ monto: 0, metodo: 'EFECTIVO' }]);
  const [clientes, setClientes] = useState([]);
  const [productos, setProductos] = useState([]);
  const [searchProducto, setSearchProducto] = useState('');

  useEffect(() => {
    cargarDatos();
  }, []);

  const cargarDatos = async () => {
    try {
      const respClientes = await clienteService.listar();
      setClientes(respClientes.data);
      
      const respProductos = await productoService.listar();
      setProductos(respProductos.data);
    } catch (error) {
      alert('Error al cargar datos');
    }
  };

  useEffect(() => {
    const newTotal = carrito.reduce((sum, item) => sum + (item.precio * item.cantidad), 0);
    setTotal(newTotal);
  }, [carrito]);

  const agregarItem = (sku) => {
    const itemExistente = carrito.find(item => item.skuId === sku.id);
    
    if (itemExistente) {
      setCarrito(carrito.map(item =>
        item.skuId === sku.id
          ? { ...item, cantidad: item.cantidad + 1 }
          : item
      ));
    } else {
      setCarrito([...carrito, {
        skuId: sku.id,
        cantidad: 1,
        precio: sku.precio,
        nombre: sku.producto?.nombre || 'Producto'
      }]);
    }
  };

  const eliminarItem = (skuId) => {
    setCarrito(carrito.filter(item => item.skuId !== skuId));
  };

  const modificarCantidad = (skuId, cantidad) => {
    if (cantidad <= 0) {
      eliminarItem(skuId);
    } else {
      setCarrito(carrito.map(item =>
        item.skuId === skuId ? { ...item, cantidad } : item
      ));
    }
  };

  const modificarPago = (index, field, value) => {
    const newPagos = [...pagos];
    newPagos[index][field] = value;
    setPagos(newPagos);
  };

  const handleProcesarVenta = async () => {
    if (!clienteId) {
      alert('Selecciona un cliente');
      return;
    }
    if (carrito.length === 0) {
      alert('Agrega al menos un producto');
      return;
    }
    if (pagos.reduce((sum, p) => sum + parseFloat(p.monto || 0), 0) < total) {
      alert('El monto pagado debe ser mayor o igual al total');
      return;
    }

    const ventaRequest = {
      clienteId: parseInt(clienteId),
      usuarioId: parseInt(usuarioId),
      tiendaId: parseInt(tiendaId),
      tipoVenta,
      items: carrito.map(item => ({
        skuId: item.skuId,
        cantidad: item.cantidad
      })),
      pagos: pagos.map(p => ({
        monto: parseFloat(p.monto),
        metodo: p.metodo
      }))
    };

    try {
      const response = await ventaService.crear(ventaRequest);
      alert('¡Venta creada exitosamente!');
      setCarrito([]);
      setPagos([{ monto: 0, metodo: 'EFECTIVO' }]);
      setClienteId('');
      console.log('Venta creada:', response.data);
    } catch (error) {
      alert('Error al crear venta: ' + error.message);
    }
  };

  const productosFiltrados = productos.filter(p =>
    p.nombre.toLowerCase().includes(searchProducto.toLowerCase())
  );

  return (
    <div className="p-4 grid grid-cols-3 gap-4">
      {/* PANEL IZQUIERDO: Búsqueda de productos */}
      <div className="col-span-2 bg-gray-100 p-4 rounded">
        <h3 className="text-lg font-bold mb-2">Agregar Productos</h3>
        
        <input
          type="text"
          placeholder="Buscar producto..."
          value={searchProducto}
          onChange={(e) => setSearchProducto(e.target.value)}
          className="w-full p-2 border rounded mb-4"
        />

        <div className="grid grid-cols-2 gap-2">
          {productosFiltrados.map(producto =>
            producto.skus?.map(sku => (
              <button
                key={sku.id}
                onClick={() => agregarItem(sku)}
                className="bg-blue-500 text-white p-2 rounded hover:bg-blue-600"
              >
                <div className="font-bold">{producto.nombre}</div>
                <div className="text-sm">Stock: {sku.stock}</div>
                <div className="text-sm">${sku.precio}</div>
              </button>
            ))
          )}
        </div>
      </div>

      {/* PANEL DERECHO: Resumen de venta */}
      <div className="bg-white border rounded p-4">
        <h3 className="text-lg font-bold mb-4">Resumen de Venta</h3>

        {/* Cliente */}
        <div className="mb-4">
          <label className="block font-bold mb-1">Cliente</label>
          <select
            value={clienteId}
            onChange={(e) => setClienteId(e.target.value)}
            className="w-full p-2 border rounded"
          >
            <option value="">Selecciona cliente</option>
            {clientes.map(cliente => (
              <option key={cliente.id} value={cliente.id}>
                {cliente.nombre}
              </option>
            ))}
          </select>
        </div>

        {/* Tipo de venta */}
        <div className="mb-4">
          <label className="block font-bold mb-1">Tipo de Venta</label>
          <select
            value={tipoVenta}
            onChange={(e) => setTipoVenta(e.target.value)}
            className="w-full p-2 border rounded"
          >
            <option value="LOCAL">Local</option>
            <option value="ONLINE">Online</option>
          </select>
        </div>

        {/* Carrito */}
        <div className="mb-4 border rounded p-2 bg-gray-50 max-h-64 overflow-y-auto">
          <h4 className="font-bold mb-2">Carrito ({carrito.length})</h4>
          {carrito.map(item => (
            <div key={item.skuId} className="flex justify-between items-center mb-2 bg-white p-2 rounded">
              <div className="flex-1">
                <div className="text-sm font-bold">{item.nombre}</div>
                <div className="text-xs text-gray-600">${item.precio}</div>
              </div>
              <input
                type="number"
                min="1"
                value={item.cantidad}
                onChange={(e) => modificarCantidad(item.skuId, parseInt(e.target.value))}
                className="w-12 p-1 border rounded text-center"
              />
              <button
                onClick={() => eliminarItem(item.skuId)}
                className="ml-2 bg-red-500 text-white px-2 rounded hover:bg-red-600"
              >
                X
              </button>
            </div>
          ))}
        </div>

        {/* Pagos */}
        <div className="mb-4">
          <h4 className="font-bold mb-2">Pagos</h4>
          {pagos.map((pago, index) => (
            <div key={index} className="flex gap-2 mb-2">
              <input
                type="number"
                placeholder="Monto"
                value={pago.monto}
                onChange={(e) => modificarPago(index, 'monto', e.target.value)}
                className="flex-1 p-2 border rounded"
              />
              <select
                value={pago.metodo}
                onChange={(e) => modificarPago(index, 'metodo', e.target.value)}
                className="p-2 border rounded"
              >
                <option value="EFECTIVO">Efectivo</option>
                <option value="TARJETA">Tarjeta</option>
                <option value="TRANSFERENCIA">Transferencia</option>
              </select>
            </div>
          ))}
        </div>

        {/* Total */}
        <div className="text-right font-bold text-2xl mb-4">
          Total: ${total.toFixed(2)}
        </div>

        {/* Botón procesar */}
        <button
          onClick={handleProcesarVenta}
          className="w-full bg-green-600 text-white p-3 rounded font-bold hover:bg-green-700"
        >
          Procesar Venta
        </button>
      </div>
    </div>
  );
};
```

---

## 9️⃣ COMPONENTE: DASHBOARD CON REPORTES

```javascript
import React from 'react';
import { reporteService } from '../services/reporteService';
import { useApi } from '../hooks/useApi';
import { Line, Bar } from 'react-chartjs-2';

export const Dashboard = () => {
  const hoy = new Date().toISOString().split('T')[0];

  const { data: totalVendido } = useApi(
    () => reporteService.totalVendidoHastaAhora(),
    []
  );

  const { data: ventasHoy } = useApi(
    () => reporteService.ventasPorPeriodo(hoy, hoy),
    []
  );

  const { data: productosTop } = useApi(
    () => reporteService.productosMasVendidos(hoy, hoy, 5),
    []
  );

  const chartDataProductos = {
    labels: productosTop?.map(p => p.nombre) || [],
    datasets: [{
      label: 'Cantidad Vendida',
      data: productosTop?.map(p => p.cantidadVendida) || [],
      backgroundColor: 'rgba(75, 192, 192, 0.8)',
    }]
  };

  return (
    <div className="p-4 grid grid-cols-1 md:grid-cols-2 gap-4">
      {/* Card: Total Vendido */}
      <div className="bg-blue-500 text-white p-4 rounded">
        <h3 className="text-lg">Total Vendido (Histórico)</h3>
        <p className="text-3xl font-bold">${totalVendido?.totalHistorico?.toFixed(2)}</p>
        <p className="text-sm">Última venta: {totalVendido?.ultimaVenta}</p>
      </div>

      {/* Card: Ventas Hoy */}
      <div className="bg-green-500 text-white p-4 rounded">
        <h3 className="text-lg">Ventas de Hoy</h3>
        <p className="text-3xl font-bold">${ventasHoy?.totalVentas?.toFixed(2)}</p>
        <p className="text-sm">Transacciones: {ventasHoy?.cantidadTransacciones}</p>
      </div>

      {/* Gráfico: Top Productos */}
      <div className="col-span-2 bg-white p-4 rounded border">
        <h3 className="text-lg font-bold mb-4">Top 5 Productos Vendidos (Hoy)</h3>
        <Bar data={chartDataProductos} />
      </div>
    </div>
  );
};
```

---

## 🔟 COMPONENTE: LISTAR TICKETS

```javascript
import React from 'react';
import { ventaService } from '../services/ventaService';
import { documentoService } from '../services/documentoService';
import { useApi } from '../hooks/useApi';

export const TicketList = () => {
  const { data: ventas, loading, error } = useApi(
    () => ventaService.listar(),
    []
  );

  const descargarPDF = async (ventaId) => {
    try {
      const blob = await documentoService.descargarPDF(ventaId);
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.download = `ticket_${ventaId}.pdf`;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    } catch (error) {
      alert('Error al descargar PDF');
    }
  };

  if (loading) return <div>Cargando...</div>;
  if (error) return <div className="text-red-600">{error}</div>;

  return (
    <div className="p-4">
      <h2 className="text-2xl font-bold mb-4">Tickets</h2>
      
      <table className="w-full border-collapse border">
        <thead className="bg-blue-600 text-white">
          <tr>
            <th className="p-2">ID</th>
            <th className="p-2">Cliente</th>
            <th className="p-2">Total</th>
            <th className="p-2">Fecha</th>
            <th className="p-2">Acciones</th>
          </tr>
        </thead>
        <tbody>
          {ventas?.map((venta) => (
            <tr key={venta.id} className="border">
              <td className="p-2">{venta.id}</td>
              <td className="p-2">{venta.cliente?.nombre}</td>
              <td className="p-2">${venta.total?.toFixed(2)}</td>
              <td className="p-2">{new Date(venta.fecha).toLocaleString()}</td>
              <td className="p-2">
                <button
                  onClick={() => descargarPDF(venta.id)}
                  className="bg-green-600 text-white px-2 py-1 rounded hover:bg-green-700"
                >
                  Descargar PDF
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};
```

---

**Última actualización**: 2026-04-03

