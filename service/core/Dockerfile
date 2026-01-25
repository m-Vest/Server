FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY build/libs/*.jar mvest-core.jar
EXPOSE 9000
CMD ["java", "-jar", "mvest-core.jar"]