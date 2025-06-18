
package Vista;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.PedidoDelivery;

/**
 *
 * @author Usuario
 */
public class TablaDePEDIDOSDElivery extends javax.swing.JFrame {

    public TablaDePEDIDOSDElivery() {
        initComponents();
    }
// Método para llenar la tabla de Pedidos de Delivery
public void llenarTablaPedidosDelivery(List<PedidoDelivery> pedidos) {
    DefaultTableModel model = (DefaultTableModel) jTablePedidosDelivery.getModel();
    model.setRowCount(0); // Limpiar la tabla antes de llenarla
    for (PedidoDelivery pedido : pedidos) {
        model.addRow(new Object[]{
            pedido.getId(),
            pedido.getIdCliente(),
            pedido.getDireccionEntrega(),
            pedido.getEstado(),
            pedido.getFechaPedido()
        });
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPanePedidosDelivery = new javax.swing.JScrollPane();
        jTablePedidosDelivery = new javax.swing.JTable();
        jButtonAgregarPedidoDelivery = new javax.swing.JButton();
        jButtonMODIFICARPedidoDelivery = new javax.swing.JButton();
        jButtonEliminarPedidoDelivery = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

    jButtonMODIFICARPedidoDelivery.setBackground(new java.awt.Color(51, 51, 51));
    jButtonMODIFICARPedidoDelivery.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
    jButtonMODIFICARPedidoDelivery.setForeground(new java.awt.Color(255, 255, 255));
    jButtonMODIFICARPedidoDelivery.setText("MODIFICAR DELIVERY");
    jButtonMODIFICARPedidoDelivery.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonMODIFICARPedidoDeliveryActionPerformed(evt);
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

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPanePedidosDelivery, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jButtonAgregarPedidoDelivery, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                    .addContainerGap())
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addComponent(jButtonMODIFICARPedidoDelivery, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                    .addGap(15, 15, 15))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jButtonEliminarPedidoDelivery, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                    .addContainerGap())))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addComponent(jScrollPanePedidosDelivery, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
            .addContainerGap())
        .addGroup(layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButtonAgregarPedidoDelivery, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(59, 59, 59)
            .addComponent(jButtonEliminarPedidoDelivery, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(42, 42, 42)
            .addComponent(jButtonMODIFICARPedidoDelivery, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(115, 115, 115))
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAgregarPedidoDeliveryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarPedidoDeliveryActionPerformed
        // ShowJpanel(new VentaDelivery());
    }//GEN-LAST:event_jButtonAgregarPedidoDeliveryActionPerformed

    private void jButtonMODIFICARPedidoDeliveryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMODIFICARPedidoDeliveryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonMODIFICARPedidoDeliveryActionPerformed

    private void jButtonEliminarPedidoDeliveryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarPedidoDeliveryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonEliminarPedidoDeliveryActionPerformed

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
            java.util.logging.Logger.getLogger(TablaDePEDIDOSDElivery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TablaDePEDIDOSDElivery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TablaDePEDIDOSDElivery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TablaDePEDIDOSDElivery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TablaDePEDIDOSDElivery().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAgregarPedidoDelivery;
    private javax.swing.JButton jButtonEliminarPedidoDelivery;
    private javax.swing.JButton jButtonMODIFICARPedidoDelivery;
    private javax.swing.JScrollPane jScrollPanePedidosDelivery;
    private javax.swing.JTable jTablePedidosDelivery;
    // End of variables declaration//GEN-END:variables
}
