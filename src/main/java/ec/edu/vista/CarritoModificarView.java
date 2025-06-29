package ec.edu.vista;

import javax.swing.*;

public class CarritoModificarView extends JInternalFrame {
    private JPanel PanelPrincipal;
    private JLabel LblTitulo;
    private JTable TblProductos;
    private JComboBox CBoxCantidad;
    private JButton BtnEliminar;
    private JLabel LblSubtotal;
    private JTextField TxtSubtotal;
    private JLabel LblIVA;
    private JTextField TxtIVA;
    private JLabel LblTotal;
    private JTextField TxtTotal;

    public CarritoModificarView() {
        setTitle("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setContentPane(PanelPrincipal);
    }

    public JPanel getPanelPrincipal() {
        return PanelPrincipal;
    }
    public void setPanelPrincipal(JPanel PanelPrincipal) {
        this.PanelPrincipal = PanelPrincipal;
    }
    public JTable getTblProductos() {
        return TblProductos;
    }
    public void setTblProductos(JTable TblProductos) {
        this.TblProductos = TblProductos;

    }
    public JComboBox getCBoxCantidad() {
        return CBoxCantidad;
    }
    public void setCBoxCantidad(JComboBox CBoxCantidad) {
        this.CBoxCantidad = CBoxCantidad;
    }
    public JButton getBtnEliminar() {
        return BtnEliminar;
    }
    public void setBtnEliminar(JButton BtnEliminar) {
        this.BtnEliminar = BtnEliminar;
    }
    public JTextField getTxtSubtotal() {
        return TxtSubtotal;
    }
    public void setTxtSubtotal(JTextField TxtSubtotal) {
        this.TxtSubtotal = TxtSubtotal;
    }
    public JTextField getTxtIVA() {
        return TxtIVA;
    }
    public void setTxtIVA(JTextField TxtIVA) {
        this.TxtIVA = TxtIVA;
    }
    public JTextField getTxtTotal() {
        return TxtTotal;
    }
    public void setTxtTotal(JTextField TxtTotal) {
        this.TxtTotal = TxtTotal;
    }
}




