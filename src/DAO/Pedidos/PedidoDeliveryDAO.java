package DAO.Pedidos;

import Modelo.Interface.Pedido;
import java.sql.*;
import Modelo.PedidosDelivery.PedidoDelivery;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PedidoDeliveryDAO implements PedidoDAO {
    private final Connection connection;

    public PedidoDeliveryDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insertar(Pedido pedido) throws SQLException {
        if (!(pedido instanceof PedidoDelivery pd)) {
            throw new IllegalArgumentException("El pedido no es un PedidoDelivery");
        }

        // Insertar en la tabla Pedido
        String sqlPedido = "INSERT INTO Pedido (id_cliente, tipo, estado, fecha_pedido) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmtPedido = connection.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
            stmtPedido.setInt(1, pd.getIdCliente());
            stmtPedido.setString(2, "delivery");
            stmtPedido.setString(3, pd.getEstado());
            stmtPedido.setTimestamp(4, pd.getFechaPedido());

            int affectedRows = stmtPedido.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al insertar pedido en la tabla Pedido");
            }

            // Obtener el ID generado del pedido
            try (ResultSet generatedKeys = stmtPedido.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pd.setId(generatedKeys.getInt(1)); // Asignar el ID del pedido
                } else {
                    throw new SQLException("Error al obtener ID generado");
                }
            }

            // Insertar los detalles en la tabla PedidoDelivery
            String sqlDetalle = "INSERT INTO PedidoDelivery (id_pedido, direccion_entrega) VALUES (?, ?)";
            try (PreparedStatement stmtDetalle = connection.prepareStatement(sqlDetalle)) {
                stmtDetalle.setInt(1, pd.getId());
                stmtDetalle.setString(2, pd.getDireccionEntrega());
                stmtDetalle.executeUpdate();
            }

        } catch (SQLException e) {
            throw new SQLException("Error al insertar el pedido: " + e.getMessage());
        }
    }

    @Override
    public List<Pedido> listarTodos() throws SQLException {
         List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM Pedido p JOIN PedidoDelivery pd ON p.id_pedido = pd.id_pedido";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PedidoDelivery pd = new PedidoDelivery(
                    rs.getInt("id_pedido"),
                    rs.getInt("id_cliente"),
                    rs.getString("direccion_entrega"),
                    rs.getString("estado"),
                    rs.getTimestamp("fecha_pedido") // Usamos rs.getTimestamp para la fecha
                );
                pedidos.add(pd);
            }
        }

        return pedidos;
    }

    @Override
    public void actualizar(Pedido pedido) throws SQLException {
        if (!(pedido instanceof PedidoDelivery pd)) {
            throw new IllegalArgumentException("El pedido no es un PedidoDelivery");
        }

        // Actualizar el pedido en la tabla Pedido
        String sqlPedido = "UPDATE Pedido SET id_cliente = ?, estado = ?, fecha_pedido = ? WHERE id_pedido = ?";
        try (PreparedStatement stmtPedido = connection.prepareStatement(sqlPedido)) {
            stmtPedido.setInt(1, pd.getIdCliente());
            stmtPedido.setString(2, pd.getEstado());
            stmtPedido.setTimestamp(3, pd.getFechaPedido());
            stmtPedido.setInt(4, pd.getId());

            stmtPedido.executeUpdate();
        }

        // Actualizar los detalles en la tabla PedidoDelivery
        String sqlDetalle = "UPDATE PedidoDelivery SET direccion_entrega = ? WHERE id_pedido = ?";
        try (PreparedStatement stmtDetalle = connection.prepareStatement(sqlDetalle)) {
            stmtDetalle.setString(1, pd.getDireccionEntrega());
            stmtDetalle.setInt(2, pd.getId());
            stmtDetalle.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        // Eliminar los detalles de PedidoDelivery
        String sqlDetalle = "DELETE FROM PedidoDelivery WHERE id_pedido = ?";
        try (PreparedStatement stmtDetalle = connection.prepareStatement(sqlDetalle)) {
            stmtDetalle.setInt(1, id);
            stmtDetalle.executeUpdate();
        }

        // Ahora eliminamos el pedido principal de la tabla Pedido
        String sqlPedido = "DELETE FROM Pedido WHERE id_pedido = ?";
        try (PreparedStatement stmtPedido = connection.prepareStatement(sqlPedido)) {
            stmtPedido.setInt(1, id);
            stmtPedido.executeUpdate();
        }
    }

    @Override
    public Pedido buscarPorId(int id) throws SQLException {
       String sql = "SELECT * FROM Pedido p JOIN PedidoDelivery pd ON p.id_pedido = pd.id_pedido WHERE p.id_pedido = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PedidoDelivery(
                        rs.getInt("id_pedido"),
                        rs.getInt("id_cliente"),
                        rs.getString("direccion_entrega"),
                        rs.getString("estado"),
                        rs.getTimestamp("fecha_pedido") // Usamos rs.getTimestamp directamente
                    );
                }
            }
        }

        return null;
    }
}
