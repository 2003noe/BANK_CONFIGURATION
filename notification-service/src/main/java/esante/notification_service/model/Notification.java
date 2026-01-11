package esante.notification_service.model;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String recipient; // Email ou numéro de téléphone
    
    @Column(nullable = false)
    private String subject; // Objet du message
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message; // Contenu du message
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type; // EMAIL, SMS, BOTH
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status; // PENDING, SENT, FAILED, RETRYING
    
    @Column(name = "patient_id")
    private Long patientId; // ID du patient concerné (optionnel)
    
    @Column(name = "service_source")
    private String serviceSource; // Service qui a demandé la notification
    
    @Column(name = "retry_count")
    private Integer retryCount = 0; // Nombre de tentatives
    
    @Column(name = "error_message")
    private String errorMessage; // Message d'erreur en cas d'échec
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "sent_at")
    private LocalDateTime sentAt; // Date et heure d'envoi
}
