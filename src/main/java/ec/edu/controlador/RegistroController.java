package ec.edu.controlador;

import ec.edu.dao.UsuarioDAO;
import ec.edu.modelo.Rol;
import ec.edu.modelo.Usuario;
import ec.edu.vista.PreguntasSeguridadView;
import ec.edu.vista.RegistrarUsuarioView;

public class RegistroController {
    private UsuarioDAO usuarioDAO;
    private RegistrarUsuarioView registroView;
    private UsuarioController usuarioController;


    public RegistroController(UsuarioDAO usuarioDAO, RegistrarUsuarioView registroView, UsuarioController usuarioController) {
        this.usuarioDAO = usuarioDAO;
        this.registroView = registroView;
        this.usuarioController = usuarioController;
        configurarEventos();
    }

    private void configurarEventos() {
        registroView.getBtnRegistrarse().addActionListener(e -> registrarUsuario());
    }

    private void registrarUsuario() {
        String username = registroView.getTxtNombre().getText();
        String contrasena = new String(registroView.getTxtContraseña().getPassword());

        Usuario nuevoUsuario = new Usuario(username, contrasena, Rol.USUARIO);

        System.out.println("Registrando usuario: " + nuevoUsuario);
        usuarioDAO.guardar(nuevoUsuario);

        PreguntasSeguridadView preguntasView = new PreguntasSeguridadView(
                usuarioController.getMensajes(), usuarioController, "registro"
        );
        preguntasView.setVisible(true);
        registroView.dispose();
    }

}

