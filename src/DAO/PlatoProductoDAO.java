package DAO;

import ConexionSQL.Conexion; // Asegúrate de que esta clase maneje la conexión a SQL Server
import Modelo.PlatoProducto;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class PlatoProductoDAO {
    private Connection conn; 

    public PlatoProductoDAO(Connection conn) {
        this.conn = conn;
    }

    public void agregarPlatoProducto(PlatoProducto pp) throws SQLException {
        String query = "INSERT INTO Plato_Producto(id_plato, id_producto, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
            statement.setInt(1, pp.getId_plato());
            statement.setInt(2, pp.getId_producto());
            statement.setInt(3, pp.getCantidad());
            statement.executeUpdate();
        }
    }

    public PlatoProducto obtenerPlatoProducto(int id_plato, int id_producto) throws SQLException {
        String query = "SELECT * FROM Plato_Producto WHERE id_plato = ? AND id_producto = ?";
        PlatoProducto pp = null;
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
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
        try (PreparedStatement statement = conn.prepareStatement(query); // Cambiado 'connection' a 'conn'
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                lista.add(new PlatoProducto(rs.getInt("id_plato"), rs.getInt("id_producto"), rs.getInt("cantidad")));
            }
        }
        return lista;
    }

    public void actualizarPlatoProducto(PlatoProducto pp) throws SQLException {
        String query = "UPDATE Plato_Producto SET cantidad = ? WHERE id_plato = ? AND id_producto = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
            statement.setInt(1, pp.getCantidad());
            statement.setInt(2, pp.getId_plato());
            statement.setInt(3, pp.getId_producto());
            statement.executeUpdate();
        }
    }

    public void eliminarPlatoProducto(int id_plato, int id_producto) throws SQLException {
        String query = "DELETE FROM Plato_Producto WHERE id_plato = ? AND id_producto = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
            statement.setInt(1, id_plato);
            statement.setInt(2, id_producto);
            statement.executeUpdate();
        }
    }
}
