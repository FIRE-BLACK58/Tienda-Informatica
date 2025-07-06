package com.tienda.informatica.infrastructure.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    // IMPORTANTE: Modifica estos valores con los de tu base de datos
    private static final String URL = "jdbc:postgresql://localhost:5432/tienda_informatica";
    private static final String USER = "postgres"; // Tu usuario de PostgreSQL
    private static final String PASSWORD = "arrieta2025"; // Tu contraseña de PostgreSQL

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error fatal: No se encontró el driver de PostgreSQL.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}