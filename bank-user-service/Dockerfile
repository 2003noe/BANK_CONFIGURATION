FROM openjdk:21-jdk
VOLUME /tmp
ADD target/bank-user-service.jar bank-user-service.jar
ENTRYPOINT [ "java", "-jar", "/bank-user-service.jar" ]
