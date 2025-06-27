/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DAO.ProductoDAO;
import Modelo.Producto;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JOSEPH ROJAS
 */
public class ProductoCont {
    private ProductoDAO producto;
    private JTable tabla;

    public ProductoCont(ProductoDAO producto, JTable tabla) {
        this.producto = producto;
        this.tabla = tabla;
    }
    public void cargarProductos() {
    // Obtenemos la lista de productos desde la base de datos
    ArrayList<Producto> productos = producto.obtenerProductos();
    DefaultTableModel modelo = new DefaultTableModel();
    modelo.setColumnIdentifiers(new String[]{
        "ID", "Nombre", "Precio", "Stock Actual", "Stock Mínimo", "Unidad", "Descripción", "Categoría ID"
    });

    for (Producto p : productos) {
        modelo.addRow(new Object[]{
            p.getIdproducto(),
            p.getNombre(),
            p.getPrecio(),
            p.getStockAct(),
            p.getStockMin(),
            p.getUnidadMedida(),
            p.getDescripcion(),
            p.getCategoria().getIdcategoria()  
        });
    }
    tabla.setModel(modelo);
    }
}