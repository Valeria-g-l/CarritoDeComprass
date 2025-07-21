package ec.edu.dao.impl;

import ec.edu.dao.UsuarioDAO;
import ec.edu.modelo.Rol;
import ec.edu.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Implementación en memoria del DAO de usuarios.
 *
 * Utiliza una lista local para almacenar usuarios temporalmente durante la ejecución.
 * Ideal para entornos de prueba o simulación sin persistencia permanente.
 * Al iniciar, se cargan automáticamente un usuario administrador y uno estándar.
 *
 * Permite operaciones CRUD completas y autenticación básica.
 *
 * @author Valeria
 * @version 1.0
 */
public class UsuarioDAOMemoria implements UsuarioDAO {

    private List<Usuario> usuarios;
    /**
     * Constructor que inicializa el DAO de usuarios en memoria.
     * <p>
     * Crea una lista vacía y añade dos usuarios por defecto:
     * un administrador y un usuario estándar con contraseñas predeterminadas.
     * </p>
     */
    public UsuarioDAOMemoria() {
        usuarios = new ArrayList<>();
        crear(new Usuario("admin", "12345", Rol.ADMINISTRADOR));
        crear(new Usuario("user", "12345", Rol.USUARIO));
    }
    /**
     * Autentica a un usuario verificando su username y contraseña.
     *
     * @param username nombre de usuario
     * @param contrasenia contraseña del usuario
     * @return el usuario autenticado o null si no coincide
     */
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
    /**
     * Crea un nuevo usuario en memoria.
     *
     * @param usuario el usuario a registrar
     */
    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
    }
    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username username del usuario
     * @return el usuario encontrado o null
     */

    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }
    /**
     * Elimina un usuario por su nombre de usuario.
     *
     * @param username username del usuario a eliminar
     */
    @Override
    public void eliminar(String username) {
        usuarios.removeIf(usuario -> usuario.getUsername().equals(username));
    }
    /**
     * Elimina un usuario a partir del objeto proporcionado.
     *
     * @param usuario el usuario a eliminar
     */

    @Override
    public void eliminar(Usuario usuario) {
        usuarios.remove(usuario);
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param usuario el usuario con los datos modificados
     */
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
    /**
     * Devuelve una lista con todos los usuarios registrados en memoria.
     *
     * @return lista de usuarios
     */
    @Override
    public List<Usuario> obtenerTodos() {
        return new ArrayList<>(usuarios);
    }
    /**
     * Lista todos los usuarios que tienen el rol especificado.
     *
     * @param rol el rol por el cual filtrar (ADMINISTRADOR, USUARIO, etc.)
     * @return lista de usuarios con el rol indicado
     */

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
    /**
     * Guarda un nuevo usuario en memoria.
     * Este método es equivalente a {@link #crear(Usuario)}.
     *
     * @param nuevoUsuario el usuario a guardar
     */
    @Override
    public void guardar(Usuario nuevoUsuario) {
        System.out.println("Guardando usuario con rol: " + nuevoUsuario.getRol());
        usuarios.add(nuevoUsuario);
    }
}
