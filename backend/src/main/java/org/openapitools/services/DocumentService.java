package org.openapitools.services;

import org.openapitools.model.DocumentDTO;
import org.openapitools.model.okresponse.GetDocument200Response;

import org.openapitools.model.okresponse.GetDocuments200Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {
    GetDocument200Response getDocument(Integer id, Integer page, Boolean fullPerms);

    String uploadDocument(DocumentDTO documentDTO, MultipartFile document);

    GetDocuments200Response getDocuments(Integer page, Integer pageSize, String query, String ordering, List<Integer> tagsIdAll, Integer documentTypeId, Integer storagePathIdIn, Integer correspondentId, Boolean truncateContent);

    String getResourcePath(Integer id);
}
