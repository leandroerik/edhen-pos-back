#!/usr/bin/env pwsh
# Script para probar todos los endpoints del sistema POS EDHEN
# Asegúrate de que la aplicación está corriendo en puerto 8080

$baseUrl = "http://localhost:8080"
$headers = @{"Content-Type" = "application/json"}

function Print-Header {
    param([string]$title)
    Write-Host ""
    Write-Host "╔═══════════════════════════════════════════════════════════════╗" -ForegroundColor Cyan
    Write-Host "║  $title" -ForegroundColor Cyan
    Write-Host "╚═══════════════════════════════════════════════════════════════╝" -ForegroundColor Cyan
}

function Test-Endpoint {
    param(
        [string]$name,
        [string]$method,
        [string]$endpoint,
        [hashtable]$body = $null
    )

    Write-Host ""
    Write-Host "📌 $name" -ForegroundColor Yellow
    Write-Host "   Método: $method | URL: $endpoint" -ForegroundColor Gray

    try {
        if ($method -eq "GET") {
            $response = Invoke-WebRequest -Uri "$baseUrl$endpoint" -Method $method -Headers $headers -ErrorAction Stop
        } else {
            $jsonBody = $body | ConvertTo-Json -Depth 10
            $response = Invoke-WebRequest -Uri "$baseUrl$endpoint" -Method $method -Headers $headers -Body $jsonBody -ErrorAction Stop
        }

        Write-Host "   ✅ Status: $($response.StatusCode)" -ForegroundColor Green

        $content = $response.Content | ConvertFrom-Json

        # Mostrar resumen
        if ($content -is [array]) {
            Write-Host "   📊 Registros: $($content.Count)" -ForegroundColor Green
            if ($content.Count -gt 0) {
                Write-Host "   └─ Primer registro: $($content[0] | ConvertTo-Json -Compress)" -ForegroundColor Gray
            }
        } else {
            Write-Host "   📄 Respuesta: $($content | ConvertTo-Json -Compress)" -ForegroundColor Gray
        }
    } catch {
        Write-Host "   ❌ Error: $($_.Exception.Message)" -ForegroundColor Red
        Write-Host "   💡 Asegúrate de que la aplicación está corriendo en puerto 8080" -ForegroundColor Yellow
    }
}

# INICIO DEL SCRIPT
Write-Host ""
Write-Host "╔═══════════════════════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║   🧪 TEST SUITE - SISTEMA POS EDHEN                          ║" -ForegroundColor Cyan
Write-Host "║   Base URL: $baseUrl" -ForegroundColor Cyan
Write-Host "╚═══════════════════════════════════════════════════════════════╝" -ForegroundColor Cyan

# Verificar conectividad
Write-Host ""
Write-Host "🔗 Verificando conexión..." -ForegroundColor Cyan
try {
    $pingTest = Invoke-WebRequest -Uri "$baseUrl/ventas" -Method GET -ErrorAction Stop
    Write-Host "✅ Conexión establecida con éxito" -ForegroundColor Green
} catch {
    Write-Host "❌ No se puede conectar a $baseUrl" -ForegroundColor Red
    Write-Host "💡 Asegúrate de ejecutar: mvn spring-boot:run" -ForegroundColor Yellow
    exit
}

# PRUEBAS BÁSICAS
Print-Header "📋 PRUEBAS BÁSICAS (GET)"

Test-Endpoint -name "Listar todas las ventas" -method "GET" -endpoint "/ventas"
Test-Endpoint -name "Obtener venta por ID (1)" -method "GET" -endpoint "/ventas/1"
Test-Endpoint -name "Listar todos los clientes" -method "GET" -endpoint "/clientes"
Test-Endpoint -name "Listar todos los productos" -method "GET" -endpoint "/productos"
Test-Endpoint -name "Listar todos los SKUs" -method "GET" -endpoint "/skus"
Test-Endpoint -name "Listar todos los usuarios" -method "GET" -endpoint "/usuarios"
Test-Endpoint -name "Listar todas las tiendas" -method "GET" -endpoint "/tiendas"

# PRUEBAS DE DOCUMENTOS
Print-Header "📄 PRUEBAS DE DOCUMENTOS (PDF/TEXTO)"

Test-Endpoint -name "Ticket en texto (venta 1)" -method "GET" -endpoint "/documentos/ticket/1"
Write-Host ""
Write-Host "📌 Descargar ticket en PDF (venta 1)" -ForegroundColor Yellow
Write-Host "   Método: GET | URL: /documentos/ticket/1/pdf" -ForegroundColor Gray
try {
    Invoke-WebRequest -Uri "$baseUrl/documentos/ticket/1/pdf" -OutFile "ticket_prueba.pdf" -ErrorAction Stop
    Write-Host "   ✅ PDF descargado: ticket_prueba.pdf" -ForegroundColor Green
} catch {
    Write-Host "   ❌ Error al descargar: $($_.Exception.Message)" -ForegroundColor Red
}

# PRUEBAS DE BÚSQUEDA Y FILTROS
Print-Header "🔍 PRUEBAS DE BÚSQUEDA Y FILTROS"

Test-Endpoint -name "Productos activos" -method "GET" -endpoint "/productos/activos"
Test-Endpoint -name "Buscar musculosas por nombre" -method "GET" -endpoint "/productos/buscar/nombre?nombre=Musculosa"
Test-Endpoint -name "Buscar clientes por nombre (María)" -method "GET" -endpoint "/clientes/buscar/nombre?nombre=María"
Test-Endpoint -name "Ventas del cliente 1" -method "GET" -endpoint "/ventas/buscar/cliente/1"
Test-Endpoint -name "Ventas de hoy (2026-04-03)" -method "GET" -endpoint "/ventas/buscar/fecha?fechaInicio=2026-04-03&fechaFin=2026-04-03"

# PRUEBAS DE REPORTES
Print-Header "📊 PRUEBAS DE REPORTES"

Test-Endpoint -name "Total vendido histórico" -method "GET" -endpoint "/reportes/total-vendido"
Test-Endpoint -name "Inventario actual" -method "GET" -endpoint "/reportes/inventario"
Test-Endpoint -name "Ventas por período" -method "GET" -endpoint "/reportes/ventas?desde=2026-04-01&hasta=2026-04-30"
Test-Endpoint -name "Productos más vendidos" -method "GET" -endpoint "/reportes/productos-mas-vendidos?desde=2026-04-01&hasta=2026-04-30&top=5"
Test-Endpoint -name "Ganancias totales" -method "GET" -endpoint "/reportes/ganancias?desde=2026-04-01&hasta=2026-04-30"
Test-Endpoint -name "Top clientes" -method "GET" -endpoint "/reportes/top-clientes?desde=2026-04-01&hasta=2026-04-30&top=5"

# PRUEBAS DE VALIDACIONES (ERRORES ESPERADOS)
Print-Header "⚠️  PRUEBAS DE VALIDACIONES (ERRORES ESPERADOS)"

Write-Host ""
Write-Host "📌 Obtener cliente inexistente (404)" -ForegroundColor Yellow
Write-Host "   Método: GET | URL: /clientes/999" -ForegroundColor Gray
try {
    Invoke-WebRequest -Uri "$baseUrl/clientes/999" -Method GET -ErrorAction Stop
} catch {
    if ($_.Exception.Response.StatusCode -eq 404) {
        Write-Host "   ✅ Error 404 recibido correctamente" -ForegroundColor Green
        $errorBody = $_.Exception.Response.Content.ReadAsStream() | { [System.IO.StreamReader]::new($_).ReadToEnd() } | ConvertFrom-Json
        Write-Host "   Mensaje: $($errorBody.detalle)" -ForegroundColor Gray
    }
}

# PRUEBAS OPCIONALES (CREAR DATOS)
Print-Header "➕ PRUEBAS DE CREACIÓN (OPCIONAL)"

Write-Host ""
Write-Host "💡 Para crear nuevos datos, usa estos ejemplos:" -ForegroundColor Cyan
Write-Host ""
Write-Host "1. Crear cliente:" -ForegroundColor Yellow
Write-Host @'
curl -X POST http://localhost:8080/clientes `
  -H "Content-Type: application/json" `
  -d '{
    "nombre": "Test User",
    "email": "test@gmail.com",
    "telefono": "1234567890",
    "tipo": "LOCAL"
  }'
'@ -ForegroundColor Gray

Write-Host ""
Write-Host "2. Crear venta:" -ForegroundColor Yellow
Write-Host @'
curl -X POST http://localhost:8080/ventas `
  -H "Content-Type: application/json" `
  -d '{
    "clienteId": 1,
    "usuarioId": 1,
    "tiendaId": 1,
    "tipoVenta": "LOCAL",
    "items": [{"skuId": 4, "cantidad": 1}],
    "pagos": [{"monto": 12000.0, "metodo": "EFECTIVO"}]
  }'
'@ -ForegroundColor Gray

# RESUMEN FINAL
Print-Header "✅ RESUMEN DE PRUEBAS"

Write-Host ""
Write-Host "📝 Pruebas completadas. Verificar:" -ForegroundColor Green
Write-Host "   ✓ Todos los GET retornan datos" -ForegroundColor White
Write-Host "   ✓ Los errores 404 funcionan correctamente" -ForegroundColor White
Write-Host "   ✓ El PDF se descargó correctamente" -ForegroundColor White
Write-Host "   ✓ Las búsquedas y filtros funcionan" -ForegroundColor White
Write-Host "   ✓ Los reportes generan datos" -ForegroundColor White
Write-Host ""
Write-Host "📚 Para más detalles, ver GUIA_PRUEBAS.md" -ForegroundColor Cyan
Write-Host ""

