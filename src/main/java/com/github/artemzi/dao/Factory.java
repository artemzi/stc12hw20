package com.github.artemzi.dao;

import com.github.artemzi.exceptions.ConfigurationException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Create connection contract based on provided options
 */
public abstract class Factory {
    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_DRIVER = "driver";
    private static final String PROPERTY_USERNAME = "username";
    private static final String PROPERTY_PASSWORD = "password";

    public static Factory getInstance(String name) throws ConfigurationException {
        if (name == null) {
            throw new ConfigurationException("Database name is null.");
        }

        PropertiesLoader properties = new PropertiesLoader(name);
        String url = properties.getProperty(PROPERTY_URL, true);
        String driverClassName = properties.getProperty(PROPERTY_DRIVER, false);
        String password = properties.getProperty(PROPERTY_PASSWORD, false);
        String username = properties.getProperty(PROPERTY_USERNAME, password != null);

        try { // check if correct driver was loaded
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            throw new ConfigurationException(
                    "Driver class '" + driverClassName + "' is missing in classpath.", e);
        }

        return new ConnectionManager(url, username, password);
    }

    public abstract Connection getConnection() throws SQLException;
}
