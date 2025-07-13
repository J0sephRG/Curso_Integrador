/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author JOSEPH ROJAS
 */
public class Proveedor {
    private int id_proveedor;
    private String numeroDocumento;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String direccion;
    // Constructor, Getters y Setters
    
    public Proveedor(int id_Proveedor, String numeroDocumento, String nombre, String apellido, String telefono, String email, String direccion) {
        this.id_proveedor = id_proveedor;
        this.numeroDocumento = numeroDocumento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
    }
    
    public Proveedor(String numeroDocumento, String nombre, String apellido, String telefono, String email, String direccion) {
        this.numeroDocumento = numeroDocumento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
    }
    
    public int getid_proveedor(){
        return id_proveedor;
    }
    
    
    public void setid_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    
 
    public String getnumeroDocumento() {
        return numeroDocumento;
    }

    public void setnumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String Apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
}
