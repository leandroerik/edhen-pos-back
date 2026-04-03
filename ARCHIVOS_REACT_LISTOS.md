# 📁 ARCHIVOS PARA COPIAR - PROYECTO REACT

## Una vez que tengas el proyecto creado, copia estos archivos en las carpetas correspondientes

---

## 1. src/services/api.js
**RUTA:** `edhen-pos-frontend/src/services/api.js`

```javascript
import axios from 'axios';

const API_URL = 'http://localhost:8080';

const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json'
  }
});

// Clientes
export const clienteService = {
  listar: () => api.get('/clientes'),
  obtener: (id) => api.get(`/clientes/${id}`),
  crear: (data) => api.post('/clientes', data),
  actualizar: (id, data) => api.put(`/clientes/${id}`, data),
  eliminar: (id) => api.delete(`/clientes/${id}`),
  buscarPorNombre: (nombre) => api.get(`/clientes/buscar/nombre?nombre=${nombre}`)
};

// Productos
export const productoService = {
  listar: () => api.get('/productos'),
  obtener: (id) => api.get(`/productos/${id}`),
  crear: (data) => api.post('/productos', data),
  actualizar: (id, data) => api.put(`/productos/${id}`, data),
  eliminar: (id) => api.delete(`/productos/${id}`),
  activos: () => api.get('/productos/activos')
};

// SKUs
export const skuService = {
  listar: () => api.get('/skus'),
  obtener: (id) => api.get(`/skus/${id}`),
  crear: (data) => api.post('/skus', data)
};

// Ventas
export const ventaService = {
  listar: () => api.get('/ventas'),
  obtener: (id) => api.get(`/ventas/${id}`),
  crear: (data) => api.post('/ventas', data)
};

// Documentos
export const documentoService = {
  obtenerTicketo: (ventaId) => api.get(`/documentos/ticket/${ventaId}`),
  descargarPDF: (ventaId) => api.get(`/documentos/ticket/${ventaId}/pdf`, { responseType: 'blob' })
};

// Reportes
export const reporteService = {
  totalVendido: () => api.get('/reportes/total-vendido'),
  productosMasVendidos: (desde, hasta, top = 5) => 
    api.get(`/reportes/productos-mas-vendidos?desde=${desde}&hasta=${hasta}&top=${top}`),
  topClientes: (desde, hasta, top = 10) => 
    api.get(`/reportes/top-clientes?desde=${desde}&hasta=${hasta}&top=${top}`),
  ganancias: (desde, hasta) => api.get(`/reportes/ganancias?desde=${desde}&hasta=${hasta}`)
};

export default api;
```

---

## 2. src/components/Navbar.js
**RUTA:** `edhen-pos-frontend/src/components/Navbar.js`

```javascript
import React from 'react';
import { Link } from 'react-router-dom';

export default function Navbar() {
  return (
    <nav className="bg-blue-600 text-white shadow-lg">
      <div className="container mx-auto px-4">
        <div className="flex justify-between items-center py-4">
          <Link to="/" className="text-2xl font-bold">
            📊 EDHEN POS
          </Link>
          <div className="flex gap-6">
            <Link to="/" className="hover:text-blue-200">Dashboard</Link>
            <Link to="/sales" className="hover:text-blue-200">Nueva Venta</Link>
            <Link to="/clients" className="hover:text-blue-200">Clientes</Link>
            <Link to="/products" className="hover:text-blue-200">Productos</Link>
            <Link to="/reports" className="hover:text-blue-200">Reportes</Link>
          </div>
        </div>
      </div>
    </nav>
  );
}
```

---

## 3. src/components/Dashboard.js
**RUTA:** `edhen-pos-frontend/src/components/Dashboard.js`

```javascript
import React, { useEffect, useState } from 'react';
import { ventaService, reporteService } from '../services/api';

export default function Dashboard() {
  const [totalVendido, setTotalVendido] = useState(0);
  const [cantidadVentas, setCantidadVentas] = useState(0);
  const [ultimaVenta, setUltimaVenta] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    cargarDatos();
  }, []);

  const cargarDatos = async () => {
    try {
      const totalRes = await reporteService.totalVendido();
      setTotalVendido(totalRes.data.montoTotal || 0);
      setCantidadVentas(totalRes.data.totalVentas || 0);

      const ventasRes = await ventaService.listar();
      if (ventasRes.data.length > 0) {
        setUltimaVenta(ventasRes.data[0]);
      }

      setLoading(false);
    } catch (error) {
      console.error('Error:', error);
      setLoading(false);
    }
  };

  if (loading) return <div className="text-center py-8">Cargando...</div>;

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold mb-6">Dashboard</h1>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
        <div className="bg-blue-500 text-white p-6 rounded-lg shadow">
          <h3 className="text-xl mb-2">Total Vendido</h3>
          <p className="text-3xl font-bold">${totalVendido.toLocaleString()}</p>
        </div>

        <div className="bg-green-500 text-white p-6 rounded-lg shadow">
          <h3 className="text-xl mb-2">Cantidad de Ventas</h3>
          <p className="text-3xl font-bold">{cantidadVentas}</p>
        </div>

        <div className="bg-purple-500 text-white p-6 rounded-lg shadow">
          <h3 className="text-xl mb-2">Ticket Promedio</h3>
          <p className="text-3xl font-bold">
            ${cantidadVentas > 0 ? Math.round(totalVendido / cantidadVentas) : 0}
          </p>
        </div>
      </div>

      {ultimaVenta && (
        <div className="bg-white p-6 rounded-lg shadow">
          <h2 className="text-2xl font-bold mb-4">Última Venta</h2>
          <div className="grid grid-cols-2 gap-4">
            <div>
              <p className="text-gray-600">Cliente:</p>
              <p className="font-bold">{ultimaVenta.cliente?.nombre}</p>
            </div>
            <div>
              <p className="text-gray-600">Total:</p>
              <p className="font-bold">${ultimaVenta.total}</p>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
```

---

## 4. src/components/Sales.js
**RUTA:** `edhen-pos-frontend/src/components/Sales.js`

```javascript
import React, { useEffect, useState } from 'react';
import { ventaService, clienteService, skuService } from '../services/api';

export default function Sales() {
  const [clientes, setClientes] = useState([]);
  const [productos, setProductos] = useState([]);
  const [items, setItems] = useState([]);
  const [clienteSeleccionado, setClienteSeleccionado] = useState('');
  const [total, setTotal] = useState(0);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    cargarDatos();
  }, []);

  useEffect(() => {
    calcularTotal();
  }, [items]);

  const cargarDatos = async () => {
    try {
      const clientesRes = await clienteService.listar();
      const productosRes = await skuService.listar();
      setClientes(clientesRes.data);
      setProductos(productosRes.data);
    } catch (error) {
      console.error('Error:', error);
    }
  };

  const agregarItem = (sku) => {
    const existe = items.find(i => i.skuId === sku.id);
    if (existe) {
      setItems(items.map(i =>
        i.skuId === sku.id ? { ...i, cantidad: i.cantidad + 1 } : i
      ));
    } else {
      setItems([...items, {
        skuId: sku.id,
        cantidad: 1,
        precio: sku.precio,
        nombre: sku.producto?.nombre
      }]);
    }
  };

  const calcularTotal = () => {
    const sum = items.reduce((acc, item) => acc + (item.precio * item.cantidad), 0);
    setTotal(sum);
  };

  const procesarVenta = async () => {
    if (!clienteSeleccionado || items.length === 0) {
      alert('Selecciona cliente y agrega items');
      return;
    }

    setLoading(true);
    try {
      const venta = {
        clienteId: parseInt(clienteSeleccionado),
        usuarioId: 1,
        tiendaId: 1,
        tipoVenta: 'LOCAL',
        items: items.map(i => ({ skuId: i.skuId, cantidad: i.cantidad })),
        pagos: [{ monto: total, metodo: 'EFECTIVO' }]
      };

      await ventaService.crear(venta);
      alert('Venta creada exitosamente!');
      setItems([]);
      setClienteSeleccionado('');
    } catch (error) {
      alert('Error: ' + error.response?.data?.detalle);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold mb-6">Nueva Venta</h1>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <div className="lg:col-span-2">
          <div className="bg-white p-6 rounded-lg shadow mb-6">
            <h2 className="text-xl font-bold mb-4">Cliente</h2>
            <select
              value={clienteSeleccionado}
              onChange={(e) => setClienteSeleccionado(e.target.value)}
              className="w-full p-2 border rounded"
            >
              <option value="">-- Selecciona un cliente --</option>
              {clientes.map(c => (
                <option key={c.id} value={c.id}>{c.nombre}</option>
              ))}
            </select>
          </div>

          <div className="bg-white p-6 rounded-lg shadow">
            <h2 className="text-xl font-bold mb-4">Productos</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {productos.map(p => (
                <div key={p.id} className="border p-4 rounded hover:bg-gray-50 cursor-pointer"
                  onClick={() => agregarItem(p)}>
                  <h3 className="font-bold">{p.producto?.nombre}</h3>
                  <p className="text-gray-600 text-sm">Stock: {p.stock}</p>
                  <p className="text-green-600 font-bold">${p.precio}</p>
                </div>
              ))}
            </div>
          </div>
        </div>

        <div className="bg-white p-6 rounded-lg shadow h-fit">
          <h2 className="text-xl font-bold mb-4">Resumen</h2>

          <div className="border-b pb-4 mb-4">
            {items.map(item => (
              <div key={item.skuId} className="flex justify-between py-2 text-sm">
                <div>
                  <p className="font-bold">{item.nombre}</p>
                  <p className="text-gray-600">{item.cantidad}x ${item.precio}</p>
                </div>
              </div>
            ))}
          </div>

          <div className="bg-gray-100 p-4 rounded mb-4">
            <div className="flex justify-between font-bold text-xl">
              <span>Total:</span>
              <span>${total.toLocaleString()}</span>
            </div>
          </div>

          <button
            onClick={procesarVenta}
            disabled={loading}
            className="w-full bg-green-600 text-white py-3 rounded font-bold hover:bg-green-700"
          >
            {loading ? 'Procesando...' : 'Procesar Venta'}
          </button>
        </div>
      </div>
    </div>
  );
}
```

---

## 5. src/components/Clients.js
**RUTA:** `edhen-pos-frontend/src/components/Clients.js`

```javascript
import React, { useEffect, useState } from 'react';
import { clienteService } from '../services/api';

export default function Clients() {
  const [clientes, setClientes] = useState([]);
  const [mostrarFormulario, setMostrarFormulario] = useState(false);
  const [formData, setFormData] = useState({ nombre: '', email: '', telefono: '', tipo: 'LOCAL' });
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    cargarClientes();
  }, []);

  const cargarClientes = async () => {
    try {
      const res = await clienteService.listar();
      setClientes(res.data);
    } catch (error) {
      console.error('Error:', error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      await clienteService.crear(formData);
      alert('Cliente creado!');
      setFormData({ nombre: '', email: '', telefono: '', tipo: 'LOCAL' });
      setMostrarFormulario(false);
      cargarClientes();
    } catch (error) {
      alert('Error: ' + error.response?.data?.detalle);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="p-6">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold">Clientes</h1>
        <button
          onClick={() => setMostrarFormulario(!mostrarFormulario)}
          className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
        >
          + Nuevo Cliente
        </button>
      </div>

      {mostrarFormulario && (
        <form onSubmit={handleSubmit} className="bg-white p-6 rounded-lg shadow mb-6">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <input
              type="text"
              placeholder="Nombre"
              value={formData.nombre}
              onChange={(e) => setFormData({ ...formData, nombre: e.target.value })}
              className="border p-2 rounded"
              required
            />
            <input
              type="email"
              placeholder="Email"
              value={formData.email}
              onChange={(e) => setFormData({ ...formData, email: e.target.value })}
              className="border p-2 rounded"
            />
          </div>
          <button
            type="submit"
            disabled={loading}
            className="mt-4 w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700"
          >
            {loading ? 'Guardando...' : 'Guardar Cliente'}
          </button>
        </form>
      )}

      <div className="bg-white rounded-lg shadow overflow-hidden">
        <table className="w-full">
          <thead className="bg-gray-100 border-b">
            <tr>
              <th className="px-6 py-3 text-left font-bold">Nombre</th>
              <th className="px-6 py-3 text-left font-bold">Email</th>
              <th className="px-6 py-3 text-left font-bold">Tipo</th>
            </tr>
          </thead>
          <tbody>
            {clientes.map(cliente => (
              <tr key={cliente.id} className="border-b hover:bg-gray-50">
                <td className="px-6 py-3">{cliente.nombre}</td>
                <td className="px-6 py-3">{cliente.email}</td>
                <td className="px-6 py-3">{cliente.tipo}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
```

---

## 6. src/components/Products.js
**RUTA:** `edhen-pos-frontend/src/components/Products.js`

```javascript
import React, { useEffect, useState } from 'react';
import { productoService } from '../services/api';

export default function Products() {
  const [productos, setProductos] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    cargarProductos();
  }, []);

  const cargarProductos = async () => {
    try {
      const res = await productoService.listar();
      setProductos(res.data);
      setLoading(false);
    } catch (error) {
      console.error('Error:', error);
      setLoading(false);
    }
  };

  if (loading) return <div className="p-6">Cargando...</div>;

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold mb-6">Productos</h1>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {productos.map(producto => (
          <div key={producto.id} className="bg-white p-6 rounded-lg shadow">
            <h3 className="text-xl font-bold mb-2">{producto.nombre}</h3>
            <p className="text-gray-600 text-sm mb-4">{producto.descripcion}</p>
            <div className="flex justify-between items-center">
              <span className="text-green-600 font-bold">${producto.skus?.[0]?.precio}</span>
              <span className={`px-3 py-1 rounded-full text-sm ${producto.activo ? 'bg-green-100 text-green-800' : 'bg-gray-100 text-gray-800'}`}>
                {producto.activo ? 'Activo' : 'Inactivo'}
              </span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
```

---

## 7. src/components/Reports.js
**RUTA:** `edhen-pos-frontend/src/components/Reports.js`

```javascript
import React, { useEffect, useState } from 'react';
import { reporteService } from '../services/api';

export default function Reports() {
  const [totalVendido, setTotalVendido] = useState(null);
  const [topProductos, setTopProductos] = useState([]);
  const [topClientes, setTopClientes] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    cargarReportes();
  }, []);

  const cargarReportes = async () => {
    try {
      const hoy = new Date().toISOString().split('T')[0];
      const inicio = new Date();
      inicio.setMonth(inicio.getMonth() - 1);
      const desde = inicio.toISOString().split('T')[0];

      const [total, top, topCli] = await Promise.all([
        reporteService.totalVendido(),
        reporteService.productosMasVendidos(desde, hoy, 10),
        reporteService.topClientes(desde, hoy, 10)
      ]);

      setTotalVendido(total.data);
      setTopProductos(top.data || []);
      setTopClientes(topCli.data || []);
      setLoading(false);
    } catch (error) {
      console.error('Error:', error);
      setLoading(false);
    }
  };

  if (loading) return <div className="p-6">Cargando...</div>;

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold mb-6">Reportes</h1>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
        <div className="bg-blue-500 text-white p-6 rounded-lg">
          <h3 className="text-lg mb-2">Total Vendido</h3>
          <p className="text-3xl font-bold">${totalVendido?.montoTotal?.toLocaleString()}</p>
        </div>

        <div className="bg-green-500 text-white p-6 rounded-lg">
          <h3 className="text-lg mb-2">Total Ventas</h3>
          <p className="text-3xl font-bold">{totalVendido?.totalVentas}</p>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div className="bg-white p-6 rounded-lg shadow">
          <h2 className="text-xl font-bold mb-4">Top Productos</h2>
          {topProductos.map((p, i) => (
            <div key={i} className="flex justify-between py-2 border-b">
              <span>{p.nombreProducto}</span>
              <span className="font-bold">{p.cantidadVendida} unidades</span>
            </div>
          ))}
        </div>

        <div className="bg-white p-6 rounded-lg shadow">
          <h2 className="text-xl font-bold mb-4">Top Clientes</h2>
          {topClientes.map((c, i) => (
            <div key={i} className="flex justify-between py-2 border-b">
              <span>{c.nombreCliente}</span>
              <span className="font-bold">${c.montoTotal?.toLocaleString()}</span>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
```

---

## 8. src/App.js
**RUTA:** `edhen-pos-frontend/src/App.js`

REEMPLAZA el contenido completo con:

```javascript
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Dashboard from './components/Dashboard';
import Sales from './components/Sales';
import Clients from './components/Clients';
import Products from './components/Products';
import Reports from './components/Reports';
import './App.css';

function App() {
  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={<Dashboard />} />
        <Route path="/sales" element={<Sales />} />
        <Route path="/clients" element={<Clients />} />
        <Route path="/products" element={<Products />} />
        <Route path="/reports" element={<Reports />} />
      </Routes>
    </Router>
  );
}

export default App;
```

---

## 9. src/index.css
**RUTA:** `edhen-pos-frontend/src/index.css`

REEMPLAZA el contenido con:

```css
@tailwind base;
@tailwind components;
@tailwind utilities;

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen',
    'Ubuntu', 'Cantarell', 'Fira Sans', 'Droid Sans', 'Helvetica Neue',
    sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  background-color: #f3f4f6;
}

code {
  font-family: source-code-pro, Menlo, Monaco, Consolas, 'Courier New',
    monospace;
}
```

---

## INSTRUCCIONES DE COPIA

1. Copia cada archivo
2. Crea el archivo en la ruta indicada
3. Pega el contenido
4. Guarda

Ejemplo:
- Archivo: `src/services/api.js`
- Abre VS Code
- New File
- Pega código
- File → Save As → `src/services/api.js`

---

¿Necesitas ayuda con algún archivo?

