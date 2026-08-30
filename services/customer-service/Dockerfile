FROM maven:3.9.11-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml ./
COPY .mvn .mvn

RUN mvn -s .mvn/settings.xml -B dependency:go-offline

COPY src ./src

RUN mvn -s .mvn/settings.xml -B clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
