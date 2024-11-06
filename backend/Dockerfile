FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build.gradle settings.gradle /app/

COPY gradlew /app/gradlew
COPY gradle /app/gradle

COPY src /app/src

RUN chmod +x gradlew

RUN ./gradlew bootJar

EXPOSE 8090

CMD ["java", "-jar", "build/libs/PrestaBancoBackend-0.0.1-SNAPSHOT.jar"]