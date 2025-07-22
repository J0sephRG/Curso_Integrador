package ConexionSQL;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    Connection xd = null;
    String usuario = "sa";
    String pass = "070205H";
    String db = "ElDoradoDB";
    String servidor = "DESKTOP-E541CQV"; 
    String puerto = "1433";

    public Connection Conectar() {
        try {
            String cadena = "jdbc:sqlserver://" + servidor + ":" + puerto + ";"
                    + "databaseName=" + db + ";"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            xd = DriverManager.getConnection(cadena, usuario, pass);
            System.out.println("Se conecto correctamente a la base de datos.");
        } catch (Exception error) {
            System.out.println("No se conecto correctamente a la base de datos.");
            System.out.println("Error: " + error.getMessage());
        }
        return xd;
    }
}