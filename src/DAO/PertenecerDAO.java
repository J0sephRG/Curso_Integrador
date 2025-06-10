package DAO;

import ConexionSQL.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PertenecerDAO {
    private Connection connection;

    public PertenecerDAO(Connection connection) {
        this.connection = connection;
    }

    public void agregarRelacion(int idCategoria, int idProducto) throws SQLException {
        String query = "INSERT INTO Pertenecer(id_categoria, id_producto) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idCategoria);
            stmt.setInt(2, idProducto);
            stmt.executeUpdate();
        }
    }

    public boolean existeRelacion(int idCategoria, int idProducto) throws SQLException {
        String query = "SELECT 1 FROM Pertenecer WHERE id_categoria = ? AND id_producto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idCategoria);
            stmt.setInt(2, idProducto);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public List<Integer> listarProductosPorCategoria(int idCategoria) throws SQLException {
        String query = "SELECT id_producto FROM Pertenecer WHERE id_categoria = ?";
        List<Integer> productos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idCategoria);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    productos.add(rs.getInt("id_producto"));
                }
            }
        }
        return productos;
    }

    public List<Integer> listarCategoriasPorProducto(int idProducto) throws SQLException {
        String query = "SELECT id_categoria FROM Pertenecer WHERE id_producto = ?";
        List<Integer> categorias = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idProducto);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    categorias.add(rs.getInt("id_categoria"));
                }
            }
        }
        return categorias;
    }

    public void eliminarRelacion(int idCategoria, int idProducto) throws SQLException {
        String query = "DELETE FROM Pertenecer WHERE id_categoria = ? AND id_producto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idCategoria);
            stmt.setInt(2, idProducto);
            stmt.executeUpdate();
        }
    }
}
