FROM amazoncorretto:21-alpine
LABEL org.opencontainers.image.source=https://github.com/octocat/nopechouniverse
LABEL authors="nopecho"

ARG FROM_JAR=build/libs/bootstrap.jar
COPY $FROM_JAR app.jar

EXPOSE $PORT
ENTRYPOINT ["java", "-jar", "app.jar"]