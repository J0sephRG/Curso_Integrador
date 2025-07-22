/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modelo.Categoria;
import Modelo.Producto;
import java.sql.Connection;
import java.sql.ResultSet;
 import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.Statement;
/**
 *
 * @author JOSEPH ROJAS
 */
public class ProductoDAO {
    private Connection conn; 

    public ProductoDAO(Connection conn) {
        this.conn = conn;
    }
    
    public boolean insertarProducto(Producto producto) {
        String sql = "INSERT INTO Producto (nombre, precio, stock_actual, stock_minimo, unidad_medida, descripcion, id_categoria) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getStockAct());
            ps.setInt(4, producto.getStockMin());
            ps.setString(5, producto.getUnidadMedida());
            ps.setString(6, producto.getDescripcion());
            ps.setInt(7, producto.getCategoria().getIdcategoria()); 

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al insertar producto: " + e.getMessage());
            return false;
        }
    }
    //metodo antes, o sea que muestra los productos en la tabla, antes de realizar alguna accion del CRUD
    public ArrayList<Producto> obtenerProductos() {
        String query = "SELECT * FROM Producto"; 
        ArrayList<Producto> productos = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id_producto");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio_unitario"); 
                int stockAct = rs.getInt("stock_actual"); 
                int stockMin = rs.getInt("stock_minimo"); 
                String unidadMedida = rs.getString("unidad_medida"); 
                String descripcion = rs.getString("descripcion");
                int categoriaId = rs.getInt("id_categoria"); 

                String categoriaNombre = rs.getString("nombre"); 
                String categoriaDescripcion = rs.getString("descripcion"); 
                Categoria categoria = new Categoria(categoriaId, categoriaNombre, categoriaDescripcion);
                Producto producto = new Producto(id, nombre, precio, stockAct, stockMin, unidadMedida, categoria, descripcion);
                productos.add(producto);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

 public Categoria obtenerCategoriaPorId(int categoriaId) {
    String query = "SELECT * FROM Categoria WHERE id_categoria = ?";
    Categoria categoria = null;
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, categoriaId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String descripcion = rs.getString("descripcion");
            categoria = new Categoria(
                rs.getInt("id_categoria"),
                rs.getString("nombre_categoria"),  // ← corregido
                descripcion
            );
        }
        rs.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return categoria;
}

    public void eliminarProducto(int idProducto) {
    String query = "DELETE FROM Producto WHERE id_producto = ?";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, idProducto);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
 
    public ArrayList<Producto> buscarProductos(String searchTerm) {
        ArrayList<Producto> productos = new ArrayList<>();
        String query = "SELECT * FROM Producto WHERE nombre LIKE ? OR id_producto LIKE ? OR precio LIKE ? OR stock_actual LIKE ? OR stock_minimo LIKE ? OR unidad_medida LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            String searchQuery = "%" + searchTerm + "%"; 
            stmt.setString(1, searchQuery);
            stmt.setString(2, searchQuery);
            stmt.setString(3, searchQuery);
            stmt.setString(4, searchQuery);
            stmt.setString(5, searchQuery);
            stmt.setString(6, searchQuery);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Producto p = new Producto(
                    rs.getInt("id_producto"),
                    rs.getString("nombre"),
                    rs.getDouble("precio_unitario"),
                    rs.getInt("stock_actual"),
                    rs.getInt("stock_minimo"),
                    rs.getString("unidad_medida"),
                    new Categoria(rs.getInt("id_categoria"), rs.getString("nombre"), rs.getString("descripcion")),
                    rs.getString("descripcion")
                );
                productos.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }
}
