/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionSQL;

import java.sql.Connection; 
import java.sql.DriverManager;
import javax.swing.JOptionPane;


/**
 *
 * @author JOSEPH ROJAS
 */
public class Conexion {
 Connection xd = null;
    String usuario ="sa";
    String pass = "Josephrojas123";
    String db = "ElDoradoDB";
    String ip = "localhost";
    String puerto = "1433";
                            
                            
    public Connection Conectar() {
        try {
            String cadena = "jdbc:sqlserver://" + ip + ":" + puerto + ";databaseName=" + db + ";encrypt=true;trustServerCertificate=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            xd = DriverManager.getConnection(cadena, usuario, pass);
            System.out.println("Se conectó correctamente a la base de datos");
        } catch (Exception error) {
           System.out.println("No se conectó correctamente a la base de datos");
        }
        return xd;
    }
    
}
