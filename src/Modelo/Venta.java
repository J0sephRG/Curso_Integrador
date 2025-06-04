
package Modelo;

import java.util.Date;

public class Venta {
    private int id_venta;
    private Date fecha_venta;
    private int id_usuario;
    private String metodo_pago;
    private double monto_total;
    public Venta() {}
    public Venta(int id_venta, Date fecha_venta, int id_usuario, String metodo_pago, double monto_total) {
        this.id_venta = id_venta;
        this.fecha_venta = fecha_venta;
        this.id_usuario = id_usuario;
        this.metodo_pago = metodo_pago;
        this.monto_total = monto_total;
    }
    public int getId_venta() { return id_venta; }
    public void setId_venta(int id_venta) { this.id_venta = id_venta; }
    public Date getFecha_venta() { return fecha_venta; }
    public void setFecha_venta(Date fecha_venta) { this.fecha_venta = fecha_venta; }
    public int getId_usuario() { return id_usuario; }
    public void setId_usuario(int id_usuario) { this.id_usuario = id_usuario; }
    public String getMetodo_pago() { return metodo_pago; }
    public void setMetodo_pago(String metodo_pago) { this.metodo_pago = metodo_pago; }
    public double getMonto_total() { return monto_total; }
    public void setMonto_total(double monto_total) { this.monto_total = monto_total; }
}