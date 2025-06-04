
package DAO;
import Modelo.Producto;
import java.util.List;

public interface ProductoDao {
    void agregarProducto(Producto producto);
    void actualizarProducto(Producto producto);
    void eliminarProducto(int id_producto);
    Producto obtenerProducto(int id_producto);
    List<Producto> obtenerTodosProductos();
}

