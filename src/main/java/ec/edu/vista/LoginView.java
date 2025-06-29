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
        PanelPrincipal = new JPanel();
        PanelPrincipal.setLayout(new GridLayout(5, 2));


        setTitle("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        PanelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblTitulo = new JLabel("Ingrese un usuario");
        lblIdioma = new JLabel("Ingrese un idioma");
        CBoxIdioma = new JComboBox<>(new String[]{"Espanol", "Ingles", "Frances"});
        lblUsuario = new JLabel("Ingrese un usuario");
        TxtUsuario = new JTextField(10);
        LblContraseña= new JLabel("Contrasena");
        TxtContraseña = new JPasswordField(10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        PanelPrincipal.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        PanelPrincipal.add(lblIdioma, gbc);

        gbc.gridx = 1;
        PanelPrincipal.add(CBoxIdioma, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        PanelPrincipal.add(lblUsuario, gbc);

        gbc.gridx = 1;
        PanelPrincipal.add(TxtUsuario, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        PanelPrincipal.add(LblContraseña, gbc);

        gbc.gridx = 1;
        PanelPrincipal.add(TxtContraseña, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(BtnIngresar);
        panelBotones.add(BtnRegistrarse);
        PanelPrincipal.add(panelBotones, gbc);

        setContentPane(PanelPrincipal);
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
