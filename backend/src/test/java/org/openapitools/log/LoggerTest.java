package org.openapitools.log;

import org.junit.jupiter.api.Test;

public class LoggerTest {
    @Test
    public void testLoggingLevels() throws Exception {
        LoggerController loggerController = new LoggerControllerImpl();

        loggerController.trace("Test trace");
        loggerController.debug("Test debug");
        loggerController.info("Test info");
        loggerController.warn("Test warn");
        loggerController.error("Test error");
        loggerController.fatal("Test fatal");

        loggerController.error("Test error with exception", new IllegalArgumentException());
        loggerController.error("Test fatal with exception", new IllegalArgumentException());
    }
}
