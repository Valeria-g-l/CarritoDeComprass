package ec.edu.controlador;

import ec.edu.dao.UsuarioDAO;
import ec.edu.modelo.Usuario;
import ec.edu.vista.*;

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
    private Usuario usuarioEnProceso;

    public void setUsuarioEnProceso(Usuario usuario) {
        this.usuarioEnProceso = usuario;
    }
    public void setPreguntasSeguridadActual(String r1, String r2, String r3, String p1, String p2, String p3) {
        usuarioEnProceso.setPregunta1(p1);
        usuarioEnProceso.setRespuesta1(r1);
        usuarioEnProceso.setPregunta2(p2);
        usuarioEnProceso.setRespuesta2(r2);
        usuarioEnProceso.setPregunta3(p3);
        usuarioEnProceso.setRespuesta3(r3);
    }

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
            RegistrarUsuarioView registroView = new RegistrarUsuarioView(this);
            registroView.setVisible(true);
        });
        loginView.getBtnOlvideContrasena().addActionListener(ev -> abrirPreguntasRecuperacion());
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


    public boolean verificarPreguntas(String p1, String r1, String p2, String r2, String p3, String r3) {
        return p1.equals(usuarioEnProceso.getPregunta1()) && r1.equalsIgnoreCase(usuarioEnProceso.getRespuesta1()) &&
                p2.equals(usuarioEnProceso.getPregunta2()) && r2.equalsIgnoreCase(usuarioEnProceso.getRespuesta2()) &&
                p3.equals(usuarioEnProceso.getPregunta3()) && r3.equalsIgnoreCase(usuarioEnProceso.getRespuesta3());
    }
    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public Usuario getUsuarioEnProceso() {
        return usuarioEnProceso;
    }
    public void abrirPreguntasRecuperacion() {
        String username = JOptionPane.showInputDialog(null, "Ingresa tu nombre de usuario para recuperar la contraseña:");

        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nombre de usuario requerido.");
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        if (usuario == null) {
            JOptionPane.showMessageDialog(null, "No se encontró el usuario.");
            return;
        }


        this.usuarioEnProceso = usuario;

        PreguntasSeguridadView preguntasView = new PreguntasSeguridadView(
                mensajes, this, "recuperacion"
        );

        preguntasView.setVisible(true);
    }
    public void registrarUsuarioConPreguntas(Usuario usuario) {
        usuarioDAO.guardar(usuario);
        setUsuarioEnProceso(usuario);
        PreguntasSeguridadView preguntasView = new PreguntasSeguridadView(mensajes, this, "registro");
        preguntasView.setVisible(true);
    }




}
