package Modelo;

import Interface.Pedido;
import Strategy.CalculoTotalLlevar;
import Strategy.CalculoTotalStrategy;
import java.sql.Timestamp;

public class PedidoLlevar implements Pedido {
    private int id;
    private int idCliente;
    private String estado;
    private Timestamp fechaPedido;
    private final CalculoTotalStrategy estrategiaCalculo;

    public PedidoLlevar(int id, int idCliente, String estado, Timestamp fechaPedido) {
        this.id = id;
        this.idCliente = idCliente;
        this.estado = estado;
        this.fechaPedido = fechaPedido;
        this.estrategiaCalculo = new CalculoTotalLlevar(this);
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
    @Override public String getDireccionEntrega() { return "RECOGER EN TIENDA"; }
    
    @Override
    public double calcularTotal() {
        return estrategiaCalculo.calcularTotal();
    }

    @Override
    public String getTipoPedido() {
        return "PARA LLEVAR";
    }
}

