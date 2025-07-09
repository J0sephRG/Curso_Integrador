package Seguridad;

import Modelo.Usuario;

public class Sesion {
   /* private static Usuario usuarioActual;

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static void setUsuarioActual(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
    }*/
    
    private static Usuario usuarioActual;

    public static void setUsuarioActual(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static boolean isRole(String rol) {
        return usuarioActual != null && usuarioActual.getRol().equals(rol);
    }

    public static void cerrarSesion() {
        usuarioActual = null;
    }
}
