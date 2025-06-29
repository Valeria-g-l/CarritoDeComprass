package ec.edu.controlador;

import ec.edu.dao.UsuarioDAO;
import ec.edu.modelo.Usuario;
import ec.edu.vista.LoginView;
import ec.edu.vista.RegistrarUsuarioView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

public class UsuarioController {

    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final LoginView loginView;
    private ResourceBundle mensajes;

    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.usuario = null;
        configurarEventosEnVistas();
    }

    private void configurarEventosEnVistas() {
        loginView.getBtnIngresar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticar();
            }
        });


        loginView.getBtnRegistrarse().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrarUsuarioView registroView = new RegistrarUsuarioView(); // tu nueva ventana
                new RegistroController(usuarioDAO, registroView); // y su controlador
                registroView.setVisible(true);
            }
        });
    }



    private void autenticar() {
        String username = loginView.getTxtUsuario().getText();
        String contrasenia = loginView.getTxtContraseña().getText();


        String idiomaSeleccionado = loginView.getIdiomaSeleccionado();
        Locale locale;
        switch (idiomaSeleccionado) {
            case "English":
                locale = new Locale("en");
                break;
            case "Français":
                locale = new Locale("fr");
                break;
            default:
                locale = new Locale("es");
        }
        mensajes = ResourceBundle.getBundle("messages", locale);

        usuario = usuarioDAO.autenticar(username, contrasenia);
        if (usuario == null) {
            loginView.mostrarMensaje(mensajes.getString("login.error_usuario_contraseña"));
        } else {
            loginView.dispose();

        }
    }

    public ResourceBundle getMensajes() {
        return mensajes;
    }


    public Usuario getUsuarioAutenticado() {
        return usuario;
    }

}
