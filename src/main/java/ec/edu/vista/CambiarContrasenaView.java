package ec.edu.vista;

import ec.edu.util.ActualizablePorIdioma;
import ec.edu.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class CambiarContrasenaView extends JInternalFrame implements ActualizablePorIdioma {
    private JPanel PanelPrincipal;
    private JLabel LblTitulo;
    private JTextField TxtContraseñaA;
    private JTextField TxtContraseña;
    private JButton BtnGuardar;
    private JLabel LblContraseña;
    private JLabel LblContraseñaA;
    private MensajeInternacionalizacionHandler mensajeHandler;
    private JButton BtnCancelar;
    /**
     * Constructor para la ventana de cambio de contraseña.
     * <p>
     * Inicializa la interfaz con el manejador de internacionalización, configura
     * textos, panel principal, título, tamaño y comportamiento al cerrar.
     * </p>
     *
     * @param handler Manejador para la internacionalización de mensajes.
     */
    public CambiarContrasenaView(MensajeInternacionalizacionHandler handler) {
        this.mensajeHandler = Main.mensajeHandler;
        actualizarTextos(Main.mensajeHandler.getBundle());
        setContentPane(PanelPrincipal);
        setTitle("Productos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);

        ImageIcon iconoGuardar = new ImageIcon(getClass().getResource("/Imagenes/shield-check.png"));
        Image imagenEscalada = iconoGuardar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        BtnGuardar.setIcon(new ImageIcon(imagenEscalada));

        BtnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
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
    public JButton getBtnCancelar() {
        return BtnCancelar;
    }
    public void setBtnCancelar(JButton BtnCancelar) {
        this.BtnCancelar = BtnCancelar;
    }

    public void actualizarTextos(ResourceBundle bundle) {
        LblTitulo.setText(mensajeHandler.get("login.titulo"));
        LblContraseñaA.setText(mensajeHandler.get("login.ContrasenaA"));
        LblContraseña.setText(mensajeHandler.get("login.contrasena"));
        BtnGuardar.setText(mensajeHandler.get("login.boton"));
    }
}