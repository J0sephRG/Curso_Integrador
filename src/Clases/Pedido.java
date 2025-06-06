package Modelo;

import java.util.List;

public class Pedido {
    private int id;
    private List<Producto> productos;
    private String mesa;
    private double total;

    public Pedido(int id, List<Producto> productos, String mesa, double total) {
        this.id = id;
        this.productos = productos;
        this.mesa = mesa;
        this.total = total;
    }
    // Métodos para agregar, eliminar productos y calcular total
    
    // Método para calcular el total
    private double calcularTotal() {
        double suma = 0;
        for (Producto producto : productos) {
            suma += producto.getPrecio_unitario();
        }
        return suma;
    }
    
}
