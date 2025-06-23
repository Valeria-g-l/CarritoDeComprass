package ec.edu.ups.vista;

import javax.swing.*;

public class LoginView extends JFrame {
    private JTextField txtUsuario;
    private JTextField txtContraseña;
    private JButton BtnIngresar;
    private JPanel PanelPrincipal;
    private JPanel PanelSecundario;
    private JPanel PanelTerciario;
    private JLabel lblTitulo;
    private JLabel LblUsuario;
    private JLabel LblContraseña;
    private JButton BtnRegistrarse;

    public LoginView() {

        setContentPane(PanelPrincipal);
        setTitle("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

    public JPanel getPanelPrincipal() {
        return PanelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        PanelPrincipal = panelPrincipal;
    }

    public JPanel getPanelSecundario() {
        return PanelSecundario;
    }
    public void setPanelSecundario(JPanel panelSecundario) {
        PanelSecundario = panelSecundario;
    }
    public JPanel getPanelTerciario() {
        return PanelTerciario;
    }
    public void setPanelTerciario(JPanel panelTerciario) {
        PanelTerciario = panelTerciario;
    }
   public JLabel getLblTitulo() {
        return lblTitulo;
   }
   public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
   }
   public JLabel getLblUsuario() {
        return LblUsuario;
   }
   public void setLblUsuario(JLabel lblUsuario) {
        this.LblUsuario = lblUsuario;
   }
   public JTextField getTxtUsuario() {
        return txtUsuario;
   }
   public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
   }
   public JLabel getLblContraseña(){
        return LblContraseña;
   }
   public void setLblContraseña(JLabel lblContraseña){
        this.LblContraseña= lblContraseña;
   }
   public JTextField getTxtContraseña(){
        return txtContraseña;
   }
   public void setTxtContraseña(JTextField txtContraseña){
        this.txtContraseña= txtContraseña;
   }
   public JButton getBtnIngresar() {
        return BtnIngresar;
   }
   public void setBtnIngresar(JButton btnIngresar) {
        this.BtnIngresar = btnIngresar;
   }
   public JButton getBtnRegistrarse() {
        return BtnRegistrarse;
   }
   public void setBtnRegistrarse(JButton btnRegistrarse) {
        this.BtnRegistrarse = btnRegistrarse;
   }
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
