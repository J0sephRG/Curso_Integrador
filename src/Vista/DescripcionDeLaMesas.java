package VISTA;
import ConexionSQL.Conexion;
import DAO.ClienteDAO;
import DAO.DetalleVentaDAO;
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
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class DescripcionDeLaMesas extends javax.swing.JPanel {
  private Connection connection; 
  private int mesaNumber;
  DefaultTableModel modelo = new DefaultTableModel();
  JTable jTableListaDeLosPedidos = new JTable(modelo);
    
   public DescripcionDeLaMesas(Connection connection, int numeroMesa) throws SQLException {
        this.connection = connection; 
        this.mesaNumber = numeroMesa; 
        initComponents();
        cargarPlatosEnComboBox(); 
        configurarComponentes(); 
        actualizarEtiquetaMesa(); 

    }
    
    private void configurarComponentes() {
        // Configurar solo lectura para ciertos campos
        textNombre.setEditable(false);
        jTextFieldVuelto.setEditable(false);
        jTextFieldMontoACobrar.setEditable(false);
        jTextFieldTotalDeVenta.setEditable(false);
        jTextFieldResultadoDescuento.setEditable(false);
        cboTipoDePago.setModel(new DefaultComboBoxModel<>(new String[]{"Efectivo", "Billetera Digital", "Tarjeta"}));
        jComboBoxTipoDeComprobante.setModel(new DefaultComboBoxModel<>(new String[]{"Factura", "Boleta por DNI", "Boleta simple"}));
    }
    
    private void cargarPlatosEnComboBox() {
        try {
            PlatoDAO platoDAO = new PlatoDAO(connection);
            List<Plato> platos = platoDAO.listarPlatos();
            for (Plato plato : platos) {
                jComboBoxDeBusquedaDePlatillos.addItem(plato.getNombre()); // Agregar nombres de platos al combo box
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando platos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    
    private void actualizarEtiquetaMesa() {
        jLabel1.setText("Mesa " + mesaNumber);
    }
    
    
    private void actualizarTotales() {
        BigDecimal total = BigDecimal.ZERO; // Asegúrate de inicializar total
        for (int i = 0; i < jTableListaDeLosPedidos.getRowCount(); i++) {
            // Asegúrate de que el valor de la fila no sea null
            BigDecimal filaTotal = (BigDecimal) jTableListaDeLosPedidos.getValueAt(i, 4);
            if (filaTotal != null) {
                total = total.add(filaTotal); // Solo sumar si filaTotal no es null
            }
        }
        jTextFieldTotalDeVenta.setText(total.toString());
        // Calcular descuento si existe
        String descuentoStr = jTextFieldDescuento.getText().trim();
        BigDecimal descuentoAplicado = BigDecimal.ZERO;
        if (!descuentoStr.isEmpty()) {
            try {
                BigDecimal porcentaje = new BigDecimal(descuentoStr);
                descuentoAplicado = total.multiply(porcentaje).divide(new BigDecimal("100"));
            } catch (NumberFormatException ex) {
                // No hacer nada o mostrar error con validación
            }
        }
        jTextFieldResultadoDescuento.setText(descuentoAplicado.toString());
        // Monto a cobrar = total - descuento
        BigDecimal montoACobrar = total.subtract(descuentoAplicado);
        if (montoACobrar.compareTo(BigDecimal.ZERO) < 0) montoACobrar = BigDecimal.ZERO;
        jTextFieldMontoACobrar.setText(montoACobrar.toString());
        calcularVuelto();
    }

        
    private void calcularVuelto() {
        try {
            BigDecimal montoPago = new BigDecimal(jTextFieldMontoDePago.getText().trim());
            BigDecimal montoACobrar = new BigDecimal(jTextFieldMontoACobrar.getText().trim());
            BigDecimal vuelto = montoPago.subtract(montoACobrar);
            jTextFieldVuelto.setText(vuelto.toString());
        } catch (NumberFormatException e) {
            // Campo con valor inválido: poner vuelto vacío.
            jTextFieldVuelto.setText("");
        }
    }
     
    private int obtenerIdPlatoSeleccionado() throws SQLException {
        String nombrePlato = (String) jComboBoxDeBusquedaDePlatillos.getSelectedItem();
        if (nombrePlato == null) return -1;
        PlatoDAO platoDAO = new PlatoDAO(connection);
        List<Plato> platos = platoDAO.listarPlatos();
        for (Plato p : platos) {
            if (p.getNombre().equals(nombrePlato)) return p.getId_plato();
        }
        return -1;
    }
     
    private void agregarPedido() {
        try {
            // Validación de entrada: cantidad
            String cantidadStr = textCantidad.getText().trim();
            int cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) {
                mostrarMensaje("Ingrese una cantidad válida mayor que 0", "Cantidad inválida", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Validación de plato seleccionado
            int idPlato = obtenerIdPlatoSeleccionado();
            if (idPlato == -1) {
                mostrarMensaje("Seleccione un plato válido", "Plato inválido", JOptionPane.WARNING_MESSAGE);
                return;
            }
// Obtener el plato y verificar su existencia
            PlatoDAO platoDAO = new PlatoDAO(connection);
            Plato plato = platoDAO.buscarPlatoPorId(idPlato);
            if (plato == null) {
                mostrarMensaje("Plato no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Cálculo del total y agregar a la tabla
            BigDecimal totalLinea = plato.getPrecio().multiply(new BigDecimal(cantidad));
            agregarFilaTabla(idPlato, plato.getNombre(), cantidad, plato.getPrecio(), totalLinea);
            // Guardar el pedido en la base de datos
            guardarPedidoEnBaseDeDatos(idPlato, cantidad, plato.getPrecio());
            // Actualizar totales y limpiar el campo de entrada
            actualizarTotales();
            textCantidad.setText("");
        } catch (NumberFormatException ex) {
            mostrarMensaje("Cantidad debe ser un número entero válido", "Error de entrada", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException sqle) {
            mostrarMensaje("Error obteniendo datos desde la base: " + sqle.getMessage(), "Error de BD", JOptionPane.WARNING_MESSAGE);
        }
    }

  private void guardarPedidoEnBaseDeDatos(int idPlato, int cantidad, BigDecimal precioUnitario) throws SQLException {
        int idVenta = obtenerIdVentaActual(); // Implementa este método según tu lógica
        if (idVenta == -1) {
            throw new SQLException("No hay una venta activa para guardar el pedido.");
        }
        // Crear un nuevo objeto DetalleVenta
        DetalleVenta detalleVenta = new DetalleVenta(0, idVenta, idPlato, cantidad, precioUnitario, precioUnitario.multiply(new BigDecimal(cantidad)));
        // Usar DetalleVentaDAO para agregar el detalle de la venta
        DetalleVentaDAO detalleVentaDAO = new DetalleVentaDAO(connection);
        detalleVentaDAO.agregarDetalleVenta(detalleVenta);
    }

    private int obtenerIdVentaActual() throws SQLException {
        String query = "SELECT id_venta FROM Venta WHERE numero_mesa = ? AND estado = 'activa'"; // Asegúrate de que el campo 'estado' exista
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, mesaNumber); // mesaNumber es el número de la mesa actual
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_venta");
            }
        }
        return -1; // Si no se encuentra una venta activa
    }

     
// Método auxiliar para mostrar mensajes
    private void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
    // Método auxiliar para agregar fila a la tabla
    private void agregarFilaTabla(int idPlato, String nombre, int cantidad, BigDecimal precioUnitario, BigDecimal totalLinea) {
        DefaultTableModel model = (DefaultTableModel) jTableListaDeLosPedidos.getModel();
        model.addRow(new Object[]{idPlato, nombre, cantidad, precioUnitario, totalLinea});
    }
     
private void eliminarPedido() {
        int filaSeleccionada = jTableListaDeLosPedidos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Obtener el idPlato de la fila seleccionada
        int idPlato = (int) jTableListaDeLosPedidos.getValueAt(filaSeleccionada, 0);
        
        // Eliminar de la base de datos
        try {
            DetalleVentaDAO detalleVentaDAO = new DetalleVentaDAO(connection);
            detalleVentaDAO.eliminarDetalleVenta(idPlato); // Método que debes implementar en DetalleVentaDAO
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error eliminando pedido: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        // Eliminar de la tabla
        DefaultTableModel model = (DefaultTableModel) jTableListaDeLosPedidos.getModel();
        model.removeRow(filaSeleccionada); // Use the model to remove the row
        actualizarTotales();
    }
    
private void buscarClientePorDNI() {
        String dniCliente = textDni.getText().trim();
        if (dniCliente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese DNI para buscar", "Campo vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            ClienteDAO clienteDAO = new ClienteDAO(connection);
            List<Cliente> clientes = clienteDAO.listarClientes();
            for (Cliente c : clientes) {
                // Aquí se asume búsqueda por nombre o apellido incluyendo DNI si estuviera
                if (c.getTelefono() != null && c.getTelefono().equals(dniCliente)) {
                    textNombre.setText(c.getNombre() + " " + c.getApellido());
                    return;
                }
            }
            textNombre.setText("");
            JOptionPane.showMessageDialog(this, "Cliente no encontrado con DNI proporcionado.", "No encontrado", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error buscando cliente: " + e.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }
        
 private void registrarVenta() {
        try {
            if (jTableListaDeLosPedidos.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No hay pedidos para registrar", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            BigDecimal montoTotal = new BigDecimal(jTextFieldMontoACobrar.getText().trim());
            if (montoTotal.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "El monto total debe ser mayor que cero", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
// Crear venta
            VentaDAO ventaDAO = new VentaDAO(connection);
            Venta venta = new Venta(0, new java.sql.Timestamp(System.currentTimeMillis()), null,
                    (String) cboTipoDePago.getSelectedItem(), montoTotal);
            ventaDAO.agregarVenta(venta);
            // Obtener ID de venta (debe implementarse método para recuperar último ID o usar retorno en agregarVenta)
            // Por simplicidad, se asume ID generado fuera de este ejemplo.
            // Registrar detalles de venta según tabla pedidos
            DetalleVentaDAO detalleVentaDAO = new DetalleVentaDAO(connection);
            for (int i = 0; i < jTableListaDeLosPedidos.getRowCount(); i++) {
                int idPlato = (int) jTableListaDeLosPedidos.getValueAt(i, 0);
                int cantidad = (int) jTableListaDeLosPedidos.getValueAt(i, 2);
                BigDecimal precioUnitario = (BigDecimal) jTableListaDeLosPedidos.getValueAt(i, 3);
                DetalleVenta detalle = new DetalleVenta(0, venta.getId_venta(), idPlato, cantidad, precioUnitario,
                        precioUnitario.multiply(new BigDecimal(cantidad)));
                detalleVentaDAO.agregarDetalleVenta(detalle);
            }
            JOptionPane.showMessageDialog(this, "Venta registrada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error registrando venta: " + e.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }
    
private void limpiarFormulario() {
        textDni.setText("");
        textNombre.setText("");
        jComboBoxDeBusquedaDePlatillos.setSelectedIndex(-1);
        textCantidad.setText("");
        jTextFieldTotalDeVenta.setText("");
        jTextFieldMontoACobrar.setText("");
        jTextFieldMontoDePago.setText("");
        jTextFieldVuelto.setText("");
        jTextFieldDescuento.setText("");
        jTextFieldResultadoDescuento.setText("");
        cboTipoDePago.setSelectedIndex(0);
        jComboBoxTipoDeComprobante.setSelectedIndex(0);
    }
     
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton4 = new javax.swing.JButton();
        jButtonMesas = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        textDni = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButtonBuscarCLIENTE = new javax.swing.JButton();
        cboTipoDePago = new javax.swing.JComboBox<>();
        jComboBoxTipoDeComprobante = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        textNombre = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablelListaDeLosPedidos = new javax.swing.JTable();
        jButtonAgregarAListaDeLosPedidos = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        textCantidad = new javax.swing.JTextField();
        jComboBoxDeBusquedaDePlatillos = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jButtonEliminarDeListaDePedidos = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldTotalDeVenta = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldIGV = new javax.swing.JTextField();
        jTextFieldMontoDePago = new javax.swing.JTextField();
        jTextFieldMontoACobrar = new javax.swing.JTextField();
        jTextFieldVuelto = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldDescuento = new javax.swing.JTextField();
        jButtonAtras = new javax.swing.JButton();
        jButtoRegistrarVenta = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jTextFieldResultadoDescuento = new javax.swing.JTextField();

        jButton4.setText("jButton4");

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonMesas.setBackground(new java.awt.Color(51, 51, 51));
        jButtonMesas.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        jButtonMesas.setForeground(new java.awt.Color(255, 255, 255));
        jButtonMesas.setText("Mesas");
        add(jButtonMesas, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 20, -1, -1));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        jLabel1.setText("MESAS");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(392, 45, -1, -1));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Tipo de documento");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 14, -1, -1));

        jLabel3.setText("DNI");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));
        jPanel2.add(textDni, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 110, -1));

        jLabel4.setText("Tipo de pago:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        jLabel5.setText("Tipo de comprobante:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, -1, -1));

        jButtonBuscarCLIENTE.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonBuscarCLIENTE.setText("Buscar");
        jButtonBuscarCLIENTE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarCLIENTEActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonBuscarCLIENTE, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 80, -1));

        cboTipoDePago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTipoDePagoActionPerformed(evt);
            }
        });
        jPanel2.add(cboTipoDePago, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 110, -1));

        jComboBoxTipoDeComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTipoDeComprobanteActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBoxTipoDeComprobante, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 130, 120, -1));

        jLabel14.setText("Nombre:");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        textNombre.setEditable(false);
        jPanel2.add(textNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 200, -1));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 600, 160));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setText("Lista de los pedidos");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        jTablelListaDeLosPedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "idPlato", "Plato", "Cantidad", "Precio", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTablelListaDeLosPedidos);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 580, 190));

        jButtonAgregarAListaDeLosPedidos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonAgregarAListaDeLosPedidos.setText("Agregar");
        jButtonAgregarAListaDeLosPedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarAListaDeLosPedidosActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonAgregarAListaDeLosPedidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 20, -1, -1));

        jLabel15.setText("Registro de venta");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel16.setText("Platillo:");
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        jLabel17.setText("Cantidad:");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 60, -1));
        jPanel3.add(textCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 140, -1));

        jComboBoxDeBusquedaDePlatillos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel3.add(jComboBoxDeBusquedaDePlatillos, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 140, -1));
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, -1, -1));

        jButtonEliminarDeListaDePedidos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonEliminarDeListaDePedidos.setText("Eliminar");
        jButtonEliminarDeListaDePedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarDeListaDePedidosActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonEliminarDeListaDePedidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 20, -1, -1));

        jLabel19.setText("Tipo de venta:");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 50, -1, -1));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel3.add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 80, -1, -1));

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 600, 370));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setText("Comprobante");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Total:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 50, -1));
        jPanel1.add(jTextFieldTotalDeVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 90, 40));

        jLabel9.setText("IGV:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 50, -1));

        jLabel10.setText("Monto de pago:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        jLabel11.setText("Monto a cobrar:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        jLabel12.setText("Vuelto:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, -1, -1));
        jPanel1.add(jTextFieldIGV, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 140, -1));
        jPanel1.add(jTextFieldMontoDePago, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, 110, -1));
        jPanel1.add(jTextFieldMontoACobrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 110, -1));

        jTextFieldVuelto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldVueltoKeyReleased(evt);
            }
        });
        jPanel1.add(jTextFieldVuelto, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, 140, -1));

        jLabel13.setText("Descuento:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, -1, -1));

        jTextFieldDescuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldDescuentoKeyReleased(evt);
            }
        });
        jPanel1.add(jTextFieldDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 280, 120, -1));

        jButtonAtras.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonAtras.setText("Atras");
        jButtonAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAtrasActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonAtras, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, 70, -1));

        jButtoRegistrarVenta.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtoRegistrarVenta.setText("Registrar");
        jButtoRegistrarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtoRegistrarVentaActionPerformed(evt);
            }
        });
        jPanel1.add(jButtoRegistrarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 490, -1, -1));

        jLabel20.setText("Descuento Aplicado");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, -1, -1));
        jPanel1.add(jTextFieldResultadoDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 120, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 90, 230, 540));
    }// </editor-fold>//GEN-END:initComponents

    private void jButtoRegistrarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtoRegistrarVentaActionPerformed
        registrarVenta();
    }//GEN-LAST:event_jButtoRegistrarVentaActionPerformed

    private void jButtonAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtrasActionPerformed
       // Cerrar o esconder esta ventana o panel
        SwingUtilities.getWindowAncestor(this).dispose();
    }//GEN-LAST:event_jButtonAtrasActionPerformed

    private void jButtonBuscarCLIENTEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarCLIENTEActionPerformed
        buscarClientePorDNI();
    }//GEN-LAST:event_jButtonBuscarCLIENTEActionPerformed

    private void jComboBoxTipoDeComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTipoDeComprobanteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxTipoDeComprobanteActionPerformed

    private void cboTipoDePagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTipoDePagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTipoDePagoActionPerformed

    private void jButtonAgregarAListaDeLosPedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarAListaDeLosPedidosActionPerformed
        agregarPedido();
    }//GEN-LAST:event_jButtonAgregarAListaDeLosPedidosActionPerformed

    private void jButtonEliminarDeListaDePedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarDeListaDePedidosActionPerformed
        eliminarPedido();
    }//GEN-LAST:event_jButtonEliminarDeListaDePedidosActionPerformed

    private void jTextFieldVueltoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldVueltoKeyReleased
         calcularVuelto();
    }//GEN-LAST:event_jTextFieldVueltoKeyReleased

    private void jTextFieldDescuentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldDescuentoKeyReleased
        actualizarTotales();
    }//GEN-LAST:event_jTextFieldDescuentoKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboTipoDePago;
    private javax.swing.JButton jButtoRegistrarVenta;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButtonAgregarAListaDeLosPedidos;
    private javax.swing.JButton jButtonAtras;
    private javax.swing.JButton jButtonBuscarCLIENTE;
    private javax.swing.JButton jButtonEliminarDeListaDePedidos;
    private javax.swing.JButton jButtonMesas;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBoxDeBusquedaDePlatillos;
    private javax.swing.JComboBox<String> jComboBoxTipoDeComprobante;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablelListaDeLosPedidos;
    private javax.swing.JTextField jTextFieldDescuento;
    private javax.swing.JTextField jTextFieldIGV;
    private javax.swing.JTextField jTextFieldMontoACobrar;
    private javax.swing.JTextField jTextFieldMontoDePago;
    private javax.swing.JTextField jTextFieldResultadoDescuento;
    private javax.swing.JTextField jTextFieldTotalDeVenta;
    private javax.swing.JTextField jTextFieldVuelto;
    private javax.swing.JTextField textCantidad;
    private javax.swing.JTextField textDni;
    private javax.swing.JTextField textNombre;
    // End of variables declaration//GEN-END:variables

}