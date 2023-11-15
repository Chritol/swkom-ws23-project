package org.openapitools.services.rabbitMq;

public interface RabbitMqSender {
    void sendToDocumentInQueue(String message);
}
