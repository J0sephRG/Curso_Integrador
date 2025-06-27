/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class HistorialPrecio {
    private int id_historial;
    private int id_producto;
    private BigDecimal precio;
    private Timestamp fecha_cambio;
    // Constructor, Getters y Setters

    public HistorialPrecio(int id_historial, int id_producto, BigDecimal precio, Timestamp fecha_cambio) {
        this.id_historial = id_historial;
        this.id_producto = id_producto;
        this.precio = precio;
        this.fecha_cambio = fecha_cambio;
    }

    public int getId_historial() {
        return id_historial;
    }

    public void setId_historial(int id_historial) {
        this.id_historial = id_historial;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Timestamp getFecha_cambio() {
        return fecha_cambio;
    }

    public void setFecha_cambio(Timestamp fecha_cambio) {
        this.fecha_cambio = fecha_cambio;
    }
}