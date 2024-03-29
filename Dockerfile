FROM --platform=linux/amd64 eclipse-temurin:21

WORKDIR /app
ADD build/libs/cinema-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/application.properties /app/application.properties
CMD java -jar -Dspring.config.location=classpath:/application.properties,file:/app/application.properties app.jar
