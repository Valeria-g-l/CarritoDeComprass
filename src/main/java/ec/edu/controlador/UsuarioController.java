package ec.edu.controlador;

import ec.edu.dao.UsuarioDAO;
import ec.edu.modelo.Usuario;
import ec.edu.vista.CambiarContrasenaView;
import ec.edu.vista.LoginView;
import ec.edu.vista.MenuPrincipalView;
import ec.edu.vista.RegistrarUsuarioView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

public class UsuarioController {

    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final LoginView loginView;
    private MenuPrincipalView menuPrincipalView;
    private ResourceBundle mensajes;
    private Usuario usuarioAutenticado;
    private CambiarContrasenaView cambiarContraseniaView;


    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.menuPrincipalView = null;
        this.usuario = null;
        configurarEventosEnVistas();
    }


    public UsuarioController(UsuarioDAO usuarioDAO, MenuPrincipalView menuPrincipalView, Usuario usuarioAutenticado) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = null;
        this.menuPrincipalView = menuPrincipalView;
        this.usuarioAutenticado = usuarioAutenticado;
        configurarEventosMenuPrincipal();
    }


    private void configurarEventosEnVistas() {
        loginView.getBtnIngresar().addActionListener(e -> autenticar());

        loginView.getBtnRegistrarse().addActionListener(e -> {
            RegistrarUsuarioView registroView = new RegistrarUsuarioView();
            new RegistroController(usuarioDAO, registroView);
            registroView.setVisible(true);
        });
    }


    private void configurarEventosMenuPrincipal() {
        if (menuPrincipalView != null) {
            menuPrincipalView.getMenuItemCambiarContrasenia().addActionListener(e -> mostrarCambiarContrasenia());
        }
    }

    private void autenticar() {
        String username = loginView.getTxtUsuario().getText();
        String contrasenia = new String(loginView.getTxtContraseña().getPassword());

        String idiomaSeleccionado = loginView.getIdiomaSeleccionado();
        Locale locale = new Locale(idiomaSeleccionado);
        mensajes = ResourceBundle.getBundle("messages", locale);

        usuario = usuarioDAO.autenticar(username, contrasenia);
        if (usuario == null) {
            loginView.mostrarMensaje(mensajes.getString("login.error_usuario_contraseña"));
        } else {
            usuarioAutenticado = usuario;
            loginView.dispose();
        }
    }

    public ResourceBundle getMensajes() {
        return mensajes;
    }

    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public void mostrarCambiarContrasenia() {
        System.out.println("Entró a mostrarCambiarContrasenia()");
        if (menuPrincipalView == null) {
            System.out.println("Error: menuPrincipalView es null, no se puede mostrar la ventana interna");
            return;
        }

        if (cambiarContraseniaView == null) {
            cambiarContraseniaView = new CambiarContrasenaView();
            menuPrincipalView.getjDesktopPane().add(cambiarContraseniaView);
            cambiarContraseniaView.setVisible(true);

            cambiarContraseniaView.getBtnGuardar().addActionListener(e -> {
                System.out.println("Click en guardar contraseña");
                cambiarContrasenia();
            });
        } else {
            cambiarContraseniaView.setVisible(true);
            cambiarContraseniaView.toFront();
        }
    }

    private void cambiarContrasenia() {
        String contraseniaActual = cambiarContraseniaView.getTxtContraseñaA().getText();
        String contraseniaNueva = cambiarContraseniaView.getTxtContraseña().getText();

        if (usuarioAutenticado == null) {
            JOptionPane.showMessageDialog(cambiarContraseniaView, "Error: Usuario no autenticado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!usuarioAutenticado.getContrasenia().equals(contraseniaActual)) {
            JOptionPane.showMessageDialog(cambiarContraseniaView, "La contraseña actual es incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        usuarioAutenticado.setContrasenia(contraseniaNueva);
        usuarioDAO.actualizar(usuarioAutenticado);

        JOptionPane.showMessageDialog(cambiarContraseniaView, "Contraseña cambiada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        cambiarContraseniaView.dispose();
    }
}
