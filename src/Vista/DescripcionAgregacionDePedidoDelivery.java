package Vista;

import ConexionSQL.Conexion; // Asegúrate de que esta clase maneje la conexión a SQL Server
import Controlador.DescripcionDeliveryController;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import Modelo.Usuario;
import Seguridad.Sesion;


public class DescripcionAgregacionDePedidoDelivery extends JPanel {
    private Connection connection;
    private DescripcionDeliveryController controller;
    private TablaDePEDIDOSDElivery vistaPrincipal;
    private int usuarioActualId;
    
    public DescripcionAgregacionDePedidoDelivery(Connection connection, TablaDePEDIDOSDElivery vistaPrincipal) {
        this.connection = connection;
        this.vistaPrincipal = vistaPrincipal; // ✅ Mover aquí primero

        // Obtener el usuario actual desde la sesión
        Usuario usuario = Seguridad.Sesion.getUsuarioActual();
        if (usuario != null) {
            this.usuarioActualId = usuario.getId_usuario();
        } else {
            this.usuarioActualId = -1;
            JOptionPane.showMessageDialog(this, "No hay un usuario autenticado en sesión.");
        }

        this.controller = new DescripcionDeliveryController(connection, this);
        initComponents();
        controller.inicializar();

        if (this.vistaPrincipal != null) {
            this.vistaPrincipal.actualizarTablaPedidos(); // ✅ Ya no lanza NullPointerException
        }
    }
       
    public void actualizarTabla(DefaultTableModel modelo) {
        jTablelListaDeLosPedidosDeliveryCLiente.setModel(modelo);
    }
    
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    
    public String getDni() {
        return textDni.getText();
    }
    
    public String getCantidad() {
        return textCantidad.getText();
    }
    
    public String getPlatilloSeleccionado() {
        return (String) jComboBoxDeBusquedaDePlatillos.getSelectedItem();
    }
    
    public String getTipoDePago() {
        return (String) jComboBoxTipoDePago.getSelectedItem();
    }
     
    public void setMontoACobrar(String monto) {
        jTextFieldMontoACobrar.setText(monto);
    }
    
    public void setVuelto(String vuelto) {
        jTextFieldVuelto.setText(vuelto);
    }
    
    public String getMontoDePago() {
        return jTextFieldMontoDePago.getText();
    }
    
    public DefaultTableModel getModeloTablaPedidos() {
        return (DefaultTableModel) jTablelListaDeLosPedidosDeliveryCLiente.getModel();
    }
    
    public void setModeloTablaPedidos(DefaultTableModel modelo) {
        jTablelListaDeLosPedidosDeliveryCLiente.setModel(modelo);
        jTablelListaDeLosPedidosDeliveryCLiente.repaint();
    }
    
    public String getTipoDeComprobante() {
        return (String) jComboBoxTipoDeComprobante.getSelectedItem();
    }
    
    public void setTextNombre(String nombre) {
        textNombre.setText(nombre);
    }
    
    public void setIGV(String igv) {
        jTextFieldIGV.setText(igv);
    }
    
    public void setResultadoDescuento(String descuento) {
        jTextFieldResultadoDescuento.setText(descuento);
    }
    
    public void setTotalDeVenta(String total) {
        jTextFieldTotalDeVenta.setText(total);
    }
    
    public JTable getTablaPedidos() {
    return jTablelListaDeLosPedidosDeliveryCLiente;
    }

    public String getTotalVenta() {
        return jTextFieldTotalDeVenta.getText();
    }

    public String getMetodoPago() {
        return (String) jComboBoxTipoDePago.getSelectedItem();
    }

    public String getDireccionEntrega() {
    return jTextFieldDireccionEntrega.getText().trim();
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton4 = new javax.swing.JButton();
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
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        textCantidad = new javax.swing.JTextField();
        jComboBoxDeBusquedaDePlatillos = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jButtonAgregarAListaDeLosPedido_Plato = new javax.swing.JButton();
        jButtonEliminarDeListaDePedido_Plato = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablelListaDeLosPedidosDeliveryCLiente = new javax.swing.JTable();
        jTextFieldDireccionEntrega = new javax.swing.JTextField();
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
        jButtoRegistrarPedido = new javax.swing.JButton();
        jLabelDELIVERY = new javax.swing.JLabel();
        jButtonDELIVERY = new javax.swing.JButton();

        jButton4.setText("jButton4");

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        jPanel2.add(jComboBoxTipoDePago, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 110, -1));

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

        jLabel19.setText("DIRECCION:");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, 70, -1));

        jButtonAgregarAListaDeLosPedido_Plato.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonAgregarAListaDeLosPedido_Plato.setText("Agregar");
        jButtonAgregarAListaDeLosPedido_Plato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarAListaDeLosPedido_PlatoActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonAgregarAListaDeLosPedido_Plato, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 20, -1, -1));

        jButtonEliminarDeListaDePedido_Plato.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonEliminarDeListaDePedido_Plato.setText("Eliminar");
        jButtonEliminarDeListaDePedido_Plato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarDeListaDePedido_PlatoActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonEliminarDeListaDePedido_Plato, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 20, -1, -1));

        jTablelListaDeLosPedidosDeliveryCLiente.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTablelListaDeLosPedidosDeliveryCLiente);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 580, 190));
        jPanel3.add(jTextFieldDireccionEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, 140, -1));

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
        jPanel1.add(jButtonAtras, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, 70, -1));

        jButtoRegistrarVenta.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtoRegistrarVenta.setText("Registrar Venta");
        jButtoRegistrarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtoRegistrarVentaActionPerformed(evt);
            }
        });
        jPanel1.add(jButtoRegistrarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, -1, -1));

        jLabel20.setText("Descuento Aplicado");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, -1, -1));
        jPanel1.add(jTextFieldResultadoDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 120, -1));

        jButtoRegistrarPedido.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtoRegistrarPedido.setText("Registrar Pedido");
        jButtoRegistrarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtoRegistrarPedidoActionPerformed(evt);
            }
        });
        jPanel1.add(jButtoRegistrarPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 480, -1, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 90, 230, 540));

        jLabelDELIVERY.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        jLabelDELIVERY.setText("DELIVERY");
        add(jLabelDELIVERY, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, -1, -1));

        jButtonDELIVERY.setBackground(new java.awt.Color(51, 51, 51));
        jButtonDELIVERY.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        jButtonDELIVERY.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDELIVERY.setText("DELIVERY");
        add(jButtonDELIVERY, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 20, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void jButtoRegistrarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtoRegistrarVentaActionPerformed
     controller.registrarVenta();
    }//GEN-LAST:event_jButtoRegistrarVentaActionPerformed

    private void jButtonAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtrasActionPerformed
       // Cerrar o esconder esta ventana o panel
        SwingUtilities.getWindowAncestor(this).dispose();
    }//GEN-LAST:event_jButtonAtrasActionPerformed

    private void jButtonBuscarCLIENTEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarCLIENTEActionPerformed
        controller.buscarClientePorDNI();
    }//GEN-LAST:event_jButtonBuscarCLIENTEActionPerformed

    private void jTextFieldVueltoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldVueltoKeyReleased
         controller.calcularVuelto();
    }//GEN-LAST:event_jTextFieldVueltoKeyReleased

    private void jTextFieldDescuentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldDescuentoKeyReleased
        controller.actualizarTotales();
    }//GEN-LAST:event_jTextFieldDescuentoKeyReleased

    private void jButtonAgregarAListaDeLosPedido_PlatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarAListaDeLosPedido_PlatoActionPerformed
        controller.agregarPedido();
    }//GEN-LAST:event_jButtonAgregarAListaDeLosPedido_PlatoActionPerformed

    private void jButtonEliminarDeListaDePedido_PlatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarDeListaDePedido_PlatoActionPerformed
       int filaSeleccionada = jTablelListaDeLosPedidosDeliveryCLiente.getSelectedRow();
        controller.eliminarPedido(filaSeleccionada);
    }//GEN-LAST:event_jButtonEliminarDeListaDePedido_PlatoActionPerformed

    private void jButtoRegistrarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtoRegistrarPedidoActionPerformed
        controller.registrarPedidoDelivery();
    }//GEN-LAST:event_jButtoRegistrarPedidoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButtoRegistrarPedido;
    public javax.swing.JButton jButtoRegistrarVenta;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButtonAgregarAListaDeLosPedido_Plato;
    public javax.swing.JButton jButtonAtras;
    public javax.swing.JButton jButtonBuscarCLIENTE;
    private javax.swing.JButton jButtonDELIVERY;
    private javax.swing.JButton jButtonEliminarDeListaDePedido_Plato;
    public javax.swing.JComboBox<String> jComboBoxDeBusquedaDePlatillos;
    public javax.swing.JComboBox<String> jComboBoxTipoDeComprobante;
    public javax.swing.JComboBox<String> jComboBoxTipoDePago;
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
    private javax.swing.JLabel jLabelDELIVERY;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTablelListaDeLosPedidosDeliveryCLiente;
    public javax.swing.JTextField jTextFieldDescuento;
    private javax.swing.JTextField jTextFieldDireccionEntrega;
    public javax.swing.JTextField jTextFieldIGV;
    public javax.swing.JTextField jTextFieldMontoACobrar;
    public javax.swing.JTextField jTextFieldMontoDePago;
    public javax.swing.JTextField jTextFieldResultadoDescuento;
    public javax.swing.JTextField jTextFieldTotalDeVenta;
    public javax.swing.JTextField jTextFieldVuelto;
    private javax.swing.JTextField textCantidad;
    public javax.swing.JTextField textDni;
    public javax.swing.JTextField textNombre;
    // End of variables declaration//GEN-END:variables

}