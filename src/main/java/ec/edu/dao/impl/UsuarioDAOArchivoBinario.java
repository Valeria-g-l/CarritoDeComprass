package ec.edu.dao.impl;

import ec.edu.dao.UsuarioDAO;
import ec.edu.modelo.Usuario;
import ec.edu.modelo.Rol;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UsuarioDAOArchivoBinario implements UsuarioDAO {
    private final File archivo;

    public UsuarioDAOArchivoBinario(String rutaArchivo) {
        archivo = new File(rutaArchivo);
        /**Se creo 1 usuario y 1 admin paara asi administrar*/
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
/**Crear un muevo usuario*/
    @Override
    public void crear(Usuario usuario) {
        guardar(usuario);
    }
    /**Agregar el usuarii*/
    @Override
    public void guardar(Usuario nuevoUsuario) {
        List<Usuario> usuarios = obtenerTodos();
        usuarios.add(nuevoUsuario);
        escribirTodos(usuarios);
    }

    /**muestra cuantos usuarios hay registrados*/
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

    //Buscar por la cedula al los usuarios
    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario u : obtenerTodos()) {
            if (u.getUsername().equals(username)) return u;
        }
        return null;
    }
 //Elimiinar usuarios
    @Override
    public void eliminar(String username) {
        List<Usuario> usuarios = obtenerTodos();
        usuarios.removeIf(u -> u.getUsername().equals(username));
        escribirTodos(usuarios);
    }


    @Override
    public void eliminar(Usuario usuario) {
        eliminar(usuario.getUsername());
    }
//Actualizar
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

    //Se lista dependiendo si es Administrador o Usuario
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

    private void escribirTodos(List<Usuario> usuarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            System.out.println("Error escribiendo usuarios binarios: " + e.getMessage());
        }
    }
}
