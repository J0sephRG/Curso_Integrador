/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
miguel
 */
package DAO;

import java.util.*;
import model.PedidoLlevar;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class PedidoLlevarDAO {
    private Connection connection;

    public PedidoLlevarDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertar(PedidoLlevar pedido) throws SQLException {
        String sql = "INSERT INTO PedidoLlevar (id_cliente, estado, fecha_pedido) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getIdCliente());
            stmt.setString(2, pedido.getEstado());
            stmt.setTimestamp(3, pedido.getFechaPedido());
            stmt.executeUpdate();
        }
    }

    public List<PedidoLlevar> listarTodos() throws SQLException {
        List<PedidoLlevar> lista = new ArrayList<>();
        String sql = "SELECT * FROM PedidoLlevar";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PedidoLlevar p = new PedidoLlevar();
                p.setId(rs.getInt("id_llevar"));
                p.setIdCliente(rs.getInt("id_cliente"));
                p.setEstado(rs.getString("estado"));
                p.setFechaPedido(rs.getTimestamp("fecha_pedido"));
                lista.add(p);
            }
        }
        return lista;
    }
}
