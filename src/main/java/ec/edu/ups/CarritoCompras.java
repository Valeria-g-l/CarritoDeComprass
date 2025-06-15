package ec.edu.ups;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarritoCompras extends JFrame {
    private JPanel PanelTitulo;
    private JLabel LblTitle;
    private JLabel LblProducto;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton btnCancelar;
    private JButton Guardar;
    private JPanel PanelSecundario;
    private JButton BtnLimpiar;

    public CarritoCompras() {
        setContentPane(PanelSecundario);
        setTitle("Mostar Compras");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


    }
}

