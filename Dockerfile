FROM gradle:8.5.0-jdk17-alpine as builder
#FROM arm64v8/gradle:8.5.0-jdk17 as builder

WORKDIR /app

COPY . /app/
RUN gradle build


FROM eclipse-temurin:17-jdk
LABEL authors="Jack Kellogg"

WORKDIR /app

COPY --from=builder /app/build/libs/waitlist*-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

HEALTHCHECK --interval=15s --timeout=3s \
  CMD curl -f http://localhost:8080/api/actuator/health || exit 1