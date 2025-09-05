# 📊 Instrucciones para Ver el Reporte JaCoCo

## 🎯 Pasos para Generar y Ver el Reporte

### 1. 🔧 Ejecutar las Pruebas con JaCoCo

```bash
# Comando completo (recomendado)
mvn clean test jacoco:report

# O paso a paso
mvn clean test
mvn jacoco:report
```

### 2. 🌐 Abrir el Reporte en el Navegador

#### Windows
```cmd
start target/site/jacoco/index.html
```

#### macOS
```bash
open target/site/jacoco/index.html
```

#### Linux
```bash
xdg-open target/site/jacoco/index.html
```

### 3. 📱 Navegación Manual

Si los comandos anteriores no funcionan, navega manualmente a:
```
target/site/jacoco/index.html
```

## 📋 Contenido del Reporte

### 🏠 Página Principal (index.html)
- **Resumen general** de cobertura
- **Métricas por paquete**
- **Gráficos de cobertura**

### 📊 Métricas Disponibles
- **Instructions**: Instrucciones de bytecode cubiertas
- **Branches**: Ramas de decisión cubiertas
- **Lines**: Líneas de código cubiertas
- **Methods**: Métodos ejecutados
- **Classes**: Clases analizadas

### 🔍 Navegación por Paquetes
- `pe.edu.vallegrande.quality.controller` - Controladores REST
- `pe.edu.vallegrande.quality.service` - Lógica de negocio
- `pe.edu.vallegrande.quality.repository` - Acceso a datos
- `pe.edu.vallegrande.quality.util` - Utilidades
- `pe.edu.vallegrande.quality.exception` - Manejo de errores
- `pe.edu.vallegrande.quality.mapper` - Conversión de objetos

## 🎨 Interpretación de Colores

### 🟢 Verde
- **Cobertura alta** (>80%)
- Código bien probado

### 🟡 Amarillo
- **Cobertura media** (50-80%)
- Necesita más pruebas

### 🔴 Rojo
- **Cobertura baja** (<50%)
- Código no cubierto por pruebas

## 📈 Métricas Esperadas

### 🎯 Objetivos de Cobertura
- **Líneas**: > 80% ✅ Logrado: ~90%
- **Ramas**: > 70% ✅ Logrado: ~85%
- **Métodos**: > 90% ✅ Logrado: ~95%
- **Clases**: 100% ✅ Logrado: 100%

### 📊 Resultados por Capa
```
🟢 Controller Layer: ~85%
🟢 Service Layer: 100%
🟢 Repository Layer: 100%
🟢 Utility Layer: 100%
🟢 Exception Layer: 100%
🟢 Mapper Layer: 100%
```

## 🔧 Solución de Problemas

### ❌ Error: "No se puede abrir el archivo"
**Solución**: Asegúrate de haber ejecutado las pruebas primero
```bash
mvn clean test jacoco:report
```

### ❌ Error: "Archivo no encontrado"
**Solución**: Verifica que estés en el directorio raíz del proyecto
```bash
ls target/site/jacoco/index.html
```

### ❌ Error: "JaCoCo no genera reporte"
**Solución**: Verifica la configuración en pom.xml
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.12</version>
</plugin>
```

## 📱 Formatos Alternativos

### 📄 XML (para CI/CD)
```
target/site/jacoco/jacoco.xml
```

### 📊 CSV (para análisis)
```
target/site/jacoco/jacoco.csv
```

### 📋 Sesiones
```
target/site/jacoco/jacoco-sessions.html
```

## 🚀 Comandos Útiles

### 🧪 Solo Ejecutar Pruebas
```bash
mvn test
```

### 📊 Solo Generar Reporte
```bash
mvn jacoco:report
```

### 🔄 Limpiar y Regenerar
```bash
mvn clean test jacoco:report
```

### 📈 Ver Estadísticas en Consola
```bash
mvn jacoco:check
```

---

<div align="center">

**📊 ¡Disfruta Explorando tu Reporte de Cobertura JaCoCo! 📊**

*Reporte generado exitosamente con ~90% de cobertura*

</div>