#!/usr/bin/env pwsh
# Script para ejecutar y probar el sistema POS EDHEN

Write-Host "╔════════════════════════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║       🚀 SISTEMA POS EDHEN - SCRIPT DE EJECUCIÓN 🚀           ║" -ForegroundColor Cyan
Write-Host "╚════════════════════════════════════════════════════════════════╝" -ForegroundColor Cyan
Write-Host ""

# Cambiar al directorio del proyecto
$projectPath = "D:\edhen\sistema-edhen\edhen-pos-back"
Set-Location $projectPath

Write-Host "📁 Ubicación: $projectPath" -ForegroundColor Green
Write-Host ""

# Menú principal
$menuOptions = @(
    "1. Ejecutar la aplicación (mvn spring-boot:run)",
    "2. Compilar y ejecutar (mvn clean install + spring-boot:run)",
    "3. Solo compilar (mvn clean install)",
    "4. Ejecutar JAR existente (java -jar)",
    "5. Ver estado de puertos (verificar si 8080 está disponible)",
    "6. Limpiar proyecto (mvn clean)",
    "7. Salir"
)

Write-Host "┌─ OPCIONES DISPONIBLES ─────────────────────────────────────────┐" -ForegroundColor Yellow
foreach ($option in $menuOptions) {
    Write-Host "  $option" -ForegroundColor White
}
Write-Host "└────────────────────────────────────────────────────────────────┘" -ForegroundColor Yellow
Write-Host ""

$choice = Read-Host "Selecciona una opción (1-7)"

switch ($choice) {
    "1" {
        Write-Host ""
        Write-Host "▶ Ejecutando: mvn spring-boot:run" -ForegroundColor Cyan
        Write-Host "⏳ Esto puede tomar 30-60 segundos la primera vez..." -ForegroundColor Yellow
        Write-Host ""
        mvn spring-boot:run
    }

    "2" {
        Write-Host ""
        Write-Host "▶ Compilando e instalando (clean install)..." -ForegroundColor Cyan
        mvn clean install -q

        Write-Host ""
        Write-Host "✅ Compilación completada" -ForegroundColor Green
        Write-Host ""
        Write-Host "▶ Ejecutando: mvn spring-boot:run" -ForegroundColor Cyan
        Write-Host "⏳ Iniciando la aplicación..." -ForegroundColor Yellow
        Write-Host ""
        mvn spring-boot:run
    }

    "3" {
        Write-Host ""
        Write-Host "▶ Compilando el proyecto..." -ForegroundColor Cyan
        mvn clean install -q

        Write-Host ""
        Write-Host "✅ Proyecto compilado exitosamente" -ForegroundColor Green
        Write-Host "💡 Ahora puedes ejecutar con la opción 4 (java -jar)" -ForegroundColor Yellow
    }

    "4" {
        Write-Host ""
        Write-Host "▶ Ejecutando JAR..." -ForegroundColor Cyan

        $jarFile = "target/pos-0.0.1-SNAPSHOT.jar"

        if (Test-Path $jarFile) {
            Write-Host "✅ JAR encontrado: $jarFile" -ForegroundColor Green
            Write-Host ""
            Write-Host "⏳ Iniciando..." -ForegroundColor Yellow
            Write-Host ""
            java -jar $jarFile
        } else {
            Write-Host "❌ JAR no encontrado en: $jarFile" -ForegroundColor Red
            Write-Host "💡 Primero compila con la opción 2 o 3" -ForegroundColor Yellow
        }
    }

    "5" {
        Write-Host ""
        Write-Host "🔍 Verificando puerto 8080..." -ForegroundColor Cyan
        Write-Host ""

        $port8080 = Get-NetTCPConnection -LocalPort 8080 -ErrorAction SilentlyContinue

        if ($port8080) {
            Write-Host "⚠️  Puerto 8080 está EN USO" -ForegroundColor Red
            Write-Host "Procesos usando el puerto:" -ForegroundColor Yellow
            Get-Process | Where-Object { $_.Id -in $port8080.OwningProcess } | Select-Object Name, Id, @{Name="Memoria(MB)";Expression={[math]::Round($_.WorkingSet / 1MB, 2)}}
        } else {
            Write-Host "✅ Puerto 8080 está DISPONIBLE" -ForegroundColor Green
        }
    }

    "6" {
        Write-Host ""
        Write-Host "🧹 Limpiando proyecto..." -ForegroundColor Cyan
        mvn clean

        Write-Host ""
        Write-Host "✅ Proyecto limpiado" -ForegroundColor Green
    }

    "7" {
        Write-Host ""
        Write-Host "👋 ¡Hasta luego!" -ForegroundColor Green
        exit
    }

    default {
        Write-Host ""
        Write-Host "❌ Opción inválida. Intenta de nuevo." -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "═════════════════════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host ""
Write-Host "💡 TIPS PARA PROBAR:" -ForegroundColor Yellow
Write-Host "   • Abre otra ventana de PowerShell para ejecutar curl" -ForegroundColor White
Write-Host "   • Ver GUIA_PRUEBAS.md para todos los endpoints disponibles" -ForegroundColor White
Write-Host "   • La BD se guardará en: $projectPath/pos.db" -ForegroundColor White
Write-Host ""

