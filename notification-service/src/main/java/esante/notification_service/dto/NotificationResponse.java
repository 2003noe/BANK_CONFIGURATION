package esante.notification_service.dto;

import java.time.LocalDateTime;

import esante.notification_service.model.NotificationStatus;
import esante.notification_service.model.NotificationType;

public class NotificationResponse {
    
    private Long id;
    private String recipient;
    private String subject;
    private String message;
    private NotificationType type;
    private NotificationStatus status;
    private Long patientId;
    private String serviceSource;
    private Integer retryCount;
    private String errorMessage;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
    
    // Constructeurs
    public NotificationResponse() {
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
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
    
    public NotificationStatus getStatus() {
        return status;
    }
    
    public Long getPatientId() {
        return patientId;
    }
    
    public String getServiceSource() {
        return serviceSource;
    }
    
    public Integer getRetryCount() {
        return retryCount;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getSentAt() {
        return sentAt;
    }
    
    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public void setStatus(NotificationStatus status) {
        this.status = status;
    }
    
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
    
    public void setServiceSource(String serviceSource) {
        this.serviceSource = serviceSource;
    }
    
    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}