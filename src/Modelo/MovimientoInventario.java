package Modelo;

import java.sql.Timestamp;


public class MovimientoInventario {
    private int id_movimiento;
    private int id_producto;
    private Timestamp fecha_movimiento;
    private String tipo_movimiento; // entrada, salida
    private int cantidad;
    // Constructor, Getters y Setters

    public MovimientoInventario(int id_movimiento, int id_producto, Timestamp fecha_movimiento, String tipo_movimiento, int cantidad) {
        this.id_movimiento = id_movimiento;
        this.id_producto = id_producto;
        this.fecha_movimiento = fecha_movimiento;
        this.tipo_movimiento = tipo_movimiento;
        this.cantidad = cantidad;
    }

    public int getId_movimiento() {
        return id_movimiento;
    }

    public void setId_movimiento(int id_movimiento) {
        this.id_movimiento = id_movimiento;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public Timestamp getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(Timestamp fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    public String getTipo_movimiento() {
        return tipo_movimiento;
    }

    public void setTipo_movimiento(String tipo_movimiento) {
        this.tipo_movimiento = tipo_movimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}