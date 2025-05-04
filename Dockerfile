FROM gradle:jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle clean build --refresh-dependencies


FROM eclipse-temurin:17-jre-alpine
COPY --from=build /app/build/libs/application*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]