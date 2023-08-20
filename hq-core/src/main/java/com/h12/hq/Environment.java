package com.h12.hq;

import com.h12.hq.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Environment {
    private static final Logger LOGGER = LoggerFactory.getLogger(Environment.class);
    private static final String FILE_NAME = "application";
    private static final String HYPHEN = "-";
    private static final String EXTENSION = ".properties";
    private static final Properties properties = new Properties();

    public Environment() throws IOException {
        init();
    }

    private void init() throws IOException {
        try {
            ClassLoader classLoader = Environment.class.getClassLoader();

            InputStream defaultPropertyFile = classLoader.getResourceAsStream(Config.DEFAULT_PROPERTY_FILE_NAME);
            properties.load(defaultPropertyFile);
            properties.putAll(System.getenv());
            String ENV_NAME = properties.getProperty(Config.ENV_NAME);
            if (ENV_NAME != null) {
                properties.load(classLoader.getResourceAsStream(FILE_NAME + HYPHEN + ENV_NAME + EXTENSION));
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

    public String getProperty(String propKey) {
        return properties.getProperty(propKey);
    }

    public String getProperty(String propKey, String defaultVal) {
        return properties.getProperty(propKey, defaultVal);
    }

    private Object get(String propKey) {
        return properties.get(propKey);
    }
}
