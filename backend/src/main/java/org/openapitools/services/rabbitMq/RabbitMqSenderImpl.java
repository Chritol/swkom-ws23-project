package org.openapitools.services.rabbitMq;

import lombok.extern.slf4j.Slf4j;
import org.openapitools.configuration.RabbitMQConfiguration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMqSenderImpl implements  RabbitMqSender{
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMqSenderImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    @Override
    public void sendToDocumentInQueue(String message) {
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.DOCUMENT_IN_QUEUE, message);
        log.info("Send to rabbitMq: " + message);
    }
}
