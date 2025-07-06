package ec.edu.vista;

import ec.edu.util.ActualizablePorIdioma;
import ec.edu.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginView extends JFrame implements ActualizablePorIdioma {

    private MensajeInternacionalizacionHandler mensajeHandler;
    private JPanel PanelPrincipal;
    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JTextField TxtUsuario;
    private JLabel LblContraseña;
    private JPasswordField TxtContraseña;
    private JButton BtnIngresar;
    private JButton BtnRegistrarse;
    private JButton BtnOlvideContrasena;
    private JMenuBar MenuBar;




    public LoginView(MensajeInternacionalizacionHandler handler) {

        JMenuBar menuBar = new JMenuBar();

        this.mensajeHandler = Main.mensajeHandler;
        $$$setupUI$$$();
        actualizarTextos(Main.mensajeHandler.getBundle());
        setTitle("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(PanelPrincipal, BorderLayout.CENTER);
        setJMenuBar(menuBar);

        JMenu menuIdioma = new JMenu("Idioma");


        JMenuItem itemEspanol = new JMenuItem("Español");
        JMenuItem itemIngles = new JMenuItem("English");
        JMenuItem itemFrench = new JMenuItem("French");
        JMenuItem itemPortuguese = new JMenuItem("Portuguese");

        menuIdioma.add(itemEspanol);
        menuIdioma.add(itemIngles);
        menuIdioma.add(itemFrench);
        menuIdioma.add(itemPortuguese);
        menuBar.add(menuIdioma);


        itemEspanol.addActionListener(e -> {
            mensajeHandler.setLocale(new Locale("es", "EC"), "messages");
            actualizarTextos(mensajeHandler.getBundle());
        });

        itemIngles.addActionListener(e -> {
            mensajeHandler.setLocale(new Locale("en", "US"), "messages");
            actualizarTextos(mensajeHandler.getBundle());
        });
        itemFrench.addActionListener(e -> {
            mensajeHandler.setLocale(new Locale("fr", "FR"), "messages");
            actualizarTextos(mensajeHandler.getBundle());
        });
        itemPortuguese.addActionListener(e -> {
            mensajeHandler.setLocale(new Locale("pt", "PT"), "messages");
            actualizarTextos(mensajeHandler.getBundle());
        });


        ImageIcon iconIngresar = new ImageIcon(getClass().getResource("/imagenes/check.png"));
        BtnIngresar.setIcon(new ImageIcon(iconIngresar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon iconRegistrarse = new ImageIcon(getClass().getResource("/Imagenes/user-add.png"));
        BtnRegistrarse.setIcon(new ImageIcon(iconRegistrarse.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon iconOlvide = new ImageIcon(getClass().getResource("/Imagenes/exclamation.png"));
        BtnOlvideContrasena.setIcon(new ImageIcon(iconOlvide.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
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
    public JButton getBtnOlvideContrasena() {
        return BtnOlvideContrasena;
    }
    public void setBtnOlvideContrasena(JButton btnOlvideContrasena) {
        BtnOlvideContrasena = btnOlvideContrasena;
    }



    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void actualizarTextos(ResourceBundle Bundle) {
        lblUsuario.setText(mensajeHandler.get("login.usuario"));
        LblContraseña.setText(mensajeHandler.get("login.contrasena"));
        BtnIngresar.setText(mensajeHandler.get("login.boton"));
        BtnRegistrarse.setText(mensajeHandler.get("login.registro"));
        BtnOlvideContrasena.setText(mensajeHandler.get("login.olvide"));
    }

}

