package ec.edu.vista;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JPanel PanelPrincipal;
    private JLabel lblTitulo;
    private JLabel lblIdioma;
    private JComboBox CBoxIdioma;
    private JLabel lblUsuario;
    private JTextField TxtUsuario;
    private JLabel LblContraseña;
    private JPasswordField TxtContraseña;
    private JButton BtnIngresar;
    private JButton BtnRegistrarse;


    public LoginView() {
        $$$setupUI$$$();
        setTitle("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setContentPane(PanelPrincipal);
    }

    private void $$$setupUI$$$() {

    }


    public JPanel getPanelPrincipal() {
        return PanelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        PanelPrincipal = panelPrincipal;
    }
    public JLabel getLblTitulo() {
        return lblTitulo;
    }
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }
    public JLabel getLblIdioma() {
        return lblIdioma;
    }
    public void setLblIdioma(JLabel lblIdioma) {
        this.lblIdioma = lblIdioma;
    }
    public JComboBox getCBoxIdioma() {
        return CBoxIdioma;
    }
    public void setCBoxIdioma(JComboBox cBoxIdioma) {
        CBoxIdioma = cBoxIdioma;
    }
    public JLabel getLblUsuario() {

        return lblUsuario;
    }
    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }
    public JTextField getTxtUsuario() {
        return TxtUsuario;
    }
    public void setTxtUsuario(JTextField txtUsuario) {
        this.TxtUsuario = txtUsuario;
    }
    public JLabel getLblContraseña() {
        return LblContraseña;
    }
    public void setLblContraseña(JLabel lblContraseña) {
        this.LblContraseña = lblContraseña;
    }
    public JPasswordField getTxtContraseña() {
        return TxtContraseña;
    }

    public JButton getBtnIngresar() {
        return BtnIngresar;
    }
    public void setBtnIngresar(JButton btnIngresar) {
        BtnIngresar = btnIngresar;
    }
    public JButton getBtnRegistrarse() {
        return BtnRegistrarse;
    }
    public void setBtnRegistrarse(JButton btnRegistrarse) {
        BtnRegistrarse = btnRegistrarse;
    }


    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

}

