package Controlador;
/**@author Miguel*/

import DAO.Pedidos.PedidoDAO;
import DAO.Pedidos.PedidoDeliveryDAO;
import DAO.Pedidos.PedidoLlevarDAO;
import Modelo.Interface.Pedido;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ControladorPedidosTablas {
    private final Connection connection;
    private final PedidoDAO deliveryDAO;
    private final PedidoDAO llevarDAO;

    public ControladorPedidosTablas(Connection connection) {
        this.connection = connection;
        this.deliveryDAO = new PedidoDeliveryDAO(connection);
        this.llevarDAO = new PedidoLlevarDAO(connection);
    }

    public void agregarPedido(Pedido pedido) throws SQLException {
        if (pedido instanceof Modelo.PedidosDelivery.PedidoDelivery) {
            deliveryDAO.insertar(pedido);
        } else if (pedido instanceof Modelo.PedidosLlevar.PedidoLlevar) {
            llevarDAO.insertar(pedido);
        } else {
            throw new IllegalArgumentException("Tipo de pedido no reconocido");
        }
    }

    public void actualizarPedido(Pedido pedido) throws SQLException {
        if (pedido instanceof Modelo.PedidosDelivery.PedidoDelivery) {
            deliveryDAO.actualizar(pedido);
        } else if (pedido instanceof Modelo.PedidosLlevar.PedidoLlevar) {
            llevarDAO.actualizar(pedido);
        } else {
            throw new IllegalArgumentException("Tipo de pedido no reconocido");
        }
    }

    public void eliminarPedido(int id, String tipoPedido) throws SQLException {
        if (tipoPedido.equalsIgnoreCase("DELIVERY")) {
            deliveryDAO.eliminar(id);
        } else if (tipoPedido.equalsIgnoreCase("llevar")) {
            llevarDAO.eliminar(id);
        } else {
            throw new IllegalArgumentException("Tipo de pedido no reconocido");
        }
    }

    public Pedido buscarPedidoPorId(int id, String tipoPedido) throws SQLException {
        if (tipoPedido.equalsIgnoreCase("DELIVERY")) {
            return deliveryDAO.buscarPorId(id);
        } else if (tipoPedido.equalsIgnoreCase("llevar")) {
            return llevarDAO.buscarPorId(id);
        } else {
            throw new IllegalArgumentException("Tipo de pedido no reconocido");
        }
    }

    public List<Pedido> listarPedidosPorTipo(String tipoPedido) throws SQLException {
        if (tipoPedido.equalsIgnoreCase("DELIVERY")) {
            return deliveryDAO.listarTodos();
        } else if (tipoPedido.equalsIgnoreCase("llevar")) {
            return llevarDAO.listarTodos();
        } else {
            throw new IllegalArgumentException("Tipo de pedido no reconocido");
        }
    }

    public Connection getConnection() {
        return this.connection;
    }
}
