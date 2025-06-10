package DAO;

import Conexion.DatabaseConnection;
import model.Promocion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PromocionDAO {
    private Connection connection;

    public PromocionDAO(Connection connection) throws SQLException {
         this.connection = DatabaseConnection.getConnection(); // Obtener la conexión de la base de datos
    }

    public void agregarPromocion(Promocion promocion) throws SQLException {
        String query = "INSERT INTO Promocion(descripcion, tipo, valor, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, promocion.getDescripcion());
            statement.setString(2, promocion.getTipo());
            statement.setBigDecimal(3, promocion.getValor());
            if (promocion.getFecha_inicio() != null) {
                statement.setDate(4, new java.sql.Date(promocion.getFecha_inicio().getTime()));
            } else {
                statement.setNull(4, Types.DATE);
            }
            if (promocion.getFecha_fin() != null) {
                statement.setDate(5, new java.sql.Date(promocion.getFecha_fin().getTime()));
            } else {
                statement.setNull(5, Types.DATE);
            }
            statement.executeUpdate();
        }
    }

    public Promocion obtenerPromocion(int id_promocion) throws SQLException {
        String query = "SELECT * FROM Promocion WHERE id_promocion = ?";
        Promocion promocion = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_promocion);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                promocion = new Promocion(
                    rs.getInt("id_promocion"),
                    rs.getString("descripcion"),
                    rs.getString("tipo"),
                    rs.getBigDecimal("valor"),
                    rs.getDate("fecha_inicio"),
                    rs.getDate("fecha_fin")
                );
            }
        }
        return promocion;
    }

    public List<Promocion> listarPromociones() throws SQLException {
        List<Promocion> promociones = new ArrayList<>();
        String query = "SELECT * FROM Promocion";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                promociones.add(new Promocion(
                    rs.getInt("id_promocion"),
                    rs.getString("descripcion"),
                    rs.getString("tipo"),
                    rs.getBigDecimal("valor"),
                    rs.getDate("fecha_inicio"),
                    rs.getDate("fecha_fin")
                ));
            }
        }
        return promociones;
    }

    public void actualizarPromocion(Promocion promocion) throws SQLException {
        String query = "UPDATE Promocion SET descripcion = ?, tipo = ?, valor = ?, fecha_inicio = ?, fecha_fin = ? WHERE id_promocion = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, promocion.getDescripcion());
            statement.setString(2, promocion.getTipo());
            statement.setBigDecimal(3, promocion.getValor());
            if (promocion.getFecha_inicio() != null) {
                statement.setDate(4, new java.sql.Date(promocion.getFecha_inicio().getTime()));
            } else {
                statement.setNull(4, Types.DATE);
            }
            if (promocion.getFecha_fin() != null) {
                statement.setDate(5, new java.sql.Date(promocion.getFecha_fin().getTime()));
            } else {
                statement.setNull(5, Types.DATE);
            }
            statement.setInt(6, promocion.getId_promocion());
            statement.executeUpdate();
        }
    }

    public void eliminarPromocion(int id_promocion) throws SQLException {
        String query = "DELETE FROM Promocion WHERE id_promocion = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_promocion);
            statement.executeUpdate();
        }
    }
}
