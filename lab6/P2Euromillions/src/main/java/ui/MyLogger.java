package ui;

import java.util.logging.Level;
import java.util.logging.Logger;;

class MyLogger {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public void log(String msg)
    {
        LOGGER.log(Level.INFO, msg);
    }
}