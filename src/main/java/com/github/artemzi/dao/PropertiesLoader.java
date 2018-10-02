package com.github.artemzi.dao;

import com.github.artemzi.exceptions.ConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * PropertiesLoader used for load configuration parameters from properties file
 */
public class PropertiesLoader {
    private static final String PROPERTIES_FILE = "database.properties";
    private static final Properties PROPERTIES = new Properties();
    private String specificKey;

    public PropertiesLoader(String specificKey) throws ConfigurationException {
        this.specificKey = specificKey;
    }

    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);

        if (propertiesFile == null) {
            throw new ConfigurationException(
                    "Properties file '" + PROPERTIES_FILE + "' is missing in classpath.");
        }

        try {
            PROPERTIES.load(propertiesFile);
        } catch (IOException e) {
            throw new ConfigurationException(
                    "Cannot load properties file '" + PROPERTIES_FILE + "'.", e);
        }
    }

    public String getProperty(String key, boolean mandatory) throws ConfigurationException {
        String fullKey = specificKey + "." + key;
        String property = PROPERTIES.getProperty(fullKey);

        if (property == null || property.trim().length() == 0) {
            if (mandatory) {
                throw new ConfigurationException("Required property '" + fullKey + "'"
                        + " is missing in properties file '" + PROPERTIES_FILE + "'.");
            } else {
                // Make empty value null. Empty Strings are evil.
                property = null;
            }
        }

        return property;
    }
}
