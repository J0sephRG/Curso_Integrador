package Controlador;

import DAO.PlatoProductoDAO;
import Modelo.Plato;
import Modelo.ProductoSeleccionado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlatoProductoCont {

    private PlatoProductoDAO dao;
    private JTable tablaProductos;
    private JComboBox<Plato> comboPlatos;
    private Connection conn;

    public PlatoProductoCont(Connection conn, JTable tablaProductos, JComboBox<Plato> comboPlatos) {
        // Verificar que la conexión no sea nula
        if (conn == null) {
            throw new IllegalArgumentException("La conexión no puede ser nula.");
        }
        this.conn = conn;
        this.dao = new PlatoProductoDAO(conn);
        this.tablaProductos = tablaProductos;
        this.comboPlatos = comboPlatos;
    }

    public void cargarPlatos() {
        String sql = "SELECT id_plato, nombre, precio, descripcion FROM Plato ORDER BY nombre";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            comboPlatos.removeAllItems();
            while (rs.next()) {
                Plato p = new Plato(
                        rs.getInt("id_plato"),
                        rs.getString("nombre"),
                        rs.getBigDecimal("precio"),
                        rs.getString("descripcion")
                );
                comboPlatos.addItem(p);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar platos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void cargarProductos() {
        DefaultTableModel modelo = new DefaultTableModel(new String[]{
            "Seleccionar", "ID Producto", "Nombre", "Unidad", "Stock Actual",
            "Stock Mínimo", "Precio Unitario", "Categoría", "Descripción", "Cantidad"
        }, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 9; // Solo las columnas de selección y cantidad son editables
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Boolean.class; // Columna de selección
                if (columnIndex == 9) return Integer.class; // Columna de cantidad
                return String.class; // Otras columnas son de tipo String
            }
        };

        String sql = "SELECT id_producto, nombre, unidad_medida, stock_actual, stock_minimo, " +
                "precio_unitario, c.nombre AS nombre_categoria, p.descripcion " +
                "FROM Producto p " +
                "LEFT JOIN Categoria c ON p.id_categoria = c.id_categoria " +
                "ORDER BY p.nombre";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    false, // Seleccionar
                    rs.getInt("id_producto"),
                    rs.getString("nombre"),
                    rs.getString("unidad_medida"),
                    rs.getInt("stock_actual"),
                    rs.getInt("stock_minimo"),
                    rs.getDouble("precio_unitario"),
                    rs.getString("nombre_categoria"),
                    rs.getString("descripcion"),
                    0 // Cantidad inicial
                });
            }

            tablaProductos.setModel(modelo);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void guardarProductosParaPlato() {
        Plato plato = (Plato) comboPlatos.getSelectedItem();
        if (plato == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un plato.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idPlato = plato.getId_plato();
        List<ProductoSeleccionado> productos = new ArrayList<>();
        DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            boolean seleccionado = Boolean.TRUE.equals(modelo.getValueAt(i, 0));
            int cantidad = 0;

            try {
                cantidad = Integer.parseInt(modelo.getValueAt(i, 9).toString());
            } catch (NumberFormatException e) {
                cantidad = 0; // Si no se puede convertir, se establece a 0
            }

            if (seleccionado && cantidad > 0) {
                int idProducto = (int) modelo.getValueAt(i, 1);
                productos.add(new ProductoSeleccionado(idProducto, cantidad));
            }
        }

        if (productos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar al menos un producto con cantidad mayor a 0.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            boolean exito = dao.insertarProductosEnPlato(idPlato, productos);
            if (exito) {
                JOptionPane.showMessageDialog(null, "Productos agregados correctamente al plato.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarProductos(); // Opcional: para refrescar la tabla de productos
            } else {
                JOptionPane.showMessageDialog(null, "Error al insertar los productos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
