package esante.notification_service.model;

public enum NotificationStatus {
    PENDING,    // En attente
    SENT,       // Envoyée
    FAILED,     // Échouée
    RETRYING    // En cours de renvoi
}
