package org.openapitools.paperlessocr;

import io.minio.MinioClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openapitools.paperlessocr.persistence.entities.DocumentsDocument;
import org.openapitools.paperlessocr.persistence.repositories.DocumentsDocumentRepository;
import org.openapitools.paperlessocr.services.OcrService;
import org.openapitools.paperlessocr.services.OcrServiceImpl;

import java.io.ByteArrayInputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OCRServiceTest {

    @Mock
    private MinioClient minioClient;

    @Mock
    private DocumentsDocumentRepository documentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testPerformOcr() {
        // TODO Auto-generated method stub
    }

}
