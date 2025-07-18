package ec.edu.dao.impl;

import ec.edu.dao.UsuarioDAO;
import ec.edu.modelo.Producto;
import ec.edu.modelo.Rol;
import ec.edu.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Implementación del DAO de usuarios utilizando persistencia en archivo de texto plano.
 *
 * Cada usuario se guarda en una línea con el formato: username,contraseña,rol,nombre,correo.
 * Esta clase permite realizar operaciones CRUD, autenticación, y filtrado por rol,
 * utilizando serialización manual de objetos {@link Usuario}.
 *
 * Al crearse el archivo por primera vez, se agregan automáticamente dos usuarios:
 * un administrador y uno estándar para garantizar la operatividad inicial del sistema.
 *
 * @author Valeria
 * @version 1.0
 */

public class UsuarioDAOArchivoTexto implements UsuarioDAO{
    private final File archivo;

    /**
     * Constructor que inicializa el DAO de usuarios utilizando un archivo de texto.
     * <p>
     * Si el archivo no existe, lo crea y añade dos usuarios por defecto:
     * un administrador y un usuario normal para facilitar la administración inicial.
     * </p>
     *
     * @param rutaArchivo Ruta del archivo de texto donde se almacenan los usuarios.
     */
    public UsuarioDAOArchivoTexto(String rutaArchivo) {
        archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                if (obtenerTodos().isEmpty()) {
                    guardar(new Usuario("admin", "Admin123!", Rol.ADMINISTRADOR));
                    guardar(new Usuario("user", "User123!", Rol.USUARIO));
                }

            } catch (IOException e) {
                System.out.println("Error al crear archivo: " + e.getMessage());
            }
        }
    }

    /**
     * Autentica al usuario con su nombre y contraseña.
     *
     * @param username nombre de usuario
     * @param contrasenia contraseña del usuario
     * @return el usuario autenticado o null si no coincide
     */

    @Override
    public Usuario autenticar(String username, String contrasenia) {
        List<Usuario> usuarios = obtenerTodos();
        for (Usuario u : usuarios) {
            if (u.getUsername().equals(username) && u.getContrasenia().equals(contrasenia)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Guarda un nuevo usuario en el archivo.
     *
     * @param nuevoUsuario usuario a escribir
     */
    @Override
    public void guardar(Usuario nuevoUsuario) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(serializarUsuario(nuevoUsuario));
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al escribir usuario: " + e.getMessage());
        }
    }
    /**
     * Busca un usuario por su nombre de usuario (username).
     *
     * @param username el nombre de usuario a buscar
     * @return el usuario encontrado o null
     */
    @Override
    public Usuario buscarPorUsername(String username) {
        List<Usuario> usuarios = obtenerTodos();
        for (Usuario u : usuarios) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }
    /**
     * Elimina un usuario por su nombre de usuario.
     *
     * @param username nombre del usuario a eliminar
     */
    @Override
    public void eliminar(String username) {
        List<Usuario> usuarios = obtenerTodos();
        usuarios.removeIf(u -> u.getUsername().equals(username));
        reescribirArchivo(usuarios);
    }
    /**
     * Actualiza los datos del usuario, reemplazando su entrada en el archivo.
     *
     * @param usuario usuario actualizado
     */
    @Override
    public void actualizar(Usuario usuario) {
        List<Usuario> usuarios = obtenerTodos();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equals(usuario.getUsername())) {
                usuarios.set(i, usuario);
                break;
            }
        }
        reescribirArchivo(usuarios);
    }
    /**
     * Lista todos los usuarios que tienen el rol especificado.
     *
     * @param rol rol a filtrar (ADMINISTRADOR o USUARIO)
     * @return lista de usuarios con ese rol
     */
    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        List<Usuario> usuarios = obtenerTodos();
        List<Usuario> filtrados = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u.getRol().equals(rol)) {
                filtrados.add(u);
            }
        }
        return filtrados;
    }


    /**
     * Crea un nuevo usuario en el sistema.
     *
     * @param usuario el usuario a crear
     */
    @Override
    public void crear(Usuario usuario) {
        guardar(usuario);
    }


    /**
     * Obtiene todos los usuarios registrados en el archivo.
     *
     * @return lista completa de usuarios
     */
    @Override
    public List<Usuario> obtenerTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Usuario u = deserializarUsuario(linea);
                if (u != null) usuarios.add(u);
            }
        } catch (IOException e) {
            System.out.println("Error al leer usuarios: " + e.getMessage());
        }
        return usuarios;
    }
    @Override
    public void eliminar(Usuario usuario) {
        if (usuario != null) {
            eliminar(usuario.getUsername());
        }
    }


    /**
     * Convierte un objeto {@link Usuario} a una línea de texto para guardar en el archivo.
     *
     * @param u usuario a serializar
     * @return representación en texto del usuario
     */
    private String serializarUsuario(Usuario u) {
        return String.join(",",
                u.getUsername(),
                u.getContrasenia(),
                u.getRol().name(),
                u.getNombre() != null ? u.getNombre() : "",
                u.getCorreo() != null ? u.getCorreo() : ""
        );
    }
    /**
     * Reconstruye un objeto {@link Usuario} desde una línea del archivo.
     *
     * @param linea línea del archivo con datos del usuario
     * @return objeto usuario o null si la línea es inválida
     */
    private Usuario deserializarUsuario(String linea) {
        String[] partes = linea.split(",");
        if (partes.length >= 3) {
            Usuario u = new Usuario();
            u.setUsername(partes[0]);
            u.setContrasenia(partes[1]);
            u.setRol(Rol.valueOf(partes[2]));
            u.setNombre(partes.length > 3 ? partes[3] : null);
            u.setCorreo(partes.length > 4 ? partes[4] : null);
            return u;
        }
        return null;
    }
    /**
     * Sobrescribe el archivo completo con una nueva lista de usuarios.
     *
     * @param usuarios lista que reemplazará el contenido del archivo
     */
    private void reescribirArchivo(List<Usuario> usuarios) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false))) {
            for (Usuario u : usuarios) {
                bw.write(serializarUsuario(u));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al reescribir archivo: " + e.getMessage());
        }
    }



}

