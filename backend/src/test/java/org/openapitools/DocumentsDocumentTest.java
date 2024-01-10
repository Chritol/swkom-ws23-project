package org.openapitools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openapitools.persistence.entities.DocumentsDocument;

public class DocumentsDocumentTest {

    @Test
    public void testFilenameSetterGetter() {
        DocumentsDocument document = new DocumentsDocument();
        document.setFilename("testFileName");
        Assertions.assertEquals("testFileName", document.getFilename());
    }

    @Test
    public void testArchiveSerialNumberSetterGetter() {
        DocumentsDocument document = new DocumentsDocument();
        document.setArchiveSerialNumber(12345);
        Assertions.assertEquals(12345, document.getArchiveSerialNumber());
    }

    @Test
    public void testArchiveChecksumSetterGetter() {
        DocumentsDocument document = new DocumentsDocument();
        document.setArchiveChecksum("testArchiveChecksum");
        Assertions.assertEquals("testArchiveChecksum", document.getArchiveChecksum());
    }

    @Test
    public void testArchiveFilenameSetterGetter() {
        DocumentsDocument document = new DocumentsDocument();
        document.setArchiveFilename("testArchiveFilename");
        Assertions.assertEquals("testArchiveFilename", document.getArchiveFilename());
    }

    @Test
    public void testOriginalFilenameSetterGetter() {
        DocumentsDocument document = new DocumentsDocument();
        document.setOriginalFilename("testOriginalFilename");
        Assertions.assertEquals("testOriginalFilename", document.getOriginalFilename());
    }
}

