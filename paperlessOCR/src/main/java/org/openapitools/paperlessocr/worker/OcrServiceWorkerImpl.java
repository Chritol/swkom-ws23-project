package org.openapitools.paperlessocr.worker;

import lombok.extern.slf4j.Slf4j;
import org.openapitools.paperlessocr.constants.RabbitMqConstants;
import org.openapitools.paperlessocr.services.OcrServiceImpl;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class OcrServiceWorkerImpl implements OcrServiceWorker {
    private final OcrServiceImpl ocrServiceImpl;

    @Bean
    public Queue documentUploadQueue() {
        // Declare the queue with the desired properties
        return new Queue(RabbitMqConstants.DOCUMENT_IN_QUEUE, false, false, false);
    }

    @Autowired
    public OcrServiceWorkerImpl(OcrServiceImpl ocrServiceImpl) {
        this.ocrServiceImpl = ocrServiceImpl;
    }

    @RabbitListener(queues = RabbitMqConstants.DOCUMENT_IN_QUEUE)
    public void processDocumentUpload(String message) {
        // Fetch the original PDF-document from MinIO
        // Perform OCR recognition
        // Store the text result in PostgreSQL
        Integer pd_id = 0;
        log.info(message);
        try {
            String pattern = "/(\\d+)-";

            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(message);

            if (m.find()) {
                pd_id = Integer.parseInt(m.group(1));
            } else {
                log.warn("No match found");
            }

        } catch (Exception e) {
            log.warn(e.getMessage());
        }

        log.info("Receives from queue: " + pd_id);
        ocrServiceImpl.performOcr(pd_id);
    }
}
