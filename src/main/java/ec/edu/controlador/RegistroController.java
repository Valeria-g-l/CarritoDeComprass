package ec.edu.controlador;

import ec.edu.dao.UsuarioDAO;
import ec.edu.modelo.Rol;
import ec.edu.modelo.Usuario;
import ec.edu.util.MensajeInternacionalizacionHandler;
import ec.edu.vista.PreguntasSeguridadView;
import ec.edu.vista.RegistrarUsuarioView;

public class RegistroController {
    private UsuarioDAO usuarioDAO;
    private RegistrarUsuarioView registroView;
    private UsuarioController usuarioController;
    private MensajeInternacionalizacionHandler mensajeHandler;


    public RegistroController(UsuarioDAO usuarioDAO,
                              RegistrarUsuarioView registroView,
                              UsuarioController usuarioController,
                              MensajeInternacionalizacionHandler handler) {
        this.usuarioDAO = usuarioDAO;
        this.registroView = registroView;
        this.usuarioController = usuarioController;
        this.mensajeHandler = handler;
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
                mensajeHandler,
                usuarioController,
                "registro"
        );
        preguntasView.setVisible(true);
        registroView.dispose();
    }
    public MensajeInternacionalizacionHandler getMensajeHandler() {
        return mensajeHandler;
    }

}

