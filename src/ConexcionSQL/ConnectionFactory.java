package ConexcionSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class ConnectionFactory {
 /**   private static final String URL = "jdbc:mysql://localhost:3306/restaurant"; // Nombre de la base de datos
    private static final String USER = "root"; // Usuario por defecto de XAMPP
    private static final String PASSWORD = ""; // Contraseña por defecto de XAMPP (dejar vacío si no hay)

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar a la base de datos", e);
        }
    }**/
    private static final String URL = "jdbc:mysql://localhost:3306/restaurant?useSSL=false&serverTimezone=UTC";
    private static final String USER = "roo";  // Usuario XAMPP por defecto
    private static final String PASSWORD = "";  // Contraseña vacía por defecto

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
