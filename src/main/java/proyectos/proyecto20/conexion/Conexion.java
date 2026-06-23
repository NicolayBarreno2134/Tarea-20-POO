package proyectos.proyecto20.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static Connection conexion;

    private Conexion() {}

    public static Connection getInstance() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            conexion = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/concurso_db",
                    "postgres",
                    "123456"
            );
            System.out.println("Conexión exitosa a PostgreSQL");
        }
        return conexion;
    }
}