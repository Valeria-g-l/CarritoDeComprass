package ec.edu.controlador;

import ec.edu.dao.UsuarioDAO;
import ec.edu.modelo.Usuario;
import ec.edu.util.MensajeInternacionalizacionHandler;
import ec.edu.vista.*;

import javax.swing.*;
import java.util.Locale;

public class UsuarioController {

    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final LoginView loginView;
    private MenuPrincipalView menuPrincipalView;
    private Usuario usuarioAutenticado;
    private CambiarContrasenaView cambiarContraseniaView;
    private Usuario usuarioEnProceso;
    private final MensajeInternacionalizacionHandler mensajeHandler;

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


    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView, MensajeInternacionalizacionHandler handler) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.menuPrincipalView = null;
        this.usuario = null;
        this.mensajeHandler = handler;
        configurarEventosEnVistas();
    }


    public UsuarioController(UsuarioDAO usuarioDAO, MenuPrincipalView menuPrincipalView, Usuario usuarioAutenticado, MensajeInternacionalizacionHandler handler) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = null;
        this.menuPrincipalView = menuPrincipalView;
        this.usuarioAutenticado = usuarioAutenticado;
        this.mensajeHandler = handler;
        configurarEventosMenuPrincipal();
    }

    private void configurarEventosEnVistas() {
        loginView.getBtnIngresar().addActionListener(e -> autenticar());

        loginView.getBtnRegistrarse().addActionListener(e -> {
            RegistrarUsuarioView registroView = new RegistrarUsuarioView(this, mensajeHandler);
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

        usuario = usuarioDAO.autenticar(username, contrasenia);
        if (usuario == null) {
            loginView.mostrarMensaje(mensajeHandler.get("login.error_usuario_contraseña"));
        } else {
            usuarioAutenticado = usuario;
            loginView.dispose();
        }
    }

    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public void mostrarCambiarContrasenia() {
        if (menuPrincipalView == null) return;

        if (cambiarContraseniaView == null) {
            cambiarContraseniaView = new CambiarContrasenaView(Main.mensajeHandler);
            menuPrincipalView.getjDesktopPane().add(cambiarContraseniaView);
            cambiarContraseniaView.setVisible(true);

            cambiarContraseniaView.getBtnGuardar().addActionListener(e -> cambiarContrasenia());
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
        String username = JOptionPane.showInputDialog(null, mensajeHandler.get("login.ingresa_usuario"));

        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, mensajeHandler.get("login.usuario_requerido"));
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        if (usuario == null) {
            JOptionPane.showMessageDialog(null, mensajeHandler.get("login.usuario_no_encontrado"));
            return;
        }

        this.usuarioEnProceso = usuario;

        PreguntasSeguridadView preguntasView = new PreguntasSeguridadView(mensajeHandler, this, "recuperacion");
        preguntasView.setVisible(true);
    }

    public void registrarUsuarioConPreguntas(Usuario usuario) {
        usuarioDAO.guardar(usuario);
        setUsuarioEnProceso(usuario);

        PreguntasSeguridadView preguntasView = new PreguntasSeguridadView(mensajeHandler, this, "registro");
        preguntasView.setVisible(true);
    }

    public MensajeInternacionalizacionHandler getMensajeHandler() {
        return Main.mensajeHandler;
    }


}
