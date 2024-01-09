package org.openapitools.paperlessocr;

import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openapitools.paperlessocr.persistence.entities.DocumentsDocument;
import org.openapitools.paperlessocr.persistence.repositories.DocumentsDocumentRepository;
import org.openapitools.paperlessocr.services.OcrServiceImpl;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class PaperlessOcrApplicationTests {

}
