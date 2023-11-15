package org.openapitools.helpers;

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
}
