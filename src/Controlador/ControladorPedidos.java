package Controlador;
/**@author Miguel*/

import DAO.*;
import Interface.Pedido;
import Interface.PedidoDAO;
import Modelo.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Modelo.PedidoDelivery;
import Modelo.PedidoLlevar;








public class ControladorPedidos {
    private final PedidoDAO deliveryDAO;
    private final PedidoDAO llevarDAO;

    public ControladorPedidos(Connection connection) {
        this.deliveryDAO = new PedidoDeliveryDAO(connection);
        this.llevarDAO = new PedidoLlevarDAO(connection);
    }

    public void agregarPedido(Pedido pedido) throws SQLException {
        if (pedido.getTipoPedido().equals("DELIVERY")) {
            deliveryDAO.insertar(pedido);
        } else {
            llevarDAO.insertar(pedido);
        }
    }

    public void actualizarPedido(Pedido pedido) throws SQLException {
        if (pedido.getTipoPedido().equals("DELIVERY")) {
            deliveryDAO.actualizar(pedido);
        } else {
            llevarDAO.actualizar(pedido);
        }
    }

    public void eliminarPedido(int id, String tipoPedido) throws SQLException {
        if (tipoPedido.equals("DELIVERY")) {
            deliveryDAO.eliminar(id);
        } else {
            llevarDAO.eliminar(id);
        }
    }

    public Pedido buscarPedidoPorId(int id, String tipoPedido) throws SQLException {
        if (tipoPedido.equals("DELIVERY")) {
            return deliveryDAO.buscarPorId(id);
        } else {
            return llevarDAO.buscarPorId(id);
        }
    }

    public List<Pedido> listarTodosLosPedidos() throws SQLException {
        List<Pedido> todosLosPedidos = new ArrayList<>();
        todosLosPedidos.addAll(deliveryDAO.listarTodos());
        todosLosPedidos.addAll(llevarDAO.listarTodos());
        return todosLosPedidos;
    }

    public List<Pedido> listarPedidosPorTipo(String tipoPedido) throws SQLException {
        if (tipoPedido.equals("DELIVERY")) {
            return deliveryDAO.listarTodos();
        } else {
            return llevarDAO.listarTodos();
        }
    }
}
