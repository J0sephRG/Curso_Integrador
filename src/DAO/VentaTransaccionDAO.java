package DAO;

import Conexion.DatabaseConnection;
import model.Venta;
import model.DetalleVenta;
import java.sql.*;
import java.util.List;

public class VentaTransaccionDAO {
    private Connection connection;

    public VentaTransaccionDAO(Connection connection) throws SQLException {
         this.connection = DatabaseConnection.getConnection(); // Obtener la conexión de la base de datos
    }

    /**
     * Inserta una venta con lista de detalles en una transacción.
     * Commit solo si todos los inserts se realizan exitosamente.
     */
    public void insertarVentaConDetalles(Venta venta, List<DetalleVenta> detalles) throws SQLException {
        String insertVenta = "INSERT INTO Venta(fecha_venta, id_usuario, metodo_pago, monto_total) VALUES (?, ?, ?, ?)";
        String insertDetalle = "INSERT INTO Detalle_Venta(id_venta, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement ventaStmt = connection.prepareStatement(insertVenta, Statement.RETURN_GENERATED_KEYS)) {
                ventaStmt.setTimestamp(1, venta.getFecha_venta());
                ventaStmt.setObject(2, venta.getId_usuario());
                ventaStmt.setString(3, venta.getMetodo_pago());
                ventaStmt.setBigDecimal(4, venta.getMonto_total());
                ventaStmt.executeUpdate();

                ResultSet generatedKeys = ventaStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idVenta = generatedKeys.getInt(1);

                    try (PreparedStatement detalleStmt = connection.prepareStatement(insertDetalle)) {
                        for (DetalleVenta detalle : detalles) {
                            detalleStmt.setInt(1, idVenta);
                            detalleStmt.setInt(2, detalle.getId_producto());
                            detalleStmt.setInt(3, detalle.getCantidad());
                            detalleStmt.setBigDecimal(4, detalle.getPrecio_unitario());
                            detalleStmt.addBatch();
                        }
                        detalleStmt.executeBatch();
                    }
                } else {
                    throw new SQLException("No se pudo obtener el id generado para la venta.");
                }
            }

            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
