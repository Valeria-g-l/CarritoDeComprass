package ec.edu.vista;

import ec.edu.controlador.CarritoController;
import ec.edu.util.ActualizablePorIdioma;
import ec.edu.util.FormateadorUtils;
import ec.edu.util.MensajeInternacionalizacionHandler;
import ec.edu.util.ActualizablePorIdioma;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

public class CarritoAnadirView extends JInternalFrame implements ActualizablePorIdioma {
    private MensajeInternacionalizacionHandler mensajeHandler;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTable tblCarrito;
    private JTextField TxtSubtotal;
    private JTextField TxtIVA;
    private JTextField TxtTotal;
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
    private JButton BtnCancelar;
    private JLabel LblCodigo;
    private JLabel LblIVA;
    private JLabel LblTotal;
    private CarritoController carritoController;

    public void setCarritoController(CarritoController carritoController) {
        this.carritoController = carritoController;
    }


    public CarritoAnadirView(MensajeInternacionalizacionHandler handler) {
        super("Carrito de Compras", true, true, false, true);
        setContentPane(JPanel);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);

        DefaultTableModel modelo = new DefaultTableModel();
        Object[] columnas = {"Codigo", "Nombre", "Precio", "Cantidad", "Subtotal"};
        modelo.setColumnIdentifiers(columnas);
        tblCarrito.setModel(modelo);

        cargarDatos();
        this.mensajeHandler = Main.mensajeHandler;
        actualizarTextos(handler.getBundle());

        BtnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        ImageIcon iconBuscar = new ImageIcon(getClass().getResource("/imagenes/search.png"));
        btnBuscar.setIcon(new ImageIcon(iconBuscar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon iconAnadir = new ImageIcon(getClass().getResource("/imagenes/plus.png"));
        BtnAñadir.setIcon(new ImageIcon(iconAnadir.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon iconGuardar = new ImageIcon(getClass().getResource("/imagenes/shield-check.png"));
        BtnGuardar.setIcon(new ImageIcon(iconGuardar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon iconLimpiar = new ImageIcon(getClass().getResource("/imagenes/broom.png"));
        BtnLimpiar.setIcon(new ImageIcon(iconLimpiar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon iconCancelar = new ImageIcon(getClass().getResource("/imagenes/cross (1).png"));
        BtnCancelar.setIcon(new ImageIcon(iconCancelar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        BtnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void cargarDatos() {
        CBoxCantidad.removeAllItems();
        for (int i = 1; i <= 20; i++) {
            CBoxCantidad.addItem(i);
        }
    }

    public DefaultTableModel getModelo() {
        return (DefaultTableModel) tblCarrito.getModel();
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

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JButton getBtnAñadir() {
        return BtnAñadir;
    }

    public void setBtnAñadir(JButton btnAñadir) {
        this.BtnAñadir = btnAñadir;
    }

    public JTable getTblCarrito() {
        return tblCarrito;
    }

    public void setTblCarrito(JTable tblCarrito) {
        this.tblCarrito = tblCarrito;
    }

    public JTextField getTxtSubtotal() {
        return TxtSubtotal;
    }

    public void setTxtSubtotal(JTextField txtSubtotal) {
        this.TxtSubtotal = txtSubtotal;
    }

    public JTextField getTxtIVA() {
        return TxtIVA;
    }

    public void setTxtIVA(JTextField txtIVA) {
        this.TxtIVA = txtIVA;
    }

    public JTextField getTxtTotal() {
        return TxtTotal;
    }

    public void setTxtTotal(JTextField txtTotal) {
        this.TxtTotal = txtTotal;
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

    public JButton getBtnCancelar() {
        return BtnCancelar;
    }

    public void setBtnCancelar(JButton btnCancelar) {
        BtnCancelar = btnCancelar;
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCamposProducto() {

    }

    public void actualizarTextos(ResourceBundle bundle) {
        LblCodigo.setText(mensajeHandler.get("login.Codigo"));
        lblNombre.setText(mensajeHandler.get("login.Nombre"));
        lblPrecio.setText(mensajeHandler.get("login.Precio"));
        lblCantidad.setText(mensajeHandler.get("login.Cantidad"));
        btnBuscar.setText(mensajeHandler.get("login.Buscar"));
        BtnAñadir.setText(mensajeHandler.get("login.anadir"));
        BtnGuardar.setText(mensajeHandler.get("login.guardar"));
        BtnLimpiar.setText(mensajeHandler.get("login.limpiar"));
        BtnCancelar.setText(mensajeHandler.get("login.cancelar"));
        lblSubtotal.setText(mensajeHandler.get("login.subtotal"));
        LblIVA.setText(mensajeHandler.get("login.iva"));
        LblTotal.setText(mensajeHandler.get("login.total"));


    }
    public void actualizarTotalesFormateados(double subtotal, double iva, double total) {
        Locale locale = mensajeHandler.getLocale();
        TxtSubtotal.setText(FormateadorUtils.formatearMoneda(subtotal, locale));
        TxtIVA.setText(FormateadorUtils.formatearMoneda(iva, locale));
        TxtTotal.setText(FormateadorUtils.formatearMoneda(total, locale));
    }

}