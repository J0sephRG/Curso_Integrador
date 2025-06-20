package DAO;

import Interface.Pedido;
import Interface.PedidoDAO;
import Modelo.PedidoDelivery;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDeliveryDAO implements PedidoDAO {
    private final Connection connection;

    public PedidoDeliveryDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insertar(Pedido pedido) throws SQLException {
        PedidoDelivery pd = (PedidoDelivery) pedido;
        String sql = "INSERT INTO PedidoDelivery (id_cliente, direccion_entrega, estado, fecha_pedido) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, pd.getIdCliente());
            stmt.setString(2, pd.getDireccionEntrega());
            stmt.setString(3, pd.getEstado());
            stmt.setTimestamp(4, pd.getFechaPedido());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Error al insertar pedido, ninguna fila afectada");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pd.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Error al obtener ID generado");
                }
            }
        }
    }

    @Override
    public List<Pedido> listarTodos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM PedidoDelivery";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PedidoDelivery pd = new PedidoDelivery(
                    rs.getInt("id_delivery"),
                    rs.getInt("id_cliente"),
                    rs.getString("direccion_entrega"),
                    rs.getString("estado"),
                    rs.getTimestamp("fecha_pedido")
                );
                pedidos.add(pd);
            }
        }
        return pedidos;
    }

    @Override
    public void actualizar(Pedido pedido) throws SQLException {
        PedidoDelivery pd = (PedidoDelivery) pedido;
        String sql = "UPDATE PedidoDelivery SET id_cliente = ?, direccion_entrega = ?, estado = ?, fecha_pedido = ? WHERE id_delivery = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pd.getIdCliente());
            stmt.setString(2, pd.getDireccionEntrega());
            stmt.setString(3, pd.getEstado());
            stmt.setTimestamp(4, pd.getFechaPedido());
            stmt.setInt(5, pd.getId());
            
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM PedidoDelivery WHERE id_delivery = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Pedido buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM PedidoDelivery WHERE id_delivery = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PedidoDelivery(
                        rs.getInt("id_delivery"),
                        rs.getInt("id_cliente"),
                        rs.getString("direccion_entrega"),
                        rs.getString("estado"),
                        rs.getTimestamp("fecha_pedido")
                    );
                }
            }
        }
        return null;
    }
}
