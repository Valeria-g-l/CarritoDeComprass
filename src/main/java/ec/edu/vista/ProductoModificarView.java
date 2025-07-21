package ec.edu.vista;

import ec.edu.controlador.ProductoController;
import ec.edu.util.ActualizablePorIdioma;
import ec.edu.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class ProductoModificarView extends  JInternalFrame implements ActualizablePorIdioma {
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnActualizar;
    private JPanel PanelPrincipal;
    private JLabel lblTitulo;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JButton btnLimpiar;
    private JButton btnCancelar;
    private JButton btnBuscar;
    private MensajeInternacionalizacionHandler mensajeHandler;
    private ProductoController productoController;

    public JButton getBtnBuscar() {
        return btnBuscar;
    }
    /**
     * Obtiene el botón de buscar.
     *
     * @return El botón utilizado para realizar búsquedas.
     */
    public void setProductoController(ProductoController productoController) {
        this.productoController = productoController;
    }


    public  ProductoModificarView(MensajeInternacionalizacionHandler handler){
        this.mensajeHandler = Main.mensajeHandler;
        actualizarTextos(Main.mensajeHandler.getBundle());
        setContentPane(PanelPrincipal);
        setTitle("Datos del Producto");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);


        ImageIcon iconActualizar = new ImageIcon(getClass().getResource("/Imagenes/check.png"));
        btnActualizar.setIcon(new ImageIcon(iconActualizar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon iconLimpiar = new ImageIcon(getClass().getResource("/Imagenes/broom.png"));
        btnLimpiar.setIcon(new ImageIcon(iconLimpiar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon iconCancelar = new ImageIcon(getClass().getResource("/Imagenes/cross (1).png"));
        btnCancelar.setIcon(new ImageIcon(iconCancelar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        setResizable(true);


        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();

            }
        });
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public JPanel getPanel(){

        return PanelPrincipal;
    }
    public void setPanel(JPanel PanelPrincipal){

        this.PanelPrincipal = PanelPrincipal;
    }
    public JLabel getLabelTitulo() {
        return lblTitulo;
    }
    public void setLabelTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
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
    public void setTxtPrecio(String TxtPrecio) {
        this.txtPrecio = txtPrecio;
    }
    public JButton getBtnActualizar() {
        return btnActualizar;
    }
    public void setBtnActualizar(JButton btnActualizar) {
        this.btnActualizar = btnActualizar;
    }
    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }
    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }
    public JButton getBtnCancelar() {
        return btnCancelar;
    }
    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    public JLabel getlblCodigo(){

        return lblCodigo;
    }
    public void setlblCodigo(JLabel lblCodigo){
        this.lblCodigo = lblCodigo;
    }
    public JLabel getlblNombre(){
        return lblNombre;
    }
    public void setlblNombre(JLabel lblNombre){
        this.lblNombre = lblNombre;
    }
    public JLabel getlblPrecio(){
        return lblPrecio;
    }
    public void setlblPrecio(JLabel lblPrecio){
        this.lblPrecio = lblPrecio;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                mensaje,
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );
        return respuesta == JOptionPane.YES_OPTION;
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    public void actualizarTextos(ResourceBundle Bundle) {
        lblTitulo.setText(mensajeHandler.get("titulo"));
        lblCodigo.setText(mensajeHandler.get("codigo"));
        lblNombre.setText(mensajeHandler.get("nombre"));
        lblPrecio.setText(mensajeHandler.get("precio"));
        btnActualizar.setText(mensajeHandler.get("actualizar"));
        btnLimpiar.setText(mensajeHandler.get("limpiar"));
        btnCancelar.setText(mensajeHandler.get("cancelar"));
    }

}
