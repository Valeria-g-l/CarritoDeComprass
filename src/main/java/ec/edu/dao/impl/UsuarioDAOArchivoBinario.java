package ec.edu.dao.impl;

import ec.edu.dao.UsuarioDAO;
import ec.edu.modelo.Usuario;
import ec.edu.modelo.Rol;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Implementación del DAO de usuarios con persistencia en archivo binario.
 *
 * Gestiona usuarios realizando operaciones CRUD y autenticación.
 * Al inicializar, crea automáticamente un administrador y un usuario por defecto,
 * facilitando la configuración inicial del sistema.
 *
 * Utiliza {@link ObjectInputStream} y {@link ObjectOutputStream} para serializar
 * y deserializar objetos de tipo {@link Usuario}. La persistencia es local y no requiere base de datos.
 *
 * @author Valeria
 * @version 1.0
 */
public class UsuarioDAOArchivoBinario implements UsuarioDAO {
    private final File archivo;
    /**
     * Constructor que inicializa el DAO de usuarios utilizando un archivo binario.
     * <p>
     * Si el archivo no existe o está vacío, crea un usuario administrador y un usuario normal
     * por defecto para facilitar la administración inicial del sistema.
     * </p>
     *
     * @param rutaArchivo Ruta del archivo binario donde se almacenan los usuarios.
     */
    public UsuarioDAOArchivoBinario(String rutaArchivo) {
        archivo = new File(rutaArchivo);
        /**
         * Autentica un usuario comparando username y contraseña.
         *
         * @param username nombre de usuario
         * @param contrasenia contraseña del usuario
         * @return el usuario autenticado o null si no coincide
         */
        if (!archivo.exists() || archivo.length() == 0) {
            crear(new Usuario("admin", "Admin123!", Rol.ADMINISTRADOR));
            crear(new Usuario("user", "User123!", Rol.USUARIO));
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.out.println("Error al crear archivo binario: " + e.getMessage());
            }
        }
    }

    @Override
    public Usuario autenticar(String username, String contrasenia) {
        for (Usuario u : obtenerTodos()) {
            if (u.getUsername().equals(username) && u.getContrasenia().equals(contrasenia)) {
                return u;
            }
        }
        return null;
    }
    /**
     * Registra un nuevo usuario en el archivo.
     *
     * @param usuario usuario a crear
     */
    @Override
    public void crear(Usuario usuario) {
        guardar(usuario);
    }
    /**
     * Agrega un nuevo usuario a la lista persistente.
     *
     * @param nuevoUsuario el usuario a guardar
     */
    @Override
    public void guardar(Usuario nuevoUsuario) {
        List<Usuario> usuarios = obtenerTodos();
        usuarios.add(nuevoUsuario);
        escribirTodos(usuarios);
    }

    /**
     * Obtiene la lista completa de usuarios almacenados.
     *
     * @return lista de usuarios existentes
     */
    @Override
    public List<Usuario> obtenerTodos() {
        if (!archivo.exists() || archivo.length() == 0) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<Usuario>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Error leyendo usuarios binarios: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username nombre de usuario
     * @return el usuario encontrado o null
     */
    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario u : obtenerTodos()) {
            if (u.getUsername().equals(username)) return u;
        }
        return null;
    }
    /**
     * Elimina un usuario según su nombre de usuario.
     *
     * @param username nombre del usuario a eliminar
     */
    @Override
    public void eliminar(String username) {
        List<Usuario> usuarios = obtenerTodos();
        usuarios.removeIf(u -> u.getUsername().equals(username));
        escribirTodos(usuarios);
    }
    /**
     * Elimina el usuario especificado.
     *
     * @param usuario usuario a eliminar
     */

    @Override
    public void eliminar(Usuario usuario) {
        eliminar(usuario.getUsername());
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param usuarioModificado usuario con nueva información
     */
    @Override
    public void actualizar(Usuario usuarioModificado) {
        List<Usuario> usuarios = obtenerTodos();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equals(usuarioModificado.getUsername())) {
                usuarios.set(i, usuarioModificado);
                break;
            }
        }
        escribirTodos(usuarios);
    }

    /**
     * Lista usuarios según el rol (ADMINISTRADOR o USUARIO).
     *
     * @param rol rol a filtrar
     * @return lista de usuarios con el rol especificado
     */
    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario u : obtenerTodos()) {
            if (u.getRol().equals(rol)) {
                resultado.add(u);
            }
        }
        return resultado;
    }

    /**
     * Guarda toda la lista de usuarios en el archivo binario.
     *
     * @param usuarios lista completa de usuarios actualizada
     */
    private void escribirTodos(List<Usuario> usuarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            System.out.println("Error escribiendo usuarios binarios: " + e.getMessage());
        }
    }
}
