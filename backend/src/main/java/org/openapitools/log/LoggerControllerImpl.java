package org.openapitools.log;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class LoggerControllerImpl implements LoggerController{
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void trace(String message) {

    }

    @Override
    public void debug(String message) {

    }

    @Override
    public void info(String message) {

    }

    @Override
    public void warn(String message) {

    }

    @Override
    public void error(String message) {

    }

    @Override
    public void fatal(String message) {

    }

    @Override
    public void error(String message, Exception exception) {

    }

    @Override
    public void fatal(String message, Exception exception) {

    }
}
