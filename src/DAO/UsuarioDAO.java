package DAO;
import ConexionSQL.Conexion;
import java.sql.*;
import java.sql.Connection;
import Modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }
    
    public void agregarUsuario(Usuario usuario) throws SQLException {
        String query = "INSERT INTO Usuario(nombre, apellido, rol, clave) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getApellido());
            statement.setString(3, usuario.getRol());
            statement.setString(4, usuario.getClave());
            statement.executeUpdate();
        }
    }

public Usuario obtenerUsuario(int id_usuario) throws SQLException {
        String query = "SELECT * FROM Usuario WHERE id_usuario = ?";
        Usuario usuario = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_usuario);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario(rs.getInt("id_usuario"), rs.getString("nombre"),
                                          rs.getString("apellido"), rs.getString("rol"),
                                          rs.getString("clave"), rs.getTimestamp("fecha_creacion"));
                }
            }
        }
        return usuario;
    }


    public List<Usuario> listarUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT * FROM Usuario";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                usuarios.add(new Usuario(rs.getInt("id_usuario"), rs.getString("nombre"),
                                         rs.getString("apellido"), rs.getString("rol"),
                                         rs.getString("clave"), rs.getTimestamp("fecha_creacion")));
            }
        }
        return usuarios;
    }

    public void actualizarUsuario(Usuario usuario) throws SQLException {
        String query = "UPDATE Usuario SET nombre = ?, apellido = ?, rol = ?, clave = ? WHERE id_usuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getApellido());
            statement.setString(3, usuario.getRol());
            statement.setString(4, usuario.getClave());
            statement.setInt(5, usuario.getId_usuario());
            statement.executeUpdate();
        }
    }

    public void eliminarUsuario(int id_usuario) throws SQLException {
        String query = "DELETE FROM Usuario WHERE id_usuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_usuario);
            statement.executeUpdate();
        }
    }
}
