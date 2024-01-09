package org.openapitools.services;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openapitools.helpers.FileHelper;
import org.openapitools.model.DocumentDTO;
import org.openapitools.model.okresponse.GetDocument200Response;
import org.openapitools.model.okresponse.GetDocuments200Response;
import org.openapitools.model.okresponse.GetDocuments200ResponseResultsInner;
import org.openapitools.persistence.elasticsearch.entities.ElasticDocumentDocument;
import org.openapitools.persistence.elasticsearch.repositories.ElasticDocumentDocumentRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import static org.openapitools.helpers.FileHelper.*;

@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final DocumentsDocumentRepository documentRepository;

    private final DocumentsCorrespondentRepository correspondentRepository;
    private final DocumentsDocumenttypeRepository doctypeRepository;
    private final DocumentsStoragepathRepository storagepathRepository;

    private final ElasticsearchService elasticsearchService;

    private final MinioClient minioClient;
    private final RabbitMqSender rabbitMQSender;


    @Value("${minio.bucketName}")
    private String bucketName;

    @Autowired
    public DocumentServiceImpl(
            DocumentsDocumentRepository documentRepository,
            DocumentsCorrespondentRepository correspondentRepository,
            DocumentsDocumenttypeRepository doctypeRepository,
            DocumentsStoragepathRepository storagepathRepository,
            MinioClient minioClient,
            RabbitMqSenderImpl rabbitMQSender,
            ElasticsearchService elasticsearchService) {
        this.documentRepository = documentRepository;
        this.correspondentRepository = correspondentRepository;
        this.doctypeRepository = doctypeRepository;
        this.storagepathRepository = storagepathRepository;
        this.minioClient = minioClient;
        this.rabbitMQSender = rabbitMQSender;
        this.elasticsearchService = elasticsearchService;

        log.info("DocumentServiceImpl created");
    }

    @Override
    public GetDocument200Response getDocument(Integer id, Integer page, Boolean fullPerms) {
        DocumentsDocument entity = documentRepository.getReferenceById(id);

        return DocumentMapper.toOkRes(entity);
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

        //Everything I need to set before saving! :3
        entity.setCreated(OffsetDateTime.now());
        entity.setModified(OffsetDateTime.now());
        entity.setAdded(OffsetDateTime.now());
        entity.setStoragePath(getDocumentStoragePath("",""));

        storagepathRepository.save(entity.getStoragePath());
        Integer id = documentRepository.save(entity).getId();

        log.info(id+"");

        String filePath = storeInMinIo(document, id);
        if (filePath == null) return null;

        // Update path in database
        entity.setStoragePath( getDocumentStoragePath(filePath, document.getOriginalFilename()) );
        storagepathRepository.save(entity.getStoragePath());
        documentRepository.save(entity);

        rabbitMQSender.sendToDocumentInQueue(entity.getStoragePath().getPath());
        documentDTO.getOriginalFileName().ifPresent(System.out::println);
        return filePath;
    }

    @Nullable
    private String storeInMinIo(MultipartFile document, Integer id) {
        String minioObjectName = "";
        try {
            minioObjectName = getMinioObjectName(document.getOriginalFilename());
        } catch (InvalidParameterException e) {
            log.error(e.getMessage(), e);
            return null;
        }
        String filePath = bucketName + "/" + id.toString() + "-" + minioObjectName;

        try {
            storeInMinIO(document, filePath);
        } catch (ServerException | InsufficientDataException | ErrorResponseException | IOException | NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException | InternalException e) {
            log.error(e.getMessage(), e);
            return null;
        }
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
        log.info("Query: " + query + "; Ordering: " + ordering);

        int startingIndex =
                page == null ?
                        0 :
                        (pageSize == null ? page : page*pageSize) ;

        List<DocumentsDocument> alldocs;
        if(query != null && !query.isEmpty()) {
            List<ElasticDocumentDocument> elasticResult = elasticsearchService.fuzzySearch(
                    query,
                    page == null ? 0 : page,
                    pageSize == null ? 10 : pageSize).getContent();

            alldocs = getAllDocsFromElasticResult(elasticResult);
        } else {
            DocumentsDocument searchTemplate = getSearchTemplate(documentTypeId, storagePathIdIn, correspondentId);
            alldocs = documentRepository.findAll(Example.of(searchTemplate));
        }

        int maxIndex = Math.min(alldocs.size() , startingIndex + pageSize);

        if (startingIndex>maxIndex) {
            startingIndex = maxIndex;
        }

        int previousId = 0;
        int nextId = 0;

        if(!alldocs.isEmpty()){
            // Weird minmax bounds fix
            previousId = alldocs.get(Math.max(startingIndex - 1, 0)).getId();
            nextId = alldocs.get(Math.max(Math.min(maxIndex + 1, alldocs.size() - 1), 0)).getId();
            alldocs = alldocs.subList(startingIndex, maxIndex);
        }
        
        List<Integer> allIds = new ArrayList<Integer>();
        List<GetDocuments200ResponseResultsInner> results = new ArrayList<GetDocuments200ResponseResultsInner>();
        for (DocumentsDocument doc : alldocs) {
            GetDocuments200ResponseResultsInner dto = DocumentMapper.toOkInnerRes(doc);
            allIds.add(doc.getId());

            if (truncateContent == null || truncateContent) {
                String content = doc.getContent();
                dto.content( content.substring(0, Math.min( Math.max(content.length()-1,0) ,47) ) + "..." );
            }

            results.add(dto);
        }

        //Pack data in response Element
        GetDocuments200Response response = new GetDocuments200Response();
        response.setCount(maxIndex-startingIndex);
        response.setPrevious(previousId);
        response.setNext(nextId);
        response.setAll(allIds);
        response.setResults(results);

        //Return response Relement
        return response;

    }

    @NotNull
    private List<DocumentsDocument> getAllDocsFromElasticResult(List<ElasticDocumentDocument> elasticResult) {
        List<DocumentsDocument> docs = new ArrayList<>();
        for (ElasticDocumentDocument doc : elasticResult) {
            documentRepository.findById(doc.getId()).ifPresent(docs::add);
        }
        return docs;
    }

    @NotNull
    private DocumentsDocument getSearchTemplate(Integer documentTypeId, Integer storagePathIdIn, Integer correspondentId) {
        DocumentsDocument searchTemplate = new DocumentsDocument();

        //externals
        searchTemplate.setCorrespondent(getDocumentsCorrespondent(correspondentId)); // Assuming a default value is not needed or create a new DocumentsCorrespondent instance
        searchTemplate.setDocumentType(getDocumentsDocumenttype(documentTypeId)); // Assuming a default value is not needed or create a new DocumentsDocumenttype instance
        searchTemplate.setStoragePath(getDocumentsStoragepath(storagePathIdIn)); // Assuming a default value is not needed or create a new DocumentsStoragepath instance
        searchTemplate.setOwner(null); // Assuming a default value is not needed or create a new AuthUser instance
        return searchTemplate;
    }

    @Nullable
    private DocumentsStoragepath getDocumentsStoragepath(Integer storagePathIdIn) {
        DocumentsStoragepath storagePath = null;
        if (storagePathIdIn != null) {
            storagePath = storagepathRepository.findById(storagePathIdIn).orElse(null);
        }
        return storagePath;
    }

    @Nullable
    private DocumentsDocumenttype getDocumentsDocumenttype(Integer documentTypeId) {
        DocumentsDocumenttype documentType = null;
        if (documentTypeId != null) {
            documentType = doctypeRepository.findById(documentTypeId).orElse(null);
        }
        return documentType;
    }

    @Nullable
    private DocumentsCorrespondent getDocumentsCorrespondent(Integer correspondentId) {
        DocumentsCorrespondent correspondent = null;
        if (correspondentId != null) {
            correspondent = correspondentRepository.findById(correspondentId).orElse(null);
        }
        return correspondent;
    }

    @Override
    public String getResourcePath(Integer id) {
        DocumentsDocument doc = documentRepository.findById(id).orElseThrow();

        return doc.getStoragePath().getPath();
    }

    public byte[] getPdfData(String pdfFilePath) {
        if(!pdfFilePath.endsWith(".pdf")) {
            log.info("Only pdf files are currently supported for OCR. File not processable: " + pdfFilePath);
        }

        String[] bucketAndFileName = extractBucketAndFileName(pdfFilePath);
        if(bucketAndFileName == null) {
            log.info("Could not extract bucket and file name from: " + pdfFilePath);
            return null;
        }

        log.info(bucketAndFileName[0] +", "+ bucketAndFileName[1]);
        String bucketName = bucketAndFileName[0];

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
}
