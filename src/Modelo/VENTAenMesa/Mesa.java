package Modelo.VENTAenMesa;

public class Mesa {
    private int id_mesa;
    private int numero_mesa;
    private int capacidad;
    private String estado; // disponible, ocupada, reservada
    // Constructor, Getters y Setters

    public Mesa(int id_mesa, int numero_mesa, int capacidad, String estado) {
        this.id_mesa = id_mesa;
        this.numero_mesa = numero_mesa;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    public int getId_mesa() {
        return id_mesa;
    }

    public void setId_mesa(int id_mesa) {
        this.id_mesa = id_mesa;
    }

    public int getNumero_mesa() {
        return numero_mesa;
    }

    public void setNumero_mesa(int numero_mesa) {
        this.numero_mesa = numero_mesa;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
