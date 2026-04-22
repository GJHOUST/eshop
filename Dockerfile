FROM eclipse-temurin:21-jre-jammy
WORKDIR /eshop
COPY target/eshop-0.0.1-SNAPSHOT.jar eshop.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "eshop.jar"]