package esante.notification_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import esante.notification_service.dto.NotificationRequest;
import esante.notification_service.dto.NotificationResponse;
import esante.notification_service.model.Notification;
import esante.notification_service.model.NotificationStatus;
import esante.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;
    private final SmsService smsService;
    
    private static final int MAX_RETRY_COUNT = 3;
    
    /**
     * Crée et envoie une nouvelle notification
     */
    @Transactional
    public NotificationResponse createAndSendNotification(NotificationRequest request) {
        log.info("Création d'une nouvelle notification pour : {}", request.getRecipient());
        
        // Créer l'entité notification
        Notification notification = new Notification();
        notification.setRecipient((String) request.getRecipient());
        notification.setSubject(request.getSubject());
        notification.setMessage(request.getMessage());
        notification.setType(request.getType());
        notification.setStatus(NotificationStatus.PENDING);
        notification.setPatientId(request.getPatientId());
        notification.setServiceSource(request.getServiceSource());
        notification.setRetryCount(0);
        
        // Sauvegarder la notification
        notification = notificationRepository.save(notification);
        
        // Tenter d'envoyer la notification
        boolean sent = sendNotification(notification);
        
        if (sent) {
            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());
            log.info("Notification {} envoyée avec succès", notification.getId());
        } else {
            notification.setStatus(NotificationStatus.FAILED);
            notification.setErrorMessage("Échec de l'envoi");
            log.error("Échec de l'envoi de la notification {}", notification.getId());
        }
        
        notification = notificationRepository.save(notification);
        
        return convertToResponse(notification);
    }
    
    /**
     * Envoie une notification selon son type
     */
    private boolean sendNotification(Notification notification) {
        boolean emailSent = false;
        boolean smsSent = false;
        
        switch (notification.getType()) {
            case EMAIL -> {
                emailSent = emailService.sendEmail(
                        notification.getRecipient(),
                        notification.getSubject(),
                        notification.getMessage()
                );
                return emailSent;
            }
                
            case SMS -> {
                smsSent = smsService.sendSms(
                        notification.getRecipient(),
                        notification.getSubject() + "\n" + notification.getMessage()
                );
                return smsSent;
            }
                
            case BOTH -> {
                // Envoyer à la fois email et SMS
                emailSent = emailService.sendEmail(
                        notification.getRecipient(),
                        notification.getSubject(),
                        notification.getMessage()
                );
                
                smsSent = smsService.sendSms(
                        notification.getRecipient(),
                        notification.getSubject() + "\n" + notification.getMessage()
                );
                
                // Considéré comme succès si au moins un des deux est envoyé
                return emailSent || smsSent;
            }
                
            default -> {
                return false;
            }
        }
    }
    
    /**
     * Récupère toutes les notifications
     */
    public List<NotificationResponse> getAllNotifications() {
        return notificationRepository.findAll()
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }
    
    /**
     * Récupère une notification par son ID
     */
    public NotificationResponse getNotificationById(Long id) {
        Notification notification = notificationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Notification non trouvée avec l'ID : " + id));
        return convertToResponse(notification);
    }
    
    /**
     * Récupère les notifications par statut
     */
    public List<NotificationResponse> getNotificationsByStatus(NotificationStatus status) {
        return notificationRepository.findByStatus(status)
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }
    
    /**
     * Récupère les notifications d'un patient
     */
    public List<NotificationResponse> getNotificationsByPatient(Long patientId) {
        return notificationRepository.findByPatientId(patientId)
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }
    
    /**
     * Réessaie d'envoyer les notifications échouées
     */
    @Transactional
    public void retryFailedNotifications() {
        log.info("Réessai des notifications échouées...");
        
        List<Notification> failedNotifications = notificationRepository
            .findByStatusAndRetryCountLessThan(NotificationStatus.FAILED, MAX_RETRY_COUNT);
        
        for (Notification notification : failedNotifications) {
            notification.setStatus(NotificationStatus.RETRYING);
            notification.setRetryCount(notification.getRetryCount() + 1);
            
            boolean sent = sendNotification(notification);
            
            if (sent) {
                notification.setStatus(NotificationStatus.SENT);
                notification.setSentAt(LocalDateTime.now());
                notification.setErrorMessage(null);
                log.info("Notification {} réenvoyée avec succès", notification.getId());
            } else {
                notification.setStatus(NotificationStatus.FAILED);
                notification.setErrorMessage("Échec après " + notification.getRetryCount() + " tentatives");
                log.error("Échec du renvoi de la notification {}", notification.getId());
            }
            
            notificationRepository.save(notification);
        }
    }
    
    /**
     * Supprime une notification
     */
    @Transactional
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
        log.info("Notification {} supprimée", id);
    }
    
    /**
     * Obtient les statistiques des notifications
     */
    public NotificationStats getStatistics() {
        NotificationStats stats = new NotificationStats();
        stats.setTotal(notificationRepository.count());
        stats.setSent(notificationRepository.countByStatus(NotificationStatus.SENT));
        stats.setFailed(notificationRepository.countByStatus(NotificationStatus.FAILED));
        stats.setPending(notificationRepository.countByStatus(NotificationStatus.PENDING));
        return stats;
    }
    
    /**
     * Convertit une entité Notification en NotificationResponse
     */
    private NotificationResponse convertToResponse(Notification notification) {
        NotificationResponse response = new NotificationResponse();
        response.setId(notification.getId());
        response.setRecipient(notification.getRecipient());
        response.setSubject(notification.getSubject());
        response.setMessage(notification.getMessage());
        response.setType(notification.getType());
        response.setStatus(notification.getStatus());
        response.setPatientId(notification.getPatientId());
        response.setServiceSource(notification.getServiceSource());
        response.setRetryCount(notification.getRetryCount());
        response.setErrorMessage(notification.getErrorMessage());
        response.setCreatedAt(notification.getCreatedAt());
        response.setSentAt(notification.getSentAt());
        return response;
    }
    
    /**
     * Classe interne pour les statistiques
     */
    public static class NotificationStats {
        private Long total;
        private Long sent;
        private Long failed;
        private Long pending;
        
        // Getters et Setters
        public Long getTotal() {
            return total;
        }
        
        public void setTotal(Long total) {
            this.total = total;
        }
        
        public Long getSent() {
            return sent;
        }
        
        public void setSent(Long sent) {
            this.sent = sent;
        }
        
        public Long getFailed() {
            return failed;
        }
        
        public void setFailed(Long failed) {
            this.failed = failed;
        }
        
        public Long getPending() {
            return pending;
        }
        
        public void setPending(Long pending) {
            this.pending = pending;
        }
    }
}