package ec.edu.dao.impl;

import ec.edu.dao.UsuarioDAO;
import ec.edu.modelo.Rol;
import ec.edu.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOArchivoTexto implements UsuarioDAO{
    private final File archivo;

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


    @Override
    public void guardar(Usuario nuevoUsuario) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(serializarUsuario(nuevoUsuario));
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al escribir usuario: " + e.getMessage());
        }
    }
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

    @Override
    public void eliminar(String username) {
        List<Usuario> usuarios = obtenerTodos();
        usuarios.removeIf(u -> u.getUsername().equals(username));
        reescribirArchivo(usuarios);
    }

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


    @Override
    public void crear(Usuario usuario) {
        guardar(usuario);
    }


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



    private String serializarUsuario(Usuario u) {
        return String.join(",",
                u.getUsername(),
                u.getContrasenia(),
                u.getRol().name(),
                u.getNombre() != null ? u.getNombre() : "",
                u.getCorreo() != null ? u.getCorreo() : ""
        );
    }

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



