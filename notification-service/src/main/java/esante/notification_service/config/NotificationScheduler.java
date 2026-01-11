package esante.notification_service.config;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import esante.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationScheduler {
    
    private final NotificationService notificationService;
    
    /**
     * Tâche planifiée pour réessayer les notifications échouées
     * S'exécute toutes les 30 minutes
     */
    @Scheduled(fixedRate = 1800000) // 30 minutes en millisecondes
    public void retryFailedNotifications() {
        log.info("Exécution de la tâche planifiée : renvoi des notifications échouées");
        try {
            notificationService.retryFailedNotifications();
        } catch (Exception e) {
            log.error("Erreur lors de l'exécution de la tâche planifiée : {}", e.getMessage());
        }
    }
}