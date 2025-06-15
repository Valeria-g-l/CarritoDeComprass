package ec.edu.ups;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaP  extends  JFrame{
    private JLabel labelTitulo;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JLabel LblCodigo;
    private JLabel LblNombre;
    private JTextField txtPrecio;
    private JLabel LblPreecio;
    private JPanel PanelPrincipal;
    private JPanel PanelTitle;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JButton BtnLimpiarr;


    public VentanaP() {
        setContentPane(PanelPrincipal);
        setTitle("Agregar Producto");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        BtnLimpiarr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtCodigo.setText("");
                txtNombre.setText("");
                txtPrecio.setText("");
            }
        });
    }


    {
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}





