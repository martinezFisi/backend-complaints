FROM eclipse-temurin:17.0.4_8-jdk
ADD target/complaints-0.0.1.jar /complaints-app/complaints-0.0.1.jar
WORKDIR /complaints-app
EXPOSE 8282
ENTRYPOINT java -jar complaints-0.0.1.jar