package esante.notification_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
        System.out.println("==============================================");
        System.out.println("Service de Notification eSantéCmr démarré !");
        System.out.println("Port : 8083");
        System.out.println("API disponible sur : http://localhost:8083/api/notifications");
        System.out.println("==============================================");
    }
}