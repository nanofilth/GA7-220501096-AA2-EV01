/*
 * Construcción de aplicaciones con Java GA7-220501096-AA2-EV01
 * Instructor: LAURA VICTORIA YEPEZ
 * Aprendíz: Gilber Andres Gutierrez Bohorquez
 * A.D.S.O Ficha: 2521974
 * */

import java.sql.*;

/*
 * Clase encargada de establecer la conexión a la base de datos
 * base de datos MySQL con estructura para el proyecto MAKER
 * se encarga tambien de retornar el ultimo id creado en las sentencias INSERT
 * */

// Declarar una clase para gestionar la conexión a la base de datos
public class DBConnection {
    // Definir las constantes para la URL, nombre de usuario y contraseña de la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/maker"; // URL de la base de datos
    private static final String USERNAME = "root"; // Nombre de usuario de la base de datos
    private static final String PASSWORD = ""; // Contraseña de la base de datos

    // Método estático para establecer la conexión a la base de datos
    public static Connection connection() throws SQLException {
        Connection connection = null; // Inicializar la conexión como nula
        try {
            // Cargar la clase del controlador de MySQL (Driver)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión utilizando la URL, nombre de usuario y contraseña
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Verificar si la conexión se ha establecido correctamente
            if (connection != null) {
                System.out.println("Connected to the database successfully!");
                //connection.close(); // Comentar esta línea ya que la conexión no se cierra aquí
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // Imprimir información sobre el error en caso de que ocurra
        }
        return connection; // Devolver la conexión establecida
    }

    // Método para obtener el ID generado después de una inserción
    public static int getGeneratedId(PreparedStatement preparedStatement) {
        int generatedId = -1; // Inicializar el ID generado como -1 (indicando un error por defecto)
        try {
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys(); // Obtener las claves generadas
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1); // Obtener el valor del primer campo (ID)
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprimir información sobre el error en caso de que ocurra
        }
        return generatedId; // Devolver el ID generado
    }
}
