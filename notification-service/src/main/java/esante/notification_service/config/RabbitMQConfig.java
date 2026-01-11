package esante.notification_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    @Value("${notification.queue.name}")
    private String queueName;
    
    @Value("${notification.exchange.name}")
    private String exchangeName;
    
    @Value("${notification.routing.key}")
    private String routingKey;
    
    /**
     * Créer la Queue (File d'attente)
     */
    @Bean
    public Queue notificationQueue() {
        return new Queue(queueName, true); // durable = true (survit au redémarrage)
    }
    
    /**
     * Créer l'Exchange (Point de distribution)
     */
    @Bean
    public TopicExchange notificationExchange() {
        return new TopicExchange(exchangeName);
    }
    
    /**
     * Lier la Queue à l'Exchange avec une Routing Key
     */
    @Bean
    public Binding binding(Queue notificationQueue, TopicExchange notificationExchange) {
        return BindingBuilder
                .bind(notificationQueue)
                .to(notificationExchange)
                .with(routingKey);
    }
    
    /**
     * Convertisseur de messages (JSON)
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    /**
     * RabbitTemplate avec convertisseur JSON
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}