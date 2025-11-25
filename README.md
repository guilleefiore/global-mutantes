# 🔬 Mutantes API – Examen MercadoLibre  
**Versión:** 1.0 — *Detecta ADN mutante, guarda registros y expone estadísticas.*

---

## 📌 Descripción general

Esta API permite determinar si una secuencia de ADN pertenece a un **mutante** o a un **humano**, cumpliendo con el desafío técnico de MercadoLibre.  
El servicio está desarrollado con **Spring Boot 3.5**, base en **H2 en memoria**, documentación **OpenAPI/Swagger**, test unitarios, test de integración y **Jacoco** para aseguramiento de calidad.

---

# 🚀 Tecnologías utilizadas

- Java 21  
- Spring Boot 3.5.8  
- Spring Web  
- Spring Data JPA  
- H2 Database  
- Spring Validation  
- SpringDoc OpenAPI (Swagger UI)  
- JUnit 5  
- Jacoco  
- Lombok  
- Gradle  

---

# 🧬 Funcionalidades principales

### ✔ Detectar si un ADN es mutante  
### ✔ Persistir ADN analizados con hash único  
### ✔ Obtener estadísticas globales  
- `count_mutant_dna`  
- `count_human_dna`  
- `ratio`  

### ✔ Validación completa del ADN  
- No nulo  
- Matriz NxN  
- Caracteres válidos: A – T – C – G  

### ✔ Rate Limiting  
Máximo **10 requests por minuto por IP**.

### ✔ Documentación Swagger  
Disponible en:  
📌 **http://localhost:8080/swagger-ui/index.html**

---

# 🗂 Estructura del Proyecto (paquetes principales)

```

ar.edu.utn.mutantes
├── controller
├── service
├── validator
├── exception
├── repository
├── entity
├── config
└── dto

````

---

# 📡 Endpoints

## 🔹 POST `/mutant`
Evalúa si el ADN es mutante.

### Request:
```json
{
  "dna": ["ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"]
}
````

### Responses:

**200 OK** – es mutante
**403 Forbidden** – no es mutante
**400 Bad Request** – ADN inválido

---

## 🔹 GET `/api/stats`

Devuelve estadísticas:

```json
{
  "count_mutant_dna": 40,
  "count_human_dna": 100,
  "ratio": 0.4
}
```

---

## 🔹 GET `/health`

Simple check de salud:

```json
{
  "status": "UP",
  "timestamp": "2025-11-24T..."
}
```

---

# 🧪 Ejecución del proyecto

### ▶ **1. Compilar**

```bash
./gradlew clean build
```

### ▶ **2. Levantar la API**

```bash
./gradlew bootRun
```

Disponible en:
👉 [http://localhost:8080](http://localhost:8080)

---

# 📘 Swagger / OpenAPI

```
http://localhost:8080/swagger-ui/index.html
```

API Docs en JSON:

```
http://localhost:8080/v3/api-docs
```

---

# 🧪 Correr los tests

```bash
./gradlew test
```

Todos los tests unitarios + integración deben pasar correctamente.

---

# 📊 Reporte de cobertura Jacoco

### ▶ Generar reporte:

```bash
./gradlew jacocoTestReport
```

### ▶ Abrir reporte HTML:

Mac/Linux:

```bash
open build/reports/jacoco/test/html/index.html
```

Windows:

```bash
start build/reports/jacoco/test/html/index.html
```

### 📌 **Mi cobertura final:**

*(Incluye controlador, servicio, validador y excepciones)*

👉 **95% de cobertura total**

<img width="1440" height="372" alt="Captura de pantalla 2025-11-25 a la(s) 02 12 02" src="https://github.com/user-attachments/assets/5cacdde1-83af-4864-955b-ba5616202d5c" />

---

# 🧠 Lógica de detección de mutantes

Se detectan secuencias de **4 letras iguales** en:

* Horizontal
* Vertical
* Diagonal principal
* Diagonal inversa

Si se encuentran **2 o más**, el ADN es mutante.

---

# 📘 **Documentación Swagger + Evidencias**

A continuación se muestran capturas reales de Swagger demostrando el funcionamiento correcto de la API en cada uno de sus endpoints principales.

Esto sirve como evidencia de que el sistema responde de forma consistente y acorde a lo solicitado en el examen.

---

## 🧬 **1. POST /mutant – Caso Mutante (200 OK)**

Este ejemplo muestra una secuencia de ADN válida que contiene al menos **dos patrones mutantes**, lo que permite responder con código **200 OK**.

📌 **Request (Body – JSON):**

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

📌 **Respuesta esperada: (Swagger)**
✔ **200 OK** – Mutante detectado

<img width="1440" height="703" alt="Captura de pantalla 2025-11-24 a la(s) 23 21 54" src="https://github.com/user-attachments/assets/cc165076-29b4-4d86-823e-b963b3debbf8" />

<img width="1440" height="784" alt="Captura de pantalla 2025-11-24 a la(s) 23 22 05" src="https://github.com/user-attachments/assets/a56c6ca3-4815-4017-ad80-ea4beb84e612" />

---

## 🚫 **2. POST /mutant – Caso Humano (403 Forbidden)**

Este ejemplo contiene un ADN válido, pero **no cumple** con las dos secuencias requeridas, por lo que la API debe responder **403 Forbidden**.

📌 **Request (Body – JSON):**

```json
{
  "dna": [
    "ATGCGA",
    "TACGTA",
    "CGTACG",
    "GCATGC",
    "ATGCAT",
    "TACGTA"
  ]
}
```

📌 **Respuesta esperada: (Swagger)**
❌ **403 Forbidden** – No es mutante

<img width="1440" height="678" alt="Captura de pantalla 2025-11-24 a la(s) 23 32 15" src="https://github.com/user-attachments/assets/9dc3b14c-4b95-4991-9413-12968f04c0df" />

<img width="1440" height="787" alt="Captura de pantalla 2025-11-24 a la(s) 23 32 24" src="https://github.com/user-attachments/assets/176a7cf9-8417-4ffd-88f3-134f1a84fa5d" />

---

## 📊 **3. GET /api/stats – Estadísticas reales**

Después de enviar varios casos, stats debe reflejar:

* `count_mutant_dna`
* `count_human_dna`
* `ratio = mutants / humans`

📌 **Respuesta esperada:**

```json
{
  "count_mutant_dna": 2,
  "count_human_dna": 1,
  "ratio": 2
}
```

<img width="1440" height="861" alt="Captura de pantalla 2025-11-25 a la(s) 00 14 46" src="https://github.com/user-attachments/assets/2193250a-8cb6-4cf2-b53b-aa9e5e605618" />

---

# 🧩 Diagrama de Secuencia — Versión Completa de Toda la Aplicación

El siguiente diagrama de secuencia representa **todo el flujo interno de la Mutantes API**, incluyendo los tres endpoints implementados:

* **POST /mutant**
* **GET /api/stats**
* **GET /health**

Este diagrama describe de manera detallada **cómo viaja la información desde el cliente**, pasando por validaciones, filtros, servicio de negocio, detección, repositorio, excepciones y construcción de respuestas.

---

## 🛡 Componentes incluidos en el diagrama

### ✔ **Filtros y manejo transversal**

* **RateLimitingFilter**: limita a 10 requests por minuto por IP y devuelve *429 Too Many Requests* cuando se excede.
* **GlobalExceptionHandler**: captura excepciones y devuelve respuestas estructuradas, como:

  * `InvalidDnaException` → **400 Bad Request**
  * otras excepciones → Bad Request

### ✔ **Controllers**

* **MutantController**
* **StatsController**
* **HealthController**

### ✔ **Validación**

* **DnaValidator**
  Verifica que el ADN sea:

  * Matriz **NxN**
  * Solo caracteres **A, T, C, G**

### ✔ **Lógica de negocio**

* **MutantService**

  * valida ADN
  * genera hash
  * revisa si el ADN existe en BD
  * delega al detector
  * persiste resultado
  * construye respuesta
  * calcula estadísticas

* **MutantDetector**
  Implementa detección por:

  * horizontal
  * vertical
  * diagonal derecha
  * diagonal izquierda
  * manejo interno de secuencias superpuestas

### ✔ **Persistencia**

* **DnaRecordRepository**

  * guarda cada ADN con su hash
  * consulta si ya existe
  * calcula `countMutants` y `countHumans`

### ✔ **DTOs involucrados**

* **DnaRequest**
* **StatsResponse**
* **HealthResponse**

---

## ✔ **Flujos alternativos representados**

* **200 OK** para mutantes
* **403 Forbidden** para humanos
* **400 Bad Request** si el ADN es inválido
* **429 Too Many Requests** si se supera el rate limit
* **200 OK** en stats y health

---

## 🎯 Propósito del diagrama

Este diagrama centraliza **todo el comportamiento del sistema** en un único flujo, mostrando cómo se comunican los módulos que implementaste:

* validación
* detección
* persistencia
* manejo de excepciones
* rate limiting
* endpoints REST
* respuestas JSON

Es la representación **más completa y profesional** de la arquitectura lógica de tu entrega.

<img width="7901" height="5528" alt="DS" src="https://github.com/user-attachments/assets/4dd73e70-1800-4126-8094-296e5238e9a8" />

---

# 🧱 Modelo de Datos (Entidad)

```java
@Entity
public class DnaRecord {
    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String dnaHash;

    private boolean isMutant;
}
```

Hash SHA-256 asegura unicidad por ADN evaluado.

---

# 🚦 Rate Limiting

Implementado en `RateLimitingFilter`:

* Límite: **10 requests/minuto por IP**
* Excepciones:

    * `/v3/api-docs`
    * `/swagger-ui/**`

---

# ❗ Manejo de Errores (GlobalExceptionHandler)

### Ejemplo 400 BAD REQUEST:

```json
{
  "timestamp": "...",
  "error": "Invalid DNA",
  "message": "Matriz no es NxN"
}
```

### Ejemplo 500 INTERNAL SERVER ERROR:

```json
{
  "timestamp": "...",
  "error": "Internal Server Error",
  "message": "..."
}
```

---

# 📦 Cómo clonar y ejecutar el proyecto

```bash
git clone https://github.com/guilleefiore/global-mutantes.git
cd global-mutantes
./gradlew bootRun
```

---

## 🌐 API Deployada en Render

La API se encuentra desplegada en Render y accesible públicamente para pruebas y corrección del trabajo.

### ⭐ URL Principal (Home)
La raíz de la aplicación redirige automáticamente a la documentación Swagger.

👉 **https://global-mutantes-43o3.onrender.com/**

---

## 📘 Documentación OpenAPI (Swagger UI)

Interfaz interactiva para probar todos los endpoints de la API:

👉 **https://global-mutantes-43o3.onrender.com/swagger-ui/index.html**

---

# ✔ Evaluación del desafío (cumplimiento)

| Requisito                     | Estado |
| ----------------------------- | ------ |
| POST /mutant funcionando      | ✔      |
| GET /stats funcionando        | ✔      |
| Validaciones ADN              | ✔      |
| Hash + persistencia           | ✔      |
| Tests unitarios + integración | ✔      |
| Jacoco                        | ✔      |
| Swagger                       | ✔      |
| Rate Limit opcional           | ✔      |
| Arquitectura limpia           | ✔      |
| README completo               | ✔      |

---

# 🏁 Autor

**Guillermina Fiore**
**Legajo:** 50024
UTN — Facultad Regional Mendoza
2025
