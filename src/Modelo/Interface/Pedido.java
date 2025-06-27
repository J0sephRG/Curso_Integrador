package Modelo.Interface;
import java.sql.Timestamp;

// Interfaz base para pedidos
public interface Pedido {
    int getId();
    void setId(int id);
    int getIdCliente();
    void setIdCliente(int idCliente);
    String getEstado();
    void setEstado(String estado);
    Timestamp getFechaPedido();
    void setFechaPedido(Timestamp fechaPedido);
    double calcularTotal(); // Este método ya no usa Strategy, pero sigue siendo útil
    String getTipoPedido();
    String getDireccionEntrega();
}


