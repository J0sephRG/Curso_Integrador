package Modelo.VENTAenMesa;
public class MesaUnida {
    private int id_unida;
    private Integer id_mesa_principal; // Puede ser null
    private Integer id_mesa_secundaria; // Puede ser null
    // Constructor, Getters y Setters

    public MesaUnida( Integer id_mesa_principal, Integer id_mesa_secundaria) {
        
        this.id_mesa_principal = id_mesa_principal;
        this.id_mesa_secundaria = id_mesa_secundaria;
    }

    public int getId_unida() {
        return id_unida;
    }

    public void setId_unida(int id_unida) {
        this.id_unida = id_unida;
    }

    public Integer getId_mesa_principal() {
        return id_mesa_principal;
    }

    public void setId_mesa_principal(Integer id_mesa_principal) {
        this.id_mesa_principal = id_mesa_principal;
    }

    public Integer getId_mesa_secundaria() {
        return id_mesa_secundaria;
    }

    public void setId_mesa_secundaria(Integer id_mesa_secundaria) {
        this.id_mesa_secundaria = id_mesa_secundaria;
    }
}