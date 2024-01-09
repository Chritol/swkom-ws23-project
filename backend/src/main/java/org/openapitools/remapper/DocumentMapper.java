package org.openapitools.remapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openapitools.jackson.nullable.JsonNullable;
import org.openapitools.model.DocumentDTO;
import org.openapitools.model.DocumentNoteDTO;
import org.openapitools.model.Permissions;
import org.openapitools.model.okresponse.GetDocument200Response;
import org.openapitools.model.okresponse.GetDocuments200ResponseResultsInner;
import org.openapitools.persistence.entities.*;
import org.openapitools.persistence.repositories.DocumentsCorrespondentRepository;
import org.openapitools.persistence.repositories.DocumentsDocumenttypeRepository;
import org.openapitools.persistence.repositories.DocumentsStoragepathRepository;
import org.openapitools.remapper.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Begründung:
 * Kein mapping framework verwendet, sondern selbst geschrieben, weil Mapstruct, bei dieser Java nicht mit anderen modules zusammen gearbeitet hat, aber spring nich bei einer Version, wo Mapstruct ginge.
 * Unter anderem ermöglicht das auch besseres mapping, weil die "Externals" nicht auch implimentiert werden müssen.
 * Bei dieser menge an Usecases ist das auch noch kürzer.
 */
public class DocumentMapper {

    public static DocumentDTO toDto(DocumentsDocument entity) {
        if(entity == null){
            return null;
        }

        DocumentDTO dto = new DocumentDTO();

        dto     //id
                .id(entity.getId());

        dto     //externals
                .correspondent(entity.getCorrespondent() != null ? entity.getCorrespondent().getId() : 0)
                .documentType(entity.getDocumentType() != null ? entity.getDocumentType().getId() : 0)
                .storagePath(entity.getStoragePath() != null ? entity.getStoragePath().getId() : 0);


        dto     //content
                .title(entity.getTitle())
                .content(entity.getContent());

        Set<DocumentsDocumentTags> tagEntities = entity.getDocumentDocumentsDocumentTagses();
        if (tagEntities != null) {
            List<Integer> tagIds = new ArrayList<Integer>();
            for (DocumentsDocumentTags tagEntity :
                    tagEntities) {
                tagIds.add(tagEntity.getId());
            }
            dto     //tags
                    .tags(tagIds);
        }

        dto     //dateTime
                .created(entity.getCreated())
                .createdDate(entity.getCreated())
                .modified(entity.getModified())
                .added(entity.getAdded());

        dto     //file
                .archiveSerialNumber("default")
                .originalFileName(entity.getOriginalFilename())
                .archivedFileName(entity.getArchiveFilename());

        return dto;
    }

    public static GetDocument200Response toOkRes(DocumentsDocument entity) {
        if(entity == null){
            return null;
        }

        GetDocument200Response dto = new GetDocument200Response();

        dto     //id
                .id(entity.getId());

        dto     //externals
                .correspondent(entity.getCorrespondent() != null ? entity.getCorrespondent().getId() : 0)
                .documentType(entity.getDocumentType() != null ? entity.getDocumentType().getId() : 0)
                .storagePath(entity.getStoragePath() != null ? entity.getStoragePath().getId() : 0);


        dto     //content
                .title(entity.getTitle())
                .content(entity.getContent());

        Set<DocumentsDocumentTags> tagEntities = entity.getDocumentDocumentsDocumentTagses();
        List<Integer> tagIds = new ArrayList<Integer>();
        for (DocumentsDocumentTags tagEntitie:
                tagEntities) {
            tagIds.add(tagEntitie.getId());
        }
        dto     //tags
                .tags(tagIds);

        dto     //dateTime
                .created(entity.getCreated().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .createdDate(entity.getCreated().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .modified(entity.getModified().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .added(entity.getAdded().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        dto     //file
                .archiveSerialNumber(entity.getArchiveSerialNumber())
                .originalFileName(entity.getOriginalFilename())
                .archivedFileName(entity.getArchiveFilename());

        Permissions permissions = new Permissions();

        dto     //permissions
                .owner(entity.getOwner() != null ? entity.getOwner().getId() : 0)
                .permissions(permissions);

        entity.getDocumentDocumentsNotes();
        dto     //notes
                .notes(new ArrayList<DocumentNoteDTO>());


        return dto;
    }

    public static GetDocuments200ResponseResultsInner toOkInnerRes(DocumentsDocument entity) {
        if(entity == null){
            return null;
        }

        GetDocuments200ResponseResultsInner dto = new GetDocuments200ResponseResultsInner();

        dto     //id
                .id(entity.getId());

        dto     //externals
                .correspondent(entity.getCorrespondent() != null ? entity.getCorrespondent().getId() : 0)
                .documentType(entity.getDocumentType() != null ? entity.getDocumentType().getId() : 0)
                .storagePath(entity.getStoragePath() != null ? entity.getStoragePath().getId() : 0);


        dto     //content
                .title(entity.getTitle())
                .content(entity.getContent());

        Set<DocumentsDocumentTags> tagEntities = entity.getDocumentDocumentsDocumentTagses();
        List<Integer> tagIds = new ArrayList<Integer>();
        for (DocumentsDocumentTags tagEntity : tagEntities) {
            tagIds.add(tagEntity.getId());
        }
        dto     //tags
                .tags(tagIds);

        dto     //dateTime
                .created(entity.getCreated().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .createdDate(entity.getCreated().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .modified(entity.getModified().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .added(entity.getAdded().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        dto     //file
                .archiveSerialNumber(entity.getArchiveSerialNumber())
                .originalFileName(entity.getOriginalFilename())
                .archivedFileName(entity.getArchiveFilename());

        dto     //owner and userCanChange
                .owner(entity.getOwner() != null ? entity.getOwner().getId() : 0)
                .userCanChange(false);

        entity.getDocumentDocumentsNotes();
        dto     //notes
                .notes(new ArrayList<DocumentNoteDTO>());


        return dto;
    }

    public static DocumentsDocument toEntity(DocumentDTO dto) {
        if(dto == null){
            return null;
        }

        return toEntity(dto, null, null, null);
    }

    public static DocumentsDocument toEntity(
            DocumentDTO dto,
            DocumentsCorrespondentRepository correspondentRepository,
            DocumentsDocumenttypeRepository doctypeRepository,
            DocumentsStoragepathRepository storagepathRepository
    ) {
        if(dto == null){
            return null;
        }

        DocumentsDocument entity = new DocumentsDocument();

        //id
        entity.setId(dto.getId());

        //externals
        Integer correspondentId = dto.getCorrespondent().orElse(null);
        DocumentsCorrespondent correspondent = null;
        if (correspondentId != null) {
             correspondent = correspondentRepository.findById(correspondentId).orElse(null);
        }
        Integer documentTypeId = dto.getDocumentType().orElse(null);
        DocumentsDocumenttype documentType = null;
        if (documentTypeId!= null) {
             documentType = doctypeRepository.findById(documentTypeId).orElse(null);
        }
        Integer storagePathId = dto.getStoragePath().orElse(null);
        DocumentsStoragepath storagePath = null;
        if (storagePathId!= null) {
             storagePath = storagepathRepository.findById(storagePathId).orElse(null);
        }

        entity.setCorrespondent(correspondent); // Assuming a default value is not needed or create a new DocumentsCorrespondent instance
        entity.setDocumentType(documentType); // Assuming a default value is not needed or create a new DocumentsDocumenttype instance
        entity.setStoragePath(storagePath); // Assuming a default value is not needed or create a new DocumentsStoragepath instance
        entity.setOwner(null); // Assuming a default value is not needed or create a new AuthUser instance

        //content
        entity.setTitle(dto.getTitle().orElse("default"));
        entity.setContent(dto.getContent().orElse("default"));

        //notes
        entity.setDocumentDocumentsNotes(new HashSet<DocumentsNote>()); // Empty set

        //tags
        entity.setDocumentDocumentsDocumentTagses(new HashSet<DocumentsDocumentTags>()); // Empty set

        //dateTime
        //OffsetDateTime defaultDateTime = OffsetDateTime.now();
        entity.setCreated(dto.getCreated());
        entity.setModified(dto.getModified());
        entity.setAdded(dto.getAdded());

        //file
        entity.setChecksum("default");
        entity.setStorageType("default");
        entity.setFilename("default");
        entity.setMimeType("default");
        entity.setArchiveChecksum("default"); // Example MD5
        entity.setArchiveFilename(dto.getArchivedFileName().orElse("default"));
        entity.setOriginalFilename(dto.getOriginalFileName().orElse("default"));



        return entity;
    }


    public static DocumentsDocument toEntity(GetDocument200Response dto) {
        if(dto == null){
            return null;
        }

        DocumentsDocument entity = new DocumentsDocument();

        //id
        entity.setId(dto.getId());

        //externals
        entity.setCorrespondent(new DocumentsCorrespondent()); // Assuming a default value is not needed or create a new DocumentsCorrespondent instance
        entity.setDocumentType(new DocumentsDocumenttype()); // Assuming a default value is not needed or create a new DocumentsDocumenttype instance
        entity.setStoragePath(new DocumentsStoragepath()); // Assuming a default value is not needed or create a new DocumentsStoragepath instance
        entity.setOwner(new AuthUser()); // Assuming a default value is not needed or create a new AuthUser instance

        //content
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());

        //notes
        entity.setDocumentDocumentsNotes(new HashSet<DocumentsNote>()); // Empty set

        //tags
        entity.setDocumentDocumentsDocumentTagses(new HashSet<DocumentsDocumentTags>()); // Empty set

        //dateTime
        //OffsetDateTime defaultDateTime = OffsetDateTime.now();
        entity.setCreated( OffsetDateTime.parse(dto.getCreated()) );
        entity.setModified( OffsetDateTime.parse(dto.getModified()) );
        entity.setAdded( OffsetDateTime.parse(dto.getAdded()) );

        //file
        entity.setChecksum("default");
        entity.setStorageType("default");
        entity.setFilename("default");
        entity.setMimeType("default");
        entity.setArchiveChecksum("default"); // Example MD5
        entity.setArchiveFilename(dto.getArchivedFileName());
        entity.setOriginalFilename(dto.getOriginalFileName());

        return entity;
    }


}


