# Build da aplicação
FROM eclipse-temurin:21-jdk-jammy as build
WORKDIR /workspace/app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
RUN chmod +x ./mvnw

COPY src ./src
RUN ./mvnw package -DskipTests

# Imagem final
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /workspace/app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
