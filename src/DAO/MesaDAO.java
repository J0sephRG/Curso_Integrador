package DAO;

import ConexionSQL.Conexion;
import Modelo.Mesa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MesaDAO {
    private Connection connection;

    public MesaDAO(Connection connection) {
        this.connection = connection;
    }

    public void agregarMesa(Mesa mesa) throws SQLException {
        String query = "INSERT INTO Mesa(numero_mesa, capacidad, estado) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, mesa.getNumero_mesa());
            statement.setInt(2, mesa.getCapacidad());
            statement.setString(3, mesa.getEstado());
            statement.executeUpdate();
        }
    }

    public Mesa obtenerMesa(int id_mesa) throws SQLException {
        String query = "SELECT * FROM Mesa WHERE id_mesa = ?";
        Mesa mesa = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_mesa);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                mesa = new Mesa
                (rs.getInt("id_mesa"), 
                 rs.getInt("numero_mesa"),
                 rs.getInt("capacidad"),
                 rs.getString("estado"));
            }
        }
        return mesa;
    }

    public List<Mesa> listarMesas() throws SQLException {
        List<Mesa> mesas = new ArrayList<>();
        String query = "SELECT * FROM Mesa";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                mesas.add(new Mesa(rs.getInt("id_mesa"), rs.getInt("numero_mesa"),
                                   rs.getInt("capacidad"), rs.getString("estado")));
            }
        }
        return mesas;
    }

    public void actualizarMesa(Mesa mesa) throws SQLException {
        String query = "UPDATE Mesa SET numero_mesa = ?, capacidad = ?, estado = ? WHERE id_mesa = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, mesa.getNumero_mesa());
            statement.setInt(2, mesa.getCapacidad());
            statement.setString(3, mesa.getEstado());
            statement.setInt(4, mesa.getId_mesa());
            statement.executeUpdate();
        }
    }

    public void eliminarMesa(int id_mesa) throws SQLException {
        String query = "DELETE FROM Mesa WHERE id_mesa = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_mesa);
            statement.executeUpdate();
        }
    }
}
