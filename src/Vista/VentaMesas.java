package Vista;
import ConexionSQL.Conexion;
import DAO.MesaDAO;
import DAO.MesaPlatoDAO;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import Modelo.Usuario;
import Modelo.VENTAenMesa.Mesa;
import Seguridad.Sesion;
import java.util.List;
import javax.swing.SwingUtilities;
public class VentaMesas extends javax.swing.JPanel {
    private Connection connection; 

    public VentaMesas(Connection connection) throws SQLException {
        this.connection = connection;
        initComponents();
        aplicarEstiloModerno();
        actualizarEstadoMesas(); 
    }
    // Método para actualizar visualmente los estados de las mesas
    public void actualizarEstadoMesas() {
        try {
            List<Mesa> mesas = new MesaDAO(connection).listarMesas();
            for (Mesa mesa : mesas) {
                JButton botonMesa = obtenerBotonMesa(mesa.getNumero_mesa());
                if (botonMesa != null) {
                    if (mesa.getEstado().equalsIgnoreCase("ocupada")) {
                        botonMesa.setBackground(new Color(220, 53, 69)); // Rojo
                        botonMesa.setForeground(Color.WHITE);
                    } else {
                        botonMesa.setBackground(new Color(40, 167, 69)); // Verde
                        botonMesa.setForeground(Color.WHITE);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar estados de mesas: " + e.getMessage());
        }
    }

  // Método auxiliar para obtener el botón de mesa por número
    private JButton obtenerBotonMesa(int numeroMesa) {
        switch (numeroMesa) {
            case 1: return Mesa1Boton;
            case 2: return Mesa2Boton;
            case 3: return Mesa3Boton;
            case 4: return Mesa4Boton;
            case 5: return Mesa5Boton;
            case 6: return Mesa6Boton;
            case 7: return Mesa7Boton;
            case 8: return Mesa8Boton;
            case 9: return Mesa9Boton;
            case 10: return Mesa10Boton;
            default: return null;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpMesas = new javax.swing.JPanel();
        Mesa1Boton = new javax.swing.JButton();
        Mesa2Boton = new javax.swing.JButton();
        jButtonUnirMesas = new javax.swing.JButton();
        Mesa3Boton = new javax.swing.JButton();
        Mesa7Boton = new javax.swing.JButton();
        Mesa4Boton = new javax.swing.JButton();
        Mesa8Boton = new javax.swing.JButton();
        Mesa9Boton = new javax.swing.JButton();
        Mesa5Boton = new javax.swing.JButton();
        Mesa6Boton = new javax.swing.JButton();
        Mesa10Boton = new javax.swing.JButton();
        jButtonFinalizarTurno = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        jpMesas.setBackground(new java.awt.Color(204, 204, 204));

        Mesa1Boton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Mesa1Boton.setText("Mesa 1");
        Mesa1Boton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Mesa1BotonActionPerformed(evt);
            }
        });

        Mesa2Boton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Mesa2Boton.setText("Mesa 2");
        Mesa2Boton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Mesa2BotonActionPerformed(evt);
            }
        });

        jButtonUnirMesas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonUnirMesas.setText("Unir mesas");
        jButtonUnirMesas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUnirMesasActionPerformed(evt);
            }
        });

        Mesa3Boton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Mesa3Boton.setText("Mesa 3");
        Mesa3Boton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Mesa3BotonActionPerformed(evt);
            }
        });

        Mesa7Boton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Mesa7Boton.setText("Mesa 7");
        Mesa7Boton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Mesa7BotonActionPerformed(evt);
            }
        });

        Mesa4Boton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Mesa4Boton.setText("Mesa 4");
        Mesa4Boton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Mesa4BotonActionPerformed(evt);
            }
        });

        Mesa8Boton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Mesa8Boton.setText("Mesa 8");
        Mesa8Boton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Mesa8BotonActionPerformed(evt);
            }
        });

        Mesa9Boton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Mesa9Boton.setText("Mesa 9");
        Mesa9Boton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Mesa9BotonActionPerformed(evt);
            }
        });

        Mesa5Boton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Mesa5Boton.setText("Mesa 5");
        Mesa5Boton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Mesa5BotonActionPerformed(evt);
            }
        });

        Mesa6Boton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Mesa6Boton.setText("Mesa 6");
        Mesa6Boton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Mesa6BotonActionPerformed(evt);
            }
        });

        Mesa10Boton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Mesa10Boton.setText("Mesa 10");
        Mesa10Boton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Mesa10BotonActionPerformed(evt);
            }
        });

        jButtonFinalizarTurno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonFinalizarTurno.setText("Finalizar turno");
        jButtonFinalizarTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFinalizarTurnoActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        jLabel1.setText("MESAS");

        javax.swing.GroupLayout jpMesasLayout = new javax.swing.GroupLayout(jpMesas);
        jpMesas.setLayout(jpMesasLayout);
        jpMesasLayout.setHorizontalGroup(
            jpMesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMesasLayout.createSequentialGroup()
                .addGroup(jpMesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpMesasLayout.createSequentialGroup()
                        .addGroup(jpMesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpMesasLayout.createSequentialGroup()
                                .addGap(144, 144, 144)
                                .addComponent(Mesa7Boton)
                                .addGap(79, 79, 79)
                                .addComponent(Mesa8Boton))
                            .addGroup(jpMesasLayout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addComponent(Mesa1Boton)
                                .addGap(44, 44, 44)
                                .addComponent(Mesa2Boton)
                                .addGap(44, 44, 44)
                                .addComponent(Mesa3Boton)))
                        .addGap(44, 44, 44)
                        .addGroup(jpMesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpMesasLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(Mesa9Boton)
                                .addGap(57, 57, 57)
                                .addComponent(Mesa10Boton))
                            .addGroup(jpMesasLayout.createSequentialGroup()
                                .addComponent(Mesa4Boton)
                                .addGap(54, 54, 54)
                                .addGroup(jpMesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonFinalizarTurno)
                                    .addGroup(jpMesasLayout.createSequentialGroup()
                                        .addComponent(Mesa5Boton)
                                        .addGap(44, 44, 44)
                                        .addComponent(Mesa6Boton))))))
                    .addGroup(jpMesasLayout.createSequentialGroup()
                        .addGap(347, 347, 347)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpMesasLayout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jButtonUnirMesas)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jpMesasLayout.setVerticalGroup(
            jpMesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMesasLayout.createSequentialGroup()
                .addGroup(jpMesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpMesasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpMesasLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addGroup(jpMesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Mesa1Boton)
                            .addComponent(Mesa2Boton)
                            .addComponent(Mesa3Boton)
                            .addComponent(Mesa4Boton)
                            .addComponent(Mesa5Boton)
                            .addComponent(Mesa6Boton))
                        .addGap(43, 43, 43)
                        .addGroup(jpMesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Mesa8Boton)
                            .addComponent(Mesa7Boton)
                            .addComponent(Mesa9Boton)
                            .addComponent(Mesa10Boton))))
                .addGap(51, 51, 51)
                .addGroup(jpMesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonUnirMesas)
                    .addComponent(jButtonFinalizarTurno))
                .addContainerGap(91, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpMesas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpMesas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
 // Modificar el método crearBotonMesa para incluir estado inicial
    private void crearBotonMesa(JButton boton, String texto, int numeroMesa) {
        boton.setText(texto);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        boton.setBackground(new Color(40, 167, 69)); // Verde por defecto (libre)
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setOpaque(true);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(20, 40, 20, 40))
        );
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(boton.getBackground().darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (boton.getBackground().equals(new Color(220, 53, 69))) { // Rojo
                    boton.setBackground(new Color(220, 53, 69));
                } else {
                    boton.setBackground(new Color(40, 167, 69)); // Verde
                }
            }
        });
        boton.addActionListener(evt -> {
            jLabel1.setText("Mesa " + numeroMesa);
            abrirDescripcionDeLaMesa(numeroMesa);
        });
        jpMesas.add(boton);
    }
   
    private void abrirDescripcionDeLaMesa(int numeroMesa) {
    int usuarioActualId = Sesion.getUsuarioActual().getIdUsuario(); 
    DescripcionDeLaMesas panelDescripcion = new DescripcionDeLaMesas(connection, numeroMesa);
    JFrame frame = new JFrame("Descripción Mesa " + numeroMesa);
    frame.setContentPane(panelDescripcion);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.pack();
    frame.setLocationRelativeTo(this);
    frame.setVisible(true);
    }
     
   private void aplicarEstiloModerno() {
        // Estilo global para mejorar legibilidad y apariencia profesional
        setBackground(Color.WHITE); // Fondo blanco puro para el panel principal
        Font fuentePrincipal = new Font("Segoe UI", Font.PLAIN, 16);
        Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 48);
        // Aplicar fuente neutra y color gris suave a todos los componentes hijos
        for (Component comp : getComponents()) {
            comp.setFont(fuentePrincipal);
            comp.setForeground(new Color(107, 114, 128)); // gris neutral
            // Para los labels aplicar fuente título si es el label principal
            if (comp instanceof JLabel && ((JLabel) comp).getText() != null && ((JLabel) comp).getText().equals("MESAS")) {
                comp.setFont(fuenteTitulo);
                comp.setForeground(new Color(31, 41, 55)); // color más oscuro para títulos
            }
            if (comp instanceof JPanel) {
                // Recursivamente aplicar estilos a paneles hijos
                aplicarEstiloAPanel((JPanel) comp, fuentePrincipal);
            }
        }
         // Mejorar apariencia del panel de botones con bordes redondeados y sombra sutil
        jpMesas.setBackground(Color.WHITE);
        jpMesas.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 40, 20, 40),
                BorderFactory.createLineBorder(new Color(156, 163, 175))
        ));
    }

   private void aplicarEstiloAPanel(JPanel panel, Font fuente) {
        for (Component hijo : panel.getComponents()) {
            hijo.setFont(fuente);
            hijo.setForeground(new Color(107, 114, 128));
            if (hijo instanceof JPanel) {
                aplicarEstiloAPanel((JPanel) hijo, fuente);
            }
        }
    }

    private void Mesa1BotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Mesa1BotonActionPerformed
        abrirDescripcionDeLaMesa(1);
    }//GEN-LAST:event_Mesa1BotonActionPerformed

    private void Mesa2BotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Mesa2BotonActionPerformed
     abrirDescripcionDeLaMesa(2); 
    }//GEN-LAST:event_Mesa2BotonActionPerformed

    private void Mesa3BotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Mesa3BotonActionPerformed
        abrirDescripcionDeLaMesa(3);
    }//GEN-LAST:event_Mesa3BotonActionPerformed

    private void Mesa4BotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Mesa4BotonActionPerformed
         abrirDescripcionDeLaMesa(4);
    }//GEN-LAST:event_Mesa4BotonActionPerformed

    private void Mesa5BotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Mesa5BotonActionPerformed
         abrirDescripcionDeLaMesa(5);
    }//GEN-LAST:event_Mesa5BotonActionPerformed

    private void Mesa6BotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Mesa6BotonActionPerformed
         abrirDescripcionDeLaMesa(6);
    }//GEN-LAST:event_Mesa6BotonActionPerformed

    private void Mesa7BotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Mesa7BotonActionPerformed
         abrirDescripcionDeLaMesa(7);
    }//GEN-LAST:event_Mesa7BotonActionPerformed

    private void Mesa8BotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Mesa8BotonActionPerformed
         abrirDescripcionDeLaMesa(8);
    }//GEN-LAST:event_Mesa8BotonActionPerformed

    private void Mesa9BotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Mesa9BotonActionPerformed
         abrirDescripcionDeLaMesa(9);
    }//GEN-LAST:event_Mesa9BotonActionPerformed

    private void Mesa10BotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Mesa10BotonActionPerformed
         abrirDescripcionDeLaMesa(10);
    }//GEN-LAST:event_Mesa10BotonActionPerformed

    private void jButtonUnirMesasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUnirMesasActionPerformed
        // Crear y mostrar la ventana de unión de mesas
    UnionDeMesas unionDeMesas = new UnionDeMesas(connection, this);
    unionDeMesas.setVisible(true);
    }//GEN-LAST:event_jButtonUnirMesasActionPerformed

    private void jButtonFinalizarTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFinalizarTurnoActionPerformed
     String nombreUsuario = Sesion.getUsuarioActual().getNombre();
    
        // 1. Cerrar la sesión
        Sesion.cerrarSesion();

        // 2. Mostrar mensaje de despedida
        JOptionPane.showMessageDialog(
            this, 
            "¡Hasta pronto, " + nombreUsuario + "!", 
            "Sesión finalizada", 
            JOptionPane.INFORMATION_MESSAGE
        );

        // 3. Obtener el JFrame padre y cerrarlo
        javax.swing.JFrame parentFrame = (javax.swing.JFrame) SwingUtilities.getWindowAncestor(this);
        parentFrame.dispose();

        // 4. Abrir la ventana de login
        Login login = new Login();
        login.setVisible(true);
    }//GEN-LAST:event_jButtonFinalizarTurnoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Mesa10Boton;
    private javax.swing.JButton Mesa1Boton;
    private javax.swing.JButton Mesa2Boton;
    private javax.swing.JButton Mesa3Boton;
    private javax.swing.JButton Mesa4Boton;
    private javax.swing.JButton Mesa5Boton;
    private javax.swing.JButton Mesa6Boton;
    private javax.swing.JButton Mesa7Boton;
    private javax.swing.JButton Mesa8Boton;
    private javax.swing.JButton Mesa9Boton;
    public javax.swing.JButton jButtonFinalizarTurno;
    public javax.swing.JButton jButtonUnirMesas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jpMesas;
    // End of variables declaration//GEN-END:variables
}
