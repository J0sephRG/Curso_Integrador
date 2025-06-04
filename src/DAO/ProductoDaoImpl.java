
package DAO;
import ConexcionSQL.ConnectionFactory;
import Modelo.Producto;
import Modelo.Venta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.*; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ProductoDaoImpl implements ProductoDao {
    @Override
    public void agregarProducto(Producto producto) {
        String sql = "INSERT INTO Producto (id_producto, nombre, stock_actual, stock_minimo, precio_unitario, unidad_medida, id_categoria, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, producto.getId_producto());
            ps.setString(2, producto.getNombre());
            ps.setInt(3, producto.getStock_actual());
            ps.setInt(4, producto.getStock_minimo());
            ps.setDouble(5, producto.getPrecio_unitario());
            ps.setString(6, producto.getUnidad_medida());
            ps.setInt(7, producto.getId_categoria());
            ps.setString(8, producto.getDescripcion());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizarProducto(Producto producto) {
        String sql = "UPDATE Producto SET nombre=?, stock_actual=?, stock_minimo=?, precio_unitario=?, unidad_medida=?, id_categoria=?, descripcion=? WHERE id_producto=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, producto.getNombre());
            ps.setInt(2, producto.getStock_actual());
            ps.setInt(3, producto.getStock_minimo());
            ps.setDouble(4, producto.getPrecio_unitario());
            ps.setString(5, producto.getUnidad_medida());
            ps.setInt(6, producto.getId_categoria());
            ps.setString(7, producto.getDescripcion());
            ps.setInt(8, producto.getId_producto());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarProducto(int id_producto) {
        String sql = "DELETE FROM Producto WHERE id_producto=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id_producto);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Producto obtenerProducto(int id_producto) {
        String sql = "SELECT * FROM Producto WHERE id_producto=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id_producto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getInt("stock_actual"),
                        rs.getInt("stock_minimo"),
                        rs.getDouble("precio_unitario"),
                        rs.getString("unidad_medida"),
                        rs.getInt("id_categoria"),
                        rs.getString("descripcion"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Producto> obtenerTodosProductos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM Producto";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getInt("stock_actual"),
                        rs.getInt("stock_minimo"),
                        rs.getDouble("precio_unitario"),
                        rs.getString("unidad_medida"),
                        rs.getInt("id_categoria"),
                        rs.getString("descripcion")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
