package Controlador;
/**@author Miguel*/

import DAO.Pedidos.PedidoDAO;
import DAO.Pedidos.PedidoDeliveryDAO;
import DAO.Pedidos.PedidoLlevarDAO;
import Modelo.Interface.Pedido;
import Modelo.Usuario;
import Seguridad.Sesion;

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
        // Obtener el usuario actual desde la sesión
        Usuario usuarioActual = Sesion.getUsuarioActual();
        
        // Verificar si el usuario está autenticado
        if (usuarioActual == null) {
            throw new IllegalStateException("No hay un usuario autenticado.");
        }

        // Aquí podrías agregar más validaciones basadas en el usuario (por ejemplo, permisos)
        if (!usuarioActual.getRol().equals("admin")) {
            throw new IllegalArgumentException("El usuario no tiene permisos para agregar un pedido.");
        }

        // Proceder con el agregar pedido si el usuario tiene permisos
        if (pedido instanceof Modelo.PedidosDelivery.PedidoDelivery) {
            deliveryDAO.insertar(pedido);
        } else if (pedido instanceof Modelo.PedidosLlevar.PedidoLlevar) {
            llevarDAO.insertar(pedido);
        } else {
            throw new IllegalArgumentException("Tipo de pedido no reconocido");
        }
    }

    public void actualizarPedido(Pedido pedido) throws SQLException {
        // Obtener el usuario actual desde la sesión
        Usuario usuarioActual = Sesion.getUsuarioActual();
        
        // Verificar si el usuario está autenticado
        if (usuarioActual == null) {
            throw new IllegalStateException("No hay un usuario autenticado.");
        }

        // Validación de permisos para actualizar el pedido
        if (!usuarioActual.getRol().equals("admin")) {
            throw new IllegalArgumentException("El usuario no tiene permisos para actualizar un pedido.");
        }

        // Actualizar el pedido si el usuario tiene permisos
        if (pedido instanceof Modelo.PedidosDelivery.PedidoDelivery) {
            deliveryDAO.actualizar(pedido);
        } else if (pedido instanceof Modelo.PedidosLlevar.PedidoLlevar) {
            llevarDAO.actualizar(pedido);
        } else {
            throw new IllegalArgumentException("Tipo de pedido no reconocido");
        }
    }

    public void eliminarPedido(int id, String tipoPedido) throws SQLException {
        // Obtener el usuario actual desde la sesión
        Usuario usuarioActual = Sesion.getUsuarioActual();
        
        // Verificar si el usuario está autenticado
        if (usuarioActual == null) {
            throw new IllegalStateException("No hay un usuario autenticado.");
        }

        // Validación de permisos para eliminar el pedido
        if (!usuarioActual.getRol().equals("admin")) {
            throw new IllegalArgumentException("El usuario no tiene permisos para eliminar un pedido.");
        }

        // Eliminar el pedido si el usuario tiene permisos
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
