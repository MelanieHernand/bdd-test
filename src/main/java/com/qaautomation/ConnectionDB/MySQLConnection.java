package com.qaautomation.ConnectionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    private static final String DATABASE_URL = "jdbc:mysql://34.75.198.133:3306/";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "CFpufB5usjgmC0DP";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            System.out.println("Conexión establecida con MySQL.");
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return connection;
    }
}


