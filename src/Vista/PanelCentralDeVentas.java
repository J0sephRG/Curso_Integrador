package VISTA;
import ConexionSQL.Conexion; 
import Controlador.ControladorPedidosTablas;
import java.awt.BorderLayout;
import java.sql.SQLException;
import javax.swing.JPanel;
import java.sql.Connection;
import DAO.MesaDAO;
import javax.swing.JOptionPane;
import Vista.TablaDePEDIDOSDElivery;
import Vista.TablaDePEDIDOS;
import javax.swing.table.DefaultTableModel;
public class PanelCentralDeVentas extends javax.swing.JPanel {
 
    private MesaDAO mesadao;
    private Conexion conexion;
    public PanelCentralDeVentas() {
        initComponents();
        conexion = new Conexion();  
        mesadao = new MesaDAO(conexion.Conectar());  
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpMes = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnMesas = new javax.swing.JButton();
        jButtonParaLlevar = new javax.swing.JButton();
        jButtonDelivery = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpMes.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jpMesLayout = new javax.swing.GroupLayout(jpMes);
        jpMes.setLayout(jpMesLayout);
        jpMesLayout.setHorizontalGroup(
            jpMesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 960, Short.MAX_VALUE)
        );
        jpMesLayout.setVerticalGroup(
            jpMesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 520, Short.MAX_VALUE)
        );

        add(jpMes, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 960, 520));

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        btnMesas.setBackground(new java.awt.Color(51, 51, 51));
        btnMesas.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnMesas.setForeground(new java.awt.Color(255, 255, 255));
        btnMesas.setText("MESAS");
        btnMesas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMesasActionPerformed(evt);
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
                .addComponent(btnMesas, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111)
                .addComponent(jButtonDelivery, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86)
                .addComponent(jButtonParaLlevar)
                .addContainerGap(331, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMesas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonDelivery, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonParaLlevar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 960, 90));
    }// </editor-fold>//GEN-END:initComponents

    private void btnMesasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMesasActionPerformed
      try {
        VentaMesas panelVentaMesas = new VentaMesas(conexion.Conectar());
        ShowJpanel(panelVentaMesas);
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error al cargar las mesas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
    }//GEN-LAST:event_btnMesasActionPerformed

    private void jButtonParaLlevarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonParaLlevarActionPerformed
     ConexionSQL.Conexion conexionBD = new ConexionSQL.Conexion();
    Connection connection = conexionBD.Conectar();

    if (connection != null) {
        TablaDePEDIDOS panelPedidosLlevar = new TablaDePEDIDOS(connection);
        ShowJpanel(panelPedidosLlevar); // ← Aquí lo muestras dentro de jpMes
    } else {
        JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jButtonParaLlevarActionPerformed

    private void jButtonDeliveryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeliveryActionPerformed
     // Importar tu clase de conexión
    ConexionSQL.Conexion conexionBD = new ConexionSQL.Conexion();
    
    // Obtener la conexión
    Connection connection = conexionBD.Conectar();
    
    if (connection != null) {
        // Crear e instanciar la ventana TablaDePEDIDOSDElivery
        TablaDePEDIDOSDElivery ventanaDelivery = new TablaDePEDIDOSDElivery(connection);
        ShowJpanel(ventanaDelivery); // ← Aquí lo muestras dentro de jpMes
    } else {
        JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jButtonDeliveryActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMesas;
    private javax.swing.JButton jButtonDelivery;
    private javax.swing.JButton jButtonParaLlevar;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jpMes;
    // End of variables declaration//GEN-END:variables
    private void ShowJpanel(JPanel p) {
        p.setSize(1280, 820);
        p.setLocation(0, 0);
        jpMes.removeAll();
        jpMes.add(p, BorderLayout.CENTER);
        jpMes.revalidate();
        jpMes.repaint();
    }

}
