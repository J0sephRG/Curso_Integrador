package Modelo;

import java.math.BigDecimal;

public class Plato {
    private int id_plato;
    private String nombre;
    private BigDecimal precio;
    private String descripcion;
    // Constructor, Getters y Setters

    public Plato(int id_plato, String nombre, BigDecimal precio, String descripcion) {
        this.id_plato = id_plato;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public int getId_plato() {
        return id_plato;
    }

    public void setId_plato(int id_plato) {
        this.id_plato = id_plato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

   public void setPrecio(BigDecimal precio) {
        if (precio.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
     @Override
    public String toString() {
        return nombre; // Solo mostramos el nombre en el JComboBox
    }
}
