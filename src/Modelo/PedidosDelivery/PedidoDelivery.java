package Modelo.PedidosDelivery;

import Modelo.PedidoBase;
import java.sql.Timestamp;

public class PedidoDelivery extends PedidoBase {

    private String direccionEntrega;

    // Constructor
    public PedidoDelivery(int id, int idCliente, String direccionEntrega, 
                          String estado, Timestamp fechaPedido) {
        super(id, idCliente, estado, fechaPedido);
        this.direccionEntrega = direccionEntrega;
    }

    // Métodos getter y setter
    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    @Override
    public String getTipoPedido() {
        return "DELIVERY";  // Se especifica que es un pedido de tipo "Delivery"
    }

    @Override
    public double calcularTotal() {
        // Aquí deberías agregar la lógica para calcular el total con base en los platos seleccionados
        // Ejemplo ficticio:
        double precioBase = 10.0;  // Este sería el precio base de los platos
        double cargoEntrega = 5.0;  // Costo adicional de entrega
        return precioBase + cargoEntrega;
    }
}