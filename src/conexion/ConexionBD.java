package conexion;
import java.sql.*;

public class ConexionBD {
    private static final String URL = "jdbc:postgresql://localhost:5432/tienda";
    private static final String USER = "postgres";
    private static final String PASS = "tu_contraseña";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}