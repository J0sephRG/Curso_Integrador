
package DAO;

import ConexcionSQL.ConnectionFactory;
import Modelo.Categoria;
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

public class CategoriaDaoImpl implements CategoriaDao {
    @Override
    public void agregarCategoria(Categoria categoria) {
        String sql = "INSERT INTO Categoria (id_categoria, nombre_categoria, descripcion) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoria.getId_categoria());
            ps.setString(2, categoria.getNombre_categoria());
            ps.setString(3, categoria.getDescripcion());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizarCategoria(Categoria categoria) {
        String sql = "UPDATE Categoria SET nombre_categoria=?, descripcion=? WHERE id_categoria=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, categoria.getNombre_categoria());
            ps.setString(2, categoria.getDescripcion());
            ps.setInt(3, categoria.getId_categoria());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarCategoria(int id_categoria) {
        String sql = "DELETE FROM Categoria WHERE id_categoria=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id_categoria);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Categoria obtenerCategoria(int id_categoria) {
        String sql = "SELECT * FROM Categoria WHERE id_categoria=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id_categoria);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Categoria(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre_categoria"),
                        rs.getString("descripcion"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Categoria> obtenerTodasCategorias() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM Categoria";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Categoria(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre_categoria"),
                        rs.getString("descripcion")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}