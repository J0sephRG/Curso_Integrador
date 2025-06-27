package DAO;

import ConexionSQL.Conexion;
import Modelo.HistorialPrecio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistorialPrecioDAO {
    private Connection connection;

    public HistorialPrecioDAO(Connection connection) {
        this.connection = connection;
    }



    public void agregarHistorial(HistorialPrecio historial) throws SQLException {
        String query = "INSERT INTO Historial_Precio(id_producto, precio, fecha_cambio) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, historial.getId_producto());
            statement.setBigDecimal(2, historial.getPrecio());
            statement.setTimestamp(3, historial.getFecha_cambio());
            statement.executeUpdate();
        }
    }

    public HistorialPrecio obtenerHistorial(int id_historial) throws SQLException {
        String query = "SELECT * FROM Historial_Precio WHERE id_historial = ?";
        HistorialPrecio historial = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_historial);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                historial = new HistorialPrecio(rs.getInt("id_historial"), rs.getInt("id_producto"),
                                                 rs.getBigDecimal("precio"), rs.getTimestamp("fecha_cambio"));
            }
        }
        return historial;
    }

    public List<HistorialPrecio> listarHistoriales() throws SQLException {
        List<HistorialPrecio> historiales = new ArrayList<>();
        String query = "SELECT * FROM Historial_Precio";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                historiales.add(new HistorialPrecio(rs.getInt("id_historial"), rs.getInt("id_producto"),
                                                     rs.getBigDecimal("precio"), rs.getTimestamp("fecha_cambio")));
            }
        }
        return historiales;
    }

    public void actualizarHistorial(HistorialPrecio historial) throws SQLException {
        String query = "UPDATE Historial_Precio SET id_producto = ?, precio = ?, fecha_cambio = ? WHERE id_historial = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, historial.getId_producto());
            statement.setBigDecimal(2, historial.getPrecio());
            statement.setTimestamp(3, historial.getFecha_cambio());
            statement.setInt(4, historial.getId_historial());
            statement.executeUpdate();
        }
    }

    public void eliminarHistorial(int id_historial) throws SQLException {
        String query = "DELETE FROM Historial_Precio WHERE id_historial = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_historial);
            statement.executeUpdate();
        }
    }
}
