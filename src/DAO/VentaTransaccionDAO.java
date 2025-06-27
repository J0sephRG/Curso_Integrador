package DAO;

import ConexionSQL.Conexion; // Asegúrate de que esta clase maneje la conexión a SQL Server
import Modelo.Venta;
import Modelo.DetalleVenta;
import java.sql.*;
import java.util.List;

public class VentaTransaccionDAO {
    private Connection conn; 

    public VentaTransaccionDAO(Connection conn) { // Cambiado el nombre del constructor
        this.conn = conn;
    }

    /**
     * Inserta una venta con lista de detalles en una transacción.
     * Commit solo si todos los inserts se realizan exitosamente.
     */
    public void insertarVentaConDetalles(Venta venta, List<DetalleVenta> detalles) throws SQLException {
        String insertVenta = "INSERT INTO Venta(fecha_venta, id_usuario, metodo_pago, monto_total) VALUES (?, ?, ?, ?)";
        String insertDetalle = "INSERT INTO Detalle_Venta(id_venta, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";

        try {
            conn.setAutoCommit(false); // Cambiado 'connection' a 'conn'

            try (PreparedStatement ventaStmt = conn.prepareStatement(insertVenta, Statement.RETURN_GENERATED_KEYS)) { // Cambiado 'connection' a 'conn'
                ventaStmt.setTimestamp(1, venta.getFecha_venta());
                ventaStmt.setObject(2, venta.getId_usuario());
                ventaStmt.setString(3, venta.getMetodo_pago());
                ventaStmt.setBigDecimal(4, venta.getMonto_total());
                ventaStmt.executeUpdate();

                ResultSet generatedKeys = ventaStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idVenta = generatedKeys.getInt(1);

                    try (PreparedStatement detalleStmt = conn.prepareStatement(insertDetalle)) { // Cambiado 'connection' a 'conn'
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

            conn.commit(); // Cambiado 'connection' a 'conn'
        } catch (SQLException ex) {
            conn.rollback(); // Cambiado 'connection' a 'conn'
            throw ex;
        } finally {
            conn.setAutoCommit(true); // Cambiado 'connection' a 'conn'
        }
    }
}
