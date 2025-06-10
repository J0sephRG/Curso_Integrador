package DAO;

import ConexionSQL.Conexion;
import Modelo.PlatoProducto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlatoProductoDAO {
    private Connection connection;

    public PlatoProductoDAO(Connection connection) {
        this.connection = connection;
    }

    public void agregarPlatoProducto(PlatoProducto pp) throws SQLException {
        String query = "INSERT INTO Plato_Producto(id_plato, id_producto, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, pp.getId_plato());
            statement.setInt(2, pp.getId_producto());
            statement.setInt(3, pp.getCantidad());
            statement.executeUpdate();
        }
    }

    public PlatoProducto obtenerPlatoProducto(int id_plato, int id_producto) throws SQLException {
        String query = "SELECT * FROM Plato_Producto WHERE id_plato = ? AND id_producto = ?";
        PlatoProducto pp = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_plato);
            statement.setInt(2, id_producto);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                pp = new PlatoProducto(rs.getInt("id_plato"), rs.getInt("id_producto"), rs.getInt("cantidad"));
            }
        }
        return pp;
    }

    public List<PlatoProducto> listarPlatoProductos() throws SQLException {
        List<PlatoProducto> lista = new ArrayList<>();
        String query = "SELECT * FROM Plato_Producto";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                lista.add(new PlatoProducto(rs.getInt("id_plato"), rs.getInt("id_producto"), rs.getInt("cantidad")));
            }
        }
        return lista;
    }

    public void actualizarPlatoProducto(PlatoProducto pp) throws SQLException {
        String query = "UPDATE Plato_Producto SET cantidad = ? WHERE id_plato = ? AND id_producto = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, pp.getCantidad());
            statement.setInt(2, pp.getId_plato());
            statement.setInt(3, pp.getId_producto());
            statement.executeUpdate();
        }
    }

    public void eliminarPlatoProducto(int id_plato, int id_producto) throws SQLException {
        String query = "DELETE FROM Plato_Producto WHERE id_plato = ? AND id_producto = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_plato);
            statement.setInt(2, id_producto);
            statement.executeUpdate();
        }
    }
}
