package ec.edu.controlador;

import ec.edu.dao.UsuarioDAO;
import ec.edu.modelo.Usuario;
import ec.edu.util.MensajeInternacionalizacionHandler;
import ec.edu.vista.*;

import javax.swing.*;
import java.util.List;
import java.util.Locale;
/**
 * Controlador principal para la gestión de usuarios.
 * Coordina autenticación, registro, modificación y recuperación de usuarios.
 * Facilita la navegación entre vistas Swing según el estado del usuario.
 *
 * Aplica lógica de validación de contraseñas, manejo de preguntas de seguridad,
 * y operaciones CRUD sobre el modelo Usuario. Utiliza internacionalización
 * para los mensajes mostrados en las interfaces gráficas.
 *
 * @author Valeria
 * @version 1.0
 */
public class UsuarioController {

    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final LoginView loginView;
    private MenuPrincipalView menuPrincipalView;
    private Usuario usuarioAutenticado;
    private CambiarContrasenaView cambiarContraseniaView;
    private Usuario usuarioEnProceso;
    private final MensajeInternacionalizacionHandler mensajeHandler;

    /**
     * Establece el usuario que está siendo procesado.
     *
     * @param usuario el usuario que está en proceso
     */
    public void setUsuarioEnProceso(Usuario usuario) {
        this.usuarioEnProceso = usuario;
    }
    /**
     * Asigna las preguntas y respuestas de seguridad al usuario en proceso.
     *
     * @param r1 respuesta para la pregunta 1
     * @param r2 respuesta para la pregunta 2
     * @param r3 respuesta para la pregunta 3
     * @param p1 pregunta 1
     * @param p2 pregunta 2
     * @param p3 pregunta 3
     */
    public void setPreguntasSeguridadActual(String r1, String r2, String r3, String p1, String p2, String p3) {
        usuarioEnProceso.setPregunta1(p1);
        usuarioEnProceso.setRespuesta1(r1);
        usuarioEnProceso.setPregunta2(p2);
        usuarioEnProceso.setRespuesta2(r2);
        usuarioEnProceso.setPregunta3(p3);
        usuarioEnProceso.setRespuesta3(r3);
    }

    /**
     * Constructor para el controlador de usuario que se utiliza durante el proceso de login.
     *
     * @param usuarioDAO  DAO para acceder y manipular datos de usuarios.
     * @param loginView   Vista de login que controla la autenticación del usuario.
     * @param handler     Manejador de mensajes para internacionalización.
     */
    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView, MensajeInternacionalizacionHandler handler) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.menuPrincipalView = null;
        this.usuario = null;
        this.mensajeHandler = handler;
        configurarEventosEnVistas();
    }


    /**
     * Constructor para sesión autenticada en el menú principal.
     *
     * @param usuarioDAO DAO de usuarios
     * @param menuPrincipalView vista del menú
     * @param usuarioAutenticado usuario que inició sesión
     * @param handler manejador de mensajes
     */
    public UsuarioController(UsuarioDAO usuarioDAO, MenuPrincipalView menuPrincipalView, Usuario usuarioAutenticado, MensajeInternacionalizacionHandler handler) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = null;
        this.menuPrincipalView = menuPrincipalView;
        this.usuarioAutenticado = usuarioAutenticado;
        this.mensajeHandler = handler;
        configurarEventosMenuPrincipal();
    }



    /**
     * Configura los eventos de la vista de login (ingresar, registrar, recuperar).
     */
    private void configurarEventosEnVistas() {
        loginView.getBtnIngresar().addActionListener(e -> autenticar());

        loginView.getBtnRegistrarse().addActionListener(e -> {
            RegistrarUsuarioView registroView = new RegistrarUsuarioView(this, mensajeHandler);
            new RegistroController(usuarioDAO, registroView, this, mensajeHandler);
            registroView.setVisible(true);
        });

        loginView.getBtnOlvideContrasena().addActionListener(ev -> abrirPreguntasRecuperacion());
    }
    /**
     * Configura la vista del menú principal (cambiar contraseña).
     */
    private void configurarEventosMenuPrincipal() {
        if (menuPrincipalView != null) {
            menuPrincipalView.getMenuItemCambiarContrasenia().addActionListener(e -> mostrarCambiarContrasenia());
        }
    }
    /**
     * Realiza la autenticación del usuario ingresado en la vista login.
     */
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
    /**
     * Devuelve el usuario autenticado actualmente.
     *
     * @return usuario autenticado
     */
    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }
    /**
     * Muestra la vista para cambiar la contraseña del usuario.
     */
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
    /**
     * Cambia la contraseña del usuario autenticado, si la actual es válida.
     */
    private void cambiarContrasenia() {
        String contraseniaActual = cambiarContraseniaView.getTxtContraseñaA().getText().trim();
        String contraseniaNueva = cambiarContraseniaView.getTxtContraseña().getText().trim();

        if (usuarioAutenticado == null) {
            JOptionPane.showMessageDialog(cambiarContraseniaView, "Error: Usuario no autenticado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!usuarioAutenticado.getContrasenia().equals(contraseniaActual)) {
            JOptionPane.showMessageDialog(cambiarContraseniaView, "La contraseña actual es incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!Usuario.esContrasenaValida(contraseniaNueva)) {
            JOptionPane.showMessageDialog(cambiarContraseniaView,
                    "La nueva contraseña es débil.\nDebe tener al menos 6 caracteres, una mayúscula, un número y un carácter especial.",
                    "Contraseña inválida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        usuarioAutenticado.setContrasenia(contraseniaNueva);
        usuarioDAO.actualizar(usuarioAutenticado);

        JOptionPane.showMessageDialog(cambiarContraseniaView, "Contraseña cambiada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        cambiarContraseniaView.dispose();
    }

    /**
     * Verifica que las preguntas y respuestas de seguridad coincidan.
     *
     * @return true si todas coinciden, false si alguna falla
     */
    public boolean verificarPreguntas(String p1, String r1, String p2, String r2, String p3, String r3) {
        return p1.equals(usuarioEnProceso.getPregunta1()) && r1.equalsIgnoreCase(usuarioEnProceso.getRespuesta1()) &&
                p2.equals(usuarioEnProceso.getPregunta2()) && r2.equalsIgnoreCase(usuarioEnProceso.getRespuesta2()) &&
                p3.equals(usuarioEnProceso.getPregunta3()) && r3.equalsIgnoreCase(usuarioEnProceso.getRespuesta3());
    }
    /**
     * Acceso al DAO de usuarios.
     *
     * @return instancia de UsuarioDAO
     */
    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }
    /**
     * Devuelve el usuario que está siendo procesado.
     *
     * @return el usuario en proceso
     */
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
    /**
     * Guarda un usuario y abre vista para preguntas de seguridad.
     *
     * @param usuario nuevo usuario registrado
     */
    public void registrarUsuarioConPreguntas(Usuario usuario) {
        usuarioDAO.guardar(usuario);
        setUsuarioEnProceso(usuario);

        PreguntasSeguridadView preguntasView = new PreguntasSeguridadView(mensajeHandler, this, "registro");
        preguntasView.setVisible(true);
    }
    /**
     * Crea un nuevo usuario si no existe otro con el mismo username.
     *
     * @param nuevoUsuario objeto usuario a crear
     * @return true si fue creado, false si ya existía
     */
    public boolean crearUsuario(Usuario nuevoUsuario) {
        if (usuarioDAO.buscarPorUsername(nuevoUsuario.getUsername()) != null) {
            return false;
        }
        usuarioDAO.guardar(nuevoUsuario);
        return true;
    }
    /**
     * Edita el usuario si existe en la base de datos.
     *
     * @param usuarioModificado objeto actualizado
     * @return true si fue editado correctamente
     */
    public boolean editarUsuario(Usuario usuarioModificado) {
        Usuario existente = usuarioDAO.buscarPorUsername(usuarioModificado.getUsername());
        if (existente == null) return false;

        usuarioDAO.actualizar(usuarioModificado);
        return true;
    }

    /**
     * Elimina un usuario por su username.
     *
     * @param username identificación del usuario
     * @return true si fue eliminado correctamente
     */
    public boolean eliminarUsuario(String username) {
        Usuario usuario = usuarioDAO.buscarPorUsername(username);
        if (usuario == null) return false;

        usuarioDAO.eliminar(usuario);
        return true;
    }
    public List<Usuario> listarUsuarios() {
        return usuarioDAO.obtenerTodos();
    }
    /**
     * Lista todos los usuarios registrados.
     *
     * @return lista de usuarios
     */
    public Usuario buscarUsuarioPorUsername(String username) {
        return usuarioDAO.buscarPorUsername(username);
    }



    public MensajeInternacionalizacionHandler getMensajeHandler() {
        return Main.mensajeHandler;
    }


}
