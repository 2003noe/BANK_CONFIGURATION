package esante.notification_service.controller;

import esante.notification_service.dto.NotificationRequest;
import esante.notification_service.dto.NotificationResponse;
import esante.notification_service.model.NotificationStatus;
import esante.notification_service.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class NotificationController {
    
    private final NotificationService notificationService;
    
    /**
     * Point de test pour vérifier que l'API fonctionne
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Service de notification est opérationnel !");
    }
    
    /**
     * Créer et envoyer une nouvelle notification
     * POST /api/notifications
     */
    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(
            @Valid @RequestBody NotificationRequest request) {
        log.info("Réception d'une demande de notification pour : {}", request.getRecipient());
        
        try {
            NotificationResponse response = notificationService.createAndSendNotification(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Erreur lors de la création de la notification : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Récupérer toutes les notifications
     * GET /api/notifications
     */
    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getAllNotifications() {
        log.info("Récupération de toutes les notifications");
        List<NotificationResponse> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }
    
    /**
     * Récupérer une notification par son ID
     * GET /api/notifications/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getNotificationById(@PathVariable Long id) {
        log.info("Récupération de la notification avec l'ID : {}", id);
        
        try {
            NotificationResponse notification = notificationService.getNotificationById(id);
            return ResponseEntity.ok(notification);
        } catch (RuntimeException e) {
            log.error("Notification non trouvée : {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Récupérer les notifications par statut
     * GET /api/notifications/status/{status}
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<NotificationResponse>> getNotificationsByStatus(
            @PathVariable NotificationStatus status) {
        log.info("Récupération des notifications avec le statut : {}", status);
        List<NotificationResponse> notifications = notificationService.getNotificationsByStatus(status);
        return ResponseEntity.ok(notifications);
    }
    
    /**
     * Récupérer les notifications d'un patient
     * GET /api/notifications/patient/{patientId}
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<NotificationResponse>> getNotificationsByPatient(
            @PathVariable Long patientId) {
        log.info("Récupération des notifications du patient : {}", patientId);
        List<NotificationResponse> notifications = notificationService.getNotificationsByPatient(patientId);
        return ResponseEntity.ok(notifications);
    }
    
    /**
     * Réessayer d'envoyer les notifications échouées
     * POST /api/notifications/retry
     */
    @PostMapping("/retry")
    public ResponseEntity<String> retryFailedNotifications() {
        log.info("Tentative de renvoi des notifications échouées");
        
        try {
            notificationService.retryFailedNotifications();
            return ResponseEntity.ok("Renvoi des notifications échouées effectué");
        } catch (Exception e) {
            log.error("Erreur lors du renvoi : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors du renvoi des notifications");
        }
    }
    
    /**
     * Supprimer une notification
     * DELETE /api/notifications/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long id) {
        log.info("Suppression de la notification : {}", id);
        
        try {
            notificationService.deleteNotification(id);
            return ResponseEntity.ok("Notification supprimée avec succès");
        } catch (Exception e) {
            log.error("Erreur lors de la suppression : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la suppression");
        }
    }
    
    /**
     * Obtenir les statistiques des notifications
     * GET /api/notifications/stats
     */
    @GetMapping("/stats")
    public ResponseEntity<NotificationService.NotificationStats> getStatistics() {
        log.info("Récupération des statistiques");
        NotificationService.NotificationStats stats = notificationService.getStatistics();
        return ResponseEntity.ok(stats);
    }
    /**
 * Tester RabbitMQ - Envoyer un message via RabbitMQ
 * POST /api/notifications/rabbitmq
 */
@PostMapping("/rabbitmq")
public ResponseEntity<String> sendViaRabbitMQ(@Valid @RequestBody NotificationRequest request) {
    log.info("Envoi d'une notification via RabbitMQ pour : {}", request.getRecipient());
    
    try {
        // Injecter NotificationProducer
        notificationProducer.sendNotification(request);
        return ResponseEntity.ok("Message envoyé à RabbitMQ avec succès !");
    } catch (Exception e) {
        log.error("Erreur : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur lors de l'envoi à RabbitMQ");
    }
}
    private final esante.notification_service.rabbitmq.NotificationProducer notificationProducer;
}