package ec.edu.vista;

import javax.swing.*;
import java.util.ResourceBundle;

public class RegistrarUsuarioView extends JFrame {
    private JPanel PanelPrincipal;
    private JLabel LblTitulo;
    private JLabel LblNombre;
    private JTextField TxtNombre;
    private JLabel LblContraseña;
    private JPasswordField TxtContraseña;
    private JButton BtnRegistrarse;

    public RegistrarUsuarioView() {
        setContentPane(PanelPrincipal);
        setTitle("Agregar Producto");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
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
