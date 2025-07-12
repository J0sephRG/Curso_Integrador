package Vista;
import DAO.MesaDAO;
import DAO.MesaUnidaDAO;
import Modelo.VENTAenMesa.Mesa;
import Modelo.VENTAenMesa.MesaUnida;
import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
public class UnionDeMesas extends javax.swing.JFrame {
    private Connection connection;
    private VentaMesas ventaMesas;
    
    public UnionDeMesas(Connection connection, VentaMesas ventaMesas) {
        this.connection = connection;
        this.ventaMesas = ventaMesas;
        initComponents();
        cargarMesas();
    }

    private void cargarMesas() {
        try {
            MesaDAO mesaDAO = new MesaDAO(connection);
            List<Mesa> mesas = mesaDAO.listarMesas();
            for (Mesa mesa : mesas) {
                jComboBoxDeMesasPrincipal.addItem("Mesa " + mesa.getNumero_mesa());
                jComboBoxDeMesasSecundarias.addItem("Mesa " + mesa.getNumero_mesa());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar mesas: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButtonConfirmarUnionDeMesas = new javax.swing.JButton();
        btnCancelarUnionDeMesas = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxDeMesasPrincipal = new javax.swing.JComboBox<>();
        jComboBoxDeMesasSecundarias = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("MESA PRINCIPAL");
        jLabel3.setAlignmentX(50.0F);
        jLabel3.setAlignmentY(50.0F);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("MESA SECUNDARIA");
        jLabel4.setAlignmentX(50.0F);
        jLabel4.setAlignmentY(50.0F);

        jButtonConfirmarUnionDeMesas.setBackground(new java.awt.Color(51, 51, 51));
        jButtonConfirmarUnionDeMesas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonConfirmarUnionDeMesas.setForeground(new java.awt.Color(255, 255, 255));
        jButtonConfirmarUnionDeMesas.setText("Confirmar");
        jButtonConfirmarUnionDeMesas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarUnionDeMesasActionPerformed(evt);
            }
        });

        btnCancelarUnionDeMesas.setBackground(new java.awt.Color(51, 51, 51));
        btnCancelarUnionDeMesas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCancelarUnionDeMesas.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelarUnionDeMesas.setText("Cancelar");
        btnCancelarUnionDeMesas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarUnionDeMesasActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setForeground(new java.awt.Color(153, 153, 153));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Union de Mesas");
        jLabel2.setAlignmentX(140.0F);
        jLabel2.setAlignmentY(10.0F);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel2)
                .addContainerGap(172, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel2)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jComboBoxDeMesasPrincipal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBoxDeMesasSecundarias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jButtonConfirmarUnionDeMesas)
                        .addGap(27, 27, 27)
                        .addComponent(btnCancelarUnionDeMesas))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxDeMesasPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jComboBoxDeMesasSecundarias, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBoxDeMesasPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBoxDeMesasSecundarias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarUnionDeMesas)
                    .addComponent(jButtonConfirmarUnionDeMesas))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarUnionDeMesasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarUnionDeMesasActionPerformed
        this.dispose(); // Cerrar la ventana de unión
    }//GEN-LAST:event_btnCancelarUnionDeMesasActionPerformed

    private void jButtonConfirmarUnionDeMesasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmarUnionDeMesasActionPerformed
        int mesaPrincipalIndex = jComboBoxDeMesasPrincipal.getSelectedIndex();
        int mesaSecundariaIndex = jComboBoxDeMesasSecundarias.getSelectedIndex();
        if (mesaPrincipalIndex == mesaSecundariaIndex) {
            JOptionPane.showMessageDialog(this, "No se puede unir la misma mesa.");
            return;
        }
        try {
            MesaDAO mesaDAO = new MesaDAO(connection);
            List<Mesa> mesas = mesaDAO.listarMesas();
            Mesa mesaPrincipal = mesas.get(mesaPrincipalIndex);
            Mesa mesaSecundaria = mesas.get(mesaSecundariaIndex);
            MesaUnida mesaUnida = new MesaUnida(mesaPrincipal.getId_mesa(), mesaSecundaria.getId_mesa());
            MesaUnidaDAO mesaUnidaDAO = new MesaUnidaDAO(connection);
            mesaUnidaDAO.agregarMesaUnida(mesaUnida);
             // Actualizar el estado de las mesas
            mesaPrincipal.setEstado("unida");
            mesaSecundaria.setEstado("unida");
            mesaDAO.actualizarMesa(mesaPrincipal);
            mesaDAO.actualizarMesa(mesaSecundaria);
            JOptionPane.showMessageDialog(this, "Mesas unidas exitosamente.");
            ventaMesas.actualizarEstadoMesas(); // Actualizar la UI
            this.dispose(); // Cerrar la ventana de unión
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al unir mesas: " + e.getMessage());
        }
    }//GEN-LAST:event_jButtonConfirmarUnionDeMesasActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelarUnionDeMesas;
    private javax.swing.JButton jButtonConfirmarUnionDeMesas;
    private javax.swing.JComboBox<String> jComboBoxDeMesasPrincipal;
    private javax.swing.JComboBox<String> jComboBoxDeMesasSecundarias;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
