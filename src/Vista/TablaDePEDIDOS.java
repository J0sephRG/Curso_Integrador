package Vista;

import Controlador.ControladorPedidos;
import DAO.PRUEBADEPEDIDOS.PedidoLlevar;

import Interface.Pedido;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Timestamp;
/*import Controlador.ControladorPedidos;
import Interface.Pedido;
import Modelo.PedidoLlevar;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;*/

/** @author Miguel*/
public class TablaDePEDIDOS extends javax.swing.JFrame {
    private final ControladorPedidos controlador;
    private final DefaultTableModel modeloTabla;
    
    public TablaDePEDIDOS(ControladorPedidos controlador) {
        initComponents();
        this.controlador = controlador;
        this.modeloTabla = (DefaultTableModel) jTablePedidosLlevar.getModel();
        cargarPedidos();
    }
    private void cargarPedidos() {
        modeloTabla.setRowCount(0);
        controlador.listarPedidosPorTipo("PARA LLEVAR").forEach(pedido -> {
            modeloTabla.addRow(new Object[]{
                pedido.getId(),
                pedido.getIdCliente(),
                pedido.getEstado(),
                pedido.getFechaPedido()
            });
        });
    }
    
    //NO BORRAR ESTA ES LA CONEXION A LA BASE DE DATOS
    /*public TablaDePEDIDOS(Connection connection) {
        initComponents();
        controlador = new ControladorPedidos(connection);
        modeloTabla = (DefaultTableModel) jTablePedidosLlevar.getModel();
        cargarPedidos();
    }

    private void cargarPedidos() {
        try {
            List<Pedido> pedidos = controlador.listarPedidosPorTipo("PARA LLEVAR");
            modeloTabla.setRowCount(0); // Limpiar la tabla
            for (Pedido pedido : pedidos) {
                modeloTabla.addRow(new Object[]{
                    pedido.getId(),
                    pedido.getIdCliente(),
                    pedido.getEstado(),
                    pedido.getFechaPedido()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar pedidos: " + e.getMessage());
        }
    }*/
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
       
        PedidoLlevar nuevoPedido = new PedidoLlevar(
            0, 
            200 + (int)(Math.random() * 100), 
            "NUEVO",
            new Timestamp(System.currentTimeMillis())
        );
        
        controlador.agregarPedido(nuevoPedido);
        cargarPedidos();
        JOptionPane.showMessageDialog(this, "Pedido para llevar agregado: " + nuevoPedido.getId());
   
        
        /*// Aquí puedes abrir un formulario para agregar un nuevo pedido
        // Por simplicidad, se agrega un pedido de ejemplo
        PedidoLlevar nuevoPedido = new PedidoLlevar(0, 1, "PENDIENTE", new Timestamp(System.currentTimeMillis()));
        try {
            controlador.agregarPedido(nuevoPedido);
            cargarPedidos(); // Recargar la tabla
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar pedido: " + e.getMessage());
        } */
    }//GEN-LAST:event_jButtonAgregarPedidoLLEVARActionPerformed

    private void jButtonEliminarPedidoLLEVARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarPedidoLLEVARActionPerformed
    
        int filaSeleccionada = jTablePedidosLlevar.getSelectedRow();
        if (filaSeleccionada != -1) {
            int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            controlador.eliminarPedido(id, "PARA LLEVAR");
            cargarPedidos();
            JOptionPane.showMessageDialog(this, "Pedido eliminado");
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido para eliminar");
        }
        
        /* int filaSeleccionada = jTablePedidosLlevar.getSelectedRow();
        if (filaSeleccionada != -1) {
            int idPedido = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            try {
                controlador.eliminarPedido(idPedido, "PARA LLEVAR");
                cargarPedidos(); // Recargar la tabla
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar pedido: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido para eliminar.");
        }*/
    }//GEN-LAST:event_jButtonEliminarPedidoLLEVARActionPerformed

    private void jButtonMODIFICARPedidoLLEVARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMODIFICARPedidoLLEVARActionPerformed
        int filaSeleccionada = jTablePedidosLlevar.getSelectedRow();
        if (filaSeleccionada != -1) {
            int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            Pedido pedido = controlador.buscarPedidoPorId(id, "PARA LLEVAR");
            if (pedido != null) {
                pedido.setEstado("MODIFICADO");
                controlador.actualizarPedido(pedido);
                cargarPedidos();
                JOptionPane.showMessageDialog(this, "Pedido modificado");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido para modificar");
        }
    }//GEN-LAST:event_jButtonMODIFICARPedidoLLEVARActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAgregarPedidoLLEVAR;
    private javax.swing.JButton jButtonEliminarPedidoLLEVAR;
    private javax.swing.JButton jButtonMODIFICARPedidoLLEVAR;
    private javax.swing.JScrollPane jScrollPanePedidosLlevar;
    private javax.swing.JTable jTablePedidosLlevar;
    // End of variables declaration//GEN-END:variables
}
