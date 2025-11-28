FROM openjdk:21-jdk
VOLUME /tmp
ADD target/banking-registry-service.jar banking-registry-service.jar
ENTRYPOINT [ "java", "-jar", "/banking-registry-service.jar" ]
