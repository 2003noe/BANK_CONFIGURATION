package esante.notification_service.rabbitmq;

import esante.notification_service.dto.NotificationRequest;
import esante.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Écoute les messages RabbitMQ et les traite
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationListener {
    
    private final NotificationService notificationService;
    
    /**
     * Méthode appelée automatiquement quand un message arrive
     */
    @RabbitListener(queues = "${notification.queue.name}")
    public void receiveNotification(NotificationRequest request) {
        log.info("📬 Message RabbitMQ reçu pour : {}", request.getRecipient());
        
        try {
            // Traiter la notification
            notificationService.createAndSendNotification(request);
            log.info("✅ Notification traitée avec succès via RabbitMQ");
            
        } catch (Exception e) {
            log.error("❌ Erreur lors du traitement du message RabbitMQ : {}", e.getMessage());
        }
    }
}