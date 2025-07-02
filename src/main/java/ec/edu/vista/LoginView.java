package ec.edu.vista;

import ec.edu.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private MensajeInternacionalizacionHandler mensajeHandler;
    private JPanel PanelPrincipal;
    private JLabel lblTitulo;
    private JLabel lblIdioma;
    private JComboBox<String> CBoxIdioma;
    private JLabel lblUsuario;
    private JTextField TxtUsuario;
    private JLabel LblContraseña;
    private JPasswordField TxtContraseña;
    private JButton BtnIngresar;
    private JButton BtnRegistrarse;
    private JButton BtnOlvideContrasena;


    public LoginView(MensajeInternacionalizacionHandler handler) {
        this.mensajeHandler = handler;
        $$$setupUI$$$();
        actualizarTextos();
        setTitle("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setContentPane(PanelPrincipal);

        if (CBoxIdioma.getItemCount() == 0) {
            CBoxIdioma.addItem("Español");
            CBoxIdioma.addItem("English");
            CBoxIdioma.addItem("Français");
            CBoxIdioma.addItem("Português");
        }

        ImageIcon iconIngresar = new ImageIcon(getClass().getResource("/imagenes/check.png"));
        BtnIngresar.setIcon(new ImageIcon(iconIngresar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon iconRegistrarse = new ImageIcon(getClass().getResource("/Imagenes/user-add.png"));
        BtnRegistrarse.setIcon(new ImageIcon(iconRegistrarse.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon iconOlvide = new ImageIcon(getClass().getResource("/Imagenes/exclamation.png"));
        BtnOlvideContrasena.setIcon(new ImageIcon(iconOlvide.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        CBoxIdioma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccionado = CBoxIdioma.getSelectedItem().toString();
                switch (seleccionado) {
                    case "Español":
                        Main.mensajeHandler.setLenguaje("es", "EC");
                        break;
                    case "English":
                        Main.mensajeHandler.setLenguaje("en", "US");
                        break;
                    case "Français":
                        Main.mensajeHandler.setLenguaje("fr", "FR");
                        break;
                    case "Português":
                        Main.mensajeHandler.setLenguaje("pt", "PT");
                        break;
                }
                actualizarTextos();
            }
        });
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
    public JButton getBtnOlvideContrasena() {
        return BtnOlvideContrasena;
    }
    public void setBtnOlvideContrasena(JButton btnOlvideContrasena) {
        BtnOlvideContrasena = btnOlvideContrasena;
    }



    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void actualizarTextos() {
        lblUsuario.setText(mensajeHandler.get("login.usuario"));
        LblContraseña.setText(mensajeHandler.get("login.contrasena"));
        BtnIngresar.setText(mensajeHandler.get("login.boton"));
        lblIdioma.setText(mensajeHandler.get("login.idioma"));
        BtnRegistrarse.setText(mensajeHandler.get("login.registro"));
        BtnOlvideContrasena.setText(mensajeHandler.get("login.olvide"));
    }

}

