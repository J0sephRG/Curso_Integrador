package Controlador;

import VISTA.DescripcionDeLaMesas;
import DAO.ClienteDAO;
import DAO.MesaPlatoDAO;
import DAO.PlatoDAO;
import DAO.VentaDAO;
import Modelo.Cliente;
import Modelo.DetalleVenta;
import Modelo.Plato;
import Modelo.Venta;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DescripcionDeLaMesasController {
    private final Connection connection;
    private final int mesaNumber;
    private final DescripcionDeLaMesas vista;
    private final MesaPlatoDAO mesaPlatoDAO;
    private final PlatoDAO platoDAO;
    private final ClienteDAO clienteDAO;
    private final VentaDAO ventaDAO;

    public DescripcionDeLaMesasController(Connection connection, int mesaNumber, DescripcionDeLaMesas vista) {
        this.connection = connection;
        this.mesaNumber = mesaNumber;
        this.vista = vista;
        this.mesaPlatoDAO = new MesaPlatoDAO(connection);
        this.platoDAO = new PlatoDAO(connection);
        this.clienteDAO = new ClienteDAO(connection);
        this.ventaDAO = new VentaDAO(connection);
    }

    public void inicializar() {
        cargarComboPlatillos();
        cargarTipoPagoYComprobante();
        recargarPedidos();
    }

    private void recargarPedidos() {
        List<Plato> platos = mesaPlatoDAO.obtenerPlatosPorMesa(mesaNumber);
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Plato");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("Total");
        for (Plato plato : platos) {
            BigDecimal precio = plato.getPrecio();
            int cantidad = 1; // Suponiendo cantidad 1 por ahora, ajustar si es necesario
            BigDecimal total = precio.multiply(BigDecimal.valueOf(cantidad));
            Object[] row = {plato.getId_plato(), plato.getNombre(), cantidad, precio, total};
            modelo.addRow(row);
        }
        vista.actualizarTabla(modelo);
        calcularTotales(modelo);
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
            List<Plato> filtrados = platos.stream()
                    .filter(p -> p.getNombre().equals(platoNombre))
                    .collect(Collectors.toList());
            if (filtrados.isEmpty()) {
                vista.mostrarMensaje("Plato no encontrado.");
                return;
            }
            Plato plato = filtrados.get(0);
            boolean agregado = mesaPlatoDAO.agregarPlatoAMesa(mesaNumber, plato, cantidad);
            if (agregado) {
                recargarPedidos();
                vista.mostrarMensaje("Plato agregado a la mesa.");
            } else {
                vista.mostrarMensaje("Error al agregar el plato a la mesa.");
            }
        } catch (NumberFormatException ex) {
            vista.mostrarMensaje("Cantidad inválida.");
        } catch (SQLException ex) {
            vista.mostrarMensaje("Error al agregar pedido: " + ex.getMessage());
        }
    }

    public void eliminarPedido() {
        int filaSeleccionada = vista.jTablelListaDeLosPedidos.getSelectedRow();
        if (filaSeleccionada == -1) {
            vista.mostrarMensaje("Seleccione un plato para eliminar.");
            return;
        }
        int idPlato = (int) vista.jTablelListaDeLosPedidos.getValueAt(filaSeleccionada, 0);
        boolean eliminado = mesaPlatoDAO.eliminarPlatoDeMesa(mesaNumber, idPlato);
        if (eliminado) {
            recargarPedidos();
            vista.mostrarMensaje("Plato eliminado de la mesa.");
        } else {
            vista.mostrarMensaje("Error al eliminar el plato de la mesa.");
        }
    }

    private void calcularTotales(DefaultTableModel modelo) {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            BigDecimal totalPlato = (BigDecimal) modelo.getValueAt(i, 4);
            subtotal = subtotal.add(totalPlato);
        }

        BigDecimal igv = subtotal.multiply(BigDecimal.valueOf(0.05));
        vista.jTextFieldIGV.setText(igv.toString());

        BigDecimal descuentoPorcentaje = BigDecimal.ZERO;
        String textoDescuento = vista.jTextFieldDescuento.getText().trim();
        if (!textoDescuento.isEmpty()) {
            try {
                descuentoPorcentaje = new BigDecimal(textoDescuento);
            } catch (NumberFormatException ignored) {
            }
        }

        BigDecimal descuentoAplicado = subtotal.multiply(descuentoPorcentaje.divide(BigDecimal.valueOf(100)));
        vista.jTextFieldResultadoDescuento.setText(descuentoAplicado.toString());

        BigDecimal totalPagar = subtotal.subtract(descuentoAplicado);
        vista.jTextFieldTotalDeVenta.setText(totalPagar.toString());
        vista.jTextFieldMontoACobrar.setText(totalPagar.toString());
    }

    public void calcularVuelto() {
        try {
            BigDecimal montoPago = new BigDecimal(vista.jTextFieldMontoDePago.getText());
            BigDecimal montoCobrar = new BigDecimal(vista.jTextFieldMontoACobrar.getText());
            BigDecimal vuelto = montoPago.subtract(montoCobrar);
            vista.jTextFieldVuelto.setText(vuelto.toString());
        } catch (NumberFormatException e) {
            vista.jTextFieldVuelto.setText("0.00");
        }
    }

    public void actualizarTotales() {
        calcularTotales((DefaultTableModel) vista.jTablelListaDeLosPedidos.getModel());
        calcularVuelto();
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
            for (int i = 0; i < vista.jTablelListaDeLosPedidos.getRowCount(); i++) {
                int idProducto = (int) vista.jTablelListaDeLosPedidos.getValueAt(i, 0);
                int cantidad = (int) vista.jTablelListaDeLosPedidos.getValueAt(i, 2);
                BigDecimal precioUnitario = (BigDecimal) vista.jTablelListaDeLosPedidos.getValueAt(i, 3);
                DetalleVenta detalle = new DetalleVenta(
                        0,
                        venta.getId_venta(),
                        idProducto,
                        cantidad,
                        precioUnitario,
                        precioUnitario.multiply(BigDecimal.valueOf(cantidad))
                );
                ventaDAO.agregarDetalleVenta(detalle);
            }
            mesaPlatoDAO.limpiarMesa(mesaNumber);
            recargarPedidos();
            vista.mostrarMensaje("Venta registrada correctamente.");
        } catch (SQLException e) {
            vista.mostrarMensaje("Error al registrar la venta: " + e.getMessage());
        }
    }
}
