package org.openapitools.services;

import org.openapitools.model.DocumentDTO;
import org.openapitools.model.okresponse.GetDocument200Response;
import org.openapitools.model.okresponse.GetDocuments200Response;
import org.openapitools.persistence.entities.DocumentsDocument;
import org.openapitools.persistence.entities.DocumentsStoragepath;
import org.openapitools.persistence.repositories.DocumentsDocumentRepository;
import org.openapitools.remapper.DocumentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentsDocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    @Autowired
    public DocumentServiceImpl(DocumentsDocumentRepository documentRepository, DocumentMapper documentMapper) {
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
    }

    @Override
    public GetDocument200Response getDocument(Integer id, Integer page, Boolean fullPerms) {
        return null;
    }

    public void uploadDocument(DocumentDTO documentDTO, MultipartFile document) {
        // Convert DTO to entity
        DocumentsDocument entity = documentMapper.toEntity(documentDTO);

        // Here, handle the file upload logic as required for your application
        // This can involve saving the file to disk, a database, or another storage service

        // For demonstration purposes, let's assume you have a method to handle the upload:
        // uploadFile(document, entity.getStoragePath());

        // Print the original filename from the DTO
        documentDTO.getOriginalFileName().ifPresent(System.out::println);
    }

    @Override
    public ResponseEntity<GetDocuments200Response> getDocuments(Integer page, Integer pageSize, String query, String ordering, List<Integer> tagsIdAll, Integer documentTypeId, Integer storagePathIdIn, Integer correspondentId, Boolean truncateContent) {
        return null;
    }
}
