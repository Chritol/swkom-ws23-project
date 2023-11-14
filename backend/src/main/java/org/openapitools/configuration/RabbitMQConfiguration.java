package org.openapitools.configuration;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    @Value("${rabbitmq.endpoint}")
    private String endpoint;
    @Value("${rabbitmq.username}")
    private String username;
    @Value("${rabbitmq.password}")
    private String password;

    public static final String DOCUMENT_IN_QUEUE = "DOCUMENT_IN_QUEUE";
    public static final String DOCUMENT_OUT_QUEUE = "DOCUMENT_OUT_QUEUE";

    @Bean
    public Queue documentInQueue() {
        return new Queue(DOCUMENT_IN_QUEUE, false);
    }

    @Bean
    public Queue documemtOutQueue() {
        return new Queue(DOCUMENT_OUT_QUEUE, false);
    }


    @Bean
    public ConnectionFactory rabbitMQConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(endpoint);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitMQConnectionFactory());
        rabbitTemplate.setDefaultReceiveQueue(DOCUMENT_IN_QUEUE);
        return rabbitTemplate;
    }
}
