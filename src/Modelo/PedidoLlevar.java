/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class PedidoLlevar {
    private int id;
    private int idCliente;
    private String estado;
    private java.sql.Timestamp fechaPedido;

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public java.sql.Timestamp getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(java.sql.Timestamp fechaPedido) { this.fechaPedido = fechaPedido; }
}
