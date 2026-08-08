# 1. build # imagem "temporária" de uso para o build

FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

# 2. runtime # imagem final para a aplicação backend

FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]