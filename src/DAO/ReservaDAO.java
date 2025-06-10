package DAO;

import Conexion.DatabaseConnection;
import model.Reserva;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {
    private Connection connection;

    public ReservaDAO(Connection connection) throws SQLException {
         this.connection = DatabaseConnection.getConnection(); // Obtener la conexión de la base de datos
    }

    public void agregarReserva(Reserva reserva) throws SQLException {
        String query = "INSERT INTO Reserva(id_cliente, fecha_reserva, numero_personas, estado) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, reserva.getId_cliente());
            statement.setTimestamp(2, reserva.getFecha_reserva());
            statement.setInt(3, reserva.getNumero_personas());
            statement.setString(4, reserva.getEstado());
            statement.executeUpdate();
        }
    }

    public Reserva obtenerReserva(int id_reserva) throws SQLException {
        String query = "SELECT * FROM Reserva WHERE id_reserva = ?";
        Reserva reserva = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_reserva);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                reserva = new Reserva(rs.getInt("id_reserva"), rs.getObject("id_cliente", Integer.class),
                                      rs.getTimestamp("fecha_reserva"), rs.getInt("numero_personas"),
                                      rs.getString("estado"));
            }
        }
        return reserva;
    }

    public List<Reserva> listarReservas() throws SQLException {
        List<Reserva> reservas = new ArrayList<>();
        String query = "SELECT * FROM Reserva";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                reservas.add(new Reserva(rs.getInt("id_reserva"), rs.getObject("id_cliente", Integer.class),
                                         rs.getTimestamp("fecha_reserva"), rs.getInt("numero_personas"),
                                         rs.getString("estado")));
            }
        }
        return reservas;
    }

    public void actualizarReserva(Reserva reserva) throws SQLException {
        String query = "UPDATE Reserva SET id_cliente = ?, fecha_reserva = ?, numero_personas = ?, estado = ? WHERE id_reserva = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, reserva.getId_cliente());
            statement.setTimestamp(2, reserva.getFecha_reserva());
            statement.setInt(3, reserva.getNumero_personas());
            statement.setString(4, reserva.getEstado());
            statement.setInt(5, reserva.getId_reserva());
            statement.executeUpdate();
        }
    }

    public void eliminarReserva(int id_reserva) throws SQLException {
        String query = "DELETE FROM Reserva WHERE id_reserva = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_reserva);
            statement.executeUpdate();
        }
    }
}
