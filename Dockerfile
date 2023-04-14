FROM openjdk:18-slim

# RUN apt-get update && apt-get install -y postgresql-client

COPY build/libs/blogapp-0.0.1-SNAPSHOT.jar  /app.jar

CMD ["java", "-jar", "/app.jar"]