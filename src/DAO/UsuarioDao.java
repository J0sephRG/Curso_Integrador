
package DAO;

import Modelo.Usuario;
import java.util.List;

public interface UsuarioDao {
    void agregarUsuario(Usuario usuario);
    void actualizarUsuario(Usuario usuario);
    void eliminarUsuario(int id_usuario);
    Usuario obtenerUsuario(int id_usuario);
    List<Usuario> obtenerTodosUsuarios();
}
