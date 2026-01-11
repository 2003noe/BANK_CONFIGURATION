package esante.notification_service.repository;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import esante.notification_service.model.Notification;
import esante.notification_service.model.NotificationStatus;
import esante.notification_service.model.NotificationType;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    // Trouver toutes les notifications par statut
    List<Notification> findByStatus(NotificationStatus status);
    
    // Trouver toutes les notifications par type
    List<Notification> findByType(NotificationType type);
    
    // Trouver toutes les notifications d'un patient
    List<Notification> findByPatientId(Long patientId);
    
    // Trouver toutes les notifications par service source
    List<Notification> findByServiceSource(String serviceSource);
    
    // Trouver les notifications échouées avec moins de X tentatives
    List<Notification> findByStatusAndRetryCountLessThan(NotificationStatus status, Integer maxRetries);
    
    // Trouver les notifications créées après une certaine date
    List<Notification> findByCreatedAtAfter(LocalDateTime date);
    
    // Compter les notifications par statut
    Long countByStatus(NotificationStatus status);
}
