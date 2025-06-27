/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Timestamp;

public class Reserva {
    private int id_reserva;
    private Integer id_cliente; // Puede ser null si es SET NULL
    private Timestamp fecha_reserva;
    private int numero_personas;
    private String estado; // confirmada, cancelada, completada
    // Constructor, Getters y Setters

    public Reserva(int id_reserva, Integer id_cliente, Timestamp fecha_reserva, int numero_personas, String estado) {
        this.id_reserva = id_reserva;
        this.id_cliente = id_cliente;
        this.fecha_reserva = fecha_reserva;
        this.numero_personas = numero_personas;
        this.estado = estado;
    }

    public int getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
    }

    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Timestamp getFecha_reserva() {
        return fecha_reserva;
    }

    public void setFecha_reserva(Timestamp fecha_reserva) {
        this.fecha_reserva = fecha_reserva;
    }

    public int getNumero_personas() {
        return numero_personas;
    }

    public void setNumero_personas(int numero_personas) {
        this.numero_personas = numero_personas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}