
package DAO;

import Modelo.Venta;
import java.util.List;

public interface VentaDao {
    void agregarVenta(Venta venta);
    void actualizarVenta(Venta venta);
    void eliminarVenta(int id_venta);
    Venta obtenerVenta(int id_venta);
    List<Venta> obtenerTodasVentas();
}