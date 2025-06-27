package Modelo.PedidosLlevar;

import Modelo.PedidoBase;
import java.sql.Timestamp;

public class PedidoLlevar extends PedidoBase {

    public PedidoLlevar(int id, int idCliente, String estado, Timestamp fechaPedido) {
        super(id, idCliente, estado, fechaPedido);
    }

    @Override
    public String getDireccionEntrega() {
        return "RECOGER EN TIENDA";
    }

    @Override
    public String getTipoPedido() {
        return "PARA LLEVAR";
    }

    @Override
    public double calcularTotal() {
        // Lógica interna propia
        // Ejemplo ficticio:
        return 10.0;  // total base sin cargo extra
    }
}


