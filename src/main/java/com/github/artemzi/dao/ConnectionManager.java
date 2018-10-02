package com.github.artemzi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager extends Factory {
    private String url;
    private String username;
    private String password;
    private String connectionUrl = null;

    public ConnectionManager(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.connectionUrl = System.getenv("JDBC_DATABASE_URL");
    }

    public Connection getConnection() throws SQLException {
        if (connectionUrl == null) {
            return DriverManager.getConnection(url, username, password);
        }
        return DriverManager.getConnection(connectionUrl);
    }
}
