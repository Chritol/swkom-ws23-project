package org.openapitools.paperlessocr.worker;

import org.springframework.amqp.core.Message;

public interface OcrServiceWorker {
    void processDocumentUpload(String pdfFileName);
}
