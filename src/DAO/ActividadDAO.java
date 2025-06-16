package DAO;

import Modelo.Actividad;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class ActividadDAO {
    private Connection connection;

    public ActividadDAO(Connection connection) {
        this.connection = connection;
    }

    public void agregarActividad(Actividad actividad) throws SQLException {
        String query = "INSERT INTO Actividad(id_usuario, descripcion, fecha) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            if (actividad.getId_usuario() != null) {
                statement.setInt(1, actividad.getId_usuario());
            } else {
                statement.setNull(1, Types.INTEGER);
            }
            statement.setString(2, actividad.getDescripcion());
            statement.setTimestamp(3, actividad.getFecha());
            statement.executeUpdate();
        }
    }

    public Actividad obtenerActividad(int id_actividad) throws SQLException {
        String query = "SELECT * FROM Actividad WHERE id_actividad = ?";
        Actividad actividad = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_actividad);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                actividad = new Actividad(rs.getInt("id_actividad"),
                                          (Integer) rs.getObject("id_usuario"),
                                          rs.getString("descripcion"),
                                          rs.getTimestamp("fecha"));
            }
        }
        return actividad;
    }

    public List<Actividad> listarActividades() throws SQLException {
        List<Actividad> lista = new ArrayList<>();
        String query = "SELECT * FROM Actividad";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                lista.add(new Actividad(rs.getInt("id_actividad"),
                                       (Integer) rs.getObject("id_usuario"),
                                       rs.getString("descripcion"),
                                       rs.getTimestamp("fecha")));
            }
        }
        return lista;
    }

    public void actualizarActividad(Actividad actividad) throws SQLException {
        String query = "UPDATE Actividad SET id_usuario = ?, descripcion = ?, fecha = ? WHERE id_actividad = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            if (actividad.getId_usuario() != null) {
                statement.setInt(1, actividad.getId_usuario());
            } else {
                statement.setNull(1, Types.INTEGER);
            }
            statement.setString(2, actividad.getDescripcion());
            statement.setTimestamp(3, actividad.getFecha());
            statement.setInt(4, actividad.getId_actividad());
            statement.executeUpdate();
        }
    }

    public void eliminarActividad(int id_actividad) throws SQLException {
        String query = "DELETE FROM Actividad WHERE id_actividad = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_actividad);
            statement.executeUpdate();
        }
    }
}
