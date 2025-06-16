package VISTA;
import ConexionSQL.Conexion; // Asegúrate de que esta clase maneje la conexión a SQL Server
import Vista.DeliveryVts;
import Vista.VentaParallevar;
import java.awt.BorderLayout;
import java.sql.SQLException;
import javax.swing.JPanel;
import java.sql.Connection;

public class PanelCentralDeVentas extends javax.swing.JPanel {
    private Connection conn; // Conexión a la base de datos
    
    public PanelCentralDeVentas() throws SQLException {
        this.conn = Conexion.getConnection(); // Obtener la conexión de la base de datos
    initComponents();
    }
    
    private void ShowJpanel(JPanel p) {
        p.setSize(1280, 820);
        p.setLocation(0, 0);
        jpMes.removeAll();
        jpMes.add(p, BorderLayout.CENTER);
        jpMes.revalidate();
        jpMes.repaint();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpMes = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButtonMesas = new javax.swing.JButton();
        jButtonParaLlevar = new javax.swing.JButton();
        jButtonDelivery = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpMes.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jpMesLayout = new javax.swing.GroupLayout(jpMes);
        jpMes.setLayout(jpMesLayout);
        jpMesLayout.setHorizontalGroup(
            jpMesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 850, Short.MAX_VALUE)
        );
        jpMesLayout.setVerticalGroup(
            jpMesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 520, Short.MAX_VALUE)
        );

        add(jpMes, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 850, 520));

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        jButtonMesas.setBackground(new java.awt.Color(51, 51, 51));
        jButtonMesas.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jButtonMesas.setForeground(new java.awt.Color(255, 255, 255));
        jButtonMesas.setText("MESAS");
        jButtonMesas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMesasActionPerformed(evt);
            }
        });

        jButtonParaLlevar.setBackground(new java.awt.Color(51, 51, 51));
        jButtonParaLlevar.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jButtonParaLlevar.setForeground(new java.awt.Color(255, 255, 255));
        jButtonParaLlevar.setText("PARA LLEVAR");
        jButtonParaLlevar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonParaLlevarActionPerformed(evt);
            }
        });

        jButtonDelivery.setBackground(new java.awt.Color(51, 51, 51));
        jButtonDelivery.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jButtonDelivery.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDelivery.setText("DELIVERY");
        jButtonDelivery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeliveryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jButtonMesas, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(115, 115, 115)
                .addComponent(jButtonDelivery, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82)
                .addComponent(jButtonParaLlevar)
                .addContainerGap(216, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonMesas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonDelivery, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonParaLlevar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 850, 90));
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonMesasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMesasActionPerformed
    try {
            ShowJpanel(new PanelDeVentaMesas(conn)); // Pasar la conexión al constructor
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores
        }
    }//GEN-LAST:event_jButtonMesasActionPerformed

    private void jButtonParaLlevarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonParaLlevarActionPerformed
        ShowJpanel(new VentaParallevar());
    }//GEN-LAST:event_jButtonParaLlevarActionPerformed

    private void jButtonDeliveryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeliveryActionPerformed
        ShowJpanel(new DeliveryVts());
    }//GEN-LAST:event_jButtonDeliveryActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDelivery;
    private javax.swing.JButton jButtonMesas;
    private javax.swing.JButton jButtonParaLlevar;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jpMes;
    // End of variables declaration//GEN-END:variables

}
