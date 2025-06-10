package DAO;

import ConexionSQL.Conexion;
import Modelo.MesaUnida;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MesaUnidaDAO {
    private Connection connection;

    public MesaUnidaDAO(Connection connection) {
        this.connection = connection;
    }

    public void agregarMesaUnida(MesaUnida mesaUnida) throws SQLException {
        String query = "INSERT INTO Mesa_Unida(id_mesa_principal, id_mesa_secundaria) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
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
        try (PreparedStatement statement = connection.prepareStatement(query)) {
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
        try (PreparedStatement statement = connection.prepareStatement(query);
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
        try (PreparedStatement statement = connection.prepareStatement(query)) {
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
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_unida);
            statement.executeUpdate();
        }
    }
}
