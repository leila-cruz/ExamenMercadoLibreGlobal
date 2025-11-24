🧪 Estrategia de Testing y Calidad de Código
El proyecto cuenta con una suite exhaustiva de 30 tests automatizados desarrollados con JUnit 5 y Mockito, alcanzando una cobertura de código superior al 80% (verificado con JaCoCo).

La estrategia de pruebas se divide en tres capas principales:

1. Tests de Lógica Core (Algoritmo)
La clase MutantDetectorTest es el corazón de las pruebas, encargada de verificar la robustez y eficiencia del algoritmo de detección de mutantes.

Casos Positivos: Verifica la detección correcta de secuencias horizontales, verticales y diagonales (principal e inversa).

Casos Negativos: Confirma que secuencias humanas (sin patrones o con solo una secuencia) sean rechazadas.

Validaciones (Edge Cases): Se prueban matrices nulas, vacías, no cuadradas (NxM), caracteres inválidos y matrices demasiado pequeñas (<4x4).

Lógica Avanzada: Tests específicos para evitar falsos positivos por solapamiento (ej: AAAAA cuenta como una secuencia, no dos) y cruces de secuencias (formas de L o cruz).

Performance: Incluye un benchmark con una matriz gigante de 1000x1000 para asegurar que la optimización de Early Termination funciona y el algoritmo responde en milisegundos.

2. Tests de Componentes y Servicios
Se utilizan pruebas unitarias aisladas con Mockito para validar la lógica de negocio sin depender de la base de datos.

StatsServiceTest: Verifica el cálculo matemático de las estadísticas. Prueba escenarios críticos como la división por cero (cuando no hay humanos registrados) y bases de datos vacías, asegurando que el ratio siempre se calcule correctamente.

GlobalExceptionHandlerTest: Valida que todas las excepciones (Validación de argumentos, JSON malformado, errores internos) sean capturadas y transformadas en una respuesta JSON estandarizada (ErrorResponse) con el código HTTP correcto.

3. Tests de Integración y Cobertura
Estas pruebas aseguran que las capas de la aplicación se comuniquen correctamente y que los objetos de transferencia de datos estén bien construidos.

MutantControllerTest: Utiliza MockMvc para simular peticiones HTTP reales contra la API. Verifica que los endpoints /mutant y /stats respondan con los códigos de estado adecuados (200, 400, 403) y manejen excepciones personalizadas como DnaHashCalculationException.

FullDtoEntityTest y DnaDtoTest: Pruebas exhaustivas sobre Entidades y DTOs (DnaRequest, StatsResponse, DnaRecord). Verifican constructores, getters, setters y métodos generados por Lombok (equals, hashCode, toString, @Builder), garantizando la integridad de los datos y maximizando la métrica de cobertura de código.

📊 Reporte de Cobertura
Para generar el reporte visual de cobertura, ejecutar:

Bash
./gradlew test jacocoTestReport
El reporte estará disponible en build/reports/jacoco/test/html/index.html.
