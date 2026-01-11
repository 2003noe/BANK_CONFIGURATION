package esante.notification_service.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import esante.notification_service.dto.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Envoie des messages vers RabbitMQ (optionnel, pour tester)
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {
    
    private final RabbitTemplate rabbitTemplate;
    
    @Value("${notification.exchange.name}")
    private String exchangeName;
    
    @Value("${notification.routing.key}")
    private String routingKey;
    
    /**
     * Envoyer une notification vers RabbitMQ
     */
    public void sendNotification(NotificationRequest request) {
        log.info("📤 Envoi message vers RabbitMQ pour : {}", request.getRecipient());
        
        try {
            rabbitTemplate.convertAndSend(exchangeName, routingKey, request);
            log.info("✅ Message envoyé à RabbitMQ avec succès");
            
        } catch (Exception e) {
            log.error("❌ Erreur lors de l'envoi vers RabbitMQ : {}", e.getMessage());
        }
    }
}