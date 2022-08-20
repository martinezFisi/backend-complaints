FROM eclipse-temurin:17.0.4_8-jdk
ADD target/complaints-0.0.1.jar /complaints-app/complaints-0.0.1.jar
WORKDIR /complaints-app
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000
EXPOSE 8282
EXPOSE 8000
ENTRYPOINT java -jar complaints-0.0.1.jar