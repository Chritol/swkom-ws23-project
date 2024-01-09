package org.openapitools.paperlessocr;

import org.openapitools.paperlessocr.services.OcrServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

public class FileUtilTest {

    private static final String TEMP_FILE_PREFIX = "input";
    private static final String TEMP_FILE_SUFFIX = ".pdf";
    @Test
    void testCreateTempFile() {
        // Create a temporary file using the createTempFile method
        File tempPdfFile = OcrServiceImpl.createTempFile();

        // Assert that the file is not null
        assertNotNull(tempPdfFile);

        // Assert that the file actually exists
        assertTrue(tempPdfFile.exists());

        // Assert that the file name starts with "input" and ends with ".pdf"
        assertTrue(tempPdfFile.getName().startsWith(TEMP_FILE_PREFIX));
        assertTrue(tempPdfFile.getName().endsWith(TEMP_FILE_SUFFIX));

        // Clean up by deleting the temporary file
        OcrServiceImpl.deleteTempFile(tempPdfFile);
    }

    @Test
    void testDeleteTempFile() {
        // Create a temporary file
        File tempPdfFile = OcrServiceImpl.createTempFile();

        // Assert that the file exists before deletion
        assertTrue(tempPdfFile.exists());

        // Delete the temporary file
        OcrServiceImpl.deleteTempFile(tempPdfFile);

        // Assert that the file no longer exists after deletion
        assertFalse(tempPdfFile.exists());
    }

    @AfterEach
    void cleanup() {
        // Clean up any remaining temporary files
        File[] tempFiles = new File(System.getProperty("java.io.tmpdir"))
                .listFiles((dir, name) -> name.startsWith(TEMP_FILE_PREFIX) && name.endsWith(TEMP_FILE_SUFFIX));

        if (tempFiles != null) {
            for (File file : tempFiles) {
                file.delete();
            }
        }
    }
}
