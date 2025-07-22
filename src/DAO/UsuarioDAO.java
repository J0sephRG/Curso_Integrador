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
        
    public List<Usuario> listarUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT id_usuario, nombre, apellido, clave, rol, Activo FROM Usuarios";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                usuarios.add(new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("clave"),
                    rs.getString("rol"),
                    rs.getBoolean("Activo")
                ));
            }
        }
        return usuarios;
    }
    
    public boolean agregarUsuario(String nombre, String apellido, String clave, String rol) throws SQLException {
        // Verificar unicidad del nombre
        String checkQuery = "SELECT COUNT(*) FROM Usuarios WHERE nombre = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setString(1, nombre);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false; // Nombre ya existe
            }
        }

        // Normalizar rol
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
            case "otrorol":
                rolNormalizado = "Cocinero";
                break;
            default:
                throw new IllegalArgumentException("Rol no válido: " + rol);
        }

        String hashedPassword = BCrypt.hashpw(clave, BCrypt.gensalt());
        String query = "INSERT INTO Usuarios (nombre, apellido, clave, rol, Activo) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, hashedPassword);
            stmt.setString(4, rolNormalizado);
            stmt.setBoolean(5, true);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        }
    }
    
    public boolean eliminarUsuario(int idUsuario) throws SQLException {
        String query = "DELETE FROM Usuarios WHERE id_usuario = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idUsuario);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        }
    }
    
    public List<Usuario> buscarUsuario(String idUsuario, String nombre, String apellido, String clave, String rol, Boolean activo) throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT id_usuario, nombre, apellido, clave, rol, Activo FROM Usuarios WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (idUsuario != null && !idUsuario.isEmpty()) {
            query.append(" AND id_usuario = ?");
            params.add(Integer.parseInt(idUsuario));
        }
        if (nombre != null && !nombre.isEmpty()) {
            query.append(" AND nombre LIKE ?");
            params.add("%" + nombre + "%");
        }
        if (apellido != null && !apellido.isEmpty()) {
            query.append(" AND apellido LIKE ?");
            params.add("%" + apellido + "%");
        }
        if (clave != null && !clave.isEmpty()) {
            query.append(" AND clave = ?");
            params.add(clave);
        }
        if (rol != null && !rol.isEmpty()) {
            query.append(" AND rol = ?");
            params.add(rol);
        }
        if (activo != null) {
            query.append(" AND Activo = ?");
            params.add(activo);
        }

        try (PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                usuarios.add(new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("clave"),
                    rs.getString("rol"),
                    rs.getBoolean("Activo")
                ));
            }
        }
        return usuarios;
    }
    
    public Usuario obtenerPorNombreYClave(String nombre, String clave) throws SQLException {
        String query = "SELECT id_usuario, nombre, apellido, clave, rol, Activo FROM Usuarios WHERE nombre = ? AND Activo = 1";
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
    
    public void registrarIntento(String nombre, boolean exitoso) throws SQLException {
        String query = "INSERT INTO LoginLogs (Username, Exitoso) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setBoolean(2, exitoso);
            stmt.executeUpdate();
        }
    }
    
    public String restablecerContrasena(String nombre) throws SQLException {
        String query = "SELECT nombre FROM Usuarios WHERE nombre = ? AND Activo = 1";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String tempPassword = UUID.randomUUID().toString().substring(0, 8);
                String hashedTempPassword = BCrypt.hashpw(tempPassword, BCrypt.gensalt());
                String updateQuery = "UPDATE Usuarios SET clave = ? WHERE nombre = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setString(1, hashedTempPassword);
                    updateStmt.setString(2, nombre);
                    updateStmt.executeUpdate();
                }
                return tempPassword;
            }
            return null;
        }
    }
    
    public void actualizarClave(String nombre, String nuevaClave) throws SQLException {
        String hashedPassword = BCrypt.hashpw(nuevaClave, BCrypt.gensalt());
        String query = "UPDATE Usuarios SET clave = ?, Activo = 1 WHERE nombre = ?";
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
    
    public boolean actualizarEstadoActivo(int idUsuario, boolean activo) throws SQLException {
        String query = "UPDATE Usuarios SET Activo = ? WHERE id_usuario = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBoolean(1, activo);
            stmt.setInt(2, idUsuario);
            return stmt.executeUpdate() > 0;
        }
    }
}
