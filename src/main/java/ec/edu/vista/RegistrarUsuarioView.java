package ec.edu.vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import ec.edu.controlador.UsuarioController;
import ec.edu.modelo.Rol;
import ec.edu.modelo.Usuario;

public class RegistrarUsuarioView extends JFrame {
    private JPanel PanelPrincipal;
    private JLabel LblTitulo;
    private JLabel LblNombre;
    private JTextField TxtNombre;
    private JLabel LblContraseña;
    private JPasswordField TxtContraseña;
    private JButton BtnRegistrarse;
    private UsuarioController usuarioController;

    public RegistrarUsuarioView(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
        setContentPane(PanelPrincipal);
        setTitle("Agregar Producto");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
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

}
