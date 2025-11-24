# 🧬 Mutantes API – Global Mutantes  
API REST desarrollada en **Spring Boot** para detectar si un ADN pertenece a un mutante, siguiendo la lógica del desafío de Mercado Libre.  
Incluye **validaciones**, **persistencia**, **rate limiting**, **caché**, **procesamiento asíncrono**, **Swagger**, **tests unitarios e integrales** y está lista para **deploy en Render**.

---

## 📂 Tecnologías
- Java 21  
- Spring Boot 3.2+  
- Spring Web  
- Spring Data JPA  
- H2 Database  
- Spring Cache  
- Spring AOP / Async  
- Mockito / JUnit 5  
- Swagger OpenAPI  
- Jacoco coverage  

---

# 🚀 Endpoints

### ✔ POST `/mutant`
Detecta si un ADN pertenece a un mutante.  
Devuelve:
- **200 OK** si es mutante  
- **403 Forbidden** si NO es mutante  
- **400 Bad Request** si el ADN es inválido  

#### Ejemplo de request:

```json
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
```

### ✔ GET `/stats`

Devuelve estadísticas con **caché en memoria**:

```json
{
  "countMutantDna": 40,
  "countHumanDna": 100,
  "ratio": 0.4
}
```

### ✔ GET `/health`

Endpoint simple de salud para testing / Render.

---

# 🧠 Lógica de Mutantes

La API detecta mutantes buscando **secuencias de 4 letras iguales (A, T, C, G)** en:

* Horizontal ↔
* Vertical ↕
* Diagonal ↘
* Diagonal inversa ↙

Un ADN se considera mutante si posee **al menos 2 secuencias válidas**.

Toda entrada se valida previamente como **matriz NxN** con caracteres válidos.

---

# 💾 Persistencia

Cada ADN se guarda en H2 con:

| Campo       | Descripción     |
| ----------- | --------------- |
| `dna_hash`  | SHA-256 del ADN |
| `is_mutant` | Boolean         |

Se evita repetir análisis si el ADN ya fue procesado.

---

# ⚡ Procesamiento Asíncrono

El método:

```java
@Async
public CompletableFuture<Boolean> analyzeDnaAsync(...)
```

permite ejecutar análisis de ADN en paralelo para alta carga.

---

# 🛡 Rate Limiting

Se implementa un filtro global:

* Máximo **10 requests por minuto por IP**
* Si se supera → **429 Too Many Requests**

Ideal para evitar abuso del endpoint `/mutant`.

---

# 🧠 Caché con @Cacheable

```java
@Cacheable("stats")
public StatsResponse getStats()
```

Evita recalcular estadísticas en cada request.

---

# 🧪 Tests (100% del proyecto cubierto)

La app contiene tests de:

## ✔ Unit Tests

* `MutantDetectorTest` (detección mutante)
* `DnaValidatorTest` (validación NxN, caracteres, etc.)
* `MutantServiceTest`
* `MutantServiceAsyncTest`
* Controllers (MockMvc)
* Exception Handler

## ✔ Integration Tests

* `MutantIntegrationTest`
* `StatsCacheIntegrationTest`
* `RateLimitingIntegrationTest`

## ✔ Repository Tests

* `DnaRecordRepositoryTest`

Todos ejecutables con:

```bash
./gradlew test
```

---

# 📊 Jacoco Coverage

Generar reporte:

```bash
./gradlew jacocoTestReport
```

<img width="1440" height="368" alt="Captura de pantalla 2025-11-24 a la(s) 20 30 06" src="https://github.com/user-attachments/assets/30d8c8f3-5821-41d8-97d2-9b0d8dcaa8c9" />

El reporte queda en:

```
build/reports/jacoco/test/html/index.html
```

---

# ☁ Deploy en Render

### Paso 1 — Crear servicio Web

* Lenguaje: **Java**
* Build command:

  ```
  ./gradlew build
  ```
* Start command:

  ```
  java -jar build/libs/mutantes-0.0.1-SNAPSHOT.jar
  ```

### Paso 2 — Variables recomendadas:

```
JAVA_OPTS = -Xmx512m
ENV = production
```

### Paso 3 — Exponer puerto 8080

Render detectará automáticamente el jar.

---

# 🔧 Cómo levantar en local

```bash
./gradlew bootRun
```

H2 Console:

```
http://localhost:8080/h2-console
```

JDBC URL:

```
jdbc:h2:mem:mutantesdb
```

Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

---

# 📝 Autor

**Guillermina Fiore**
Legajo: 50024
UTN – FRM
Proyecto final de APIs y Testing Avanzado
