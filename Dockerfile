FROM openjdk:17

ADD target/gtps.jar gtps.jar

ENTRYPOINT ["java", "-jar","/gtps.jar"]