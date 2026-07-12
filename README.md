# Norky's - Backend API

API REST desarrollada con Spring Boot para el sistema de delivery y gestión de la pollería Norky's.

## Tecnologías

- Java 17
- Spring Boot 4 (Web, Data JPA, Security, Validation)
- PostgreSQL
- Lombok
- Maven
- Docker (opcional, para desarrollo local)

## Variables de entorno

| Variable | Descripción | Valor local por defecto |
|---|---|---|
| `SPRING_DATASOURCE_URL` | URL JDBC de la base de datos | `jdbc:postgresql://localhost:5434/norkys_db` |
| `SPRING_DATASOURCE_USERNAME` | Usuario de la base de datos | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña de la base de datos | `root` |
| `PORT` | Puerto donde corre la API | `8080` |

En **Railway**, estas variables se configuran en la pestaña *Variables* del servicio `norkys-web-backend`, enlazándolas al servicio de Postgres del mismo proyecto:

```
SPRING_DATASOURCE_URL=jdbc:postgresql://${{Postgres.PGHOST}}:${{Postgres.PGPORT}}/${{Postgres.PGDATABASE}}
SPRING_DATASOURCE_USERNAME=${{Postgres.PGUSER}}
SPRING_DATASOURCE_PASSWORD=${{Postgres.PGPASSWORD}}
```

## Cómo ejecutar

### Opción 1: con Docker Compose (recomendado)
Levanta el backend y PostgreSQL juntos con un solo comando:
```bash
docker compose up --build
```
La API estará disponible en `http://localhost:8080`.

### Opción 2: local, sin Docker
1. Instala PostgreSQL y crea la base de datos `norkys_db`.
2. Configura `src/main/resources/application.properties` con tus credenciales (o exporta las variables de entorno de la tabla anterior).
3. Ejecuta:
   ```bash
   mvn spring-boot:run
   ```
4. La API estará disponible en `http://localhost:8080`.

### Producción
El backend se despliega en [Railway](https://railway.com) junto a una instancia de PostgreSQL administrada, ambos dentro del mismo proyecto.

## Endpoints principales

### Autenticación — `/api/auth`
| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/auth/login` | Iniciar sesión |
| POST | `/api/auth/registro` | Registrar nuevo usuario |
| POST | `/api/auth/recuperar` | Solicitar código de recuperación de contraseña |
| POST | `/api/auth/restablecer` | Restablecer contraseña con código |

### Productos — `/api/productos`
| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/productos` | Listar todos los productos activos |
| GET | `/api/productos?categoria={categoria}` | Filtrar productos por categoría |
| GET | `/api/productos/buscar?q={texto}` | Buscar productos por nombre |
| GET | `/api/productos/{id}` | Obtener un producto por id |
| POST | `/api/productos` | Crear producto (admin) |
| PUT | `/api/productos/{id}` | Actualizar producto (admin) |
| DELETE | `/api/productos/{id}` | Desactivar producto (admin) |

### Pedidos — `/api/pedidos`
| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/pedidos` | Crear pedido |
| GET | `/api/pedidos/usuario/{usuarioId}` | Listar pedidos de un usuario |
| GET | `/api/pedidos` | Listar todos los pedidos (admin) |
| PATCH | `/api/pedidos/{id}/estado` | Cambiar estado de un pedido (admin) |

### Usuarios — `/api/usuarios`
| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/usuarios` | Listar usuarios (admin) |
| GET | `/api/usuarios/{id}` | Obtener usuario por id |
| PUT | `/api/usuarios/{id}` | Actualizar datos de un usuario |
| POST | `/api/usuarios/{id}/direcciones` | Agregar dirección al usuario |
| DELETE | `/api/usuarios/{id}/direcciones/{index}` | Eliminar dirección del usuario |

## Equipo
Proyecto desarrollado para el curso Herramientas de Desarrollo — UTP 2026