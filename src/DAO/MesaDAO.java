package DAO;

import ConexionSQL.Conexion; // Asegúrate de que esta clase maneje la conexión a SQL Server
import Modelo.VENTAenMesa.Mesa;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class MesaDAO {
    private Connection conn; 

    public MesaDAO(Connection conn) {
        this.conn = conn;
    }

    public void agregarMesa(Mesa mesa) throws SQLException {
        String query = "INSERT INTO Mesa(numero_mesa, capacidad, estado) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
            statement.setInt(1, mesa.getNumero_mesa());
            statement.setInt(2, mesa.getCapacidad());
            statement.setString(3, mesa.getEstado());
            statement.executeUpdate();
        }
    }

    public Mesa obtenerMesa(int id_mesa) throws SQLException {
        String query = "SELECT * FROM Mesa WHERE id_mesa = ?";
        Mesa mesa = null;
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
            statement.setInt(1, id_mesa);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                mesa = new Mesa(
                    rs.getInt("id_mesa"), 
                    rs.getInt("numero_mesa"),
                    rs.getInt("capacidad"),
                    rs.getString("estado")
                );
            }
        }
        return mesa;
    }

    public List<Mesa> listarMesas() throws SQLException {
        List<Mesa> mesas = new ArrayList<>();
        String query = "SELECT * FROM Mesa";
        try (PreparedStatement statement = conn.prepareStatement(query); // Cambiado 'connection' a 'conn'
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                mesas.add(new Mesa(
                    rs.getInt("id_mesa"), 
                    rs.getInt("numero_mesa"),
                    rs.getInt("capacidad"), 
                    rs.getString("estado")
                ));
            }
        }
        return mesas;
    }

    public void actualizarMesa(Mesa mesa) throws SQLException {
        String query = "UPDATE Mesa SET numero_mesa = ?, capacidad = ?, estado = ? WHERE id_mesa = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
            statement.setInt(1, mesa.getNumero_mesa());
            statement.setInt(2, mesa.getCapacidad());
            statement.setString(3, mesa.getEstado());
            statement.setInt(4, mesa.getId_mesa());
            statement.executeUpdate();
        }
    }

    public void eliminarMesa(int id_mesa) throws SQLException {
        String query = "DELETE FROM Mesa WHERE id_mesa = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
            statement.setInt(1, id_mesa);
            statement.executeUpdate();
        }
    }
}
