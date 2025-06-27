package DAO;

import ConexionSQL.Conexion; // Asegúrate de que esta clase maneje la conexión a SQL Server
import Modelo.Plato;
import java.sql.*;
import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class PlatoDAO {
    private Connection conn; 

    public PlatoDAO(Connection conn) {
        this.conn = conn;
    }

    public void agregarPlato(Plato plato) throws SQLException {
        String query = "INSERT INTO Plato(nombre, precio, descripcion) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
            statement.setString(1, plato.getNombre());
            statement.setBigDecimal(2, plato.getPrecio());
            statement.setString(3, plato.getDescripcion());
            statement.executeUpdate();
        }
    }

    public Plato obtenerPlato(int id_plato) throws SQLException {
        String query = "SELECT * FROM Plato WHERE id_plato = ?";
        Plato plato = null;
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
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
        try (PreparedStatement statement = conn.prepareStatement(query); // Cambiado 'connection' a 'conn'
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                platos.add(new Plato(rs.getInt("id_plato"), rs.getString("nombre"), rs.getBigDecimal("precio"), rs.getString("descripcion")));
            }
        }
        return platos;
    }

    public void actualizarPlato(Plato plato) throws SQLException {
        String query = "UPDATE Plato SET nombre = ?, precio = ?, descripcion = ? WHERE id_plato = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
            statement.setString(1, plato.getNombre());
            statement.setBigDecimal(2, plato.getPrecio());
            statement.setString(3, plato.getDescripcion());
            statement.setInt(4, plato.getId_plato());
            statement.executeUpdate();
        }
    }

    public void eliminarPlato(int id_plato) throws SQLException {
        String query = "DELETE FROM Plato WHERE id_plato = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
            statement.setInt(1, id_plato);
            statement.executeUpdate();
        }
    }

    public Plato buscarPlatoPorId(int idPlato) throws SQLException {
        String query = "SELECT * FROM Plato WHERE id_plato = ?";
        Plato plato = null;
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
            statement.setInt(1, idPlato);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                plato = new Plato(rs.getInt("id_plato"), rs.getString("nombre"), rs.getBigDecimal("precio"), rs.getString("descripcion"));
            }
        }
        return plato;
    }
}
