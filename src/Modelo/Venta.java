package Modelo;

import java.math.BigDecimal;
import java.sql.Timestamp;


public class Venta {
    private int id_venta;
    private Timestamp fecha_venta;
    private Integer id_usuario; // Puede ser null si es SET NULL
    private String metodo_pago; // efectivo, tarjeta, transferencia
    private BigDecimal monto_total;
    // Constructor, Getters y Setters


    public Venta(int id_venta, Timestamp fecha_venta, Integer id_usuario, String metodo_pago, BigDecimal monto_total) {
        this.id_venta = id_venta;
        this.fecha_venta = fecha_venta;
        this.id_usuario = id_usuario;
        this.metodo_pago = metodo_pago;
        this.monto_total = monto_total;
    }


    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public Timestamp getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(Timestamp fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getMetodo_pago() {
        return metodo_pago;
    }

    public void setMetodo_pago(String metodo_pago) {
        this.metodo_pago = metodo_pago;
    }

    public BigDecimal getMonto_total() {
        return monto_total;
    }

    public void setMonto_total(BigDecimal monto_total) {
        this.monto_total = monto_total;
    }
}
