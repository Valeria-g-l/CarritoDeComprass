package ec.edu.dao;

import ec.edu.modelo.Rol;
import ec.edu.modelo.Usuario;

import java.util.List;

public interface UsuarioDAO {

    Usuario autenticar(String username, String contrasenia);

    void crear(Usuario usuario);

    Usuario buscarPorUsername(String username);

    void eliminar(String username);

    void actualizar(Usuario usuario);

    List<Usuario> listarTodos();

    List<Usuario> listarPorRol(Rol rol);

}