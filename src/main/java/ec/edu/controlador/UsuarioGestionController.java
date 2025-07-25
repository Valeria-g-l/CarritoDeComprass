package ec.edu.controlador;

import ec.edu.modelo.Rol;
import ec.edu.modelo.Usuario;
import ec.edu.dao.UsuarioDAO;
import ec.edu.vista.UsuarioGestionView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class UsuarioGestionController {
    private final UsuarioDAO usuarioDAO;
    private final UsuarioGestionView vista;

    public UsuarioGestionController(UsuarioDAO usuarioDAO, UsuarioGestionView vista) {
        this.usuarioDAO = usuarioDAO;
        this.vista = vista;

        inicializarEventos();
        cargarUsuariosEnTabla();
    }

    private void inicializarEventos() {

        vista.getBtnCrear().addActionListener(e -> {
            String nombre = vista.getTxtNombre().getText().trim();
            String correo = vista.getTxtCorreo().getText().trim();
            String username = vista.getTxtUsername().getText().trim();
            String contrasena = new String(vista.getPaswContrasena().getPassword()).trim();

            if (nombre.isEmpty() || correo.isEmpty() || username.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios.");
                return;
            }

            if (usuarioDAO.buscarPorUsername(username) != null) {
                JOptionPane.showMessageDialog(vista, "El nombre de usuario ya existe.");
                return;
            }

            Usuario nuevo = new Usuario();
            nuevo.setNombre(nombre);
            nuevo.setCorreo(correo);
            nuevo.setUsername(username);
            nuevo.setContrasenia(contrasena);
            nuevo.setRol(Rol.USUARIO);

            usuarioDAO.guardar(nuevo);
            JOptionPane.showMessageDialog(vista, "Usuario creado exitosamente.");
            limpiarCamposCrear();
            cargarUsuariosEnTabla();
        });


        vista.getBtnBuscar().addActionListener(e -> {
            String username = vista.getTxtUsername1().getText().trim();

            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Ingresa un nombre de usuario.");
                return;
            }

            Usuario usuario = usuarioDAO.buscarPorUsername(username);
            if (usuario != null) {
                String info = "<html>"
                        + "<b>Nombre:</b> " + usuario.getNombre() + "<br>"
                        + "<b>Correo:</b> " + usuario.getCorreo() + "<br>"
                        + "<b>Username:</b> " + usuario.getUsername() + "<br>"
                        + "<b>Rol:</b> " + usuario.getRol()
                        + "</html>";
                vista.getLblRespuesta().setText(info);
            } else {
                vista.getLblRespuesta().setText("Usuario no encontrado.");
            }
        });


        vista.getBtnCancelar2().addActionListener(e -> {
            vista.getTxtUsername1().setText("");
            vista.getLblRespuesta().setText("");
        });


        vista.getBtnCancelar().addActionListener(e -> {
            vista.getTabbedPane().setSelectedIndex(0);
        });


        vista.getBtnEditar().addActionListener(e -> {
            int fila = vista.getTablaUsuarios().getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(vista, "Selecciona un usuario para editar.");
                return;
            }

            DefaultTableModel modelo = (DefaultTableModel) vista.getTablaUsuarios().getModel();
            String username = modelo.getValueAt(fila, 2).toString();

            Usuario usuario = usuarioDAO.buscarPorUsername(username);
            if (usuario == null) {
                JOptionPane.showMessageDialog(vista, "El usuario ya no existe.");
                return;
            }

            String nuevoNombre = JOptionPane.showInputDialog(vista, "Nuevo nombre:", usuario.getNombre());
            String nuevoCorreo = JOptionPane.showInputDialog(vista, "Nuevo correo:", usuario.getCorreo());

            if (nuevoNombre != null && nuevoCorreo != null) {
                usuario.setNombre(nuevoNombre);
                usuario.setCorreo(nuevoCorreo);
                usuarioDAO.actualizar(usuario);
                JOptionPane.showMessageDialog(vista, "Usuario actualizado.");
                cargarUsuariosEnTabla();
            }
        });


        vista.getBtnEliminar().addActionListener(e -> {
            int fila = vista.getTablaUsuarios().getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(vista, "Selecciona un usuario para eliminar.");
                return;
            }

            DefaultTableModel modelo = (DefaultTableModel) vista.getTablaUsuarios().getModel();
            String username = modelo.getValueAt(fila, 2).toString();

            int confirmacion = JOptionPane.showConfirmDialog(
                    vista,
                    "¿Estás segura de eliminar al usuario '" + username + "'?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                Usuario usuario = usuarioDAO.buscarPorUsername(username);
                if (usuario != null) {
                    usuarioDAO.eliminar(usuario);
                    JOptionPane.showMessageDialog(vista, "Usuario eliminado.");
                    cargarUsuariosEnTabla();
                } else {
                    JOptionPane.showMessageDialog(vista, "El usuario ya no existe.");
                }
            }
        });
    }

    private void cargarUsuariosEnTabla() {
        List<Usuario> usuarios = usuarioDAO.obtenerTodos();
        vista.cargarUsuariosEnTabla(usuarios);
    }

    private void limpiarCamposCrear() {
        vista.getTxtNombre().setText("");
        vista.getTxtCorreo().setText("");
        vista.getTxtUsername().setText("");
        vista.getPaswContrasena().setText("");
    }
}
