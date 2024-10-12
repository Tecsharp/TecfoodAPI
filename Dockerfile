FROM openjdk:17-jdk-slim

WORKDIR /app

COPY tecfoodapi-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8086

CMD ["java", "-jar", "app.jar"]
