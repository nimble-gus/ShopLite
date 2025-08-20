package com.darwinruiz.shoplite.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase singleton para manejar la conexión centralizada a PostgreSQL
 */
public class DbConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/shoplite";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    
    private static DbConnection instance;
    private Connection connection;
    
    private DbConnection() {
        // Constructor privado para singleton
    }
    
    public static synchronized DbConnection getInstance() {
        if (instance == null) {
            instance = new DbConnection();
        }
        return instance;
    }
    
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                connection.setAutoCommit(true);
            } catch (ClassNotFoundException e) {
                throw new SQLException("PostgreSQL Driver no encontrado", e);
            }
        }
        return connection;
    }
    
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error cerrando conexión: " + e.getMessage());
            }
        }
    }
}
