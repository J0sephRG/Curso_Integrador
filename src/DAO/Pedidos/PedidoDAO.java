package DAO.Pedidos;

import Modelo.Interface.Pedido;
import java.sql.SQLException;
import java.util.List;

public interface PedidoDAO {
    void insertar(Pedido pedido) throws SQLException;
    void actualizar(Pedido pedido) throws SQLException;
    void eliminar(int id) throws SQLException;
    Pedido buscarPorId(int id) throws SQLException;
    List<Pedido> listarTodos() throws SQLException;
}

