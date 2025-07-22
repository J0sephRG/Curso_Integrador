package Modelo;

public class ProductoSeleccionado {
    private int idProducto;
    private int cantidad;

    public ProductoSeleccionado(int idProducto, int cantidad) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public int getIdProducto() { return idProducto; }
    public int getCantidad() { return cantidad; }
}
