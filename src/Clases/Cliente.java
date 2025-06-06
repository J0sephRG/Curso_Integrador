/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.Date;

/**
 *
 * @author JOSEPH ROJAS
 */
public class Cliente extends Persona {

    private int idClente;

    public Cliente(int idClente) {
        this.idClente = idClente;
    }

    public Cliente(int idClente, String nombre, String apellido, Date fechaNacimiento, String tipodocumento, String telefono, String email) {
        super(nombre, apellido, fechaNacimiento, tipodocumento, telefono, email);
        this.idClente = idClente;
    }
    
    
    @Override
    public void mostrarInformacion() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
