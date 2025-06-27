package DAO;

import ConexionSQL.Conexion;
import Modelo.Notificacion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificacionDAO {
    private Connection connection;

    public NotificacionDAO(Connection connection) {
        this.connection = connection;
    }

    public void agregarNotificacion(Notificacion notificacion) throws SQLException {
        String query = "INSERT INTO Notificacion(mensaje, estado, fecha) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, notificacion.getMensaje());
            statement.setString(2, notificacion.getEstado());
            statement.setTimestamp(3, notificacion.getFecha());
            statement.executeUpdate();
        }
    }

    public Notificacion obtenerNotificacion(int id_notificacion) throws SQLException {
        String query = "SELECT * FROM Notificacion WHERE id_notificacion = ?";
        Notificacion notificacion = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_notificacion);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                notificacion = new Notificacion(rs.getInt("id_notificacion"),
                                                rs.getString("mensaje"),
                                                rs.getString("estado"),
                                                rs.getTimestamp("fecha"));
            }
        }
        return notificacion;
    }

    public List<Notificacion> listarNotificaciones() throws SQLException {
        List<Notificacion> lista = new ArrayList<>();
        String query = "SELECT * FROM Notificacion";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                lista.add(new Notificacion(rs.getInt("id_notificacion"),
                                          rs.getString("mensaje"),
                                          rs.getString("estado"),
                                          rs.getTimestamp("fecha")));
            }
        }
        return lista;
    }

    public void actualizarNotificacion(Notificacion notificacion) throws SQLException {
        String query = "UPDATE Notificacion SET mensaje = ?, estado = ?, fecha = ? WHERE id_notificacion = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, notificacion.getMensaje());
            statement.setString(2, notificacion.getEstado());
            statement.setTimestamp(3, notificacion.getFecha());
            statement.setInt(4, notificacion.getId_notificacion());
            statement.executeUpdate();
        }
    }

    public void eliminarNotificacion(int id_notificacion) throws SQLException {
        String query = "DELETE FROM Notificacion WHERE id_notificacion = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_notificacion);
            statement.executeUpdate();
        }
    }
}
