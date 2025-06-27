/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DAO.CategoriaDAO;
import Modelo.Categoria;
import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 *
 * @author JOSEPH ROJAS
 */
public class CategoriaCont {
    private CategoriaDAO catdao;

    public CategoriaCont(CategoriaDAO catdao) {
        this.catdao = catdao;
    }
    
    public void cargarCategorias(JComboBox cboCategoria) {
        ArrayList<Categoria> categorias = catdao.obtenerCategorias();
        cboCategoria.removeAllItems();
        for (Categoria categoria : categorias) {
            cboCategoria.addItem(categoria);
        }
    }
}
