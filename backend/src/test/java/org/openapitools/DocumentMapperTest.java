package org.openapitools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.openapitools.model.DocumentDTO;
import org.openapitools.persistence.entities.DocumentsCorrespondent;
import org.openapitools.persistence.entities.DocumentsDocument;
import org.openapitools.persistence.entities.DocumentsDocumenttype;
import org.openapitools.persistence.entities.DocumentsStoragepath;
import org.openapitools.persistence.repositories.DocumentsCorrespondentRepository;
import org.openapitools.persistence.repositories.DocumentsDocumenttypeRepository;
import org.openapitools.persistence.repositories.DocumentsStoragepathRepository;
import org.openapitools.remapper.DocumentMapper;

public class DocumentMapperTest {

    @Mock
    private DocumentsCorrespondentRepository correspondentRepository;

    @Mock
    private DocumentsDocumenttypeRepository doctypeRepository;

    @Mock
    private DocumentsStoragepathRepository storagepathRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case for mapping an entity to a DTO
     */
    @Test
    public void testToDto_Success() {
        int id = 1;

        DocumentsDocument entity = createSampleDocument(id);

        DocumentDTO dto = DocumentMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
    }

    /**
     * Test case for handling a null entity when mapping to DTO
     */
    @Test
    public void testToDto_NullEntity() {
        DocumentDTO dto = DocumentMapper.toDto(null);

        assertNull(dto);
    }

    /**
     * Test case for mapping a DTO to an entity
     */
    @Test
    public void testToEntity_Success() {
        int id = 3;
        DocumentDTO dto = createSampleDocumentDTO(id);

        // Mocking repository calls for dependencies
        when(correspondentRepository.findById(anyInt())).thenReturn(java.util.Optional.of(new DocumentsCorrespondent()));
        when(doctypeRepository.findById(anyInt())).thenReturn(java.util.Optional.of(new DocumentsDocumenttype()));
        when(storagepathRepository.findById(anyInt())).thenReturn(java.util.Optional.of(new DocumentsStoragepath()));

        DocumentsDocument entity = DocumentMapper.toEntity(dto, correspondentRepository, doctypeRepository, storagepathRepository);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
    }

    /**
     * Test case for handling a null DTO when mapping to an entity
     */
    @Test
    public void testToEntity_NullDto() {
        DocumentsDocument entity = DocumentMapper.toEntity(null, correspondentRepository, doctypeRepository, storagepathRepository);

        assertNull(entity);
    }





    private DocumentsDocument createSampleDocument(int id) {
        DocumentsDocument sampleDoc = new DocumentsDocument();

        sampleDoc.setId(id);

        return sampleDoc;
    }

    private DocumentDTO createSampleDocumentDTO(int id) {
        return new DocumentDTO().id(id);
    }
}
