package ec.edu.vista;

import javax.swing.*;

public class CambiarContrasenaView extends JInternalFrame {
    private JPanel PanelPrincipal;
    private JLabel LblTitulo;
    private JTextField TxtContraseñaA;
    private JTextField TxtContraseña;
    private JButton BtnGuardar;
    private JLabel LblContraseña;
    private JLabel LblContraseñaA;

    public CambiarContrasenaView() {
        setContentPane(PanelPrincipal);
        setTitle("Productos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);

    }

    public JPanel getPanelPrincipal() {
        return PanelPrincipal;
    }
    public void setPanelPrincipal(JPanel PanelPrincipal) {
        this.PanelPrincipal = PanelPrincipal;
    }
    public JTextField getTxtContraseñaA(){
        return TxtContraseñaA;
    }
    public void setTxtContraseñaA(JTextField TxtContraseñaA){
        this.TxtContraseñaA = TxtContraseñaA;
    }
    public JButton getBtnGuardar() {
        return BtnGuardar;
    }
    public void setBtnGuardar(JButton BtnGuardar) {
        this.BtnGuardar = BtnGuardar;
    }
    public JTextField getTxtContraseña() {
        return TxtContraseña;
    }
    public void setTxtContraseña(JTextField TxtContraseña){
        this.TxtContraseña= TxtContraseña;
    }
}
