package org.openapitools.helpers;

import org.openapitools.persistence.entities.DocumentsStoragepath;

import java.security.InvalidParameterException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class FileHelper {
    public static String getFileExtension(String filePath) throws InvalidParameterException {
        if (filePath != null && !filePath.isEmpty()) {
            int lastDotIndex = filePath.lastIndexOf('.');

            if (lastDotIndex > 0 && lastDotIndex < filePath.length() - 1) {
                return filePath.substring(lastDotIndex + 1);
            }
        }

        throw new InvalidParameterException("Invalid filePathName");
    }

    public static String getFileName(String filePath) throws InvalidParameterException {
        if (filePath != null && !filePath.isEmpty()) {
            int lastDotIndex = filePath.lastIndexOf('.');

            if (lastDotIndex > 0 && lastDotIndex < filePath.length() - 1) {
                return filePath.substring(0, lastDotIndex);
            }
        }

        throw new InvalidParameterException("Invalid filePathName");
    }

    public static String getCurrentDateTimeInMilliseconds() {
        Instant instant = Instant.now();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        long milliseconds = localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
        return Long.toString(milliseconds);
    }

    public static String getMinioObjectName(String fileName) throws InvalidParameterException {
        return FileHelper.getCurrentDateTimeInMilliseconds()
                + "_"
                + FileHelper.getFileName(fileName)
                + "."
                + FileHelper.getFileExtension(fileName);
    }

    public static DocumentsStoragepath getDocumentStoragePath(String filePath, String fileName) {
        DocumentsStoragepath storagePath = new DocumentsStoragepath();
        storagePath.setPath(removeUnwantedChars(filePath));
        storagePath.setName(fileName);
        storagePath.setMatch("");
        storagePath.setMatchingAlgorithm(0);
        storagePath.setIsInsensitive(false);

        return storagePath;
    }

    public static String[] extractBucketAndFileName(String pdfFileName) {
        // Assuming the format is "bucketName/path/to/file.pdf"
        String[] parts = pdfFileName.split("/", 2);

        if (parts.length > 1) {
            return parts;
        } else {
            return null;
        }
    }

    private static String removeUnwantedChars(String text){
        return text.replaceAll("[^(\\x00-\\xFF)]+(?:$|\\s*)", "").trim();
    }
}
