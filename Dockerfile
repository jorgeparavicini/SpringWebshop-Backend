FROM gradle:7.5-jdk17-alpine AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY build.gradle.kts /workspace
COPY src /workspace/src
COPY upload /workspace/upload
RUN gradle build

FROM openjdk:17-slim
COPY --from=build /workspace/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
