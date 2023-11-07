package org.openapitools.mapper;

import org.openapitools.persistence.entities.*;
import org.openapitools.persistence.repositories.*;
import org.openapitools.model.DocumentDTO;
import org.openapitools.model.DocumentNoteDTO;
import org.openapitools.model.Permissions;
import org.openapitools.model.okresponse.GetDocument200Response;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Service
public abstract class DocumentNotesMapper implements BaseMapper<DocumentsNote, DocumentNoteDTO> {

    @Autowired
    private DocumentsCorrespondentRepository correspondentRepository;
    @Autowired
    private DocumentsDocumenttypeRepository documentTypeRepository;
    @Autowired
    private DocumentsStoragepathRepository storagePathRepository;
    @Autowired
    private AuthUserRepository userRepository;
    @Autowired
    private DocumentsDocumentTagsRepository documentTagsRepository;
    @Autowired
    private DocumentsDocumentRepository documentRepository;

    @Mapping(target = "document", source = "document", qualifiedByName = "documentDto")
    @Mapping(target = "user", source = "user", qualifiedByName = "userDto")
    abstract public DocumentsNote dtoToEntity(DocumentNoteDTO dto);

    @Mapping(target = "document", source = "document", qualifiedByName = "documentEntity")
    @Mapping(target = "user", source = "user", qualifiedByName = "userEntity")
    abstract public DocumentNoteDTO entityToDto(DocumentsNote entity);

    @Named("userEntity")
    Integer map(AuthUser user) {
        return user.getId();
    }

    @Named("documentEntity")
    Integer map(DocumentsDocument document) {
        return document.getId();
    }

    @Named("createdEntity")
    String map(OffsetDateTime created) {
        return created.toString();
    }

    @Named("userDto")
    AuthUser mapCorrespondent(Integer value) {
        return userRepository.findById(value).orElse(null);
    }

    @Named("documentDto")
    DocumentsDocument mapDocument(Integer value) {
        return documentRepository.findById(value).orElse(null);
    }


}
