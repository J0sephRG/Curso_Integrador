/*
miguel
 */
package DAO;

import Interface.Pedido;
import Interface.PedidoDAO;
import Modelo.PedidoLlevar;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoLlevarDAO implements PedidoDAO {
    private final Connection connection;

    public PedidoLlevarDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insertar(Pedido pedido) throws SQLException {
        PedidoLlevar pl = (PedidoLlevar) pedido;
        String sql = "INSERT INTO PedidoLlevar (id_cliente, estado, fecha_pedido) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, pl.getIdCliente());
            stmt.setString(2, pl.getEstado());
            stmt.setTimestamp(3, pl.getFechaPedido());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Error al insertar pedido, ninguna fila afectada");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pl.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Error al obtener ID generado");
                }
            }
        }
    }

    @Override
    public List<Pedido> listarTodos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM PedidoLlevar";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PedidoLlevar pl = new PedidoLlevar(
                    rs.getInt("id_llevar"),
                    rs.getInt("id_cliente"),
                    rs.getString("estado"),
                    rs.getTimestamp("fecha_pedido")
                );
                pedidos.add(pl);
            }
        }
        return pedidos;
    }

    @Override
    public void actualizar(Pedido pedido) throws SQLException {
        PedidoLlevar pl = (PedidoLlevar) pedido;
        String sql = "UPDATE PedidoLlevar SET id_cliente = ?, estado = ?, fecha_pedido = ? WHERE id_llevar = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pl.getIdCliente());
            stmt.setString(2, pl.getEstado());
            stmt.setTimestamp(3, pl.getFechaPedido());
            stmt.setInt(4, pl.getId());
            
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM PedidoLlevar WHERE id_llevar = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Pedido buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM PedidoLlevar WHERE id_llevar = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PedidoLlevar(
                        rs.getInt("id_llevar"),
                        rs.getInt("id_cliente"),
                        rs.getString("estado"),
                        rs.getTimestamp("fecha_pedido")
                    );
                }
            }
        }
        return null;
    }
}
