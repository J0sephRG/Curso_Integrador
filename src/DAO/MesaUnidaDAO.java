package DAO;

import ConexionSQL.Conexion; // Asegúrate de que esta clase maneje la conexión a SQL Server
import Modelo.VENTAenMesa.MesaUnida;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class MesaUnidaDAO {
    private Connection conn; 

    public MesaUnidaDAO(Connection conn) { // Cambiado el nombre del constructor
        this.conn = conn;
    }

    public void agregarMesaUnida(MesaUnida mesaUnida) throws SQLException {
        String query = "INSERT INTO Mesa_Unida(id_mesa_principal, id_mesa_secundaria) VALUES (?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
            if (mesaUnida.getId_mesa_principal() != null) {
                statement.setInt(1, mesaUnida.getId_mesa_principal());
            } else {
                statement.setNull(1, Types.INTEGER);
            }
            if (mesaUnida.getId_mesa_secundaria() != null) {
                statement.setInt(2, mesaUnida.getId_mesa_secundaria());
            } else {
                statement.setNull(2, Types.INTEGER);
            }
            statement.executeUpdate();
        }
    }

    public MesaUnida obtenerMesaUnida(int id_unida) throws SQLException {
        String query = "SELECT * FROM Mesa_Unida WHERE id_unida = ?";
        MesaUnida mesaUnida = null;
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
            statement.setInt(1, id_unida);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                mesaUnida = new MesaUnida(rs.getInt("id_unida"),
                                          (Integer) rs.getObject("id_mesa_principal"),
                                          (Integer) rs.getObject("id_mesa_secundaria"));
            }
        }
        return mesaUnida;
    }

    public List<MesaUnida> listarMesasUnidas() throws SQLException {
        List<MesaUnida> lista = new ArrayList<>();
        String query = "SELECT * FROM Mesa_Unida";
        try (PreparedStatement statement = conn.prepareStatement(query); // Cambiado 'connection' a 'conn'
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                lista.add(new MesaUnida(rs.getInt("id_unida"),
                                        (Integer) rs.getObject("id_mesa_principal"),
                                        (Integer) rs.getObject("id_mesa_secundaria")));
            }
        }
        return lista;
    }

    public void actualizarMesaUnida(MesaUnida mesaUnida) throws SQLException {
        String query = "UPDATE Mesa_Unida SET id_mesa_principal = ?, id_mesa_secundaria = ? WHERE id_unida = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
            if (mesaUnida.getId_mesa_principal() != null) {
                statement.setInt(1, mesaUnida.getId_mesa_principal());
            } else {
                statement.setNull(1, Types.INTEGER);
            }
            if (mesaUnida.getId_mesa_secundaria() != null) {
                statement.setInt(2, mesaUnida.getId_mesa_secundaria());
            } else {
                statement.setNull(2, Types.INTEGER);
            }
            statement.setInt(3, mesaUnida.getId_unida());
            statement.executeUpdate();
        }
    }

    public void eliminarMesaUnida(int id_unida) throws SQLException {
        String query = "DELETE FROM Mesa_Unida WHERE id_unida = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
            statement.setInt(1, id_unida);
            statement.executeUpdate();
        }
    }
}
