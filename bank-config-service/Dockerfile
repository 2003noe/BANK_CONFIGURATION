FROM openjdk:21-jdk
VOLUME /tmp
ADD target/bank-config-service.jar bank-config-service.jar
ENTRYPOINT [ "java", "-jar", "/bank-config-service.jar" ]
