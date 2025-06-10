package DAO;

import Conexion.DatabaseConnection;
import java.math.BigDecimal;
import model.Venta;
import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.security.Timestamp;
import model.DetalleVenta;

public class VentaDAO {
    private Connection connection;


    public VentaDAO(Connection connection1) throws SQLException {
         this.connection = DatabaseConnection.getConnection(); // Obtener la conexión de la base de datos
    }

    public void agregarVenta(Venta venta) throws SQLException {
        String query = "INSERT INTO Venta(fecha_venta, id_usuario, metodo_pago, monto_total) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setTimestamp(1, venta.getFecha_venta());
            statement.setObject(2, venta.getId_usuario());
            statement.setString(3, venta.getMetodo_pago());
            statement.setBigDecimal(4, venta.getMonto_total());
            statement.executeUpdate();
        }
    }

public void agregarDetalleVenta(DetalleVenta detalle) throws SQLException {
        String query = "INSERT INTO Detalle_Venta(id_venta, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, detalle.getId_venta());
            statement.setInt(2, detalle.getId_producto());
            statement.setInt(3, detalle.getCantidad());
            statement.setBigDecimal(4, detalle.getPrecio_unitario());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Venta obtenerVenta(int id_venta) throws SQLException {
        String query = "SELECT * FROM Venta WHERE id_venta = ?";
        Venta venta = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_venta);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                venta = new Venta(rs.getInt("id_venta"), rs.getTimestamp("fecha_venta"),
                                  rs.getObject("id_usuario", Integer.class), rs.getString("metodo_pago"),
                                  rs.getBigDecimal("monto_total"));
            }
        }
        return venta;
    }

    public List<Venta> listarVentas() throws SQLException {
        List<Venta> ventas = new ArrayList<>();
        String query = "SELECT * FROM Venta";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                ventas.add(new Venta(rs.getInt("id_venta"), rs.getTimestamp("fecha_venta"),
                                     rs.getObject("id_usuario", Integer.class), rs.getString("metodo_pago"),
                                     rs.getBigDecimal("monto_total")));
            }
        }
        return ventas;
    }

    public void actualizarVenta(Venta venta) throws SQLException {
        String query = "UPDATE Venta SET fecha_venta = ?, id_usuario = ?, metodo_pago = ?, monto_total = ? WHERE id_venta = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setTimestamp(1, venta.getFecha_venta());
            statement.setObject(2, venta.getId_usuario());
            statement.setString(3, venta.getMetodo_pago());
            statement.setBigDecimal(4, venta.getMonto_total());
            statement.setInt(5, venta.getId_venta());
            statement.executeUpdate();
        }
    }

    public void eliminarVenta(int id_venta) throws SQLException {
        String query = "DELETE FROM Venta WHERE id_venta = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_venta);
            statement.executeUpdate();
        }
    }
    /** Obtiene ventas realizadas por un usuario específico */
    public List<Venta> listarVentasPorUsuario(int idUsuario) throws SQLException {
        String query = "SELECT * FROM Venta WHERE id_usuario = ?";
        List<Venta> ventas = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ventas.add(new Venta(
                        rs.getInt("id_venta"),
                        rs.getTimestamp("fecha_venta"),
                        (Integer) rs.getObject("id_usuario"),
                        rs.getString("metodo_pago"),
                        rs.getBigDecimal("monto_total")
                    ));
                }
            }
        }
        return ventas;
    }

    /** Suma total vendida en un rango de fechas 
    public BigDecimal totalVentasPorFecha(Timestamp fechaInicio, Timestamp fechaFin) throws SQLException {
        String query = "SELECT SUM(monto_total) AS total FROM Venta WHERE fecha_venta BETWEEN ? AND ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setTimestamp(1, fechaInicio);
            stmt.setTimestamp(2, fechaFin);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("total");
                }
            }
        }
        return BigDecimal.ZERO;
    }*/
}