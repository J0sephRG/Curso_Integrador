/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Timestamp;

public class Actividad {
    private int id_actividad;
    private Integer id_usuario; // Puede ser null
    private String descripcion;
    private Timestamp fecha;
    // Constructor, Getters y Setters

    public Actividad(int id_actividad, Integer id_usuario, String descripcion, Timestamp fecha) {
        this.id_actividad = id_actividad;
        this.id_usuario = id_usuario;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public int getId_actividad() {
        return id_actividad;
    }

    public void setId_actividad(int id_actividad) {
        this.id_actividad = id_actividad;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
}
