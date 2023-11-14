package org.openapitools.remapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.openapitools.model.DocumentDTO;
import org.openapitools.persistence.entities.DocumentsDocument;

@Mapper
public interface DocumentMapper extends EntityMapper<DocumentsDocument, DocumentDTO> {
    DocumentMapper INSTANCE = Mappers.getMapper(DocumentMapper.class);

    @Mapping(source = "correspondent.id", target = "correspondent")
    @Mapping(source = "documentType.id", target = "documentType")
    @Mapping(source = "storagePath.id", target = "storagePath")
    @Mapping(source = "originalFilename", target = "originalFileName")
    @Mapping(source = "archiveFilename", target = "archivedFileName")
    DocumentDTO toDto(DocumentsDocument document);

    @Mapping(target = "correspondent.id", source = "correspondent")
    @Mapping(target = "documentType.id", source = "documentType")
    @Mapping(target = "storagePath.id", source = "storagePath")
    @Mapping(target = "originalFilename", source = "originalFileName")
    @Mapping(target = "archiveFilename", source = "archivedFileName")
    DocumentsDocument toEntity(DocumentDTO documentDTO);
}
