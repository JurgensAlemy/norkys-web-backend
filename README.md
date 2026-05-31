# Norky's - Backend API

API REST desarrollada con Spring Boot para el sistema de delivery Norky's.

## Tecnologías
- Java 17
- Spring Boot 3
- Spring Data JPA
- MySQL
- Maven

## Endpoints principales
- `POST /api/auth/login` — Autenticación
- `POST /api/auth/registro` — Registro de usuario
- `GET /api/productos` — Listar productos
- `POST /api/pedidos` — Crear pedido
- `PATCH /api/pedidos/{id}/estado` — Cambiar estado
- `GET /api/usuarios` — Listar usuarios (admin)

## Cómo ejecutar
1. Configura `application.properties` con tu base de datos MySQL
2. Ejecuta `mvn spring-boot:run`
3. La API estará disponible en `http://localhost:8080`

## Equipo
Proyecto desarrollado para el curso Herramientas de Desarrollo — UTP 2026