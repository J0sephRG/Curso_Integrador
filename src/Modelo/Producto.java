/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author JOSEPH ROJAS
 */
public class Producto {
    private int idproducto;
    private String nombre;
    private double precio;
    private int stockAct;
    private int stockMin;
    private String unidadMedida;
    private Categoria categoria;
    private String descripcion;

    public Producto() {
    }

    public Producto(int idproducto, String nombre, double precio, int stockAct, int stockMin, String unidadMedida, Categoria categoria,
                    String descripcion) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.precio = precio;
        this.stockAct = stockAct;
        this.stockMin = stockMin;
        this.unidadMedida = unidadMedida;
        this.categoria = categoria;
        this.descripcion = descripcion;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStockAct() {
        return stockAct;
    }

    public void setStockAct(int stockAct) {
        this.stockAct = stockAct;
    }

    public int getStockMin() {
        return stockMin;
    }

    public void setStockMin(int stockMin) {
        this.stockMin = stockMin;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
