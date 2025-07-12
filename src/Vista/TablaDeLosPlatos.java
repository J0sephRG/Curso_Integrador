package Vista;
import ConexionSQL.Conexion;
import Controlador.ControladorTablaDePlatos;
import Modelo.Plato;
import java.math.BigDecimal;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.sql.Connection;
import java.sql.SQLException;

public class TablaDeLosPlatos extends javax.swing.JPanel {
   
    private ControladorTablaDePlatos controlador;
    private Connection conexion;
    // Constantes de texto para evitar cadenas "hardcoded"
    private static final String ERROR_TITULO = "Error";
    private static final String ERROR_NOMBRE_DESCRIPCION = "Nombre y descripción no pueden estar vacíos.";
    private static final String ERROR_PRECIO_NUMERO = "El precio debe ser un número válido.";
    
    public TablaDeLosPlatos(Connection connection)throws SQLException {
    initComponents();
    this.conexion = connection; 
    this.controlador = new ControladorTablaDePlatos(this, connection);
    }
    
    public void setBotonesActivos(boolean activo) {
    jButtonGuardarPlatillo.setEnabled(activo);
    jButtonEliminarPlatillo.setEnabled(activo);
    jButtonBuscarPlatillo.setEnabled(activo);
    jButtonModificarPlatillo.setEnabled(activo);
    jButtonCancelar.setEnabled(activo);
    jButtonLimpiarFormulario.setEnabled(activo);
}

    public void setControlador(ControladorTablaDePlatos controlador) {
        this.controlador = controlador;
    }

    public JButton getjButtonModificarPlatillo() {
        return jButtonModificarPlatillo;
    }

    public JButton getjButtonEliminarPlatillo() {
        return jButtonEliminarPlatillo;
    }

    public JButton getjButtonBuscarPlatillo() {
        return jButtonBuscarPlatillo;
    }

    public JTable getjtablePlatosEnLaBaseDeDatos() {
        return jtablePlatosEnLaBaseDeDatos;
    }

    public JTextField getTextBuscarPlatillo() {
        return textBuscarPlatillo;
    }

    public void limpiarFormulario() {
    TextID.setText("");
    textNombrePlatillo.setText("");
    textPrecioPlatillo.setText("");
    textDescripcionPlatillo.setText("");
    }
    
    public JButton getjButtonGuardarPlatillo() {
        return jButtonGuardarPlatillo;
    }
    public JButton getjButtonLimpiarFormulario() {
        return jButtonLimpiarFormulario;
    }

    // Obtener objeto Plato desde el formulario
    public Plato getPlato() {
    try {
        int id = TextID.getText().isEmpty() ? 0 : Integer.parseInt(TextID.getText().trim());
        String nombre = textNombrePlatillo.getText().trim();
        String descripcion = textDescripcionPlatillo.getText().trim();
        String precioTexto = textPrecioPlatillo.getText().trim();

        if (nombre.isEmpty() || descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this, ERROR_NOMBRE_DESCRIPCION, ERROR_TITULO, JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (!nombre.matches("[\\p{L}0-9 .,'áéíóúÁÉÍÓÚñÑ\\-]{1,100}")) {
            JOptionPane.showMessageDialog(this, "Nombre inválido. Solo se permiten letras, números y algunos signos.", ERROR_TITULO, JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (descripcion.length() > 255) {
            JOptionPane.showMessageDialog(this, "La descripción es demasiado larga (máx. 255 caracteres)", ERROR_TITULO, JOptionPane.ERROR_MESSAGE);
            return null;
        }

        BigDecimal precio = new BigDecimal(precioTexto);
        if (precio.compareTo(BigDecimal.ZERO) <= 0 || precio.compareTo(new BigDecimal("100000")) > 0) {
            JOptionPane.showMessageDialog(this, "Precio inválido. Debe ser mayor que 0 y menor a 100,000.", ERROR_TITULO, JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return new Plato(id, nombre, precio, descripcion);

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, ERROR_PRECIO_NUMERO, ERROR_TITULO, JOptionPane.ERROR_MESSAGE);
    } catch (IllegalArgumentException e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), ERROR_TITULO, JOptionPane.ERROR_MESSAGE);
    }
    return null;
}


    // Establecer los datos del Plato en el formulario
    public void setPlato(Plato plato) {
        if (plato != null) {
            TextID.setText(String.valueOf(plato.getId_plato()));
            textNombrePlatillo.setText(plato.getNombre());
            textPrecioPlatillo.setText(plato.getPrecio().toPlainString());
            textDescripcionPlatillo.setText(plato.getDescripcion());
        }
    }


    public JButton getjButtonCancelar() {
    return jButtonCancelar;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtablePlatosEnLaBaseDeDatos = new javax.swing.JTable();
        jButtonModificarPlatillo = new javax.swing.JButton();
        jButtonEliminarPlatillo = new javax.swing.JButton();
        jButtonBuscarPlatillo = new javax.swing.JButton();
        textBuscarPlatillo = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButtonCancelar = new javax.swing.JButton();
        textNombrePlatillo = new javax.swing.JTextField();
        jButtonGuardarPlatillo = new javax.swing.JButton();
        textPrecioPlatillo = new javax.swing.JTextField();
        textDescripcionPlatillo = new javax.swing.JTextField();
        jButtonLimpiarFormulario = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabelNombre = new javax.swing.JLabel();
        jLabelID = new javax.swing.JLabel();
        TextID = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButtonActualizarTabla = new javax.swing.JButton();

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtablePlatosEnLaBaseDeDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID_Plato", "Nombre", "Precio", "Descripcion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtablePlatosEnLaBaseDeDatos);
        if (jtablePlatosEnLaBaseDeDatos.getColumnModel().getColumnCount() > 0) {
            jtablePlatosEnLaBaseDeDatos.getColumnModel().getColumn(0).setResizable(false);
            jtablePlatosEnLaBaseDeDatos.getColumnModel().getColumn(1).setResizable(false);
            jtablePlatosEnLaBaseDeDatos.getColumnModel().getColumn(2).setResizable(false);
            jtablePlatosEnLaBaseDeDatos.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 820, 320));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 830, 340));

        jButtonModificarPlatillo.setBackground(new java.awt.Color(204, 255, 204));
        jButtonModificarPlatillo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonModificarPlatillo.setText("Modificar");
        jButtonModificarPlatillo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarPlatilloActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonModificarPlatillo, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 100, -1, 30));

        jButtonEliminarPlatillo.setBackground(new java.awt.Color(255, 102, 102));
        jButtonEliminarPlatillo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonEliminarPlatillo.setText("Eliminar");
        jButtonEliminarPlatillo.setMaximumSize(new java.awt.Dimension(82, 23));
        jButtonEliminarPlatillo.setMinimumSize(new java.awt.Dimension(82, 23));
        jButtonEliminarPlatillo.setPreferredSize(new java.awt.Dimension(82, 23));
        jButtonEliminarPlatillo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarPlatilloActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonEliminarPlatillo, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 100, -1, 30));

        jButtonBuscarPlatillo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonBuscarPlatillo.setText("Buscar");
        jButtonBuscarPlatillo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarPlatilloActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonBuscarPlatillo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));
        jPanel2.add(textBuscarPlatillo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 150, -1));

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Registro de Platos");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1)
                .addContainerGap(658, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 860, 70));

        jButtonCancelar.setBackground(new java.awt.Color(51, 51, 51));
        jButtonCancelar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonCancelar.setForeground(new java.awt.Color(255, 255, 255));
        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 400, -1, -1));
        jPanel2.add(textNombrePlatillo, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 120, 186, -1));

        jButtonGuardarPlatillo.setBackground(new java.awt.Color(51, 51, 51));
        jButtonGuardarPlatillo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonGuardarPlatillo.setForeground(new java.awt.Color(255, 255, 255));
        jButtonGuardarPlatillo.setText("Guardar");
        jButtonGuardarPlatillo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarPlatilloActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonGuardarPlatillo, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 400, -1, -1));
        jPanel2.add(textPrecioPlatillo, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 160, 186, -1));
        jPanel2.add(textDescripcionPlatillo, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 230, 199, 54));

        jButtonLimpiarFormulario.setBackground(new java.awt.Color(51, 51, 51));
        jButtonLimpiarFormulario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonLimpiarFormulario.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLimpiarFormulario.setText("Limpiar");
        jButtonLimpiarFormulario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLimpiarFormularioActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonLimpiarFormulario, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 330, -1, -1));

        jPanel8.setBackground(new java.awt.Color(102, 102, 102));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Registrar Plato");
        jLabel8.setAlignmentX(140.0F);
        jLabel8.setAlignmentY(10.0F);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel8)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 0, 245, 70));

        jLabelNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelNombre.setText("Nombre:");
        jLabelNombre.setAlignmentX(50.0F);
        jLabelNombre.setAlignmentY(50.0F);
        jPanel2.add(jLabelNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 120, -1, -1));

        jLabelID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelID.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelID.setText("ID:");
        jLabelID.setAlignmentX(50.0F);
        jLabelID.setAlignmentY(50.0F);
        jPanel2.add(jLabelID, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 80, -1, -1));

        TextID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TextID.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        TextID.setAlignmentX(50.0F);
        TextID.setAlignmentY(50.0F);
        jPanel2.add(TextID, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 80, 55, 20));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Precio:");
        jLabel4.setAlignmentX(50.0F);
        jLabel4.setAlignmentY(50.0F);
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 160, -1, -1));

        jLabel7.setText("Descripcion:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 210, -1, -1));

        jButtonActualizarTabla.setBackground(new java.awt.Color(51, 51, 51));
        jButtonActualizarTabla.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonActualizarTabla.setForeground(new java.awt.Color(255, 255, 255));
        jButtonActualizarTabla.setText("ACTUALIZAR TABLA");
        jButtonActualizarTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActualizarTablaActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonActualizarTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 490, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonModificarPlatilloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarPlatilloActionPerformed
        controlador.cargarDatosSeleccionados();
    }//GEN-LAST:event_jButtonModificarPlatilloActionPerformed

    private void jButtonEliminarPlatilloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarPlatilloActionPerformed
        controlador.eliminarPlatoSeleccionado();
    }//GEN-LAST:event_jButtonEliminarPlatilloActionPerformed

    private void jButtonBuscarPlatilloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarPlatilloActionPerformed
        controlador.buscarPlatos();
    }//GEN-LAST:event_jButtonBuscarPlatilloActionPerformed

    private void jButtonLimpiarFormularioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLimpiarFormularioActionPerformed
        limpiarFormulario();
    }//GEN-LAST:event_jButtonLimpiarFormularioActionPerformed

    private void jButtonGuardarPlatilloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarPlatilloActionPerformed
        controlador.guardarPlato();
    }//GEN-LAST:event_jButtonGuardarPlatilloActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        controlador.cancelarEdicion();
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jButtonActualizarTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActualizarTablaActionPerformed
        controlador.cargarPlatosEnTabla();
    }//GEN-LAST:event_jButtonActualizarTablaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TextID;
    private javax.swing.JButton jButtonActualizarTabla;
    private javax.swing.JButton jButtonBuscarPlatillo;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonEliminarPlatillo;
    private javax.swing.JButton jButtonGuardarPlatillo;
    private javax.swing.JButton jButtonLimpiarFormulario;
    private javax.swing.JButton jButtonModificarPlatillo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelID;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtablePlatosEnLaBaseDeDatos;
    private javax.swing.JTextField textBuscarPlatillo;
    private javax.swing.JTextField textDescripcionPlatillo;
    private javax.swing.JTextField textNombrePlatillo;
    private javax.swing.JTextField textPrecioPlatillo;
    // End of variables declaration//GEN-END:variables
}