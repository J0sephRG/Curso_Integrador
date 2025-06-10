/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.*;
import java.util.*;
import model.PedidoDelivery;

public class PedidoDeliveryDAO {
    private Connection connection;

    public PedidoDeliveryDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertar(PedidoDelivery pedido) throws SQLException {
        String sql = "INSERT INTO PedidoDelivery (id_cliente, direccion_entrega, estado, fecha_pedido) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getIdCliente());
            stmt.setString(2, pedido.getDireccionEntrega());
            stmt.setString(3, pedido.getEstado());
            stmt.setTimestamp(4, pedido.getFechaPedido());
            stmt.executeUpdate();
        }
    }

    public List<PedidoDelivery> listarTodos() throws SQLException {
        List<PedidoDelivery> lista = new ArrayList<>();
        String sql = "SELECT * FROM PedidoDelivery";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PedidoDelivery p = new PedidoDelivery();
                p.setId(rs.getInt("id_delivery"));
                p.setIdCliente(rs.getInt("id_cliente"));
                p.setDireccionEntrega(rs.getString("direccion_entrega"));
                p.setEstado(rs.getString("estado"));
                p.setFechaPedido(rs.getTimestamp("fecha_pedido"));
                lista.add(p);
            }
        }
        return lista;
    }
}
