/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class DetalleProveedor {
    private int id_detalle;
    private int id_proveedor;
    private int id_producto;
    // Constructor, Getters y Setters

    public DetalleProveedor(int id_detalle, int id_proveedor, int id_producto) {
        this.id_detalle = id_detalle;
        this.id_proveedor = id_proveedor;
        this.id_producto = id_producto;
    }

    public int getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }
}