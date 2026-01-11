package esante.notification_service.dto;

import esante.notification_service.model.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NotificationRequest {
    
    @NotBlank(message = "Le destinataire est obligatoire")
    private String recipient;
    
    @NotBlank(message = "Le sujet est obligatoire")
    private String subject;
    
    @NotBlank(message = "Le message est obligatoire")
    private String message;
    
    @NotNull(message = "Le type de notification est obligatoire")
    private NotificationType type;
    
    private Long patientId;
    
    private String serviceSource;
    
    // Constructeurs
    public NotificationRequest() {
    }
    
    public NotificationRequest(String recipient, String subject, String message, NotificationType type, Long patientId, String serviceSource) {
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
        this.type = type;
        this.patientId = patientId;
        this.serviceSource = serviceSource;
    }
    
    // Getters
    public String getRecipient() {
        return recipient;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public String getMessage() {
        return message;
    }
    
    public NotificationType getType() {
        return type;
    }
    
    public Long getPatientId() {
        return patientId;
    }
    
    public String getServiceSource() {
        return serviceSource;
    }
    
    // Setters
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public void setType(NotificationType type) {
        this.type = type;
    }
    
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
    
    public void setServiceSource(String serviceSource) {
        this.serviceSource = serviceSource;
    }
}