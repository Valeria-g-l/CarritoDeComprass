package ec.edu.controlador;

import ec.edu.dao.UsuarioDAO;
import ec.edu.modelo.Rol;
import ec.edu.modelo.Usuario;
import ec.edu.util.MensajeInternacionalizacionHandler;
import ec.edu.vista.PreguntasSeguridadView;
import ec.edu.vista.RegistrarUsuarioView;
import ec.edu.excepciones.CedulaInvalidaException;
import ec.edu.excepciones.ContrasenaInvalidaException;

import javax.swing.*;

public class RegistroController {
    private UsuarioDAO usuarioDAO;
    private RegistrarUsuarioView registroView;
    private UsuarioController usuarioController;
    private MensajeInternacionalizacionHandler mensajeHandler;

    /**
     * Constructor del controlador de registro.
     *
     * @param usuarioDAO         DAO para gestión de usuarios.
     * @param registroView       Vista Swing para el registro de usuario.
     * @param usuarioController  Controlador general de usuario.
     * @param handler            Manejador de internacionalización.
     */


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
    /**
     * Configura los eventos de la vista de registro,
     * incluyendo el botón de registro de usuario.
     */
    private void configurarEventos() {
        registroView.getBtnRegistrarse().addActionListener(e -> registrarUsuario());
    }
    /**
     * Realiza el registro del usuario validando todos los campos ingresados.
     * Lanza excepciones si se encuentran errores en formato o lógica de negocio.
     * En caso exitoso, redirige a preguntas de seguridad.
     */
    private void registrarUsuario() {
        String username = registroView.getTxtNombre().getText().trim();
        String contrasena = new String(registroView.getTxtContraseña().getPassword()).trim();
        String nombreCompleto = registroView.getTxtNombre().getText().trim();
        String telefono = registroView.getTxtTelefono().getText().trim();
        String correo = registroView.getTxtCorreo().getText().trim();

        try {
            if (username.isEmpty() || contrasena.isEmpty() || nombreCompleto.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
                throw new IllegalArgumentException("Todos los campos son obligatorios.");
            }
            if (username.isEmpty() || contrasena.isEmpty()) {
                throw new IllegalArgumentException("Completa todos los campos.");
            }

            if (!Usuario.esCedulaValida(username)) {
                System.out.println("Cédula inválida, deteniendo flujo.");
                throw new CedulaInvalidaException("La cédula ingresada no es válida.");
            }

            if (!Usuario.esContrasenaValida(contrasena)) {
                System.out.println("Contraseña inválida, deteniendo flujo.");
                throw new ContrasenaInvalidaException("Contraseña débil.");
            }

            if (usuarioDAO.buscarPorUsername(username) != null) {
                JOptionPane.showMessageDialog(registroView, "Ya existe un usuario con esa cédula.", "Duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                throw new IllegalArgumentException("El correo electrónico no tiene formato válido.");
            }

            if (!telefono.matches("\\d{10}")) {
                throw new IllegalArgumentException("El número de teléfono debe tener 10 dígitos.");
            }


            System.out.println("Validación aprobada, ahora sí se guarda el usuario.");
            Usuario nuevoUsuario = new Usuario(username, contrasena, Rol.USUARIO);
            usuarioDAO.guardar(nuevoUsuario);
            usuarioController.setUsuarioEnProceso(nuevoUsuario);


            PreguntasSeguridadView preguntasView = new PreguntasSeguridadView(
                    mensajeHandler, usuarioController, "registro"
            );
            preguntasView.setVisible(true);
            registroView.dispose();

        } catch (CedulaInvalidaException | ContrasenaInvalidaException | IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(registroView, ex.getMessage(), "Validación", JOptionPane.ERROR_MESSAGE);
            System.out.println("EXCEPCIÓN INTERCEPTADA: " + ex.getMessage());
            return;
        }
    }


    /**
     * Devuelve el manejador de mensajes internacionalizados.
     *
     * @return el manejador de internacionalización usado por el controlador
     */
    public MensajeInternacionalizacionHandler getMensajeHandler() {
        return mensajeHandler;
    }

}

