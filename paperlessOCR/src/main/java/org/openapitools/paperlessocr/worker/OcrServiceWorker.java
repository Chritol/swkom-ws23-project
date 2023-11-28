package org.openapitools.paperlessocr.worker;

public interface OcrServiceWorker {
    void processDocumentUpload(String pdfFileName);
}
