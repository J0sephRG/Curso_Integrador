package Controlador;
import Vista.DescripcionAgregacionDePedidoDelivery;
import DAO.ClienteDAO;
import DAO.Pedidos.PedidoDeliveryDAO;
import DAO.PlatoDAO;
import DAO.VentaDAO;
import Modelo.Cliente;
import Modelo.DetalleVenta;
import Modelo.PedidosDelivery.PedidoDelivery;
import Modelo.Plato;
import Modelo.Usuario;
import Modelo.Venta;
import apiclientes.ApiClienteService;
import apiclientes.ApiClienteServiceImpl;

import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DescripcionDeliveryController {
    private final Connection connection;
    private final DescripcionAgregacionDePedidoDelivery vista;
    private final PlatoDAO platoDAO;
    private final ClienteDAO clienteDAO;
    private final VentaDAO ventaDAO;
    private final List<DetalleVenta> listaPedidos;
    private final int usuarioActualId;
    private final PedidoDeliveryDAO pedidoDeliveryDAO;
    
    public DescripcionDeliveryController(Connection connection, DescripcionAgregacionDePedidoDelivery vista) {
    this.connection = connection;
    this.vista = vista;
    Usuario usuario = Seguridad.Sesion.getUsuarioActual();
    this.usuarioActualId = (usuario != null) ? usuario.getIdUsuario() : -1; // Manejo por si no hay usuario logueado
    this.platoDAO = new PlatoDAO(connection);
    this.clienteDAO = new ClienteDAO(connection);
    this.ventaDAO = new VentaDAO(connection);
    this.listaPedidos = new ArrayList<>();
    this.pedidoDeliveryDAO = new PedidoDeliveryDAO(connection); 
}

    public void inicializar() {
        cargarComboPlatillos();
        cargarTipoPagoYComprobante();
    }
    private void cargarComboPlatillos() {
        try {
            List<Plato> platos = platoDAO.listarPlatos();
            vista.jComboBoxDeBusquedaDePlatillos.removeAllItems();
            for (Plato plato : platos) {
                vista.jComboBoxDeBusquedaDePlatillos.addItem(plato.getNombre());
            }
        } catch (SQLException e) {
            vista.mostrarMensaje("Error al cargar los platillos: " + e.getMessage());
        }
    }

    private void cargarTipoPagoYComprobante() {
        vista.jComboBoxTipoDePago.addItem("Efectivo");
        vista.jComboBoxTipoDePago.addItem("Tarjeta");
        vista.jComboBoxTipoDePago.addItem("Billetera Digital");
        vista.jComboBoxTipoDeComprobante.addItem("Factura");
        vista.jComboBoxTipoDeComprobante.addItem("Boleta por DNI");
        vista.jComboBoxTipoDeComprobante.addItem("Boleta Simple");
    }

    public void buscarClientePorDNI() {
    String dni = vista.getDni();

    if (dni == null || dni.trim().length() != 8) {
        vista.mostrarMensaje("El DNI debe tener 8 dígitos.");
        return;
    }

    try {
        ApiClienteService api = new ApiClienteServiceImpl();
        String nombre = api.obtenerNombrePorDni(dni);

        if (nombre != null && !nombre.isEmpty()) {
            vista.setTextNombre(nombre);
        } else {
            vista.mostrarMensaje("No se encontró el cliente con DNI: " + dni);
        }
    } catch (Exception e) {
        vista.mostrarMensaje("Error al consultar el DNI: " + e.getMessage());
        e.printStackTrace();
    }
}

    public void agregarPedido() {
        try {
            String platoNombre = vista.getPlatilloSeleccionado();
            int cantidad = Integer.parseInt(vista.getCantidad());

            List<Plato> platos = platoDAO.listarPlatos();
            Optional<Plato> optPlato = platos.stream()
                    .filter(p -> p.getNombre().equals(platoNombre))
                    .findFirst();

            if (!optPlato.isPresent()) {
                vista.mostrarMensaje("Plato no encontrado.");
                return;
            }

            Plato plato = optPlato.get();
            BigDecimal total = plato.getPrecio().multiply(BigDecimal.valueOf(cantidad));

            DetalleVenta detalle = new DetalleVenta(
                    0, 0, plato.getId_plato(), cantidad, plato.getPrecio(), total
            );
            listaPedidos.add(detalle);
            actualizarTabla();
            actualizarTotales();
        } catch (NumberFormatException ex) {
            vista.mostrarMensaje("Cantidad inválida.");
        } catch (SQLException ex) {
            vista.mostrarMensaje("Error al agregar pedido: " + ex.getMessage());
        }
    }

    public void eliminarPedido(int filaSeleccionada) {
        if (filaSeleccionada < 0 || filaSeleccionada >= listaPedidos.size()) {
            vista.mostrarMensaje("Seleccione un pedido válido.");
            return;
        }
        listaPedidos.remove(filaSeleccionada);
        actualizarTabla();
        actualizarTotales();
    }

    private void actualizarTabla() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Plato");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("Total");

        for (DetalleVenta detalle : listaPedidos) {
            try {
                Plato plato = platoDAO.obtenerPlato(detalle.getId_producto());
                modelo.addRow(new Object[]{
                        detalle.getId_producto(),
                        plato.getNombre(),
                        detalle.getCantidad(),
                        detalle.getPrecio_unitario(),
                        detalle.getSubtotal()
                });
            } catch (SQLException e) {
                vista.mostrarMensaje("Error al cargar tabla: " + e.getMessage());
            }
        }
        vista.setModeloTablaPedidos(modelo);
    }

    public void actualizarTotales() {
        BigDecimal subtotal = listaPedidos.stream()
                .map(DetalleVenta::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal igv = subtotal.multiply(BigDecimal.valueOf(0.05));
        vista.setIGV(igv.toString());

        BigDecimal descuento = BigDecimal.ZERO;
        try {
            String descTexto = vista.jTextFieldDescuento.getText().trim();
            if (!descTexto.isEmpty()) {
                descuento = subtotal.multiply(new BigDecimal(descTexto).divide(BigDecimal.valueOf(100)));
            }
        } catch (NumberFormatException ignored) {}

        vista.setResultadoDescuento(descuento.toString());

        BigDecimal totalFinal = subtotal.subtract(descuento);
        vista.setTotalDeVenta(totalFinal.toString());
        vista.setMontoACobrar(totalFinal.toString());
    }

    public void calcularVuelto() {
        try {
            BigDecimal montoPago = new BigDecimal(vista.getMontoDePago());
            BigDecimal montoCobrar = new BigDecimal(vista.jTextFieldMontoACobrar.getText());
            BigDecimal vuelto = montoPago.subtract(montoCobrar);
            vista.setVuelto(vuelto.toString());
        } catch (NumberFormatException e) {
            vista.setVuelto("0.00");
        }
    }

public void registrarVenta() {
    try {
        BigDecimal totalVenta = new BigDecimal(vista.jTextFieldTotalDeVenta.getText().trim());
        Venta venta = new Venta(
            new Timestamp(System.currentTimeMillis()),
            usuarioActualId,
            vista.getTipoDePago(),
            totalVenta
        );

        int idVentaGenerado = ventaDAO.agregarVenta(venta);
        if (idVentaGenerado <= 0) {
            vista.mostrarMensaje("No se pudo registrar la venta.");
            return;
        }

        venta.setId_venta(idVentaGenerado);

        for (DetalleVenta detalle : listaPedidos) {
            detalle.setId_venta(idVentaGenerado);
            ventaDAO.agregarDetalleVenta(detalle);
        }

        vista.mostrarMensaje("Venta registrada correctamente.");
        listaPedidos.clear();
        actualizarTabla();
        actualizarTotales();

    } catch (SQLException e) {
        vista.mostrarMensaje("Error de base de datos al registrar la venta: " + e.getMessage());
    } catch (NumberFormatException e) {
        vista.mostrarMensaje("Formato numérico inválido: " + e.getMessage());
    }
}

    public void registrarPedidoDelivery() {
    try {
        String dni = vista.getDni();
        String direccionEntrega = vista.getDireccionEntrega(); // Asume que tienes un método en la vista para esto
        String estado = "pendiente"; // Puedes ajustar según tu lógica
        Timestamp fechaPedido = new Timestamp(System.currentTimeMillis());

        List<Cliente> clientes = clienteDAO.listarClientes();
        Optional<Cliente> clienteOpt = clientes.stream()
                .filter(c -> c.getDni().equals(dni))
                .findFirst();

        if (!clienteOpt.isPresent()) {
            vista.mostrarMensaje("Cliente no encontrado. Verifica el DNI.");
            return;
        }

        int idCliente = clienteOpt.get().getId_cliente();

        // Crear objeto PedidoDelivery con ID 0 porque será autogenerado
        PedidoDelivery pedido = new PedidoDelivery(0, idCliente, direccionEntrega, estado, fechaPedido);

        // Insertar en base de datos
        pedidoDeliveryDAO.insertar(pedido);

        vista.mostrarMensaje("Pedido Delivery registrado con ID: " + pedido.getId());

    } catch (SQLException e) {
        vista.mostrarMensaje("Error al registrar el pedido: " + e.getMessage());
    } catch (Exception e) {
        vista.mostrarMensaje("Error inesperado: " + e.getMessage());
    }
}


}