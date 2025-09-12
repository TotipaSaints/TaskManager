FROM openjdk:17-jdk

WORKDIR /app

COPY build/libs/*.jar /app/app.jar
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh

EXPOSE 8080

ENTRYPOINT ["/app/wait-for-it.sh", "postgres_db:5432", "--timeout=60", "--strict", "--", "java", "-jar", "/app/app.jar"]