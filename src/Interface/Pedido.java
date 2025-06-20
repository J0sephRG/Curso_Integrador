package Interface;
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
    double calcularTotal();
    String getTipoPedido();
    String getDireccionEntrega();
}
