/*
miguel
 */
package DAO.Pedidos;
import java.sql.*;
import Modelo.PedidosLlevar.PedidoLlevar;
import Modelo.Interface.Pedido;
import java.util.ArrayList;
import java.util.List;

public class PedidoLlevarDAO implements PedidoDAO {
    private final Connection connection;

    public PedidoLlevarDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
        public void insertar(Pedido pedido) throws SQLException {
            if (!(pedido instanceof PedidoLlevar pl)) {
                throw new IllegalArgumentException("El pedido no es un PedidoLlevar");
            }

            String sqlPedido = "INSERT INTO Pedido (id_cliente, tipo, estado, fecha_pedido) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmtPedido = connection.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
                stmtPedido.setInt(1, pl.getIdCliente());
                stmtPedido.setString(2, "llevar");
                stmtPedido.setString(3, pl.getEstado());
                stmtPedido.setTimestamp(4, pl.getFechaPedido());

                int affectedRows = stmtPedido.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Error al insertar pedido en la tabla Pedido");
                }

                try (ResultSet generatedKeys = stmtPedido.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        pl.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Error al obtener ID generado");
                    }
                }

            } catch (SQLException e) {
                throw new SQLException("Error al insertar el pedido: " + e.getMessage());
            }
        }

    @Override
    public List<Pedido> listarTodos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM Pedido WHERE tipo = 'llevar'";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PedidoLlevar pl = new PedidoLlevar(
                    rs.getInt("id_pedido"),
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
        if (!(pedido instanceof PedidoLlevar pl)) {
            throw new IllegalArgumentException("El pedido no es un PedidoLlevar");
        }

        // Actualizar el pedido en la tabla Pedido
        String sqlPedido = "UPDATE Pedido SET id_cliente = ?, estado = ?, fecha_pedido = ? WHERE id_pedido = ?";
        try (PreparedStatement stmtPedido = connection.prepareStatement(sqlPedido)) {
            stmtPedido.setInt(1, pl.getIdCliente());
            stmtPedido.setString(2, pl.getEstado());
            stmtPedido.setTimestamp(3, pl.getFechaPedido());
            stmtPedido.setInt(4, pl.getId());

            stmtPedido.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
         String sql = "DELETE FROM Pedido WHERE id_pedido = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Pedido buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Pedido WHERE id_pedido = ? AND tipo = 'llevar'";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PedidoLlevar(
                        rs.getInt("id_pedido"),
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
