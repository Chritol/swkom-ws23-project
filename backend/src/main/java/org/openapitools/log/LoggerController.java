package org.openapitools.log;

public interface LoggerController {
    void trace(String message);

    void debug(String message);

    void info(String message);

    void warn(String message);

    void error(String message);

    void fatal(String message);

    void error(String message, Exception exception);

    void fatal(String message, Exception exception);
}
