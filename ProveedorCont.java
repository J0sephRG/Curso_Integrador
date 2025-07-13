/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DAO.ProveedorDAO;
import Modelo.Proveedor;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

    public class ProveedorCont {
    private ProveedorDAO proveedorDAO;
    private JTable tabla;

    public ProveedorCont(ProveedorDAO proveedorDAO, JTable tabla) {
        this.proveedorDAO = proveedorDAO;
        this.tabla = tabla;
    }
    public void cargarProveedores() {
    // Obtenemos la lista de productos desde la base de datos
    List<Proveedor> lista = proveedorDAO.obtenerTodos();
    DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
    modelo.setRowCount(0);

    for (Proveedor p : lista) {
        modelo.addRow(new Object[]{
            p.getnumeroDocumento(),
            p.getNombre(),
            p.getApellido(),
            p.getTelefono(),
            p.getEmail(),
            p.getDireccion(),  
        });
    }
    }
}
