package Vista;

import Controlador.ControladorPedidosTablas;
import Modelo.Interface.Pedido;
import Modelo.PedidosLlevar.PedidoLlevar;
import Seguridad.Sesion;
import Modelo.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class TablaDePEDIDOS extends javax.swing.JPanel {

    private static final String TIPO_LLEVAR = "llevar";
    private int usuarioActualId;
    private final ControladorPedidosTablas controlador;
    private final DefaultTableModel modeloTabla;

    public TablaDePEDIDOS(Connection connection) {
    initComponents();
    Usuario usuario = Sesion.getUsuarioActual();
    this.usuarioActualId = (usuario != null) ? usuario.getId_usuario() : -1; // -1 como valor por defecto si no hay usuario
    this.controlador = new ControladorPedidosTablas(connection);
    this.modeloTabla = (DefaultTableModel) jTablePedidosLlevar.getModel();
    cargarPedidos();
    }

    private void cargarPedidos() {
        try {
            List<Pedido> pedidos = controlador.listarPedidosPorTipo(TIPO_LLEVAR);
            modeloTabla.setRowCount(0);
            for (Pedido pedido : pedidos) {
                modeloTabla.addRow(new Object[]{
                        pedido.getId(),
                        pedido.getIdCliente(),
                        pedido.getEstado(),
                        pedido.getFechaPedido()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar pedidos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPanePedidosLlevar = new javax.swing.JScrollPane();
        jTablePedidosLlevar = new javax.swing.JTable();
        jButtonAgregarPedidoLLEVAR = new javax.swing.JButton();
        jButtonEliminarPedidoLLEVAR = new javax.swing.JButton();
        jButtonMODIFICARPedidoLLEVAR = new javax.swing.JButton();
        jButtonATRAS = new javax.swing.JButton();

        jScrollPanePedidosLlevar.setViewportView(jTablePedidosLlevar);

        jTablePedidosLlevar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID", "ID Cliente", "Estado", "Fecha de Pedido"
            }
        ));
        jTablePedidosLlevar.getTableHeader().setReorderingAllowed(false);
        jScrollPanePedidosLlevar.setViewportView(jTablePedidosLlevar);

        jButtonAgregarPedidoLLEVAR.setBackground(new java.awt.Color(51, 51, 51));
        jButtonAgregarPedidoLLEVAR.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jButtonAgregarPedidoLLEVAR.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAgregarPedidoLLEVAR.setText("Agregar Para Llevar");
        jButtonAgregarPedidoLLEVAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarPedidoLLEVARActionPerformed(evt);
            }
        });

        jButtonEliminarPedidoLLEVAR.setBackground(new java.awt.Color(51, 51, 51));
        jButtonEliminarPedidoLLEVAR.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jButtonEliminarPedidoLLEVAR.setForeground(new java.awt.Color(255, 255, 255));
        jButtonEliminarPedidoLLEVAR.setText("Eliminar Para Llevar");
        jButtonEliminarPedidoLLEVAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarPedidoLLEVARActionPerformed(evt);
            }
        });

        jButtonMODIFICARPedidoLLEVAR.setBackground(new java.awt.Color(51, 51, 51));
        jButtonMODIFICARPedidoLLEVAR.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jButtonMODIFICARPedidoLLEVAR.setForeground(new java.awt.Color(255, 255, 255));
        jButtonMODIFICARPedidoLLEVAR.setText("Modificar Para Llevar");
        jButtonMODIFICARPedidoLLEVAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMODIFICARPedidoLLEVARActionPerformed(evt);
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
                .addGap(571, 571, 571)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonATRAS, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButtonEliminarPedidoLLEVAR, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonAgregarPedidoLLEVAR, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonMODIFICARPedidoLLEVAR, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)))
                .addContainerGap(133, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPanePedidosLlevar, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(372, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(jButtonAgregarPedidoLLEVAR, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(jButtonEliminarPedidoLLEVAR, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jButtonMODIFICARPedidoLLEVAR, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jButtonATRAS, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPanePedidosLlevar, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                    .addContainerGap()))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAgregarPedidoLLEVARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarPedidoLLEVARActionPerformed
        // Crear una nueva instancia de DescripcionAgregacionDePedidoLLevar
        DescripcionAgregacionDePedioLLevar descripcionAgregacion =
    new DescripcionAgregacionDePedioLLevar(controlador.getConnection());

        JFrame frame = new JFrame("Nuevo Pedido Para Llevar");
        frame.setContentPane(descripcionAgregacion);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }//GEN-LAST:event_jButtonAgregarPedidoLLEVARActionPerformed

    private void jButtonEliminarPedidoLLEVARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarPedidoLLEVARActionPerformed

        int fila = jTablePedidosLlevar.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido para eliminar.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idPedido = (int) modeloTabla.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar el pedido seleccionado?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                controlador.eliminarPedido(idPedido, TIPO_LLEVAR);
                cargarPedidos();
                JOptionPane.showMessageDialog(this, "Pedido eliminado exitosamente.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar pedido: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonEliminarPedidoLLEVARActionPerformed

    private void jButtonMODIFICARPedidoLLEVARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMODIFICARPedidoLLEVARActionPerformed
        int fila = jTablePedidosLlevar.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido para modificar", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        try {
            Pedido pedido = controlador.buscarPedidoPorId(id, TIPO_LLEVAR);
            if (pedido == null) {
                JOptionPane.showMessageDialog(this, "Pedido no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            PedidoLlevar editable = (PedidoLlevar) pedido;
            PedidoLlevar modificado = mostrarDialogoPedido(editable);
            if (modificado != null) {
                controlador.actualizarPedido(modificado);
                cargarPedidos();
                JOptionPane.showMessageDialog(this, "Pedido modificado exitosamente.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al modificar pedido: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonMODIFICARPedidoLLEVARActionPerformed

    private void jButtonATRASActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonATRASActionPerformed
       // Cerrar o esconder esta ventana o panel
        SwingUtilities.getWindowAncestor(this).dispose();
    }//GEN-LAST:event_jButtonATRASActionPerformed

    private PedidoLlevar mostrarDialogoPedido(PedidoLlevar pedidoExistente) {
        JTextField txtIdCliente = new JTextField();
        JTextField txtEstado = new JTextField();

        if (pedidoExistente != null) {
            txtIdCliente.setText(String.valueOf(pedidoExistente.getIdCliente()));
            txtEstado.setText(pedidoExistente.getEstado());
        }

        Object[] message = {
                "ID Cliente:", txtIdCliente,
                "Estado:", txtEstado
        };

        int option = JOptionPane.showConfirmDialog(this, message,
                pedidoExistente == null ? "Agregar Pedido Para Llevar" : "Modificar Pedido Para Llevar",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                int idCliente = Integer.parseInt(txtIdCliente.getText().trim());
                String estado = txtEstado.getText().trim();
                Timestamp fechaPedido = (pedidoExistente != null)
                        ? pedidoExistente.getFechaPedido()
                        : new Timestamp(System.currentTimeMillis());

                if (estado.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return null;
                }

                if (pedidoExistente == null) {
                    return new PedidoLlevar(0, idCliente, estado, fechaPedido);
                } else {
                    pedidoExistente.setIdCliente(idCliente);
                    pedidoExistente.setEstado(estado);
                    pedidoExistente.setFechaPedido(fechaPedido);
                    return pedidoExistente;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID Cliente debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }

        return null;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonATRAS;
    private javax.swing.JButton jButtonAgregarPedidoLLEVAR;
    private javax.swing.JButton jButtonEliminarPedidoLLEVAR;
    private javax.swing.JButton jButtonMODIFICARPedidoLLEVAR;
    private javax.swing.JScrollPane jScrollPanePedidosLlevar;
    private javax.swing.JTable jTablePedidosLlevar;
    // End of variables declaration//GEN-END:variables
}
