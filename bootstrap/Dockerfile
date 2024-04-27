FROM amazoncorretto:21-alpine as deps

ARG FROM_JAR=build/libs/bootstrap.jar
COPY $FROM_JAR /app/app.jar

RUN mkdir /app/unpacked && \
    cd /app/unpacked && \
    unzip ../app.jar && \
    cd .. && \
    $JAVA_HOME/bin/jdeps \
    --ignore-missing-deps \
    --print-module-deps \
    -q \
    --recursive \
    --multi-release 21 \
    --class-path="./unpacked/BOOT-INF/lib/*" \
    --module-path="./unpacked/BOOT-INF/lib/*" \
    ./app.jar > /deps.info

FROM amazoncorretto:21-alpine as thin-jre

RUN apk add --no-cache binutils
COPY --from=deps /deps.info /deps.info

RUN $JAVA_HOME/bin/jlink \
         --verbose \
         --add-modules $(cat /deps.info) \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --output /thinjre

FROM alpine:3.19.1
LABEL org.opencontainers.image.source=https://github.com/nopechouniverse/{{app-name}}
LABEL authors=nopecho

ARG FROM_JAR=build/libs/bootstrap.jar
ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

COPY --from=thin-jre /thinjre $JAVA_HOME
COPY $FROM_JAR /app/app.jar

EXPOSE $PORT
ENTRYPOINT [ "/jre/bin/java", "-jar", "/app/app.jar" ]