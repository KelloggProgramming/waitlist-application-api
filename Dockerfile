LABEL authors="Jack Kellogg"

FROM gradle:8.5.0-jdk17-alpine as builder
WORKDIR /app

COPY . /app/
RUN gradle build

FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=builder /app/build/libs/waitlist*-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]