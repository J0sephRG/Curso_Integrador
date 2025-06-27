/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Timestamp;

public class Notificacion {
    private int id_notificacion;
    private String mensaje;
    private String estado; // leído, no_leído
    private Timestamp fecha;
    // Constructor, Getters y Setters

    public Notificacion(int id_notificacion, String mensaje, String estado, Timestamp fecha) {
        this.id_notificacion = id_notificacion;
        this.mensaje = mensaje;
        this.estado = estado;
        this.fecha = fecha;
    }

    public int getId_notificacion() {
        return id_notificacion;
    }

    public void setId_notificacion(int id_notificacion) {
        this.id_notificacion = id_notificacion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
}