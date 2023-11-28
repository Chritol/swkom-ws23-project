package org.openapitools.services;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.helpers.FileHelper;
import org.openapitools.model.DocumentDTO;
import org.openapitools.model.okresponse.GetDocument200Response;
import org.openapitools.model.okresponse.GetDocuments200Response;
import org.openapitools.persistence.entities.DocumentsDocument;
import org.openapitools.persistence.entities.DocumentsStoragepath;
import org.openapitools.persistence.repositories.DocumentsDocumentRepository;
import org.openapitools.remapper.DocumentMapper;
import org.openapitools.services.rabbitMq.RabbitMqSender;
import org.openapitools.services.rabbitMq.RabbitMqSenderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final DocumentsDocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final MinioClient minioClient;

    private final RabbitMqSender rabbitMQSender;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Autowired
    public DocumentServiceImpl(DocumentsDocumentRepository documentRepository, DocumentMapper documentMapper, MinioClient minioClient, RabbitMqSenderImpl rabbitMQSender) {
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
        this.minioClient = minioClient;
        this.rabbitMQSender = rabbitMQSender;
    }

    @Override
    public GetDocument200Response getDocument(Integer id, Integer page, Boolean fullPerms) {
        return null;
    }

    public boolean uploadDocument(DocumentDTO documentDTO, MultipartFile document) {
        // Convert DTO to entity
        DocumentsDocument entity = documentMapper.toEntity(documentDTO);

        String minioObjectName = "";
        try {
            minioObjectName = getMinioObjectName(document.getOriginalFilename());
        } catch (InvalidParameterException e) {
            log.error(e.getMessage(), e);
            return false;
        }

        try {
            storeInMinIO(document, minioObjectName);
        } catch (ServerException | InsufficientDataException | ErrorResponseException | IOException | NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException | InternalException e) {
            log.error(e.getMessage(), e);
            return false;
        }

        String filePath = bucketName + "/" + minioObjectName;
        //entity.setStoragePath(getDocumentStoragePath(filePath, document.getOriginalFilename()));


        //rabbitMQSender.sendToDocumentInQueue(entity.getStoragePath().getPath());
        rabbitMQSender.sendToDocumentInQueue(getDocumentStoragePath(filePath, document.getOriginalFilename()).getPath());
        documentDTO.getOriginalFileName().ifPresent(System.out::println);
        return true;
    }

    @Override
    public ResponseEntity<GetDocuments200Response> getDocuments(Integer page, Integer pageSize, String query, String ordering, List<Integer> tagsIdAll, Integer documentTypeId, Integer storagePathIdIn, Integer correspondentId, Boolean truncateContent) {
        return null;
    }

    private void storeInMinIO(MultipartFile file, Integer id) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(id.toString())
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );
        log.info("Stored object in minIO: " + id);
    }

    private static String getMinioObjectName(String fileName) throws InvalidParameterException {
        return FileHelper.getCurrentDateTimeInMilliseconds()
                + "_"
                + FileHelper.getFileName(fileName)
                + "."
                + FileHelper.getFileExtension(fileName);
    }

    private static DocumentsStoragepath getDocumentStoragePath(String filePath, String fileName) {
        DocumentsStoragepath storagePath = new DocumentsStoragepath();
        storagePath.setPath(filePath);
        storagePath.setName(fileName);
        storagePath.setMatch("");
        storagePath.setMatchingAlgorithm(0);
        storagePath.setIsInsensitive(false);

        return storagePath;
    }
}
