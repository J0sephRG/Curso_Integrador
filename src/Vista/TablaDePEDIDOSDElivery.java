package Vista;

import javax.swing.JPanel;
import javax.swing.*;
import Controlador.ControladorPedidosTablas;
import Modelo.Interface.Pedido;
import Modelo.PedidosDelivery.PedidoDelivery;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import Vista.DescripcionAgregacionDePedidoDelivery;
import java.awt.BorderLayout;
/**
 *
 * @author Miguel
 */
public class TablaDePEDIDOSDElivery extends javax.swing.JPanel {
private final ControladorPedidosTablas controlador;
    private final DefaultTableModel modeloTabla;

    public TablaDePEDIDOSDElivery(Connection connection) {
        initComponents();
        controlador = new ControladorPedidosTablas(connection);
        modeloTabla = (DefaultTableModel) jTablePedidosDelivery.getModel();
        cargarPedidos();
    }

    private void cargarPedidos() {
        try {
            List<Pedido> pedidos = controlador.listarPedidosPorTipo("DELIVERY");
            modeloTabla.setRowCount(0); // Limpiar tabla
            for (Pedido pedido : pedidos) {
                modeloTabla.addRow(new Object[]{
                        pedido.getId(),
                        pedido.getIdCliente(),
                        pedido.getDireccionEntrega(),
                        pedido.getEstado(),
                        pedido.getFechaPedido()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar pedidos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPanePedidosDelivery = new javax.swing.JScrollPane();
        jTablePedidosDelivery = new javax.swing.JTable();
        jButtonAgregarPedidoDelivery = new javax.swing.JButton();
        jButtonEliminarPedidoDelivery = new javax.swing.JButton();
        jButtonMODIFICARPedidoDelivery = new javax.swing.JButton();
        jButtonATRAS = new javax.swing.JButton();

        jScrollPanePedidosDelivery.setViewportView(jTablePedidosDelivery);

        jTablePedidosDelivery.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID", "ID Cliente", "Dirección de Entrega", "Estado", "Fecha de Pedido"
            }

        )
    );
    jTablePedidosDelivery.getTableHeader().setReorderingAllowed(false);
    jScrollPanePedidosDelivery.setViewportView(jTablePedidosDelivery);

    jButtonAgregarPedidoDelivery.setBackground(new java.awt.Color(51, 51, 51));
    jButtonAgregarPedidoDelivery.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
    jButtonAgregarPedidoDelivery.setForeground(new java.awt.Color(255, 255, 255));
    jButtonAgregarPedidoDelivery.setText("AGREGAR DELIVERY");
    jButtonAgregarPedidoDelivery.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonAgregarPedidoDeliveryActionPerformed(evt);
        }
    });

    jButtonEliminarPedidoDelivery.setBackground(new java.awt.Color(51, 51, 51));
    jButtonEliminarPedidoDelivery.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
    jButtonEliminarPedidoDelivery.setForeground(new java.awt.Color(255, 255, 255));
    jButtonEliminarPedidoDelivery.setText("ELIMINAR DELIVERY");
    jButtonEliminarPedidoDelivery.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonEliminarPedidoDeliveryActionPerformed(evt);
        }
    });

    jButtonMODIFICARPedidoDelivery.setBackground(new java.awt.Color(51, 51, 51));
    jButtonMODIFICARPedidoDelivery.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
    jButtonMODIFICARPedidoDelivery.setForeground(new java.awt.Color(255, 255, 255));
    jButtonMODIFICARPedidoDelivery.setText("MODIFICAR DELIVERY");
    jButtonMODIFICARPedidoDelivery.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonMODIFICARPedidoDeliveryActionPerformed(evt);
        }
    });

    jButtonATRAS.setBackground(new java.awt.Color(51, 51, 51));
    jButtonATRAS.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
    jButtonATRAS.setForeground(new java.awt.Color(255, 255, 255));
    jButtonATRAS.setText("ATRAS");
    jButtonATRAS.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonATRASActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPanePedidosDelivery, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(51, 51, 51)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jButtonATRAS, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButtonAgregarPedidoDelivery, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                        .addComponent(jButtonEliminarPedidoDelivery, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonMODIFICARPedidoDelivery, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE))
                    .addGap(30, 30, 30))))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(35, 35, 35)
                    .addComponent(jButtonAgregarPedidoDelivery, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(36, 36, 36)
                    .addComponent(jButtonEliminarPedidoDelivery, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(66, 66, 66)
                    .addComponent(jButtonMODIFICARPedidoDelivery, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(63, 63, 63)
                    .addComponent(jButtonATRAS, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPanePedidosDelivery, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(21, Short.MAX_VALUE))
    );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAgregarPedidoDeliveryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarPedidoDeliveryActionPerformed
        // Crear una nueva instancia de DescripcionAgregacionDePedidoDelivery
        DescripcionAgregacionDePedidoDelivery descripcionAgregacionDePedidoDelivery = new DescripcionAgregacionDePedidoDelivery(controlador.getConnection());
        // Mostrar la nueva ventana
        JFrame frame = new JFrame();
        frame.setContentPane(descripcionAgregacionDePedidoDelivery);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }//GEN-LAST:event_jButtonAgregarPedidoDeliveryActionPerformed

    private void jButtonEliminarPedidoDeliveryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarPedidoDeliveryActionPerformed
        int filaSeleccionada = jTablePedidosDelivery.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido para eliminar.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idPedido = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea eliminar el pedido seleccionado?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                controlador.eliminarPedido(idPedido, "DELIVERY");
                cargarPedidos();
                JOptionPane.showMessageDialog(this, "Pedido eliminado exitosamente.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar pedido: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonEliminarPedidoDeliveryActionPerformed

    private void jButtonMODIFICARPedidoDeliveryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMODIFICARPedidoDeliveryActionPerformed
        int filaSeleccionada = jTablePedidosDelivery.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido para modificar", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

        try {
            Pedido pedido = controlador.buscarPedidoPorId(id, "DELIVERY");
            if (pedido == null) {
                JOptionPane.showMessageDialog(this, "Pedido no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Mostrar diálogo para editar pedido existente
            PedidoDelivery pedidoEditable = (PedidoDelivery) pedido;
            PedidoDelivery pedidoModificado = mostrarDialogoPedido(pedidoEditable);

            if (pedidoModificado != null) {
                controlador.actualizarPedido(pedidoModificado);
                cargarPedidos();
                JOptionPane.showMessageDialog(this, "Pedido modificado exitosamente.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al modificar pedido: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonMODIFICARPedidoDeliveryActionPerformed

    private void jButtonATRASActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonATRASActionPerformed
        // Cerrar o esconder esta ventana o panel
        SwingUtilities.getWindowAncestor(this).dispose();
    }//GEN-LAST:event_jButtonATRASActionPerformed
/**
     * Muestra un diálogo para crear o modificar un PedidoDelivery.
     * Si `pedidoExistente` es null, es creación. Si no, edición.
     */
    private PedidoDelivery mostrarDialogoPedido(PedidoDelivery pedidoExistente) {
        JTextField txtIdCliente = new JTextField();
        JTextField txtDireccion = new JTextField();
        JTextField txtEstado = new JTextField();

        if (pedidoExistente != null) {
            txtIdCliente.setText(String.valueOf(pedidoExistente.getIdCliente()));
            txtDireccion.setText(pedidoExistente.getDireccionEntrega());
            txtEstado.setText(pedidoExistente.getEstado());
        }

        Object[] message = {
                "ID Cliente:", txtIdCliente,
                "Dirección de Entrega:", txtDireccion,
                "Estado:", txtEstado
        };

        int option = JOptionPane.showConfirmDialog(this, message,
                pedidoExistente == null ? "Agregar Pedido Delivery" : "Modificar Pedido Delivery",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                int idCliente = Integer.parseInt(txtIdCliente.getText().trim());
                String direccion = txtDireccion.getText().trim();
                String estado = txtEstado.getText().trim();
                Timestamp fechaPedido = pedidoExistente != null ? pedidoExistente.getFechaPedido() : new Timestamp(System.currentTimeMillis());

                if (direccion.isEmpty() || estado.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return null;
                }

                if (pedidoExistente == null) {
                    return new PedidoDelivery(0, idCliente, direccion, estado, fechaPedido);
                } else {
                    pedidoExistente.setIdCliente(idCliente);
                    pedidoExistente.setDireccionEntrega(direccion);
                    pedidoExistente.setEstado(estado);
                    pedidoExistente.setFechaPedido(fechaPedido);
                    return pedidoExistente;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID Cliente debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }

        return null; // Canceló o cerró el diálogo
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonATRAS;
    private javax.swing.JButton jButtonAgregarPedidoDelivery;
    private javax.swing.JButton jButtonEliminarPedidoDelivery;
    private javax.swing.JButton jButtonMODIFICARPedidoDelivery;
    private javax.swing.JScrollPane jScrollPanePedidosDelivery;
    private javax.swing.JTable jTablePedidosDelivery;
    // End of variables declaration//GEN-END:variables
}
