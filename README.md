# ⭐ Ayni Habilidades Service

Microservicio de gamificación para la plataforma Somos Ayni. Registra las habilidades validadas que un talento va acumulando a lo largo del tiempo y le otorga insignias cuando supera retos. Existe como dominio separado porque el reconocimiento de logros es una preocupación independiente de las postulaciones y del perfil del usuario — tiene su propio ciclo de vida y sus propias reglas de negocio.

## Responsabilidad del Bounded Context

**Maneja:**
- Registro y actualización de habilidades validadas por talento
- Otorgamiento y consulta de insignias (badges)
- Construcción del portafolio de logros de un talento

**No maneja:**
- Evaluación de postulaciones ni decisiones APROBADO/RECHAZADO (responsabilidad de `ayni-postulaciones-service`)
- Creación de retos ni publicaciones de empresas (responsabilidad de `ayni-retos-service`)
- Autenticación ni gestión de contraseñas

---

## Endpoints REST

| Método | Ruta | Descripción | Auth |
|--------|------|-------------|------|
| POST | `/api/v1/habilidades` | Registra o actualiza una habilidad validada (body: `nombre`, `nivel`) | JWT requerido |
| GET | `/api/v1/habilidades/portafolio/{talentoId}` | Retorna el portafolio completo del talento: habilidades e insignias | JWT requerido |
| POST | `/api/v1/insignias` | Otorga una insignia a un talento (body: `talentoId`, `retoId`, `titulo`, `tipo`) | JWT requerido (EMPRESA / ADMIN) |
| GET | `/api/v1/insignias/talento/{talentoId}` | Lista todas las insignias de un talento | JWT requerido |

---

## Arquitectura (Hexagonal)

```
src/main/java/com/somosayni/habilidades/
├── domain/           # Núcleo del negocio — sin dependencias externas
│   ├── model/        # Entidades y enums (HabilidadValidada, Insignia, TipoInsignia)
│   └── port/         # Interfaces de repositorio y servicios de salida
├── application/      # Casos de uso (registrar habilidad, otorgar insignia, ver portafolio)
│   └── service/      # Orquestación de la lógica de negocio
└── infrastructure/   # Adaptadores técnicos
    ├── persistence/  # JPA repositories, entidades de BD
    ├── security/     # Validación de JWT (solo verifica, no firma)
    └── web/          # Controladores REST, DTOs de entrada/salida
```

| Capa | Responsabilidad |
|------|----------------|
| **domain** | Reglas de gamificación: qué nivel es válido, qué tipos de insignia existen, cómo se compone un portafolio |
| **application** | Orquesta los puertos: guarda la habilidad o insignia, recupera el portafolio, valida condiciones de negocio |
| **infrastructure** | Todo lo técnico: Spring Boot, JPA/PostgreSQL, validación del token JWT, serialización JSON |

---

## Modelos de dominio principales

### HabilidadValidada

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | UUID | Identificador único |
| `talentoId` | UUID | ID del talento al que pertenece |
| `nombre` | String | Nombre de la habilidad (p. ej. "Java", "Diseño UX") |
| `nivel` | String | Nivel alcanzado (p. ej. `BASICO`, `INTERMEDIO`, `AVANZADO`) |
| `fechaValidacion` | LocalDate | Fecha en que se acreditó la habilidad |

### Insignia

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | UUID | Identificador único |
| `talentoId` | UUID | ID del talento que recibió la insignia |
| `retoId` | UUID | ID del reto asociado |
| `titulo` | String | Nombre descriptivo de la insignia |
| `tipo` | Enum | `TOP_10` / `VERIFICADO` / `CREATIVIDAD` |
| `fechaOtorgada` | LocalDate | Fecha de emisión |

### Tipos de insignia (TipoInsignia)

| Valor | Significado |
|-------|-------------|
| `TOP_10` | El talento estuvo entre los 10 mejores en un reto |
| `VERIFICADO` | Habilidad certificada por una empresa |
| `CREATIVIDAD` | Reconocimiento especial por solución innovadora |

---

## Cómo ejecutar

### Local

```bash
# Requisitos: Java 21, Maven, PostgreSQL corriendo en localhost:5432
mvn clean package -DskipTests
java -jar target/*.jar
```

### Docker

```bash
cp .env.example .env
# Editar .env con tus valores reales
docker-compose up --build
```

---

## Variables de entorno

| Variable | Descripción | Valor por defecto |
|----------|-------------|-------------------|
| `SERVER_PORT` | Puerto en que levanta el servicio | `8085` |
| `DB_HOST` | Host de PostgreSQL | `localhost` |
| `DB_PORT` | Puerto de PostgreSQL | `5432` |
| `DB_NAME` | Nombre de la base de datos | `somosayni` |
| `DB_USERNAME` | Usuario de PostgreSQL | `postgres` |
| `DB_PASSWORD` | Contraseña de PostgreSQL | *(requerido)* |
| `JWT_SECRET` | Clave secreta para validar tokens JWT (firmados por identidad-service) | *(requerido — compartida con todos los servicios)* |

---

## Swagger UI

Disponible en `http://localhost:8085/swagger-ui.html` mientras el servicio esté corriendo.

---

## Dependencias

Este servicio forma parte del ecosistema Somos Ayni junto con `ayni-identidad-service`, `ayni-postulaciones-service`, `ayni-perfiles-service` y `ayni-retos-service`. Los servicios comparten las siguientes convenciones:

- **Base de datos:** Todos usan la misma instancia de PostgreSQL, base de datos `somosayni`, pero cada servicio opera sobre sus propias tablas sin hacer JOINs entre contextos.
- **JWT:** Los tokens son **firmados exclusivamente por `ayni-identidad-service`**. Este servicio solo valida la firma usando la misma variable `JWT_SECRET`.
- **Comunicación:** Los servicios son independientes entre sí. Se referencian únicamente por ID (p. ej. `talentoId`, `retoId`) y no realizan llamadas HTTP entre ellos en tiempo real.
