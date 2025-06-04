
package DAO;

import ConexcionSQL.ConnectionFactory;
import Modelo.Producto;
import Modelo.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UsuarioDaoImpl implements UsuarioDao {
    
    @Override
    public void agregarUsuario(Usuario usuario) {
        String sql = "INSERT INTO Usuario (id_usuario, nombre, apellido, rol, clave) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuario.getId_usuario());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getApellido());
            stmt.setString(4, usuario.getRol());
            stmt.setString(5, usuario.getClave());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     @Override
    public void actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE Usuario SET nombre = ?, apellido = ?, rol = ?, clave = ? WHERE id_usuario = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getRol());
            stmt.setString(4, usuario.getClave());
            stmt.setInt(5, usuario.getId_usuario());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void eliminarUsuario(int id_usuario) {
        String sql = "DELETE FROM Usuario WHERE id_usuario = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id_usuario);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public Usuario obtenerUsuario(int id_usuario) {
        String sql = "SELECT * FROM Usuario WHERE id_usuario = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id_usuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("rol"),
                        rs.getString("clave")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public List<Usuario> obtenerTodosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("rol"),
                        rs.getString("clave")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
}