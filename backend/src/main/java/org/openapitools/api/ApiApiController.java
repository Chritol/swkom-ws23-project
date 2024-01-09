package org.openapitools.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.openapitools.jackson.nullable.JsonNullable;
import org.openapitools.model.DocumentDTO;
import org.openapitools.model.okresponse.GetDocument200Response;
import org.openapitools.model.okresponse.GetDocuments200Response;
import org.openapitools.model.okresponse.UpdateDocument200Response;
import org.openapitools.model.update.UpdateDocumentRequest;
import org.openapitools.services.DocumentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.net.URI;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-10-22T09:39:32.151354Z[Etc/UTC]")
@Controller
@RequestMapping("${openapi.paperlessRestServer.base-path:}")
@CrossOrigin(origins = "http://localhost:8080")
@Slf4j
public class ApiApiController implements ApiApi {

    private final DocumentServiceImpl documentServiceImpl;

    private final NativeWebRequest request;

    @Autowired
    public ApiApiController(DocumentServiceImpl documentServiceImpl, NativeWebRequest request) {
        this.documentServiceImpl = documentServiceImpl;
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }


    @Override
    public ResponseEntity<GetDocument200Response> getDocument(Integer id, Integer page, Boolean fullPerms) {
        log.info("getDocument");
        GetDocument200Response response = documentServiceImpl.getDocument(id, page, fullPerms);

        return ResponseEntity.ok( response );
    }

    @Override
    public ResponseEntity<Void> uploadDocument(String title, OffsetDateTime created, Integer documentType, List<Integer> tags, Integer correspondent, List<MultipartFile> document) {
        try {

            String name = document.get(0).getOriginalFilename();
            DocumentDTO documentDTO = new DocumentDTO();
            documentDTO.setTitle(JsonNullable.of(title == null ? name : title));
            documentDTO.setOriginalFileName(JsonNullable.of(name));
            documentDTO.setCreated(created);
            documentDTO.setDocumentType(JsonNullable.of(documentType));
            documentDTO.setTags(JsonNullable.of(tags));
            documentDTO.setCorrespondent(JsonNullable.of(correspondent));

            MultipartFile file = document.get(0);

            if(file == null || file.isEmpty()){
                return ResponseEntity.badRequest().build();
            }

            String success = documentServiceImpl.uploadDocument(documentDTO, file);
            return success != null ? ResponseEntity.ok().build() : ResponseEntity.unprocessableEntity().build();

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().build();
        }

    }

    @Override
    public ResponseEntity<GetDocuments200Response> getDocuments(Integer page, Integer pageSize, String query, String ordering, List<Integer> tagsIdAll, Integer documentTypeId, Integer storagePathIdIn, Integer correspondentId, Boolean truncateContent) {
        log.info("getDocuments");

        GetDocuments200Response response = documentServiceImpl.getDocuments(page, pageSize, query, ordering, tagsIdAll, documentTypeId, storagePathIdIn, correspondentId, truncateContent);

        return ResponseEntity.ok( response );
    }


    public ResponseEntity<Resource> getDocumentPreview( Integer id ) {
        String resourcePath = documentServiceImpl.getResourcePath(id);
        byte[] getPdfData = documentServiceImpl.getPdfData(resourcePath);

        Resource resource = new ByteArrayResource(getPdfData);
        return ResponseEntity.ok(resource);

    }

    public ResponseEntity<org.springframework.core.io.Resource> downloadDocument(
            Integer id,
            Boolean original
    ) {
        String resourcePath = documentServiceImpl.getResourcePath(id);
        byte[] getPdfData = documentServiceImpl.getPdfData(resourcePath);

        Resource resource = new ByteArrayResource(getPdfData);
        return ResponseEntity.ok(resource);

    }



}
