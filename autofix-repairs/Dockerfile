FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} autofix-repairs.jar
ENTRYPOINT ["java", "-jar", "/autofix-repairs.jar"]