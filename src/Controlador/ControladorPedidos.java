package Controlador;
/**@author Miguel*/

/*import DAO.*;
import Interface.Pedido;
import Interface.PedidoDAO;
import Modelo.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Modelo.PedidoDelivery;
import Modelo.PedidoLlevar;
*/


import DAO.PRUEBADEPEDIDOS.PedidoDelivery;
import DAO.PRUEBADEPEDIDOS.PedidoDeliveryDAOMock;
import DAO.PRUEBADEPEDIDOS.PedidoLlevar;
import DAO.PRUEBADEPEDIDOS.PedidoLlevarDAOMock;
import DAO.PRUEVAINTERFACE.PedidoDAO;
import Interface.Pedido;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ControladorPedidos {
    private final PedidoDAO deliveryDAO;
    private final PedidoDAO llevarDAO;

    public ControladorPedidos(boolean modoPrueba) {
        this.deliveryDAO = new PedidoDeliveryDAOMock();
        this.llevarDAO = new PedidoLlevarDAOMock();
        cargarDatosDePrueba();
    }

    private void cargarDatosDePrueba() {
        // Datos de prueba iniciales
        agregarPedido(new PedidoDelivery(0, 1, "Calle Principal 123", "PENDIENTE", new Timestamp(System.currentTimeMillis())));
        agregarPedido(new PedidoDelivery(0, 2, "Avenida Siempre Viva 456", "EN CAMINO", new Timestamp(System.currentTimeMillis())));
        agregarPedido(new PedidoLlevar(0, 3, "PREPARANDO", new Timestamp(System.currentTimeMillis())));
        agregarPedido(new PedidoLlevar(0, 4, "LISTO", new Timestamp(System.currentTimeMillis())));
    }

    public void agregarPedido(Pedido pedido) {
        if (pedido.getTipoPedido().equals("DELIVERY")) {
            deliveryDAO.insertar(pedido);
        } else {
            llevarDAO.insertar(pedido);
        }
    }

    public void actualizarPedido(Pedido pedido) {
        if (pedido.getTipoPedido().equals("DELIVERY")) {
            deliveryDAO.actualizar(pedido);
        } else {
            llevarDAO.actualizar(pedido);
        }
    }

    public void eliminarPedido(int id, String tipoPedido) {
        if (tipoPedido.equals("DELIVERY")) {
            deliveryDAO.eliminar(id);
        } else {
            llevarDAO.eliminar(id);
        }
    }

    public Pedido buscarPedidoPorId(int id, String tipoPedido) {
        if (tipoPedido.equals("DELIVERY")) {
            return deliveryDAO.buscarPorId(id);
        } else {
            return llevarDAO.buscarPorId(id);
        }
    }

    public List<Pedido> listarTodosLosPedidos() {
        List<Pedido> todosLosPedidos = new ArrayList<>();
        todosLosPedidos.addAll(deliveryDAO.listarTodos());
        todosLosPedidos.addAll(llevarDAO.listarTodos());
        return todosLosPedidos;
    }

    public List<Pedido> listarPedidosPorTipo(String tipoPedido) {
        if (tipoPedido.equals("DELIVERY")) {
            return deliveryDAO.listarTodos();
        } else {
            return llevarDAO.listarTodos();
        }
    }
}








/* ESTE ES EL CODIGO ORIGINAL CON LA CONEXION
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
}*/
