package ec.edu.ups.vista;

import javax.swing.*;

public class CarritoAnadirView {
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTable tblCarrito;
    private JTextField txtSubtotal;
    private JTextField txtIVA;
    private JTextField txtTotal;
    private JButton btnBuscar;
    private JComboBox CBoxCantidad;
    private JButton BtnAñadir;
    private JButton BtnGuardar;
    private JButton BtnLimpiar;
    private JPanel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblCantidad;
    private JLabel lblSubtotal;
    private JPanel lblIVA;
    private JPanel lblTotal;

    public CarritoAnadirView() {

    }

    public JPanel getLblCodigo() {
        return lblCodigo;
    }

    public void setLblCodigo(JPanel lblCodigo) {
        this.lblCodigo = lblCodigo;
    }
}

