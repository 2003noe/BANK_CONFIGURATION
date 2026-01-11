package esante.notification_service.service;


import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsService {
    
    
    
    /**
     * Envoie un SMS
     * @param phoneNumber Numéro de téléphone
     * @param message Contenu du SMS
     * @return true si envoyé avec succès
     */
    public boolean sendSms(String phoneNumber, String message) {
        try {
            // SIMULATION - À remplacer par l'intégration réelle d'un fournisseur SMS
            // Exemples de fournisseurs au Cameroun :
            // - Africa's Talking
            // - Twilio
            // - Nexah (Orange Cameroun)
            
            log.info("Simulation d'envoi SMS au numéro : {}", phoneNumber);
            log.info("Message : {}", message);
            
            // Code réel à implémenter selon le fournisseur choisi
            // Exemple avec Africa's Talking ou Twilio
            /*
            String url = smsApiUrl + "?apiKey=" + smsApiKey
                + "&to=" + phoneNumber
                + "&message=" + URLEncoder.encode(message, "UTF-8");
            
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getStatusCode().is2xxSuccessful();
            */
            
            // Pour l'instant, on simule un envoi réussi
            return true;
            
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi du SMS au {}: {}", phoneNumber, e.getMessage());
            return false;
        }
    }
    
    /**
     * Valide le format du numéro de téléphone camerounais
     * @param phoneNumber Numéro à valider
     * @return true si valide
     */
    public boolean isValidPhoneNumber(String phoneNumber) {
        // Format camerounais : +237XXXXXXXXX ou 237XXXXXXXXX ou 6XXXXXXXX
        return phoneNumber != null &&(phoneNumber.matches("^\\+?237[26][0-9]{8}$") || 
                phoneNumber.matches("^[26][0-9]{8}$"));
    }
}
