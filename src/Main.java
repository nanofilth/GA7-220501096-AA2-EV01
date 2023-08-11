/*
 * Construcción de aplicaciones con Java GA7-220501096-AA2-EV01
 * Instructor: LAURA VICTORIA YEPEZ
 * Aprendíz: Gilber Andres Gutierrez Bohorquez
 * A.D.S.O Ficha: 2521974
 * */

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/*
* Clase Principal encargada de realizar las pruebas necesarias
* para demostrar la conexión a un base de datos MySQL usando JDBC
* y la ejecución de las sentencias SQL de un CRUD
* */

public class Main {
    public static void main(String[] args) throws SQLException {
        /* 1. Conectarse a la base de datos */
        Connection connection = DBConnection.connection();

        if (connection != null) {
            /* 2. Insertar en la tabla products */
            String[] productData1 = {"Página web", "1"};
            int insertedId1 = Transaction.insertRecord("products", productData1);
            System.out.println("Inserted ID for first record: " + insertedId1);

            /* 3. Obtener todos los registros de la tabla products */
            List<Map<String, Object>> records = Transaction.getRecords("products" );
            for (Map<String, Object> record : records) {
                System.out.println("Record: " + record);
            }

            /* 4. Insertar en la tabla products */
            String[] productData2 = {"Landing page", "1"};
            Transaction.insertRecord("products", productData2);

            /* 5. Insertar en la tabla products */
            String[] productData3 = {"App", "1"};
            Transaction.insertRecord("products", productData3);

            /* 6. Actualizar el registro 2 de la tabla products */
            int recordIdToUpdate = 2;
            Map<String, String> newData = Map.of("name", "Landing", "state", "1");
            Transaction.updateRecord("products", recordIdToUpdate, newData);

            /* 7. Eliminar el registro 3 de la tabla products */
            int recordIdToDelete = 3;
            Transaction.deleteRecord("products", recordIdToDelete);

            connection.close();
        }
    }
}