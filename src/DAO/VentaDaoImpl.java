
package DAO;

import ConexcionSQL.ConnectionFactory;
import Modelo.Venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class VentaDaoImpl implements VentaDao {
    @Override
    public void agregarVenta(Venta venta) {
        String sql = "INSERT INTO Venta (id_venta, fecha_venta, id_usuario, metodo_pago, monto_total) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, venta.getId_venta());
            ps.setDate(2, (Date) venta.getFecha_venta());
            ps.setInt(3, venta.getId_usuario());
            ps.setString(4, venta.getMetodo_pago());
            ps.setDouble(5, venta.getMonto_total());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizarVenta(Venta venta) {
        String sql = "UPDATE Venta SET fecha_venta=?, id_usuario=?, metodo_pago=?, monto_total=? WHERE id_venta=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, (Date) venta.getFecha_venta());
            ps.setInt(2, venta.getId_usuario());
            ps.setString(3, venta.getMetodo_pago());
            ps.setDouble(4, venta.getMonto_total());
            ps.setInt(5, venta.getId_venta());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarVenta(int id_venta) {
        String sql = "DELETE FROM Venta WHERE id_venta=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id_venta);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Venta obtenerVenta(int id_venta) {
        String sql = "SELECT * FROM Venta WHERE id_venta=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id_venta);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Venta(
                        rs.getInt("id_venta"),
                        rs.getDate("fecha_venta"),
                        rs.getInt("id_usuario"),
                        rs.getString("metodo_pago"),
                        rs.getDouble("monto_total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Venta> obtenerTodasVentas() {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT * FROM Venta";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Venta(
                        rs.getInt("id_venta"),
                        rs.getDate("fecha_venta"),
                        rs.getInt("id_usuario"),
                        rs.getString("metodo_pago"),
                        rs.getDouble("monto_total")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}