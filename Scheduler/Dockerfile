FROM gradle:8.5-jdk21 AS build
WORKDIR /app

COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle

RUN gradle dependencies --no-daemon

COPY src ./src

RUN gradle build --no-daemon -x test

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN addgroup -S scheduler && adduser -S scheduler -G scheduler

COPY --from=build /app/build/libs/*.jar app.jar

RUN chown scheduler:scheduler app.jar

USER scheduler

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]