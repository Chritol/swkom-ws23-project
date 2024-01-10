package org.openapitools;

import org.junit.jupiter.api.Test;
import org.openapitools.helpers.FileHelper;
import org.openapitools.persistence.entities.DocumentsStoragepath;

import static org.junit.jupiter.api.Assertions.*;


class FileHelperTest {

    @Test
    void testGetFileExtension() {
        String filePath = "path/to/file.txt";
        String expectedExtension = "txt";

        String actualExtension = FileHelper.getFileExtension(filePath);

        assertEquals(expectedExtension, actualExtension);
    }

    @Test
    void testGetFileName() {
        String filePath = "file.txt";
        String expectedFileName = "file";

        String actualFileName = FileHelper.getFileName(filePath);

        assertEquals(expectedFileName, actualFileName);
    }

    @Test
    void testExtractBucketAndFileName() {
        String pdfFileName = "bucket/path/to/file.pdf";
        String[] expectedParts = {"bucket", "path/to/file.pdf"};

        String[] actualParts = FileHelper.extractBucketAndFileName(pdfFileName);

        assertArrayEquals(expectedParts, actualParts);
    }
}


