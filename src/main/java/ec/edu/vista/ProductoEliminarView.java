package ec.edu.vista;

import ec.edu.controlador.ProductoController;
import ec.edu.util.ActualizablePorIdioma;
import ec.edu.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class ProductoEliminarView  extends  JInternalFrame implements ActualizablePorIdioma {
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTable tblEliminar;
    private JPanel PanelPrincipal;
    private JLabel lblTitulo;
    private JLabel lblCodigo;
    private JPanel PanelSecundario;
    private JButton BtnEliminar;
    private JButton BtnCancelar;
    private MensajeInternacionalizacionHandler mensajeHandler;
    private ProductoController productoController;

    public void setProductoController(ProductoController productoController) {
        this.productoController = productoController;
    }

    public ProductoEliminarView(MensajeInternacionalizacionHandler handler) {
        this.mensajeHandler = Main.mensajeHandler;
        actualizarTextos(Main.mensajeHandler.getBundle());
        setContentPane(PanelPrincipal);
        setTitle("Edición de Productos");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);

        ImageIcon iconBuscar = new ImageIcon(getClass().getResource("/imagenes/shield-check.png"));
        btnBuscar.setIcon(new ImageIcon(iconBuscar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon iconCancelar = new ImageIcon(getClass().getResource("/imagenes/cross (1).png"));
        BtnCancelar.setIcon(new ImageIcon(iconCancelar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        setLocation(100,500);


        BtnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        BtnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
    public JButton getBtnCancelar() {
        return BtnCancelar;
    }

    public void setBtnCancelar(JButton btnCancelar) {
        BtnCancelar = btnCancelar;
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

    public void actualizarTextos(ResourceBundle Bundle) {
        lblTitulo.setText(mensajeHandler.get("titulo"));
        lblCodigo.setText(mensajeHandler.get("codigo"));
        BtnCancelar.setText(mensajeHandler.get("cancelar"));
        btnBuscar.setText(mensajeHandler.get("buscar"));
        BtnEliminar.setText(mensajeHandler.get("eliminar"));
    }

}
