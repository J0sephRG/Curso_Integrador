
package Modelo;

import Modelo.Interface.Pedido;
import java.sql.Timestamp;

public abstract class PedidoBase implements Pedido {
    protected int id;
    protected int idCliente;
    protected String estado;
    protected Timestamp fechaPedido;

    // Constructor que inicializa los atributos comunes
    public PedidoBase(int id, int idCliente, String estado, Timestamp fechaPedido) {
        this.id = id;
        this.idCliente = idCliente;
        this.estado = estado;
        this.fechaPedido = fechaPedido;
    }

    @Override public int getId() { return id; }
    @Override public void setId(int id) { this.id = id; }
    @Override public int getIdCliente() { return idCliente; }
    @Override public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    @Override public String getEstado() { return estado; }
    @Override public void setEstado(String estado) { this.estado = estado; }
    @Override public Timestamp getFechaPedido() { return fechaPedido; }
    @Override public void setFechaPedido(Timestamp fechaPedido) { this.fechaPedido = fechaPedido; }

    // Método abstracto que deben implementar las subclases
    @Override
    public abstract double calcularTotal();  // Subclases tienen que definir cómo calcular el total

    // Método abstracto para obtener la dirección de entrega, si aplica
    @Override
    public abstract String getDireccionEntrega();  // Subclases definen este comportamiento

    // Método abstracto para obtener el tipo de pedido (Delivery, Llevar, etc.)
    @Override
    public abstract String getTipoPedido();  // Subclases definen este comportamiento
}
