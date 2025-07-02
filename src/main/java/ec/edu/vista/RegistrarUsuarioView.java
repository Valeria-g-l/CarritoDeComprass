package ec.edu.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ec.edu.controlador.UsuarioController;
import ec.edu.modelo.Rol;
import ec.edu.modelo.Usuario;
import ec.edu.util.MensajeInternacionalizacionHandler;

public class RegistrarUsuarioView extends JFrame {
    private JPanel PanelPrincipal;
    private JLabel LblTitulo;
    private JLabel LblNombre;
    private JTextField TxtNombre;
    private JLabel LblContraseña;
    private JPasswordField TxtContraseña;
    private JButton BtnRegistrarse;
    private JLabel LblNombreCompleto;
    private JTextField TxtNombreCompleto;
    private JLabel LblNacimiento;
    private JLabel LblTelefono;
    private JLabel LblCorreo;
    private JTextField TxtCorreo;
    private JTextField textField1;
    private JTextField TxtTelefono;
    private UsuarioController usuarioController;
    private MensajeInternacionalizacionHandler mensajeHandler;

    public RegistrarUsuarioView(UsuarioController usuarioController, MensajeInternacionalizacionHandler handler) {
        this.mensajeHandler = handler;
        this.usuarioController = usuarioController;
        actualizarTextos();
        setContentPane(PanelPrincipal);
        setTitle("Agregar Producto");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        ImageIcon iconRegistrarse = new ImageIcon(getClass().getResource("/imagenes/user-add.png"));
        BtnRegistrarse.setIcon(new ImageIcon(iconRegistrarse.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        BtnRegistrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });

    }
    private void registrarUsuario() {
        String nombre = TxtNombre.getText().trim();
        String contrasena = new String(TxtContraseña.getPassword()).trim();

        if (nombre.isEmpty() || contrasena.isEmpty()) {
            mostrarMensaje("Por favor, llena todos los campos.");
            return;
        }

        Usuario nuevoUsuario = new Usuario(nombre, contrasena, Rol.USUARIO);
        usuarioController.registrarUsuarioConPreguntas(nuevoUsuario);
        dispose();
    }



    public JPanel getPanelPrincipal() {
        return PanelPrincipal;
    }
    public void setPanelPrincipal(JPanel PanelPrincipal) {
        this.PanelPrincipal = PanelPrincipal;
    }
    public JTextField getTxtNombre() {
        return TxtNombre;
    }
    public void setTxtNombre(JTextField TxtNombre) {
        this.TxtNombre = TxtNombre;
    }
    public JPasswordField getTxtContraseña(){
        return TxtContraseña;
    }
    public JTextField getTxtTelefono() {
        return TxtTelefono;
    }

    public void setTxtTelefono(JTextField txtTelefono) {
        TxtTelefono = txtTelefono;
    }
    public void setTxtContraseña(JPasswordField TxtContraseña){
        this.TxtContraseña= TxtContraseña;
    }
    public JButton getBtnRegistrarse() {
        return BtnRegistrarse;
    }
    public void setBtnRegistrarse(JButton BtnRegistrarse) {
        this.BtnRegistrarse = BtnRegistrarse;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void actualizarTextos() {
        LblTitulo.setText(mensajeHandler.get("titulo"));
        LblNombre.setText(mensajeHandler.get("nombre"));
        LblContraseña.setText(mensajeHandler.get("contrasena"));
        LblNombreCompleto.setText(mensajeHandler.get("nombreCompleto"));
        LblNacimiento.setText(mensajeHandler.get("nacimiento"));
        LblTelefono.setText(mensajeHandler.get("genero"));
        LblCorreo.setText(mensajeHandler.get("correo"));
        LblContraseña.setText(mensajeHandler.get("contrasena"));
        BtnRegistrarse.setText(mensajeHandler.get("registrar"));
    }

}
