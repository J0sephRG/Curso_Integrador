package Modelo.PedidosLlevar;

import Modelo.PedidoBase;
import java.sql.Timestamp;

public class PedidoLlevar extends PedidoBase {

    public PedidoLlevar(int id, int idCliente, String estado, Timestamp fechaPedido) {
        super(id, idCliente, estado, fechaPedido);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Timestamp fechaPedido) {
        this.fechaPedido = fechaPedido;
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


