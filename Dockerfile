# ---------- ETAPA 1: build ----------
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos primero solo el pom.xml para aprovechar la cache de capas de Docker
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiamos el resto del código fuente y compilamos
COPY src ./src
RUN mvn clean package -DskipTests -B

# ---------- ETAPA 2: imagen final ----------
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiamos únicamente el .jar generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
