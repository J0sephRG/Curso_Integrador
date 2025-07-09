/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import ConexionSQL.Conexion;
import DAO.UsuarioDAO;
import com.sun.jdi.connect.spi.Connection;

/**
 *
 * @author JOSEPH ROJAS
 */
public class ActualizarContrasenias {
    public static void main(String[] args) {
        try (java.sql.Connection conn = new Conexion().Conectar()) {
            if (conn == null) {
                System.out.println("No se pudo conectar a la base de datos");
                return;
            }

            UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
            //usuarioDAO.actualizarClave("Carlos", "admin123");
            usuarioDAO.actualizarClave("Neil", "Neil1230");
            usuarioDAO.actualizarClave("Luis", "luis456");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
