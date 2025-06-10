package DAO;

import Conexion.DatabaseConnection;
import model.MesaPlato;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MesaPlatoDAO {
    private Connection connection;

    public MesaPlatoDAO(Connection connection) throws SQLException {
         this.connection = DatabaseConnection.getConnection(); // Obtener la conexión de la base de datos
    }

    public void agregarMesaPlato(MesaPlato mesaPlato) throws SQLException {
        String query = "INSERT INTO Mesa_Plato(id_mesa, id_plato, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, mesaPlato.getId_mesa());
            statement.setInt(2, mesaPlato.getId_plato());
            statement.setInt(3, mesaPlato.getCantidad());
            statement.executeUpdate();
        }
    }

    public MesaPlato obtenerMesaPlato(int id) throws SQLException {
        String query = "SELECT * FROM Mesa_Plato WHERE id = ?";
        MesaPlato mesaPlato = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                mesaPlato = new MesaPlato(rs.getInt("id"), rs.getInt("id_mesa"), rs.getInt("id_plato"), rs.getInt("cantidad"));
            }
        }
        return mesaPlato;
    }

    public List<MesaPlato> listarMesaPlatos() throws SQLException {
        List<MesaPlato> lista = new ArrayList<>();
        String query = "SELECT * FROM Mesa_Plato";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                lista.add(new MesaPlato(rs.getInt("id"), rs.getInt("id_mesa"), rs.getInt("id_plato"), rs.getInt("cantidad")));
            }
        }
        return lista;
    }

    public void actualizarMesaPlato(MesaPlato mesaPlato) throws SQLException {
        String query = "UPDATE Mesa_Plato SET id_mesa = ?, id_plato = ?, cantidad = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, mesaPlato.getId_mesa());
            statement.setInt(2, mesaPlato.getId_plato());
            statement.setInt(3, mesaPlato.getCantidad());
            statement.setInt(4, mesaPlato.getId());
            statement.executeUpdate();
        }
    }

    public void eliminarMesaPlato(int id) throws SQLException {
        String query = "DELETE FROM Mesa_Plato WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
    
    /** Consulta ejemplo avanzada: Obtiene el total de precio de platos en una mesa */
    public double calcularTotalPorMesa(int idMesa) throws SQLException {
        String query = "SELECT SUM(p.precio * mp.cantidad) AS total FROM Mesa_Plato mp JOIN Plato p ON mp.id_plato = p.id_plato WHERE mp.id_mesa = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idMesa);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }
        }
        return 0.0;
    }
    
}
