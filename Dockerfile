FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} gtps.jar

ENTRYPOINT ["java", "-jar","/gtps.jar"]

EXPOSE 9001