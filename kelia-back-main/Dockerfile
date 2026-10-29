# Imagen base corregida (Ubuntu 22.04 + JDK 21)
FROM eclipse-temurin:21-jdk-jammy

# Crear directorio de trabajo
WORKDIR /app

# Copiar pom.xml y descargar dependencias (para cacheo)
COPY pom.xml .

# Instalar Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Descarga de dependencias
RUN mvn dependency:go-offline

# Copiar el código fuente
COPY src ./src

# Compilar y empaquetar
RUN mvn clean package -Dmaven.test.skip=true

# Exponer puerto
EXPOSE 8080

# Ejecutar el JAR (ajustar nombre si cambia)
CMD ["java", "-jar", "target/kelia-1.0.0.jar"]
