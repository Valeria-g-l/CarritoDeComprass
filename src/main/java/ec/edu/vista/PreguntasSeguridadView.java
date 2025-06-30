package ec.edu.vista;

import ec.edu.controlador.UsuarioController;
import ec.edu.modelo.Usuario;

import javax.swing.*;
import java.util.ResourceBundle;

public class PreguntasSeguridadView extends JInternalFrame {
    private JLabel LblTitulo;
    private JLabel LblPregunta1;
    private JTextField TxtPregunta1;
    private JLabel LblPregunta2;
    private JTextField TxtPregunta2;
    private JLabel LblPregunta3;
    private JTextField TxtPregunta3;
    private JLabel LblPregunta4;
    private JTextField TxtPregunta4;
    private JLabel LblPregunta5;
    private JTextField TxtPregunta5;
    private JButton BtnAccion;
    private JPanel PanelPrincipal;

    public PreguntasSeguridadView(ResourceBundle mensajes) {
        setTitle("Preguntas De Seguridad");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setContentPane(PanelPrincipal);
    }
    public JTextField getTxtPregunta1() {
        return TxtPregunta1;
    }

    public void setTxtPregunta1(JTextField txtPregunta1) {
        TxtPregunta1 = txtPregunta1;
    }
    public JTextField getTxtPregunta2() {
        return TxtPregunta2;
    }
    public void setTxtPregunta2(JTextField txtPregunta2) {
        TxtPregunta2 = txtPregunta2;
    }
    public JTextField getTxtPregunta3() {
        return TxtPregunta3;
    }
    public void setTxtPregunta3(JTextField txtPregunta3) {
        TxtPregunta3 = txtPregunta3;
    }
    public JTextField getTxtPregunta4() {
        return TxtPregunta4;
    }
    public void setTxtPregunta4(JTextField txtPregunta4) {
        TxtPregunta4 = txtPregunta4;
    }
    public JTextField getTxtPregunta5() {
        return TxtPregunta5;
    }
    public void setTxtPregunta5(JTextField txtPregunta5) {
        TxtPregunta5 = txtPregunta5;
    }
    public JButton getBtnAccion() {
        return BtnAccion;
    }
    public void setBtnAccion(JButton btnAccion) {
        BtnAccion = btnAccion;
    }
    public JPanel getPanelPrincipal() {
        return PanelPrincipal;
    }
    public void setPanelPrincipal(JPanel panelPrincipal) {
        PanelPrincipal = panelPrincipal;
    }

}

