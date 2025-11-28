FROM openjdk:21-jdk
VOLUME /tmp
ADD target/bank-proxy-service.jar bank-proxy-service.jar
ENTRYPOINT [ "java", "-jar", "/bank-proxy-service.jar" ]
