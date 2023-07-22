package com.h12.hq.server;

import com.h12.hq.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Environment extends Properties {
    private static final Logger LOGGER = LoggerFactory.getLogger(Environment.class);
    private static final String FILE_NAME = "application";
    private static final String HYPHEN = "-";
    private static final String EXTENSION = ".properties";
    public Environment() throws IOException {
        init();
    }

    private void init() throws IOException {
        try {
            ClassLoader classLoader = Environment.class.getClassLoader();

            InputStream defaultPropertyFile = classLoader.getResourceAsStream(Constants.DEFAULT_PROPERTY_FILE_NAME);
            this.load(defaultPropertyFile);
            this.putAll(System.getenv());
            String ENV_NAME = this.getProperty(Constants.ENV_NAME);
            if(ENV_NAME != null) {
                this.load(classLoader.getResourceAsStream(FILE_NAME + HYPHEN + ENV_NAME + EXTENSION));
            }
        } catch (IOException exception) {
            LOGGER.info("Unable to load properties.", exception);
            throw exception;
        } catch (SecurityException exception) {
            LOGGER.info("Unable to load properties.", exception);
            IOException ioException = new IOException();
            ioException.addSuppressed(exception);
            throw ioException;
        }
    }
}
