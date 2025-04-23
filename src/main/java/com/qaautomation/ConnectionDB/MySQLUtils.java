package com.qaautomation.ConnectionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLUtils {

    // Clase para encapsular ResultSet y Connection
    public static class QueryResult {
        private final ResultSet resultSet;
        private final Connection connection;

        public QueryResult(ResultSet resultSet, Connection connection) {
            this.resultSet = resultSet;
            this.connection = connection;
        }

        public ResultSet getResultSet() {
            return resultSet;
        }

        public Connection getConnection() {
            return connection;
        }
    }

    // Método para cambiar la base de datos
    public static void changeDatabase(Connection connection, String databaseName) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("USE " + databaseName);
        stmt.close(); // Cerramos el Statement después de cambiar de base
    }

    // Método para ejecutar una consulta SELECT
    public static QueryResult executeQuery(String query, String databaseName) {
        try {
            Connection connection = MySQLConnection.getConnection(); // Obtiene la conexión
            changeDatabase(connection, databaseName); // Cambia a la base de datos deseada
            Statement statement = connection.createStatement(); // Crea un Statement
            ResultSet resultSet = statement.executeQuery(query); // Ejecuta la consulta
            return new QueryResult(resultSet, connection); // Devuelve ambos
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta: " + e.getMessage());
        }
        return null; // Si ocurre un error, retorna null
    }

    // Método para ejecutar consultas de actualización (INSERT, UPDATE, DELETE)
    public static int executeUpdate(String query, String databaseName) {
        int rowsAffected = 0;
        try (Connection connection = MySQLConnection.getConnection()) {
            changeDatabase(connection, databaseName); // Cambia a la base de datos deseada
            Statement statement = connection.createStatement();
            rowsAffected = statement.executeUpdate(query); // Ejecuta la actualización
            statement.close(); // Cerramos el Statement después de ejecutarlo
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la actualización: " + e.getMessage());
        }
        return rowsAffected; // Devuelve el número de filas afectadas
    }

    // Método para cerrar recursos asociados al QueryResult
    public static void closeResources(QueryResult queryResult) {
        if (queryResult != null) {
            try {
                if (queryResult.getResultSet() != null) {
                    Statement statement = queryResult.getResultSet().getStatement();
                    queryResult.getResultSet().close(); // Cierra el ResultSet
                    if (statement != null) statement.close(); // Cierra el Statement asociado
                }
                if (queryResult.getConnection() != null) {
                    queryResult.getConnection().close(); // Cierra la conexión
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar los recursos: " + e.getMessage());
            }
        }
    }

    // Método para cerrar manualmente un Statement y una Connection
    public static void closeResources(Statement statement, Connection connection) {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar Statement y Connection: " + e.getMessage());
        }
    }
}
