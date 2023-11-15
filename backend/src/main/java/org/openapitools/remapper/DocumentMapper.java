package org.openapitools.remapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openapitools.jackson.nullable.JsonNullable;
import org.openapitools.model.DocumentDTO;
import org.openapitools.persistence.entities.DocumentsDocument;
import org.openapitools.remapper.EntityMapper;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;

@Mapper(componentModel = "spring", uses = {JsonNullableMapper.class})
public class DocumentMapper implements EntityMapper<DocumentsDocument, DocumentDTO> {



    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "correspondent", source = "entity.correspondent.id")
    @Mapping(target = "documentType", source = "entity.documentType.id")
    @Mapping(target = "storagePath", source = "entity.storagePath.id")
    @Mapping(target = "title", source = "entity.title")
    @Mapping(target = "content", source = "entity.content")
    @Mapping(target = "tags", expression = "java(entity.getDocumentDocumentsDocumentTagses().stream().map(DocumentsDocumentTags::getTagId).collect(Collectors.toList()))")
    @Mapping(target = "created", source = "entity.created")
    @Mapping(target = "createdDate", ignore = true) // No equivalent in entity
    @Mapping(target = "modified", source = "entity.modified")
    @Mapping(target = "added", source = "entity.added")
    @Mapping(target = "archiveSerialNumber", source = "entity.archiveSerialNumber")
    @Mapping(target = "originalFileName", source = "entity.originalFilename")
    @Mapping(target = "archivedFileName", source = "entity.archiveFilename")
    public DocumentDTO toDto(DocumentsDocument entity) {
        return null;
    }

    @InheritInverseConfiguration
    public DocumentsDocument toEntity(DocumentDTO documentDTO) {
        return null;
    }
}