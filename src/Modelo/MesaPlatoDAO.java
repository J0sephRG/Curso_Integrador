package DAO;

import ConexionSQL.Conexion; // Asegúrate de que esta clase maneje la conexión a SQL Server
import java.math.BigDecimal;
import model.MesaPlato;
import java.util.List;
import model.Plato;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class MesaPlatoDAO {
    private Connection conn; 

    public MesaPlatoDAO(Connection conn) {
        this.conn = conn;
    }

    // Agrega un plato a la mesa (registro del pedido)
    public boolean agregarPlatoAMesa(int idMesa, Plato plato, int cantidad) {
        String sql = "INSERT INTO mesa_plato (id_mesa, id_plato, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) { // Cambiado 'connection' a 'conn'
            ps.setInt(1, idMesa);
            ps.setInt(2, plato.getId_plato());
            ps.setInt(3, cantidad);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener lista de platos de una mesa
    public List<Plato> obtenerPlatosPorMesa(int idMesa) {
        List<Plato> lista = new ArrayList<>();
        String sql = "SELECT p.id_plato, p.nombre, p.precio, p.descripcion " +
                     "FROM mesa_plato mp " +
                     "JOIN plato p ON mp.id_plato = p.id_plato " +
                     "WHERE mp.id_mesa = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) { // Cambiado 'connection' a 'conn'
            ps.setInt(1, idMesa);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idPlato = rs.getInt("id_plato");
                String nombre = rs.getString("nombre");
                BigDecimal precio = rs.getBigDecimal("precio");
                String descripcion = rs.getString("descripcion");
                lista.add(new Plato(idPlato, nombre, precio, descripcion));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Eliminar un plato de una mesa (por ejemplo, al cancelar un pedido)
    public boolean eliminarPlatoDeMesa(int idMesa, int idPlato) {
        String sql = "DELETE FROM mesa_plato WHERE id_mesa = ? AND id_plato = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) { // Cambiado 'connection' a 'conn'
            ps.setInt(1, idMesa);
            ps.setInt(2, idPlato);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Limpiar todos los pedidos de una mesa (opcional, útil al cerrar cuenta)
    public boolean limpiarMesa(int idMesa) {
        String sql = "DELETE FROM mesa_plato WHERE id_mesa = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) { // Cambiado 'connection' a 'conn'
            ps.setInt(1, idMesa);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
