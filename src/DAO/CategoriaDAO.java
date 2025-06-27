/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modelo.Categoria;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
/**
 *
 * @author JOSEPH ROJAS
 */
public class CategoriaDAO {
    
     private Connection conn;

    public CategoriaDAO(Connection conn) {
        this.conn = conn;
    }
     
    public ArrayList<Categoria> obtenerCategorias() {
        ArrayList<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM Categoria";  

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Categoria categoria = new Categoria(
                    rs.getInt("id_categoria"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                );
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener categorías: " + e.getMessage());
        }

        return categorias;
    
    }
    
}
