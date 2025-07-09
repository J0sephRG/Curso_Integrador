package DAO;
import ConexionSQL.Conexion;
import java.sql.*;
import java.sql.Connection;
import Modelo.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioDAO {
    private Connection conn;

    public UsuarioDAO(Connection connection) {
        this.conn = connection;
    }
    
    public Usuario obtenerPorNombreYClave(String nombre, String clave) throws SQLException {
        String query = "SELECT id_usuario, nombre, apellido, clave, rol, Activo FROM Usuario WHERE nombre = ? AND Activo = 1";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("clave");
                String rol = rs.getString("rol");
                try {
                    if (hashedPassword != null && hashedPassword.startsWith("$2a$") && BCrypt.checkpw(clave, hashedPassword)) {
                        return new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            hashedPassword,
                            rol != null ? rol : "",
                            rs.getBoolean("Activo")
                        );
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println("Hash inválido para el usuario " + nombre + ": " + e.getMessage());
                }
                return null; 
            }
            return null; 
        }
    }
   
    
    public void agregarUsuario(Usuario usuario) throws SQLException {
        String query = "INSERT INTO Usuario(nombre, apellido, rol, clave) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getApellido());
            statement.setString(3, usuario.getRol());

            // ✅ Cifrado de contraseña
            String hashed = BCrypt.hashpw(usuario.getClave(), BCrypt.gensalt());
            statement.setString(4, hashed);

            statement.executeUpdate();
        }
    }
/*
    public Usuario obtenerUsuario(int id_usuario) throws SQLException {
        String query = "SELECT * FROM Usuario WHERE id_usuario = ?";
        Usuario usuario = null;
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id_usuario);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario(rs.getInt("id_usuario"), rs.getString("nombre"),
                                          rs.getString("apellido"), rs.getString("rol"),
                                          rs.getString("clave"));
                }
            }
        }
        return usuario;
    }
*/
    public void registrarIntento(String nombre, boolean exitoso) throws SQLException {
        String query = "INSERT INTO LoginLogs (Username, Exitoso) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setBoolean(2, exitoso);
            stmt.executeUpdate();
        }
    }
    
  public boolean crearUsuario(String nombre, String apellido, String clave, String rol) throws SQLException, IllegalAccessException {
        String rolNormalizado;
            switch (rol.toLowerCase()) {
                
                case "administrador":
                case "admin":
                    rolNormalizado = "Administrador";
                    break;
                case "cajero": 
                    rolNormalizado = "Cajero";
                    break;
                case "cocinero":
                    rolNormalizado = "Cocinero";
                    break;

              default:
                  throw new IllegalAccessException("Rol no valido:" + rol);
          }
        String hasshedPassword = BCrypt.hashpw(clave, BCrypt.gensalt());
        String query = "INSERT INTO Usuario (nombre, apellido, clave, rol, Activo) VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
          stmt.setString(1, nombre);
          stmt.setString(2, apellido);
          stmt.setString(3, hasshedPassword);
          stmt.setString(4, rolNormalizado);
          stmt.setBoolean(5, true);
          int filasAfectadas = stmt.executeUpdate();
          return filasAfectadas > 0;
      } catch (Exception e) {
            System.out.println("Error al crear un usuario");
            throw e;
      }
    }

    public void actualizarClave(String nombre, String nuevaClave) throws SQLException {
        String hashedPassword = BCrypt.hashpw(nuevaClave, BCrypt.gensalt());
        String query = "UPDATE Usuario SET clave = ?, Activo = 1 WHERE nombre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, hashedPassword);
            stmt.setString(2, nombre);
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Contraseña actualizada para el usuario: " + nombre);
            } else {
                System.out.println("No se encontró el usuario: " + nombre);
            }
        }
    }

    
    public String restablecerContrasena(String correo) throws SQLException {
        String query = "SELECT nombre FROM Usuario WHERE correo = ? AND Activo = 1";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String tempPassword = UUID.randomUUID().toString().substring(0, 8); 
                String hashedTempPassword = BCrypt.hashpw(tempPassword, BCrypt.gensalt());
                String updateQuery = "UPDATE Usuarios SET clave = ? WHERE correo = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setString(1, hashedTempPassword);
                    updateStmt.setString(2, correo);
                    updateStmt.executeUpdate();
                }
                return tempPassword; 
            }
            return null;
        }
    }
/*
    public List<Usuario> listarUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT * FROM Usuario";
        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                usuarios.add(new Usuario(rs.getInt("id_usuario"), rs.getString("nombre"),
                                         rs.getString("apellido"), rs.getString("rol"),
                                         rs.getString("clave") rs.getBoolean("activo")));
            }
        }
        return usuarios;
    }

    public void actualizarUsuario(Usuario usuario) throws SQLException {
        String query = "UPDATE Usuario SET nombre = ?, apellido = ?, rol = ?, clave = ? WHERE id_usuario = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
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
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id_usuario);
            statement.executeUpdate();
        }
    }*/
}
