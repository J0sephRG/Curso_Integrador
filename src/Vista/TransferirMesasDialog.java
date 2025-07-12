package Vista;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.List;
import DAO.MesaDAO;
import Modelo.VENTAenMesa.Mesa;
import java.sql.SQLException;

public class TransferirMesasDialog extends javax.swing.JDialog {
    private JComboBox<String> comboMesas;
    private JButton btnConfirmar;
    private JButton btnCancelar;
    private boolean confirmado = false;
    private int mesaSeleccionada;

    public TransferirMesasDialog(JFrame parent, Connection connection, int mesaOrigen) {
        super(parent, "Transferir Mesas", true);
        setSize(300, 200);
        setLocationRelativeTo(parent);
        
        inicializarComponentes(connection, mesaOrigen);
    }

    private void inicializarComponentes(Connection connection, int mesaOrigen) {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Combo box con las mesas disponibles
        JLabel lblInstruccion = new JLabel("Seleccione mesa destino:");
        panel.add(lblInstruccion);

        comboMesas = new JComboBox<>();
        cargarMesasDisponibles(connection, mesaOrigen);
        panel.add(comboMesas);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnConfirmar = new JButton("Confirmar");
        btnCancelar = new JButton("Cancelar");

        btnConfirmar.addActionListener(e -> {
            confirmado = true;
            mesaSeleccionada = Integer.parseInt(comboMesas.getSelectedItem().toString().split(" ")[1]);
            dispose();
        });

        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCancelar);
        panel.add(panelBotones);

        add(panel);
    }

    private void cargarMesasDisponibles(Connection connection, int mesaOrigen) {
        MesaDAO mesaDAO = new MesaDAO(connection);
        try {
            List<Mesa> mesas = mesaDAO.listarMesas();
            for (Mesa mesa : mesas) {
                if (mesa.getNumero_mesa() != mesaOrigen && !mesa.getEstado().equalsIgnoreCase("ocupada")) {
                    comboMesas.addItem("Mesa " + mesa.getNumero_mesa());
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar mesas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public int getMesaSeleccionada() {
        return mesaSeleccionada;
    }
}
