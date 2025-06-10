package DAO;

import Conexion.DatabaseConnection;
import model.Plato;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class PlatoDAO {
    private Connection connection;
    
    public PlatoDAO(Connection connection) throws SQLException {
        this.connection = DatabaseConnection.getConnection(); // Obtener la conexión de la base de datos
    }

    public void agregarPlato(Plato plato) throws SQLException {
        String query = "INSERT INTO Plato(nombre, precio, descripcion) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, plato.getNombre());
            statement.setBigDecimal(2, plato.getPrecio());
            statement.setString(3, plato.getDescripcion());
            statement.executeUpdate();
        }
    }

    public Plato obtenerPlato(int id_plato) throws SQLException {
        String query = "SELECT * FROM Plato WHERE id_plato = ?";
        Plato plato = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_plato);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                plato = new Plato(rs.getInt("id_plato"), rs.getString("nombre"), rs.getBigDecimal("precio"), rs.getString("descripcion"));
            }
        }
        return plato;
    }

    public List<Plato> listarPlatos() throws SQLException {
        List<Plato> platos = new ArrayList<>();
        String query = "SELECT * FROM Plato";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                platos.add(new Plato(rs.getInt("id_plato"), rs.getString("nombre"), rs.getBigDecimal("precio"), rs.getString("descripcion")));
            }
        }
        return platos;
    }

    public void actualizarPlato(Plato plato) throws SQLException {
        String query = "UPDATE Plato SET nombre = ?, precio = ?, descripcion = ? WHERE id_plato = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, plato.getNombre());
            statement.setBigDecimal(2, plato.getPrecio());
            statement.setString(3, plato.getDescripcion());
            statement.setInt(4, plato.getId_plato());
            statement.executeUpdate();
        }
    }

    public void eliminarPlato(int id_plato) throws SQLException {
        String query = "DELETE FROM Plato WHERE id_plato = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_plato);
            statement.executeUpdate();
        }
    }

    public Plato buscarPlatoPorId(int idPlato) throws SQLException {
    String query = "SELECT * FROM Plato WHERE id_plato = ?";
    Plato plato = null;
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, idPlato);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            plato = new Plato(rs.getInt("id_plato"), rs.getString("nombre"), rs.getBigDecimal("precio"), rs.getString("descripcion"));
        }
    }
    return plato;
    }

}
