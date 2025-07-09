/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DAO.UsuarioDAO;
import Modelo.Usuario;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JOSEPH ROJAS
 */
public class UsuarioCont {
    private UsuarioDAO usuario;
    private JTable tabla;

    public UsuarioCont(UsuarioDAO usuario, JTable tabla) {
        this.usuario = usuario;
        this.tabla = tabla;
    }
/*
    public void cargarUsuarios(JTable tabla, UsuarioDAO dao){
        DefaultTableModel modelo = new DefaultTableModel();
    modelo.setColumnIdentifiers(new String[] { "ID", "Nombre", "Apellido", "Rol" });

    try {
        for (Usuario u : dao.listarUsuarios()) {
            modelo.addRow(new Object[] {
                u.getId_usuario(),
                u.getNombre(),
                u.getApellido(),
                u.getRol()
            });
        }
        tabla.setModel(modelo);
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }*/
}
