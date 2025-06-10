/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class PlatoProducto {
    private int id_plato;
    private int id_producto;
    private int cantidad;
    // Constructor, Getters y Setters

    public PlatoProducto(int id_plato, int id_producto, int cantidad) {
        this.id_plato = id_plato;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
    }

    public int getId_plato() {
        return id_plato;
    }

    public void setId_plato(int id_plato) {
        this.id_plato = id_plato;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}