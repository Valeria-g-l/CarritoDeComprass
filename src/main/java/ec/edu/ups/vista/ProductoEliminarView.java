package ec.edu.ups.vista;

import javax.swing.*;

public class ProductoEliminarView  extends  JInternalFrame{
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTable tblEliminar;
    private JPanel PanelPrincipal;
    private JLabel lblTitulo;
    private JLabel lblCodigo;
    private JPanel PanelSecundario;
    private JButton BtnEliminar;

    public ProductoEliminarView() {
        setContentPane(PanelPrincipal);
        setTitle("Edición de Productos");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLocation(100,500);
        setVisible(true);

    }

    public JPanel getPanelPrincipal() {
        return PanelPrincipal;
    }
    public void setPanelPrincipal(JPanel PanelPrincipal) {
        this.PanelPrincipal = PanelPrincipal;
    }
    public JLabel getLabelTitulo() {
        return lblTitulo;
    }
    public void setLabelTitulo(JLabel labelTitulo) {
        this.lblTitulo = labelTitulo;
    }
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }
    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }
    public JButton getBtnBuscar() {
        return btnBuscar;
    }
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }
    public JTable getTblEliminar() {
        return tblEliminar;
    }
    public void setTblEliminar(JTable tblEliminar) {
        this.tblEliminar = tblEliminar;
    }
    public JPanel getPanelSecundario() {
        return PanelSecundario;
    }
    public void setPanelSecundario(JPanel PanelSecundario) {
        this.PanelSecundario = PanelSecundario;
    }
    public JButton getBtnEliminar() {
        return BtnEliminar;
    }
    public void setBtnEliminar(JButton btnEliminar) {
        this.BtnEliminar = btnEliminar;
    }
    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Confirmación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    public void limpiarCampos() {
        txtCodigo.setText("");

    }

}
