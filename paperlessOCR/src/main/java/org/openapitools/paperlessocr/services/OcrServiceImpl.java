package org.openapitools.paperlessocr.services;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openapitools.paperlessocr.persistence.entities.DocumentsDocument;
import org.openapitools.paperlessocr.persistence.repositories.DocumentsDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class OcrServiceImpl implements OcrService {
    private final MinioClient minioClient;
    private final DocumentsDocumentRepository documentRepository;

    @Autowired
    public OcrServiceImpl(MinioClient minioClient, DocumentsDocumentRepository documentRepository) {
        this.minioClient = minioClient;
        this.documentRepository = documentRepository;
    }

    @Value("${minio.bucketName}")
    private String bucketName;

    public void performOcr(Integer id) throws NoSuchElementException {
        DocumentsDocument document = documentRepository.findById(id).orElseThrow();

        String pdfFileName = document.getTitle();

        byte[] pdfData = getPdfData(pdfFileName);
        String result = performOcrOnPdf(pdfData);

        document.setContent(result);

        documentRepository.save(document);


        if(result.isEmpty())
            log.info("SAD:(");
        log.info(result);
    }

    private String[] extractBucketAndFileName(String pdfFileName) {
        // Assuming the format is "bucketName/path/to/file.pdf"
        String[] parts = pdfFileName.split("/", 2);

        if (parts.length > 1) {
            return parts;
        } else {
            return null;
        }
    }


    private byte[] getPdfData(String pdfFilePath) {
        if(!pdfFilePath.endsWith(".pdf")) {
            log.info("Only pdf files are currently supported for OCR. File not processable: " + pdfFilePath);
            return null;
        }

        String[] bucketAndFileName = extractBucketAndFileName(pdfFilePath);
        if(bucketAndFileName == null) return null;

        String bucketName = bucketAndFileName[0];
        String fileName = bucketAndFileName[1];

        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build())) {

            return stream.readAllBytes();
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    @NotNull
    private String performOcrOnPdf(byte[] pdfData)  {
        if(pdfData == null) return "";

        File tempPdfFile = createTempFile();
        if (tempPdfFile == null) return "";

        try (FileOutputStream fos = new FileOutputStream(tempPdfFile)) {
            fos.write(pdfData);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return "";
        }

        try {
            ITesseract tesseract = new Tesseract();
            return tesseract.doOCR(tempPdfFile);
        } catch (TesseractException e) {
            log.error(e.getMessage(), e);
            return "";
        } finally {
            // Delete the temporary file after OCR is performed
            deleteTempFile(tempPdfFile);
        }
    }

    private static void deleteTempFile(File tempPdfFile) {
        try {
            Files.deleteIfExists(tempPdfFile.toPath());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Nullable
    private static File createTempFile() {
        File tempPdfFile;
        try {
            tempPdfFile = File.createTempFile("input", ".pdf");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
        return tempPdfFile;
    }
}
