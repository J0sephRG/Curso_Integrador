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

    public ArrayList<Producto> obtenerProductos() {
        ArrayList<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM Producto";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Categoria categoria = new Categoria(
                    rs.getInt("id_categoria"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                );

                Producto producto = new Producto(
                    rs.getInt("id_producto"),
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getInt("stock_actual"),
                    rs.getInt("stock_minimo"),
                    rs.getString("unidad_medida"),
                    categoria,
                    rs.getString("descripcion")
                );

                productos.add(producto);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
        }

        return productos;
}

    
}
