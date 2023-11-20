FROM eclipse-temurin:17-jdk
LABEL authors="Jack Kellogg and Jacob Kellogg"

VOLUME /tmp

COPY /build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]

