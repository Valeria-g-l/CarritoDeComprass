package ec.edu.vista;

import ec.edu.modelo.Usuario;
import ec.edu.util.ActualizablePorIdioma;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

public class UsuarioGestionView extends JInternalFrame implements ActualizablePorIdioma {
    private DefaultTableModel modelo;
    private JTabbedPane tabbedPane;
    private JPanel panel1;
    private JTextField TxtNombre;
    private JTextField TxtCorreo;
    private JTextField TxtUsername;
    private JPasswordField PaswContrasena;
    private JPanel Crear;
    private JPanel Buscar;
    private JTextField TxtUsername1;
    private JButton BtnBuscar;
    private JPanel Listar1;
    private JTable tablaUsuarios;
    private JButton BtnCrear;
    private JButton BtnEliminar;
    private JButton BtnEditar;
    private JButton BtnCancelar2;
    private JButton BtnCancelar3;
    private JButton BtnCancelar;
    private JLabel LblNombre;
    private JLabel LblCorreo;
    private JLabel LblUsername;
    private JLabel LblContrasena;
    private JLabel LblUsername1;
    private JLabel LblRespuesta;
    private JLabel LblNombre1;
    /**
     * Constructor para la vista de gestión de usuarios.
     * <p>
     * Configura las propiedades de la ventana, incluyendo si es cerrable, iconificable,
     * maximizable y redimensionable. También establece el título, tamaño, el panel principal
     * y hace visible la ventana.
     * </p>
     */
    public UsuarioGestionView() {
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Gestión de Usuarios");
        setSize(800, 600);
        setContentPane(panel1);
        setVisible(true);

        ImageIcon iconGuardar = new ImageIcon(getClass().getResource("/Imagenes/shield-check.png"));
        BtnCrear.setIcon(new ImageIcon(iconGuardar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon iconCancelar = new ImageIcon(getClass().getResource("/Imagenes/cross (1).png"));
        BtnCancelar.setIcon(new ImageIcon(iconCancelar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon iconCancelar3 = new ImageIcon(getClass().getResource("/Imagenes/cross (1).png"));
        BtnCancelar3.setIcon(new ImageIcon(iconCancelar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon iconBuscar = new ImageIcon(getClass().getResource("/Imagenes/search.png"));
        BtnBuscar.setIcon(new ImageIcon(iconCancelar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon iconCancelar2 = new ImageIcon(getClass().getResource("/Imagenes/cross (1).png"));
        BtnCancelar2.setIcon(new ImageIcon(iconCancelar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon iconEditar = new ImageIcon(getClass().getResource("/Imagenes/plus.png"));
        BtnEditar.setIcon(new ImageIcon(iconCancelar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon iconEliminar = new ImageIcon(getClass().getResource("/Imagenes/cross (1).png"));
        BtnEliminar.setIcon(new ImageIcon(iconCancelar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        modelo = new DefaultTableModel();
        Object[] columnas = { "Nombre", "Correo", "Username", "Password" };
        modelo.setColumnIdentifiers(columnas);
        tablaUsuarios.setModel(modelo);
    }


    public void cargarUsuariosEnTabla(List<Usuario> listaUsuarios) {
        DefaultTableModel modelo = (DefaultTableModel) tablaUsuarios.getModel();
        modelo.setRowCount(0);
        for (Usuario u : listaUsuarios) {
            modelo.addRow(new Object[]{
                     u.getNombre(), u.getCorreo(), u.getUsername(), u.getRol()
            });
        }
    }
    public void mostrarUsuarios(List<Usuario> usuarios) {
        DefaultTableModel modelo = (DefaultTableModel) tablaUsuarios.getModel();
        modelo.setRowCount(0);

        for (Usuario u : usuarios) {
            modelo.addRow(new Object[]{
                    u.getNombre(),
                    u.getCorreo(),
                    u.getUsername(),
                    u.getRol()
            });
        }
    }



    public JTextField getTxtNombre() {
        return TxtNombre;
    }

    public JTextField getTxtCorreo() {
        return TxtCorreo;
    }

    public JTextField getTxtUsername() {
        return TxtUsername;
    }

    public JPasswordField getPaswContrasena() {
        return PaswContrasena;
    }

    public JTextField getTxtUsername1() {
        return TxtUsername1;
    }

    public JButton getBtnBuscar() {
        return BtnBuscar;
    }

    public JTable getTablaUsuarios() {
        return tablaUsuarios;
    }

    public JButton getBtnCancelar2() {
        return BtnCancelar2;
    }

    public JButton getBtnCancelar3() {
        return BtnCancelar3;
    }

    public JButton getBtnCancelar() {
        return BtnCancelar;
    }

    public JLabel getLblRespuesta() {
        return LblRespuesta;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }
    public JButton getBtnCrear() {
        return BtnCrear;
    }
    public JButton getBtnEliminar() {
        return BtnEliminar;
    }
    public JButton getBtnEditar() {
        return BtnEditar;
    }

    public void actualizarTextos(ResourceBundle bundle) {
        setTitle(bundle.getString("usuario.titulo"));
        LblNombre1.setText(bundle.getString("usuario.nombre"));
        LblCorreo.setText(bundle.getString("usuario.correo"));
        LblUsername.setText(bundle.getString("usuario.username"));
        LblContrasena.setText(bundle.getString("usuario.contrasena"));
        LblUsername1.setText(bundle.getString("usuario.username1"));
        BtnCrear.setText(bundle.getString("usuario.crear"));
        BtnEditar.setText(bundle.getString("usuario.editar"));
        BtnEliminar.setText(bundle.getString("usuario.eliminar"));
        BtnBuscar.setText(bundle.getString("usuario.buscar"));
        BtnCancelar.setText(bundle.getString("usuario.cancelar"));
        BtnCancelar2.setText(bundle.getString("usuario.cancelar2"));
        BtnCancelar3.setText(bundle.getString("usuario.cancelar3"));
        tabbedPane.setTitleAt(0, bundle.getString("usuario.tab.listar"));
        tabbedPane.setTitleAt(1, bundle.getString("usuario.tab.registrar"));
        tabbedPane.setTitleAt(2, bundle.getString("usuario.tab.editar"));

    }

}
