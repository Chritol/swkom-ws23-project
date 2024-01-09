package org.openapitools.paperlessocr.helper;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.openapitools.paperlessocr.persistence.elasticsearch.entities.ElasticDocumentDocument;
import org.openapitools.paperlessocr.persistence.entities.DocumentsDocument;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
public class OcrHelper {
    public static void deleteTempFile(File tempPdfFile) {
        try {
            Files.deleteIfExists(tempPdfFile.toPath());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Nullable
    public static File createTempFile() {
        File tempPdfFile;
        try {
            tempPdfFile = File.createTempFile("input", ".pdf");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
        return tempPdfFile;
    }

    public static ElasticDocumentDocument convertToElasticDocumentDocument(DocumentsDocument document) {
        return ElasticDocumentDocument.builder()
                .id(document.getId())
                .title(document.getTitle())
                .content(document.getContent())
                .build();
    }
}
