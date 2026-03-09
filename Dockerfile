FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/LVGR-0.0.3.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
