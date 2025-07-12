package Controlador;

import Vista.DescripcionAgregacionDePedioLLevar;
import DAO.ClienteDAO;
import DAO.Pedidos.PedidoLlevarDAO;
import DAO.PlatoDAO;
import DAO.VentaDAO;
import Modelo.Cliente;
import Modelo.DetalleVenta;
import Modelo.PedidosLlevar.PedidoLlevar;
import Modelo.Plato;
import Modelo.Venta;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import Seguridad.Sesion;
import Modelo.Usuario;
import apiclientes.ApiClienteService;
import apiclientes.ApiClienteServiceImpl;


public class DescripcionPedidoLlevarController {
    private final Connection connection;
    private final DescripcionAgregacionDePedioLLevar vista;
    private final PlatoDAO platoDAO;
    private final ClienteDAO clienteDAO;
    private final VentaDAO ventaDAO;
    private final List<DetalleVenta> listaPedidos;
    private final int usuarioActualId;
    private final PedidoLlevarDAO pedidoLlevarDAO;
    
    public DescripcionPedidoLlevarController(Connection connection, DescripcionAgregacionDePedioLLevar vista) {
    this.connection = connection;
    this.vista = vista;

    Usuario usuario = Sesion.getUsuarioActual();
    if (usuario == null) {
        throw new IllegalStateException("No hay un usuario en sesión.");
    }

    this.usuarioActualId = usuario.getId_usuario(); 
    this.platoDAO = new PlatoDAO(connection);
    this.clienteDAO = new ClienteDAO(connection);
    this.ventaDAO = new VentaDAO(connection);
    this.listaPedidos = new ArrayList<>(); 
    this.pedidoLlevarDAO = new PedidoLlevarDAO(connection);
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
        // Validación de ID de usuario
        if (usuarioActualId <= 0) {
            vista.mostrarMensaje("ID de usuario inválido. No se puede registrar la venta.");
            return;
        }

        // Crear la venta sin ID (el ID será generado por la base de datos)
        BigDecimal totalVenta = new BigDecimal(vista.jTextFieldTotalDeVenta.getText().trim());
        if (totalVenta.compareTo(BigDecimal.ZERO) <= 0) {
            vista.mostrarMensaje("El total de la venta debe ser mayor a cero.");
            return;
        }

        Venta venta = new Venta(
            new Timestamp(System.currentTimeMillis()), // Fecha y hora actual
            usuarioActualId, // ID del usuario que realiza la venta
            vista.getTipoDePago(), // Tipo de pago
            totalVenta // Monto total de la venta
        );

        // Registrar la venta y obtener el ID generado por la BD
        int idVentaGenerado = ventaDAO.agregarVenta(venta);
        if (idVentaGenerado <= 0) {
            vista.mostrarMensaje("No se pudo registrar la venta.");
            return;
        }
        venta.setId_venta(idVentaGenerado); // Asignar el ID generado

        // Registrar los detalles de la venta
        for (DetalleVenta detalle : listaPedidos) {
            // Asegurarse de que cada detalle tenga el ID de venta actualizado
            detalle.setId_venta(idVentaGenerado);
            ventaDAO.agregarDetalleVenta(detalle);
        }


        vista.mostrarMensaje("Venta registrada correctamente.");

    } catch (SQLException e) {
        vista.mostrarMensaje("Error al registrar la venta: " + e.getMessage());
        e.printStackTrace(); // Puedes removerlo en producción
    } catch (NumberFormatException e) {
        vista.mostrarMensaje("El total de la venta no es un valor válido.");
        e.printStackTrace(); // Puedes removerlo en producción
    }
}

    public void registrarPedidollevar() {
        try {
            // Obtener y validar el DNI
            String dni = vista.getDni().trim();
            if (dni.isEmpty()) {
                vista.mostrarMensaje("Debe ingresar un DNI.");
                return;
            }

            Optional<Cliente> clienteOpt = clienteDAO.listarClientes().stream()
                    .filter(c -> c.getDni().equals(dni))
                    .findFirst();

            if (!clienteOpt.isPresent()) {
                vista.mostrarMensaje("Cliente no encontrado.");
                return;
            }

            Cliente cliente = clienteOpt.get();

            // Crear el pedido usando el constructor que ya tienes
            /*Timestamp fechaActual = new Timestamp(System.currentTimeMillis());*/
            PedidoLlevar pedido = new PedidoLlevar(0, cliente.getId_cliente(), "pendiente", new Timestamp(System.currentTimeMillis()));

            // Insertar el pedido
            pedidoLlevarDAO.insertar(pedido);

            vista.mostrarMensaje("Pedido para llevar registrado con ID: " + pedido.getId());

        } catch (SQLException e) {
            vista.mostrarMensaje("Error al registrar el pedido: " + e.getMessage());
            e.printStackTrace();
        }
    }


}