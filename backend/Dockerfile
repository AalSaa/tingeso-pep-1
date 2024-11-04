FROM openjdk:17

WORKDIR /app

COPY build/libs/*.jar app.jar

EXPOSE 8080

CMD ["sh", "-c", "sleep 10 && java -jar app.jar"]