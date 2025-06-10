package VISTA;
import Conexion.DatabaseConnection;
import DAO.ClienteDAO;
import DAO.MesaPlatoDAO;
import DAO.PlatoDAO;
import DAO.VentaDAO;
import model.Cliente;
import model.DetalleVenta;
import model.Mesa;
import model.Plato;
import model.Venta;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DescripcionDeLaMesas extends javax.swing.JPanel {
  private Connection connection;
    private int mesaNumber;
    private DefaultTableModel modelo;
    private MesaPlatoDAO mesaPlatoDAO;
    private PlatoDAO platoDAO;
    private ClienteDAO clienteDAO;
    private VentaDAO ventaDAO;
    private Mesa mesa;

   public DescripcionDeLaMesas(Connection connection, int numeroMesa) throws SQLException {
    this.connection = connection;
        this.mesaNumber = numeroMesa;
        this.mesaPlatoDAO = new MesaPlatoDAO(connection);
        this.platoDAO = new PlatoDAO(connection);
        this.clienteDAO = new ClienteDAO(connection);
        this.ventaDAO = new VentaDAO(connection);
        initComponents();
        jLabel1.setText("Mesa " + numeroMesa);
        cargarComboPlatillos();
        cargarTipoPagoYComprobante();
        inicializarTabla();
        recargarPedidos();
        calcularTotales();
    }

   private void inicializarTabla() {
        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Plato");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("Total");
        jTablelListaDeLosPedidos.setModel(modelo);
    }
   private void recargarPedidos() {
       List<Plato> platos = mesaPlatoDAO.obtenerPlatosPorMesa(mesaNumber);
       modelo.setRowCount(0); // Limpiar la tabla
       for (Plato plato : platos) {
           BigDecimal precio = plato.getPrecio();
           int cantidad = 1; // Suponiendo cantidad 1 por ahora, ajustar si es necesario
           BigDecimal total = precio.multiply(new BigDecimal(cantidad));
           Object[] row = {plato.getId_plato(), plato.getNombre(), cantidad, precio, total};
           modelo.addRow(row);
       }
       calcularTotales();
    }

   private void cargarComboPlatillos() {
        try {
            PlatoDAO platoDAO = new PlatoDAO(connection);
            List<Plato> platos = platoDAO.listarPlatos();
            jComboBoxDeBusquedaDePlatillos.removeAllItems();
            for (Plato plato : platos) {
                jComboBoxDeBusquedaDePlatillos.addItem(plato.getNombre());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los platillos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarTipoPagoYComprobante() {
        jComboBoxTipoDePago.addItem("Efectivo");
        jComboBoxTipoDePago.addItem("Tarjeta");
        jComboBoxTipoDePago.addItem("Billetera Digital");
        jComboBoxTipoDeComprobante.addItem("Factura");
        jComboBoxTipoDeComprobante.addItem("Boleta por DNI");
        jComboBoxTipoDeComprobante.addItem("Boleta Simple");
    }
   
    private void buscarClientePorDNI() {
    String dni = textDni.getText();
    try {
        Cliente cliente = null;
        List<Cliente> clientes = clienteDAO.listarClientes();

        // Usar streams para buscar el cliente por DNI
        Optional<Cliente> optionalCliente = clientes.stream()
                .filter(c -> c.getDni().equals(dni))
                .findFirst();

        if (optionalCliente.isPresent()) {
            cliente = optionalCliente.get();
            textNombre.setText(cliente.getNombre() + " " + cliente.getApellido());
        } else {
            textNombre.setText("Cliente no encontrado");
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error al buscar cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    
     private void agregarPedido() {
        try {
            String platoNombre = (String) jComboBoxDeBusquedaDePlatillos.getSelectedItem();
            int cantidad = Integer.parseInt(textCantidad.getText());

            // Obtener el id_plato basado en el nombre seleccionado
            Plato plato = null;
            List<Plato> platos = platoDAO.listarPlatos();
            plato = platos.stream().filter(p -> p.getNombre().equals(platoNombre)).collect(Collectors.toList()).get(0);
            int idPlato = plato.getId_plato();

            // Insertar el pedido en la tabla Mesa_Plato
            boolean agregado = mesaPlatoDAO.agregarPlatoAMesa(mesaNumber, plato, cantidad);

            if (agregado) {
                recargarPedidos();
                calcularTotales();
                JOptionPane.showMessageDialog(this, "Plato agregado a la mesa.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar el plato a la mesa.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar pedido: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void eliminarPedido() {
        int selectedRow = jTablelListaDeLosPedidos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un plato para eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idPlato = (int) jTablelListaDeLosPedidos.getValueAt(selectedRow, 0);
        boolean eliminado = mesaPlatoDAO.eliminarPlatoDeMesa(mesaNumber, idPlato);
        if (eliminado) {
            recargarPedidos();
            calcularTotales();
            JOptionPane.showMessageDialog(this, "Plato eliminado de la mesa.");
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar el plato de la mesa.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void calcularTotales() {
    BigDecimal subtotal = BigDecimal.ZERO;

    // Sumar los valores de la columna 4 (Total por plato)
    for (int i = 0; i < jTablelListaDeLosPedidos.getRowCount(); i++) {
        BigDecimal totalPlato = (BigDecimal) jTablelListaDeLosPedidos.getValueAt(i, 4);
        subtotal = subtotal.add(totalPlato);
    }

    // Mostrar IGV (solo como información, no se suma al total)
    BigDecimal igv = subtotal.multiply(new BigDecimal("0.05"));
    jTextFieldIGV.setText(igv.toString());

    // Descuento (solo si se proporciona un número válido)
    BigDecimal descuentoPorcentaje = BigDecimal.ZERO;
    if (!jTextFieldDescuento.getText().trim().isEmpty()) {
        try {
            descuentoPorcentaje = new BigDecimal(jTextFieldDescuento.getText());
        } catch (NumberFormatException e) {
            // Si el número no es válido, asumimos 0%
            descuentoPorcentaje = BigDecimal.ZERO;
        }
    }

    BigDecimal descuentoAplicado = subtotal.multiply(descuentoPorcentaje.divide(new BigDecimal("100")));
    jTextFieldResultadoDescuento.setText(descuentoAplicado.toString());

    // Total a pagar sin IGV, solo con descuento si aplica
    BigDecimal totalPagar = subtotal.subtract(descuentoAplicado);
    jTextFieldTotalDeVenta.setText(totalPagar.toString());
    jTextFieldMontoACobrar.setText(totalPagar.toString());
}

    private void calcularVuelto() {
        try {
            BigDecimal montoPago = new BigDecimal(jTextFieldMontoDePago.getText());
            BigDecimal montoCobrar = new BigDecimal(jTextFieldMontoACobrar.getText());
            BigDecimal vuelto = montoPago.subtract(montoCobrar);
            jTextFieldVuelto.setText(vuelto.toString());
        } catch (NumberFormatException e) {
            jTextFieldVuelto.setText("0.00");
        }
    }
    
    private void actualizarTotales() {
        calcularTotales();
        calcularVuelto();
    }
    
    private void registrarVenta() {
        try {
            // 1. Crear la Venta
            Venta venta = new Venta(
                0, // El ID se genera automáticamente en la base de datos
                new Timestamp(System.currentTimeMillis()),
                1, //TODO Obtener el id del usuario actual
                (String) jComboBoxTipoDePago.getSelectedItem(),
                new BigDecimal(jTextFieldTotalDeVenta.getText())
            );
            // 2. Registrar la Venta en la base de datos y obtener el ID generado
            ventaDAO.agregarVenta(venta);
            // 3. Recorrer la tabla de pedidos y crear los DetallesVenta
            for (int i = 0; i < jTablelListaDeLosPedidos.getRowCount(); i++) {
                int idProducto = (int) jTablelListaDeLosPedidos.getValueAt(i, 0);
                int cantidad = (int) jTablelListaDeLosPedidos.getValueAt(i, 2);
                BigDecimal precioUnitario = (BigDecimal) jTablelListaDeLosPedidos.getValueAt(i, 3);
                DetalleVenta detalle = new DetalleVenta(
                    0, // El ID se genera automáticamente en la base de datos
                    venta.getId_venta(), // Usar el ID de la venta recién insertada
                    idProducto,
                    cantidad,
                    precioUnitario,
                    precioUnitario.multiply(new BigDecimal(cantidad))
                );
                ventaDAO.agregarDetalleVenta(detalle);
            }
            // 4. Limpiar la mesa (eliminar los platos de Mesa_Plato)
            mesaPlatoDAO.limpiarMesa(mesaNumber);
            // 5. Actualizar la interfaz
            recargarPedidos();
            calcularTotales();
            JOptionPane.showMessageDialog(this, "Venta registrada correctamente.");
        } catch (SQLException ex) {
         JOptionPane.showMessageDialog(this, "Error al registrar la venta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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
        jComboBoxTipoDePago = new javax.swing.JComboBox<>();
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

        jComboBoxTipoDePago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTipoDePagoActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBoxTipoDePago, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 110, -1));

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

    private void jComboBoxTipoDePagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTipoDePagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxTipoDePagoActionPerformed

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
    private javax.swing.JComboBox<String> jComboBoxTipoDePago;
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