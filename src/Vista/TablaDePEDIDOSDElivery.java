package Vista;

/*import Controlador.ControladorPedidos;
import Interface.Pedido;
import Modelo.PedidoDelivery;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;*/

import Controlador.ControladorPedidos;
import DAO.PRUEBADEPEDIDOS.PedidoDelivery;

import Interface.Pedido;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Timestamp;

/**
 *
 * @author Miguel
 */
public class TablaDePEDIDOSDElivery extends javax.swing.JFrame {
 
    private final ControladorPedidos controlador;
    private final DefaultTableModel modeloTabla;

    public TablaDePEDIDOSDElivery(ControladorPedidos controlador) {
        initComponents();
        this.controlador = controlador;
        this.modeloTabla = (DefaultTableModel) jTablePedidosDelivery.getModel();
        cargarPedidos();
    }
    
    private void cargarPedidos() {
        modeloTabla.setRowCount(0);
        controlador.listarPedidosPorTipo("DELIVERY").forEach(pedido -> {
            modeloTabla.addRow(new Object[]{
                pedido.getId(),
                pedido.getIdCliente(),
                pedido.getDireccionEntrega(),
                pedido.getEstado(),
                pedido.getFechaPedido()
            });
        });
    } 
    
    /*public TablaDePEDIDOSDElivery(Connection connection) {
        initComponents();
        controlador = new ControladorPedidos(connection);
        modeloTabla = (DefaultTableModel) jTablePedidosDelivery.getModel();
        cargarPedidos();
    }
   
    
     private void cargarPedidos() {
        try {
            List<Pedido> pedidos = controlador.listarPedidosPorTipo("DELIVERY");
            modeloTabla.setRowCount(0); // Limpiar la tabla
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
            JOptionPane.showMessageDialog(this, "Error al cargar pedidos: " + e.getMessage());
        }
    }
     */
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
    PedidoDelivery nuevoPedido = new PedidoDelivery(
            0, 
            100 + (int)(Math.random() * 100), 
            "Calle Prueba " + (int)(Math.random() * 100), 
            "NUEVO",
            new Timestamp(System.currentTimeMillis())
        );
        
        controlador.agregarPedido(nuevoPedido);
        cargarPedidos();
        JOptionPane.showMessageDialog(this, "Pedido de prueba agregado: " + nuevoPedido.getId());
   
        
        /*        // Aquí puedes abrir un formulario para agregar un nuevo pedido
        // Por simplicidad, se agrega un pedido de ejemplo
        PedidoDelivery nuevoPedido = new PedidoDelivery(0, 1, "Calle Principal 123", "PENDIENTE", new Timestamp(System.currentTimeMillis()));
        try {
            controlador.agregarPedido(nuevoPedido);
            cargarPedidos(); // Recargar la tabla
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar pedido: " + e.getMessage());
        }*/
    }//GEN-LAST:event_jButtonAgregarPedidoDeliveryActionPerformed

    private void jButtonMODIFICARPedidoDeliveryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMODIFICARPedidoDeliveryActionPerformed
        int filaSeleccionada = jTablePedidosDelivery.getSelectedRow();
        if (filaSeleccionada != -1) {
            int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            Pedido pedido = controlador.buscarPedidoPorId(id, "DELIVERY");
            if (pedido != null) {
                pedido.setEstado("MODIFICADO");
                controlador.actualizarPedido(pedido);
                cargarPedidos();
                JOptionPane.showMessageDialog(this, "Pedido modificado");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido para modificar");
        }
    }//GEN-LAST:event_jButtonMODIFICARPedidoDeliveryActionPerformed

    private void jButtonEliminarPedidoDeliveryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarPedidoDeliveryActionPerformed
      
                int filaSeleccionada = jTablePedidosDelivery.getSelectedRow();
        if (filaSeleccionada != -1) {
            int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            controlador.eliminarPedido(id, "DELIVERY");
            cargarPedidos();
            JOptionPane.showMessageDialog(this, "Pedido eliminado");
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido para eliminar");
        }

        
        /*int filaSeleccionada = jTablePedidosDelivery.getSelectedRow();
        if (filaSeleccionada != -1) {
            int idPedido = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            try {
                controlador.eliminarPedido(idPedido, "DELIVERY");
                cargarPedidos(); // Recargar la tabla
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar pedido: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido para eliminar.");
        }*/
    }//GEN-LAST:event_jButtonEliminarPedidoDeliveryActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAgregarPedidoDelivery;
    private javax.swing.JButton jButtonEliminarPedidoDelivery;
    private javax.swing.JButton jButtonMODIFICARPedidoDelivery;
    private javax.swing.JScrollPane jScrollPanePedidosDelivery;
    private javax.swing.JTable jTablePedidosDelivery;
    // End of variables declaration//GEN-END:variables
}
