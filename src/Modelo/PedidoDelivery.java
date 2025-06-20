package Modelo;

import Interface.Pedido;
import Strategy.CalculoTotalDelivery;
import Strategy.CalculoTotalStrategy;
import java.sql.Timestamp;

public class PedidoDelivery implements Pedido {
    private int id;
    private int idCliente;
    private String direccionEntrega;
    private String estado;
    private Timestamp fechaPedido;
    private final CalculoTotalStrategy estrategiaCalculo;

    public PedidoDelivery(int id, int idCliente, String direccionEntrega, 
                        String estado, Timestamp fechaPedido) {
        this.id = id;
        this.idCliente = idCliente;
        this.direccionEntrega = direccionEntrega;
        this.estado = estado;
        this.fechaPedido = fechaPedido;
        this.estrategiaCalculo = new CalculoTotalDelivery(this);
    }

    // Implementación de métodos de la interfaz
    @Override public int getId() { return id; }
    @Override public void setId(int id) { this.id = id; }
    @Override public int getIdCliente() { return idCliente; }
    @Override public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    @Override public String getEstado() { return estado; }
    @Override public void setEstado(String estado) { this.estado = estado; }
    @Override public Timestamp getFechaPedido() { return fechaPedido; }
    @Override public void setFechaPedido(Timestamp fechaPedido) { this.fechaPedido = fechaPedido; }
    @Override public String getDireccionEntrega() { return direccionEntrega; }
    
    @Override
    public double calcularTotal() {
        return estrategiaCalculo.calcularTotal();
    }

    @Override
    public String getTipoPedido() {
        return "DELIVERY";
    }
}
