FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jre-jammy
WORKDIR /eshop
COPY --from=build /app/target/*.jar eshop.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "eshop.jar"]