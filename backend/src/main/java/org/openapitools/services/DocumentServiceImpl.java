package org.openapitools.services;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.helpers.FileHelper;
import org.openapitools.model.DocumentDTO;
import org.openapitools.model.okresponse.GetDocument200Response;
import org.openapitools.model.okresponse.GetDocuments200Response;
import org.openapitools.model.okresponse.GetDocuments200ResponseResultsInner;
import org.openapitools.persistence.entities.DocumentsCorrespondent;
import org.openapitools.persistence.entities.DocumentsDocument;
import org.openapitools.persistence.entities.DocumentsDocumenttype;
import org.openapitools.persistence.entities.DocumentsStoragepath;
import org.openapitools.persistence.repositories.DocumentsCorrespondentRepository;
import org.openapitools.persistence.repositories.DocumentsDocumentRepository;
import org.openapitools.persistence.repositories.DocumentsDocumenttypeRepository;
import org.openapitools.persistence.repositories.DocumentsStoragepathRepository;
import org.openapitools.remapper.DocumentMapper;
import org.openapitools.services.rabbitMq.RabbitMqSender;
import org.openapitools.services.rabbitMq.RabbitMqSenderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final DocumentsDocumentRepository documentRepository;

    private final DocumentsCorrespondentRepository correspondentRepository;
    private final DocumentsDocumenttypeRepository doctypeRepository;
    private final DocumentsStoragepathRepository storagepathRepository;


    private final MinioClient minioClient;

    private final RabbitMqSender rabbitMQSender;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Autowired
    public DocumentServiceImpl(DocumentsDocumentRepository documentRepository, DocumentsCorrespondentRepository correspondentRepository, DocumentsDocumenttypeRepository doctypeRepository, DocumentsStoragepathRepository storagepathRepository, MinioClient minioClient, RabbitMqSenderImpl rabbitMQSender) {
        this.documentRepository = documentRepository;
        this.correspondentRepository = correspondentRepository;
        this.doctypeRepository = doctypeRepository;
        this.storagepathRepository = storagepathRepository;
        this.minioClient = minioClient;
        this.rabbitMQSender = rabbitMQSender;
    }

    @Override
    public GetDocument200Response getDocument(Integer id, Integer page, Boolean fullPerms) {
        DocumentsDocument entity = documentRepository.getReferenceById(id);
        GetDocument200Response response = DocumentMapper.toOkRes(entity);

        return response;
    }

    private static String removeUnwantedChars(String text){
        return text.replaceAll("[^(\\x00-\\xFF)]+(?:$|\\s*)", "").trim();
    }


    /**
     * Uploads a document to mino
     * @param documentDTO
     * @param document
     * @return Uri
     */
    public String uploadDocument(DocumentDTO documentDTO, MultipartFile document) {
        // Convert DTO to entity
        DocumentsDocument entity = DocumentMapper.toEntity(documentDTO, correspondentRepository, doctypeRepository, storagepathRepository);

        String minioObjectName = "";
        try {
            minioObjectName = getMinioObjectName(document.getOriginalFilename());
        } catch (InvalidParameterException e) {
            log.error(e.getMessage(), e);
            return null;
        }

        //Everything I need to set before saving! :3
        entity.setCreated(OffsetDateTime.now());
        entity.setModified(OffsetDateTime.now());
        entity.setAdded(OffsetDateTime.now());
        entity.setStoragePath(getDocumentStoragePath("",""));

        storagepathRepository.save(entity.getStoragePath());
        Integer id = documentRepository.save(entity).getId();

        log.info(id+"");

        String filePath = bucketName + "/" + id.toString() + "-" + minioObjectName;

        try {
            storeInMinIO(document, filePath);
        } catch (ServerException | InsufficientDataException | ErrorResponseException | IOException | NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException | InternalException e) {
            log.error(e.getMessage(), e);
            return null;
        }


        //Everything I need to set before saving! :3
        entity.setCreated(OffsetDateTime.now());
        entity.setModified(OffsetDateTime.now());
        entity.setAdded(OffsetDateTime.now());
        entity.setId(id);
        entity.setStoragePath( getDocumentStoragePath(filePath, document.getOriginalFilename()) );

        storagepathRepository.save(entity.getStoragePath());
        documentRepository.save(entity);

        rabbitMQSender.sendToDocumentInQueue(entity.getStoragePath().getPath());
        documentDTO.getOriginalFileName().ifPresent(System.out::println);
        return filePath;
    }

    @Override
    public GetDocuments200Response getDocuments(
            Integer page,
            Integer pageSize,
            String query,
            String ordering,

            List<Integer> tagsIdAll,
            Integer documentTypeId,
            Integer storagePathIdIn,
            Integer correspondentId,

            Boolean truncateContent
    ) {
        int startingIndex =
                page == null ?
                        0 :
                        (pageSize == null ? page : page*pageSize) ;

        DocumentsDocument searchTemplate = new DocumentsDocument();

        //externals
        DocumentsCorrespondent correspondent = null;
        if (correspondentId != null) {
            correspondent = correspondentRepository.findById(correspondentId).orElse(null);
        }

        DocumentsDocumenttype documentType = null;
        if (documentTypeId!= null) {
            documentType = doctypeRepository.findById(documentTypeId).orElse(null);
        }

        DocumentsStoragepath storagePath = null;
        if (storagePathIdIn!= null) {
            storagePath = storagepathRepository.findById(storagePathIdIn).orElse(null);
        }

        searchTemplate.setCorrespondent(correspondent); // Assuming a default value is not needed or create a new DocumentsCorrespondent instance
        searchTemplate.setDocumentType(documentType); // Assuming a default value is not needed or create a new DocumentsDocumenttype instance
        searchTemplate.setStoragePath(storagePath); // Assuming a default value is not needed or create a new DocumentsStoragepath instance
        searchTemplate.setOwner(null); // Assuming a default value is not needed or create a new AuthUser instance

        List<DocumentsDocument> alldocs = documentRepository.findAll(Example.of(searchTemplate));

        int maxIndex = Math.min( alldocs.size() , startingIndex + pageSize );

        if (startingIndex>maxIndex) {
            startingIndex = maxIndex;
        }

        int previousId = alldocs.get( Math.max(startingIndex-1,0) ).getId();
        int nextId = alldocs.get( Math.min(maxIndex+1, alldocs.size()-1) ).getId();
        alldocs = alldocs.subList(startingIndex, maxIndex);

        log.info(alldocs.toString() + " documents");

        List<Integer> allIds = new ArrayList<Integer>();
        List<GetDocuments200ResponseResultsInner> results = new ArrayList<GetDocuments200ResponseResultsInner>();
        for (DocumentsDocument doc : alldocs) {
            GetDocuments200ResponseResultsInner dto = DocumentMapper.toOkInnerRes(doc);
            allIds.add(doc.getId());

            if ( truncateContent == null ? true : truncateContent) {
                String content = doc.getContent();
                dto.content( content.substring(0, Math.min(content.length()-1 ,47) ) + "..." );
            }

            results.add(dto);
        }

        //Pack data in response Element
        GetDocuments200Response response = new GetDocuments200Response();
        response.setCount(maxIndex-startingIndex+1);

        response.setPrevious(previousId);
        response.setNext(nextId);

        response.setAll(allIds);

        response.setResults(results);

        //Return response Relement
        return response;

    }

    @Override
    public String getResourcePath(Integer id) {
        DocumentsDocument doc = documentRepository.findById(id).orElseThrow();
        String path = doc.getStoragePath().getPath();

        return path;
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

    public byte[] getPdfData(String pdfFilePath) {
        if(!pdfFilePath.endsWith(".pdf")) {
            log.info("Only pdf files are currently supported for OCR. File not processable: " + pdfFilePath);
            //return null;
        }

        String[] bucketAndFileName = extractBucketAndFileName(pdfFilePath);
        //if(bucketAndFileName == null) return null;

        log.info(bucketAndFileName[0] +", "+ bucketAndFileName[1]);

        String bucketName = bucketAndFileName[0];
        String fileName = bucketAndFileName[1];

        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(pdfFilePath)
                        .build())) {

            return stream.readAllBytes();
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    private void storeInMinIO(MultipartFile file, String path) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(path)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );
        log.info("Stored object in minIO: " + path);
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
        storagePath.setPath(removeUnwantedChars(filePath));
        storagePath.setName(fileName);
        storagePath.setMatch("");
        storagePath.setMatchingAlgorithm(0);
        storagePath.setIsInsensitive(false);

        return storagePath;
    }



}
