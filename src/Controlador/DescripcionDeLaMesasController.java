package Controlador;
import Vista.DescripcionDeLaMesas;
import DAO.ClienteDAO;
import DAO.MesaPlatoDAO;
import DAO.PlatoDAO;
import DAO.VentaDAO;
import Modelo.Cliente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Modelo.DetalleVenta;
import Modelo.Plato;
import Modelo.Venta;
import Seguridad.Sesion;  
import Vista.VentaMesas;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import apiclientes.ApiClienteService;
import apiclientes.ApiClienteServiceImpl;
import javax.swing.JOptionPane;

public class DescripcionDeLaMesasController {
    private final Connection connection;
    private final int mesaNumber;
    private final DescripcionDeLaMesas vista;
    private final MesaPlatoDAO mesaPlatoDAO;
    private final PlatoDAO platoDAO;
    private final ClienteDAO clienteDAO;
    private final VentaDAO ventaDAO;
    private ApiClienteService apiService;
    // Constructor actualizado sin el parámetro usuarioActualId
    public DescripcionDeLaMesasController(Connection connection, int mesaNumber, DescripcionDeLaMesas vista) {
        this.connection = connection;
        this.mesaNumber = mesaNumber;
        this.vista = vista;
        this.mesaPlatoDAO = new MesaPlatoDAO(connection);
        this.platoDAO = new PlatoDAO(connection);
        this.clienteDAO = new ClienteDAO(connection);
        this.ventaDAO = new VentaDAO(connection);
        apiService = new ApiClienteServiceImpl();
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
            // Actualizar el estado de la mesa a "ocupada"
            mesaPlatoDAO.actualizarEstadoMesa(mesaNumber, "ocupada");
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
            // Obtener el ID del usuario desde la sesión
            int usuarioActualId = Sesion.getUsuarioActual().getId_usuario();

            // Crear venta sin ID (lo genera la BD)
            Venta venta = new Venta(
                    new Timestamp(System.currentTimeMillis()),
                    usuarioActualId,
                    vista.getTipoDePago(),
                    new BigDecimal(vista.jTextFieldTotalDeVenta.getText())
            );
            // Insertar venta y obtener ID generado
            int idVentaGenerado = ventaDAO.agregarVenta(venta);
            if (idVentaGenerado <= 0) {
                vista.mostrarMensaje("No se pudo registrar la venta.");
                return;
            }
            venta.setId_venta(idVentaGenerado);
            // Registrar detalles de la venta
            for (int i = 0; i < vista.jTablelListaDeLosPedidos.getRowCount(); i++) {
                int idProducto = (int) vista.jTablelListaDeLosPedidos.getValueAt(i, 0);
                int cantidad = (int) vista.jTablelListaDeLosPedidos.getValueAt(i, 2);
                BigDecimal precioUnitario = (BigDecimal) vista.jTablelListaDeLosPedidos.getValueAt(i, 3);

                // Verificar si el producto existe antes de agregar el detalle
            if (!productoExiste(idProducto)) {
                vista.mostrarMensaje("El producto con ID " + idProducto + " no existe.");
                return;
            }
                
                DetalleVenta detalle = new DetalleVenta(
                        0,
                        idVentaGenerado,
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
    
    // Método para verificar si el producto existe
    private boolean productoExiste(int idProducto) {
        String sql = "SELECT COUNT(*) FROM Producto WHERE id_producto = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true si existe al menos un registro
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false si ocurre un error o no existe
    }
    
        // Método para transferir platos a otra mesa
        public void transferirPlatos() {
            
            String input = JOptionPane.showInputDialog(vista, "Ingrese el número de la mesa a la que desea trasladar los platos:");

            if (input == null || input.trim().isEmpty()) {
                vista.mostrarMensaje("Operación cancelada.");
                return;
            }

            int mesaDestino;
            try {
                mesaDestino = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                vista.mostrarMensaje("Número de mesa inválido.");
                return;
            }

            // Obtener los platos de la mesa actual
            List<Plato> platos = mesaPlatoDAO.obtenerPlatosPorMesa(mesaNumber);
            if (platos.isEmpty()) {
                vista.mostrarMensaje("No hay platos en la mesa actual para transferir.");
                return;
            }

            // Transferir cada plato a la mesa de destino
            for (Plato plato : platos) {
                boolean transferido = mesaPlatoDAO.transferirPlato(mesaNumber, mesaDestino, plato);
                if (!transferido) {
                    vista.mostrarMensaje("Error al transferir el plato: " + plato.getNombre());
                    return;
                }
            }

            // Limpiar la mesa actual después de la transferencia
            mesaPlatoDAO.limpiarMesa(mesaNumber);
            vista.mostrarMensaje("Platos transferidos a la mesa " + mesaDestino + " correctamente.");
            recargarPedidos(); // Actualizar la vista
        }

}
