/*
 * Construcción de aplicaciones con Java GA7-220501096-AA2-EV01
 * Instructor: LAURA VICTORIA YEPEZ
 * Aprendíz: Gilber Andres Gutierrez Bohorquez
 * A.D.S.O Ficha: 2521974
 */

import java.sql.*;
import java.util.*;

/*
* Clase encargada de realizar todas las transacciones de un CRUD a la base de datos
* Los métodos SELECT, INSERT, UPDATE, y DELETE quedan globalizados para todas las tablas
* */

public class Transaction {
    // Método para obtener registros de una tabla específica
    public static List<Map<String, Object>> getRecords(String tableName) {
        List<Map<String, Object>> records = new ArrayList<>(); // Crear una lista para los registros
        try (Connection connection = DBConnection.connection()) { // Iniciar la conexión a la base de datos
            if (connection != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + tableName)) {
                    ResultSet resultSet = preparedStatement.executeQuery(); // Ejecutar la consulta
                    while (resultSet.next()) {
                        ResultSetMetaData metaData = resultSet.getMetaData();
                        int columnCount = metaData.getColumnCount();
                        Map<String, Object> record = new HashMap<>(); // Crear un mapa para cada registro

                        for (int i = 1; i <= columnCount; i++) {
                            String columnName = metaData.getColumnName(i);
                            Object columnValue = resultSet.getObject(i);
                            record.put(columnName, columnValue); // Agregar columna y valor al mapa
                        }

                        records.add(record); // Agregar el mapa a la lista de registros
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records; // Devolver la lista de registros
    }

    // Método para insertar un registro en una tabla
    public static int insertRecord(String tableName, String[] data) {
        int newId = -1;
        try {
            Connection connection = DBConnection.connection(); // Iniciar la conexión a la base de datos

            if (connection != null) {
                StringBuilder insertQuery = new StringBuilder("INSERT INTO " + tableName + " VALUES (null, ");
                for (int i = 0; i < data.length; i++) {
                    insertQuery.append("?");
                    if (i < data.length - 1) {
                        insertQuery.append(",");
                    }
                }
                insertQuery.append(")");

                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery.toString(), PreparedStatement.RETURN_GENERATED_KEYS)) {
                    for (int i = 0; i < data.length; i++) {
                        preparedStatement.setString(i + 1, data[i]);
                    }

                    int affectedRows = preparedStatement.executeUpdate();
                    if (affectedRows > 0) {
                        newId = DBConnection.getGeneratedId(preparedStatement); // Obtener el ID generado
                    }
                }

                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newId;
    }

    // Método para actualizar un registro en una tabla
    public static void updateRecord(String tableName, int id, Map<String, String> newData) {
        try {
            Connection connection = DBConnection.connection(); // Iniciar la conexión a la base de datos

            if (connection != null) {
                StringBuilder updateQuery = new StringBuilder("UPDATE " + tableName + " SET ");
                int index = 1;
                for (String column : newData.keySet()) {
                    updateQuery.append(column).append(" = ?");
                    if (index < newData.size()) {
                        updateQuery.append(",");
                    }
                    index++;
                }
                updateQuery.append(" WHERE id = ?");

                try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery.toString())) {
                    int paramIndex = 1;
                    for (String column : newData.keySet()) {
                        preparedStatement.setString(paramIndex, newData.get(column));
                        paramIndex++;
                    }
                    preparedStatement.setInt(paramIndex, id);

                    preparedStatement.executeUpdate();
                }

                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un registro de una tabla
    public static void deleteRecord(String tableName, int id) {
        try {
            Connection connection = DBConnection.connection(); // Iniciar la conexión a la base de datos

            if (connection != null) {
                String deleteQuery = "DELETE FROM " + tableName + " WHERE id = ?";

                try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                    preparedStatement.setInt(1, id);

                    preparedStatement.executeUpdate();
                }

                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}