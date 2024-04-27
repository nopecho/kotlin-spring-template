FROM amazoncorretto:21-alpine
LABEL authors="nopecho"

ARG FROM_JAR=build/libs/bootstrap.jar
COPY $FROM_JAR app.jar

EXPOSE $PORT
ENTRYPOINT ["java", "-jar", "app.jar"]