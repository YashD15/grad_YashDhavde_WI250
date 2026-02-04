package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DBManager {
    private static DBManager instance;
    private Connection connection;

    private DBManager() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test","postgres","wi250");
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}