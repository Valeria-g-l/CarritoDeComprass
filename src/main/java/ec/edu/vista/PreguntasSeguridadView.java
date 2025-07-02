package ec.edu.vista;

import ec.edu.controlador.UsuarioController;
import ec.edu.util.ActualizablePorIdioma;
import ec.edu.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;


public class PreguntasSeguridadView extends JFrame implements ActualizablePorIdioma {
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
    private JLabel LblPregunta6;
    private JTextField TxtPregunta6;
    private JLabel LblPregunta7;
    private JTextField TxtPregunta7;
    private JLabel LblPregunta8;
    private JTextField TxtPregunta8;
    private JLabel LblPregunta9;
    private JTextField TxtPregunta9;
    private JLabel LblPregunta10;
    private JTextField TxtPregunta10;
    private UsuarioController usuarioController;
    private String modo;
    private MensajeInternacionalizacionHandler mensajeHandler;

    public PreguntasSeguridadView(MensajeInternacionalizacionHandler handler, UsuarioController usuarioController, String modo) {
        this.usuarioController = usuarioController;
        this.modo = modo;
        this.mensajeHandler = Main.mensajeHandler;
        actualizarTextos(Main.mensajeHandler.getBundle());
        setTitle("Preguntas De Seguridad");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setContentPane(PanelPrincipal);
        BtnAccion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarRespuestas();
            }
        });

        ImageIcon iconoGuardar = new ImageIcon(getClass().getResource("/imagenes/shield-check.png"));
        Image imagenEscalada = iconoGuardar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        BtnAccion.setIcon(new ImageIcon(imagenEscalada));
    }
    private void procesarRespuestas() {
        String p1 = LblPregunta1.getText();
        String p2 = LblPregunta2.getText();
        String p3 = LblPregunta3.getText();

        String r1 = TxtPregunta1.getText().trim();
        String r2 = TxtPregunta2.getText().trim();
        String r3 = TxtPregunta3.getText().trim();

        if (r1.isEmpty() || r2.isEmpty() || r3.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, responde todas las preguntas.");
            return;
        }

        if (modo.equals("registro")) {
            usuarioController.setPreguntasSeguridadActual(r1, r2, r3, p1, p2, p3);
            usuarioController.getUsuarioDAO().guardar(usuarioController.getUsuarioEnProceso());
            JOptionPane.showMessageDialog(this, "Preguntas guardadas correctamente.");
            LoginView loginView = new LoginView(mensajeHandler);
            loginView.setVisible(true);
            new UsuarioController(usuarioController.getUsuarioDAO(), loginView, mensajeHandler);
            dispose();
        } else if (modo.equals("recuperacion")) {
            boolean verificado = usuarioController.verificarPreguntas(p1, r1, p2, r2, p3, r3);
            if (verificado) {
                JOptionPane.showMessageDialog(this, "Verificación correcta. Puedes cambiar tu contraseña.");
                dispose();
                CambiarContrasenaView cambiarView = new CambiarContrasenaView(mensajeHandler);
                cambiarView.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Las respuestas no coinciden.");
            }
        }
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
    public JTextField getTxtPregunta6() {
        return TxtPregunta6;
    }
    public void setTxtPregunta6(JTextField txtPregunta6) {
        TxtPregunta6 = txtPregunta6;
    }
    public JTextField getTxtPregunta7() {
        return TxtPregunta7;
    }
    public void setTxtPregunta7(JTextField txtPregunta7) {
        TxtPregunta7 = txtPregunta7;
    }
    public JTextField getTxtPregunta8() {
        return TxtPregunta8;
    }
    public void setTxtPregunta8(JTextField txtPregunta8) {
        TxtPregunta8 = txtPregunta8;
    }
    public JTextField getTxtPregunta9() {
        return TxtPregunta9;
    }
    public void setTxtPregunta9(JTextField txtPregunta9) {
        TxtPregunta9 = txtPregunta9;
    }
    public JTextField getTxtPregunta10() {
        return TxtPregunta10;
    }
    public void setTxtPregunta10(JTextField txtPregunta10) {
        TxtPregunta10 = txtPregunta10;
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

    public void actualizarTextos(ResourceBundle Bundle) {
        LblPregunta1.setText(mensajeHandler.get("pregunta1"));
        LblPregunta2.setText(mensajeHandler.get("pregunta2"));
        LblPregunta3.setText(mensajeHandler.get("pregunta3"));
        LblPregunta4.setText(mensajeHandler.get("pregunta4"));
        LblPregunta5.setText(mensajeHandler.get("pregunta5"));
        LblPregunta6.setText(mensajeHandler.get("pregunta6"));
        LblPregunta7.setText(mensajeHandler.get("pregunta7"));
        LblPregunta8.setText(mensajeHandler.get("pregunta8"));
        LblPregunta9.setText(mensajeHandler.get("pregunta9"));
        LblPregunta10.setText(mensajeHandler.get("pregunta10"));
        LblTitulo.setText(mensajeHandler.get("titulo"));
        BtnAccion.setText(mensajeHandler.get("accion"));

    }

}

