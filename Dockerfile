# Use a multi-stage build for optimization
FROM eclipse-temurin:17-jdk-jammy AS builder

# Set working directory
WORKDIR /app

# Copy maven files first for better caching
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Download dependencies first (this layer will be cached)
RUN ./mvnw dependency:go-offline

# Copy the source code
COPY src/ src/

# Build the application
RUN ./mvnw package -DskipTests

# Use a smaller runtime image
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copy the jar file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Set the entrypoint to run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
