package ec.edu.dao.impl;

import ec.edu.dao.UsuarioDAO;
import ec.edu.modelo.Rol;
import ec.edu.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UsuarioDAOMemoria implements UsuarioDAO {

    private List<Usuario> usuarios;

    public UsuarioDAOMemoria() {
        usuarios = new ArrayList<>();
        crear(new Usuario("admin", "12345", Rol.ADMINISTRADOR));
        crear(new Usuario("user", "12345", Rol.USUARIO));
    }

    @Override
    public Usuario autenticar(String username, String contrasenia) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) && usuario.getContrasenia().equals(contrasenia)) {
                System.out.println("Usuario autenticado con rol: " + usuario.getRol());
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void eliminar(String username) {
        usuarios.removeIf(usuario -> usuario.getUsername().equals(username));
    }

    @Override
    public void eliminar(Usuario usuario) {
        usuarios.remove(usuario);
    }

    @Override
    public void actualizar(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuarioAux = usuarios.get(i);
            if (usuarioAux.getUsername().equals(usuario.getUsername())) {
                usuarios.set(i, usuario);
                break;
            }
        }
    }

    @Override
    public List<Usuario> obtenerTodos() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        List<Usuario> usuariosEncontrados = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getRol().equals(rol)) {
                usuariosEncontrados.add(usuario);
            }
        }
        return usuariosEncontrados;
    }

    @Override
    public void guardar(Usuario nuevoUsuario) {
        System.out.println("Guardando usuario con rol: " + nuevoUsuario.getRol());
        usuarios.add(nuevoUsuario);
    }
}
