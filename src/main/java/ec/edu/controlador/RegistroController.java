package ec.edu.controlador;

import ec.edu.dao.UsuarioDAO;
import ec.edu.modelo.Rol;
import ec.edu.modelo.Usuario;
import ec.edu.vista.RegistrarUsuarioView;

public class RegistroController {
    private UsuarioDAO usuarioDAO;
    private RegistrarUsuarioView registroView;

    public RegistroController(UsuarioDAO usuarioDAO, RegistrarUsuarioView registroView) {
        this.usuarioDAO = usuarioDAO;
        this.registroView = registroView;
        configurarEventos();
    }

    private void configurarEventos() {
        registroView.getBtnRegistrarse().addActionListener(e -> registrarUsuario());
    }

    private void registrarUsuario() {
        String username = registroView.getTxtNombre().getText();
        String contrasena = new String(registroView.getTxtContraseña().getPassword());
        Rol rol = Rol.USUARIO; // o lo que elijas

        Usuario nuevoUsuario = new Usuario(username, contrasena, rol);
        usuarioDAO.guardar(nuevoUsuario);
        registroView.mostrarMensaje("¡Usuario registrado con éxito!");
        registroView.dispose();
    }
}

