# 🧬 Mutant Detector API

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2+-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-Enabled-blue.svg)](https://www.docker.com/)
[![Coverage](https://img.shields.io/badge/Coverage-80%25%2B-success.svg)]()
[![Deploy](https://img.shields.io/badge/Deploy-Render-purple.svg)](https://render.com/)

API REST desarrollada como desafío técnico para **MercadoLibre**. Su objetivo es detectar si un humano es un mutante basándose en su secuencia de ADN.

---

## 🚀 Demo en Vivo (Deploy)

La aplicación está desplegada y funcionando en **Render**:

- **Base URL:** `https://examenmercadolibreglobal.onrender.com`
- **Documentación Swagger UI:** [Ver Documentación Interactiva](https://examenmercadolibreglobal.onrender.com/swagger-ui.html)
- **Estadísticas:** [Ver Stats JSON](https://examenmercadolibreglobal.onrender.com/stats)

---

## 📋 Descripción del Problema

Magneto quiere reclutar la mayor cantidad de mutantes para luchar contra los X-Men.
Te ha contratado para desarrollar un proyecto que detecte si un humano es mutante basándose en su secuencia de ADN.

**Condición:** Un humano es mutante si encuentras **más de una secuencia de cuatro letras iguales**, de forma oblicua, horizontal o vertical.

### Ejemplo de ADN Mutante
json
{
  "dna": ["ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"]
}
(Contiene una secuencia horizontal "CCCC" y diagonales/verticales que cumplen la condición).

🛠️ Tecnologías Utilizadas
Lenguaje: Java 21 (LTS)

Framework: Spring Boot 3.3.x

Base de Datos: H2 Database (En memoria, para alta velocidad en pruebas)

Build Tool: Gradle (Groovy)

Contenerización: Docker

Testing: JUnit 5, Mockito

Cobertura de Código: JaCoCo (>80%)

Documentación: SpringDoc OpenAPI (Swagger)

Nube: Render

⚡ Características y Optimizaciones
Algoritmo Eficiente (O(N)):

Se implementó Early Termination: El algoritmo se detiene inmediatamente al encontrar la segunda secuencia, evitando recorrer toda la matriz innecesariamente.

Manejo de Solapamientos: Lógica inteligente para evitar contar falsos positivos (ej: AAAAA cuenta como 1 secuencia, no 2).

Acceso Directo: Conversión de String[] a char[][] para acceso rápido por índices.

Arquitectura Robusta:

Diseño en capas: Controller -> Service -> Repository.

DTOs validados con Jakarta Validation (@NotNull, @ValidDnaSequence).

Manejo global de excepciones (GlobalExceptionHandler).

Calidad de Código:

Tests Unitarios y de Integración exhaustivos.

Reporte de cobertura de código integrado.

📡 Uso de la API
1. Detectar Mutante
Envía una secuencia de ADN para su análisis.

Endpoint: POST /mutant

Body:

JSON

{
    "dna": [
        "ATGCGA",
        "CAGTGC",
        "TTATGT",
        "AGAAGG",
        "CCCCTA",
        "TCACTG"
    ]
}
Respuestas:

200 OK: Es un Mutante.

403 Forbidden: Es un Humano.

400 Bad Request: ADN inválido (caracteres erróneos, matriz no cuadrada, null).

2. Ver Estadísticas
Devuelve el conteo de verificaciones y el ratio.

Endpoint: GET /stats

Respuesta Exitosa (200 OK):

JSON

{
    "count_mutant_dna": 40,
    "count_human_dna": 100,
    "ratio": 0.4
}
💻 Ejecución Local
Prerrequisitos
Java 21 JDK instalado.

Git instalado.

Pasos
Clonar el repositorio:

Bash

git clone [[https://github.com/TU_USUARIO/ExamenMercadoLibreGlobal.git](https://github.com/leila-cruz/ExamenMercadoLibreGlobal.git)]
cd ExamenMercadoLibreGlobal
Ejecutar la aplicación:

Bash

# En Linux/Mac
./gradlew bootRun

# En Windows (PowerShell)
./gradlew bootRun
Acceder: La API iniciará en http://localhost:8080.

🧪 Testing y Cobertura
El proyecto cuenta con una suite de tests automáticos que cubren controladores, servicios, entidades y validaciones.

Ejecutar Tests:

Bash

./gradlew test
Generar Reporte de Cobertura (JaCoCo):

Bash

./gradlew test jacocoTestReport
El reporte HTML estará disponible en: build/reports/jacoco/test/html/index.html.

🐳 Ejecución con Docker
Si prefieres usar Docker para no instalar Java localmente:

Construir la imagen:

Bash

docker build -t mutant-detector .
Correr el contenedor:

Bash

docker run -p 8080:8080 mutant-detector
Hecho por Leila Cruz para el examen de MercadoLibre.
