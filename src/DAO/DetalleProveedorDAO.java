package DAO;

import Conexion.DatabaseConnection;
import model.DetalleProveedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleProveedorDAO {
    private Connection connection;

    public DetalleProveedorDAO(Connection connection) throws SQLException {
        this.connection = DatabaseConnection.getConnection(); // Obtener la conexión de la base de datos
    }

    public void agregarDetalleProveedor(DetalleProveedor detalle) throws SQLException {
        String query = "INSERT INTO Detalle_Proveedor(id_proveedor, id_producto) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, detalle.getId_proveedor());
            statement.setInt(2, detalle.getId_producto());
            statement.executeUpdate();
        }
    }

    public DetalleProveedor obtenerDetalleProveedor(int id_detalle) throws SQLException {
        String query = "SELECT * FROM Detalle_Proveedor WHERE id_detalle = ?";
        DetalleProveedor detalle = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_detalle);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                detalle = new DetalleProveedor(rs.getInt("id_detalle"), rs.getInt("id_proveedor"), rs.getInt("id_producto"));
            }
        }
        return detalle;
    }

    public List<DetalleProveedor> listarDetalleProveedores() throws SQLException {
        List<DetalleProveedor> detalles = new ArrayList<>();
        String query = "SELECT * FROM Detalle_Proveedor";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                detalles.add(new DetalleProveedor(rs.getInt("id_detalle"), rs.getInt("id_proveedor"), rs.getInt("id_producto")));
            }
        }
        return detalles;
    }

    public void actualizarDetalleProveedor(DetalleProveedor detalle) throws SQLException {
        String query = "UPDATE Detalle_Proveedor SET id_proveedor = ?, id_producto = ? WHERE id_detalle = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, detalle.getId_proveedor());
            statement.setInt(2, detalle.getId_producto());
            statement.setInt(3, detalle.getId_detalle());
            statement.executeUpdate();
        }
    }

    public void eliminarDetalleProveedor(int id_detalle) throws SQLException {
        String query = "DELETE FROM Detalle_Proveedor WHERE id_detalle = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_detalle);
            statement.executeUpdate();
        }
    }
}
