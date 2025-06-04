
package DAO;

import Modelo.Categoria;
import java.util.List;
import java.sql.Connection;

// Interfaz CategoriaDao
public interface CategoriaDao {
    void agregarCategoria(Categoria categoria);
    void actualizarCategoria(Categoria categoria);
    void eliminarCategoria(int id_categoria);
    Categoria obtenerCategoria(int id_categoria);
    List<Categoria> obtenerTodasCategorias();
}