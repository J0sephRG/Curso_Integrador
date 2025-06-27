package Modelo;

  import java.sql.Timestamp;


public class Usuario {
    private int id_usuario;
    private String nombre;
    private String apellido;
    private String rol; // admin, cajero, cocinero
    private String clave;
    private Timestamp fecha_creacion;
    // Constructor, Getters y Setters

    public Usuario(int id_usuario, String nombre, String apellido, String rol, String clave, Timestamp fecha_creacion) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.rol = rol;
        this.clave = clave;
        this.fecha_creacion = fecha_creacion;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
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

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Timestamp getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Timestamp fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }
}
