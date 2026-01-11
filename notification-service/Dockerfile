FROM openjdk:21-jdk
VOLUME /tmp
ADD target/notification-service.jar notification-service.jar
ENTRYPOINT [ "java", "-jar", "/notification-service.jar" ]