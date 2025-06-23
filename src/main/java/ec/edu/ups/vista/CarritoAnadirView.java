package ec.edu.ups.vista;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

public class CarritoAnadirView extends JInternalFrame {
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
    private JPanel PanelPrincipal;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblCantidad;
    private JLabel lblSubtotal;
    private JPanel PanelSecundario;
    private JPanel JPanel;

    public CarritoAnadirView() {
        super("Carrito de Compras", true, true, false, true);
        setContentPane(JPanel);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);

        DefaultTableModel modelo = new DefaultTableModel();
        Object[] columnas = {"Codigo", "Nombre", "Precio", "Cantidad", "Subtotal"};
        modelo.setColumnIdentifiers(columnas);
        tblCarrito.setModel(modelo);

        cargarDatos();
    }
    private void cargarDatos() {
        CBoxCantidad.removeAllItems();
        for (int i = 0; i < 20; i++) {
            CBoxCantidad.addItem(String.valueOf(i + 1));
        }
    }
    public JPanel getPanelPrincipal() {
        return PanelPrincipal;
    }
    public void setPanelPrincipal(JPanel PanelPrincipal) {
        this.PanelSecundario = PanelPrincipal;
    }
    public JPanel getPanelSecundario() {
        return PanelSecundario;
    }
    public void setPanelSecundario(JPanel PanelSecundario) {
        this.PanelSecundario = PanelSecundario;
    }
    public JPanel getPanel() {
        return JPanel;
    }
    public void setPanel(JPanel Panel) {
        this.JPanel = Panel;
    }
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }
    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }
    public JTextField getTxtNombre() {
        return txtNombre;
    }
    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }
    public JTextField getTxtPrecio() {
        return txtPrecio;
    }
    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }
    public JComboBox getCBoxCantidad() {
        return CBoxCantidad;
    }
    public void setCBoxCantidad(JComboBox CBoxCantidad) {
        this.CBoxCantidad = CBoxCantidad;
    }
    public JButton getbtnBuscar(){
        return btnBuscar;
    }
    public void setbtnBuscar(JButton btnBuscar){
        this.btnBuscar = btnBuscar;
    }
    public JButton getBtnAñadir(){
        return BtnAñadir;
    }
    public void setBtnBuscar(JButton btnBuscar){
        this.btnBuscar = btnBuscar;
    }
    public JTable getTblCarrito() {
        return tblCarrito;
    }
    public void setTblCarrito(JTable tblCarrito) {
        this.tblCarrito = tblCarrito;
    }
    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }
    public void setTxtSubtotal(JTextField txtSubtotal) {
        this.txtSubtotal = txtSubtotal;
    }
    public JTextField getTxtIVA() {
        return txtIVA;
    }
    public void setTxtIVA(JTextField txtIVA) {
        this.txtIVA = txtIVA;
    }

    public JTextField getTxtTotal() {
        return txtTotal;
    }
    public void setTxtTotal(JTextField txtTotal) {
        this.txtTotal = txtTotal;
    }
    public JButton getBtnGuardar() {
        return BtnGuardar;
    }
    public void setBtnGuardar(JButton btnGuardar) {
        this.BtnGuardar = btnGuardar;
    }
    public JButton getBtnLimpiar() {
        return BtnLimpiar;
    }
    public void setBtnLimpiar(JButton btnLimpiar) {
        this.BtnLimpiar = btnLimpiar;
    }


    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}

