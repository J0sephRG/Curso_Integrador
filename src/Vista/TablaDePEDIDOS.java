
package Vista;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.PedidoLlevar;

/**
 *
 * @author Usuario
 */
public class TablaDePEDIDOS extends javax.swing.JFrame {

    public TablaDePEDIDOS() {
        initComponents();
    }
// Método para llenar la tabla de Pedidos Para Llevar
public void llenarTablaPedidosLlevar(List<PedidoLlevar> pedidos) {
    DefaultTableModel model = (DefaultTableModel) jTablePedidosLlevar.getModel();
    model.setRowCount(0); // Limpiar la tabla antes de llenarla
    for (PedidoLlevar pedido : pedidos) {
        model.addRow(new Object[]{
            pedido.getId(),
            pedido.getIdCliente(),
            pedido.getEstado(),
            pedido.getFechaPedido()
        });
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPanePedidosLlevar, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonEliminarPedidoLLEVAR, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                        .addGap(23, 23, 23))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonAgregarPedidoLLEVAR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonMODIFICARPedidoLLEVAR, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPanePedidosLlevar, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonAgregarPedidoLLEVAR, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(jButtonEliminarPedidoLLEVAR, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jButtonMODIFICARPedidoLLEVAR, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAgregarPedidoLLEVARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarPedidoLLEVARActionPerformed
        // ShowJpanel(new VentaDelivery());
    }//GEN-LAST:event_jButtonAgregarPedidoLLEVARActionPerformed

    private void jButtonEliminarPedidoLLEVARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarPedidoLLEVARActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonEliminarPedidoLLEVARActionPerformed

    private void jButtonMODIFICARPedidoLLEVARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMODIFICARPedidoLLEVARActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonMODIFICARPedidoLLEVARActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TablaDePEDIDOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TablaDePEDIDOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TablaDePEDIDOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TablaDePEDIDOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TablaDePEDIDOS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAgregarPedidoLLEVAR;
    private javax.swing.JButton jButtonEliminarPedidoLLEVAR;
    private javax.swing.JButton jButtonMODIFICARPedidoLLEVAR;
    private javax.swing.JScrollPane jScrollPanePedidosLlevar;
    private javax.swing.JTable jTablePedidosLlevar;
    // End of variables declaration//GEN-END:variables
}
