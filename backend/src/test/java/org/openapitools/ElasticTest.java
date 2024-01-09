package org.openapitools;

import org.junit.jupiter.api.Test;
import org.openapitools.persistence.elasticsearch.entities.ElasticDocumentDocument;

import static org.junit.jupiter.api.Assertions.*;

public class ElasticTest {

    @Test
    public void whenTestingElasticDocumentDocument_thenAllFieldsAreTested() {
        // given
        ElasticDocumentDocument document = ElasticDocumentDocument.builder()
                .id(1)
                .title("Test Title")
                .content("Test Content")
                .filename("test.txt")
                .build();

        // when
        String actual = document.toString();

        // then
        String expected = "ElasticDocumentDocument(id=1, title=Test Title, content=Test Content, filename=test.txt)";
        assertEquals(expected, actual);
    }

    @Test
    public void whenTestingElasticDocumentDocumentBuilder_thenAllFieldsAreTested() {
        // given
        Integer id = 1;
        String title = "Test Title";
        String content = "Test Content";
        String filename = "test.txt";

        // when
        ElasticDocumentDocument document = ElasticDocumentDocument.builder()
                .id(id)
                .title(title)
                .content(content)
                .filename(filename)
                .build();

        // then
        String actual = document.toString();
        String expected = "ElasticDocumentDocument(id=1, title=Test Title, content=Test Content, filename=test.txt)";
        assertEquals(expected, actual);
    }

    @Test
    public void whenTestingElasticDocumentDocumentBuilderWithNoArgs_thenAllFieldsAreDefaulted() {
        // given
        // no args

        // when
        ElasticDocumentDocument document = ElasticDocumentDocument.builder().build();

        // then
        assertNull(document.getId());
        assertNull(document.getTitle());
        assertNull(document.getContent());
        assertNull(document.getFilename());
    }

    @Test
    public void whenTestingElasticDocumentDocumentBuilderWithNullArgs_thenAllFieldsAreDefaulted() {
        // given
        Integer id = null;
        String title = null;
        String content = null;
        String filename = null;

        // when
        ElasticDocumentDocument document = ElasticDocumentDocument.builder()
                .id(id)
                .title(title)
                .content(content)
                .filename(filename)
                .build();

        // then
        assertNull(document.getId());
        assertNull(document.getTitle());
        assertNull(document.getContent());
        assertNull(document.getFilename());
    }
}
