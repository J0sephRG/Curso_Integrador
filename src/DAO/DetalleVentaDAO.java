package DAO;

import ConexionSQL.Conexion; // Asegúrate de que esta clase maneje la conexión a SQL Server
import Modelo.DetalleVenta;
import java.util.List;
import java.math.BigDecimal;
import Modelo.Plato;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class DetalleVentaDAO {
    private Connection conn; 

    public DetalleVentaDAO(Connection conn) {
        this.conn = conn;
    }

    public void agregarDetalleVenta(DetalleVenta detalle) throws SQLException {
        String query = "INSERT INTO Detalle_Venta(id_venta, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
            statement.setInt(1, detalle.getId_venta());
            statement.setInt(2, detalle.getId_producto());
            statement.setInt(3, detalle.getCantidad());
            statement.setBigDecimal(4, detalle.getPrecio_unitario());
            statement.executeUpdate();
        }
    }

    public DetalleVenta obtenerDetalleVenta(int id_detalle) throws SQLException {
        String query = "SELECT * FROM Detalle_Venta WHERE id_detalle = ?";
        DetalleVenta detalle = null;
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
            statement.setInt(1, id_detalle);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                detalle = new DetalleVenta(
                    rs.getInt("id_detalle"),
                    rs.getInt("id_venta"),
                    rs.getInt("id_producto"),
                    rs.getInt("cantidad"),
                    rs.getBigDecimal("precio_unitario"),
                    rs.getBigDecimal("subtotal")
                );
            }
        }
        return detalle;
    }

    public List<DetalleVenta> listarDetalleVentas() throws SQLException {
        List<DetalleVenta> detalles = new ArrayList<>();
        String query = "SELECT * FROM Detalle_Venta";
        try (PreparedStatement statement = conn.prepareStatement(query); // Cambiado 'connection' a 'conn'
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                detalles.add(new DetalleVenta(
                    rs.getInt("id_detalle"),
                    rs.getInt("id_venta"),
                    rs.getInt("id_producto"),
                    rs.getInt("cantidad"),
                    rs.getBigDecimal("precio_unitario"),
                    rs.getBigDecimal("subtotal")
                ));
            }
        }
        return detalles;
    }

    public void actualizarDetalleVenta(DetalleVenta detalle) throws SQLException {
        String query = "UPDATE Detalle_Venta SET id_venta = ?, id_producto = ?, cantidad = ?, precio_unitario = ? WHERE id_detalle = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
            statement.setInt(1, detalle.getId_venta());
            statement.setInt(2, detalle.getId_producto());
            statement.setInt(3, detalle.getCantidad());
            statement.setBigDecimal(4, detalle.getPrecio_unitario());
            statement.setInt(5, detalle.getId_detalle());
            statement.executeUpdate();
        }
    }

    public void eliminarDetalleVenta(int id_detalle) throws SQLException {
        String query = "DELETE FROM Detalle_Venta WHERE id_detalle = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
            statement.setInt(1, id_detalle);
            statement.executeUpdate();
        }
    }

    public List<DetalleVenta> obtenerPedidosPorMesa(int mesaNumber) throws SQLException {
        List<DetalleVenta> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM Detalle_Venta WHERE mesaId = ?"; // Asegúrate que esta consulta sea correcta
        try (PreparedStatement statement = conn.prepareStatement(sql)) { // Cambiado 'connection' a 'conn'
            statement.setInt(1, mesaNumber);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    // Crea y agrega un objeto DetalleVenta a la lista
                    DetalleVenta detalle = new DetalleVenta(
                        rs.getInt("id_detalle"),
                        rs.getInt("id_venta"),
                        rs.getInt("id_producto"),
                        rs.getInt("cantidad"),
                        rs.getBigDecimal("precio_unitario"),
                        rs.getBigDecimal("subtotal")
                    );
                    pedidos.add(detalle);
                }
            }
        }
        return pedidos;
    }

    public Plato getPlato(int id) {
        Plato plato = null;
        String query = "SELECT * FROM Plato WHERE id_plato = ?"; // Cambiado 'platos' a 'Plato'
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int id_plato = rs.getInt("id_plato");
                String nombre = rs.getString("nombre");
                BigDecimal precio = rs.getBigDecimal("precio");
                String descripcion = rs.getString("descripcion");
                plato = new Plato(id_plato, nombre, precio, descripcion);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones
        }
        return plato;
    }
}
