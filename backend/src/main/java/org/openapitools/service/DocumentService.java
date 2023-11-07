package org.openapitools.service;

import org.openapitools.model.Document;
import org.openapitools.model.ok200.GetDocument200Response;
import org.openapitools.model.ok200.GetDocuments200Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {
    GetDocument200Response getDocument(Integer id, Integer page, Boolean fullPerms);

    void uploadDocument(Document documentDTO, List<MultipartFile> document);

    ResponseEntity<GetDocuments200Response> getDocuments(Integer page, Integer pageSize, String query, String ordering, List<Integer> tagsIdAll, Integer documentTypeId, Integer storagePathIdIn, Integer correspondentId, Boolean truncateContent);
}