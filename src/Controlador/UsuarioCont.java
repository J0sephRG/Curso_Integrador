/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DAO.UsuarioDAO;
import Modelo.Usuario;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JOSEPH ROJAS
 */
public class UsuarioCont {
    private UsuarioDAO usuarioDAO;
    private JTable tabla;

    public UsuarioCont(UsuarioDAO usuario, JTable tabla) {
        this.usuarioDAO = usuario;
        this.tabla = tabla;
    }
    public void cargarUsuarios(){
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{"ID", "Nombre", "Apellido", "Rol", "Activo"});
        try {
            List<Usuario> usuarios = usuarioDAO.listarUsuarios();
            for (Usuario u : usuarios) {
                modelo.addRow(new Object[]{
                    u.getIdUsuario(),
                    u.getNombre(),
                    u.getApellido(),
                    u.getRol(),
                    u.isActivo()
                });
            }
            tabla.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void agregarUsuario(String nombre, String apellido, String clave, String confirmarClave, String rol) {
        if (nombre == null || nombre.trim().isEmpty() || 
            apellido == null || apellido.trim().isEmpty() || 
            clave == null || clave.trim().isEmpty() || 
            confirmarClave == null || confirmarClave.trim().isEmpty() || 
            rol == null || rol.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!clave.equals(confirmarClave)) {
            JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (clave.length() < 6) {
            JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos 6 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de agregar un nuevo usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            boolean exito = usuarioDAO.agregarUsuario(nombre.trim(), apellido.trim(), clave.trim(), rol.trim());
            if (exito) {
                JOptionPane.showMessageDialog(null, "Usuario creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarUsuarios();
            } else {
                JOptionPane.showMessageDialog(null, "Error al crear el usuario. El nombre ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void eliminarUsuario(int idUsuario) {
        if (idUsuario <= 0) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un usuario para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar este usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            boolean exito = usuarioDAO.eliminarUsuario(idUsuario);
            if (exito) {
                JOptionPane.showMessageDialog(null, "Usuario eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarUsuarios();
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void buscarUsuario(String idUsuario, String nombre, String apellido, String rol, Boolean activo) {
        try {
            List<Usuario> usuarios = usuarioDAO.buscarUsuario(idUsuario, nombre, apellido, null, rol, activo);
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new String[]{"ID", "Nombre", "Apellido", "Rol", "Activo"});
            for (Usuario u : usuarios) {
                modelo.addRow(new Object[]{
                    u.getIdUsuario(),
                    u.getNombre(),
                    u.getApellido(),
                    u.getRol(),
                    u.isActivo()
                });
            }
            tabla.setModel(modelo);
            if (usuarios.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontraron usuarios con los criterios especificados.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
     public void restablecerContrasena(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese el nombre del usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String tempPassword = usuarioDAO.restablecerContrasena(nombre.trim());
            if (tempPassword != null) {
                JOptionPane.showMessageDialog(null, 
                    "Contraseña restablecida. Nueva contraseña temporal: " + tempPassword + 
                    "\nEnvíe esta contraseña al usuario.", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarUsuarios();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un usuario con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
