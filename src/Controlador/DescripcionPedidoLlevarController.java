package Controlador;

import Vista.DescripcionAgregacionDePedioLLevar;
import DAO.ClienteDAO;
import DAO.PlatoDAO;
import DAO.VentaDAO;
import Modelo.Cliente;
import Modelo.DetalleVenta;
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

public class DescripcionPedidoLlevarController {
    private final Connection connection;
    private final DescripcionAgregacionDePedioLLevar vista;
    private final PlatoDAO platoDAO;
    private final ClienteDAO clienteDAO;
    private final VentaDAO ventaDAO;
    private final List<DetalleVenta> listaPedidos;

    public DescripcionPedidoLlevarController(Connection connection, DescripcionAgregacionDePedioLLevar vista) {
        this.connection = connection;
        this.vista = vista;
        this.platoDAO = new PlatoDAO(connection);
        this.clienteDAO = new ClienteDAO(connection);
        this.ventaDAO = new VentaDAO(connection);
        this.listaPedidos = new ArrayList<>();
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
        try {
            List<Cliente> clientes = clienteDAO.listarClientes();
            Optional<Cliente> clienteOpt = clientes.stream()
                    .filter(c -> c.getDni().equals(dni))
                    .findFirst();
            if (clienteOpt.isPresent()) {
                Cliente cliente = clienteOpt.get();
                vista.setTextNombre(cliente.getNombre() + " " + cliente.getApellido());
            } else {
                vista.setTextNombre("Cliente no encontrado");
            }
        } catch (SQLException e) {
            vista.mostrarMensaje("Error al buscar cliente: " + e.getMessage());
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
            Venta venta = new Venta(
                    0,
                    new Timestamp(System.currentTimeMillis()),
                    1, //TODO: reemplazar por usuario actual
                    vista.getTipoDePago(),
                    new BigDecimal(vista.jTextFieldTotalDeVenta.getText())
            );
            ventaDAO.agregarVenta(venta);
            for (DetalleVenta detalle : listaPedidos) {
                detalle.setId_venta(venta.getId_venta());
                ventaDAO.agregarDetalleVenta(detalle);
            }
            listaPedidos.clear();
            actualizarTabla();
            actualizarTotales();
            vista.mostrarMensaje("Venta registrada exitosamente.");
        } catch (SQLException e) {
            vista.mostrarMensaje("Error al registrar la venta: " + e.getMessage());
        }
    }
}

