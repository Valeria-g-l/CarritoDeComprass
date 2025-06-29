package ec.edu.vista;

import ec.edu.modelo.Producto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

public class ProductoAnadirView extends  JInternalFrame {
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
    private ResourceBundle mensajes;


    public ProductoAnadirView(ResourceBundle mensajes) {
        setContentPane(PanelPrincipal);
        setTitle("Agregar Producto");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        BtnLimpiarr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtCodigo.setText("");
                txtNombre.setText("");
                txtPrecio.setText("");
            }
        });
    }
    public JLabel getLabelTitulo() {
        return labelTitulo;
    }

    public void setLabelTitulo(JLabel labelTitulo) {
        this.labelTitulo = labelTitulo;
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

    public JLabel getLblCodigo() {
        return LblCodigo;
    }

    public void setLblCodigo(JLabel LblCodigo) {
        this.LblCodigo = LblCodigo;
    }

    public JLabel getLblNombre() {
        return LblNombre;
    }

    public void setLblNombre(JLabel LblNombre) {
        this.LblNombre = LblNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    public JLabel getLblPrecio() {
        return LblPreecio;
    }

    public void setLblPrecio(JLabel LblPrecio) {
        this.LblPreecio = LblPrecio;
    }

    public JPanel getPanelPrincipal() {
        return PanelPrincipal;
    }

    public void setPanelPrincipal(JPanel PanelPrincipal) {
        this.PanelPrincipal = PanelPrincipal;
    }

    public JPanel getPanelTitle() {
        return PanelTitle;
    }

    public void setPanelTitle(JPanel PanelTitle) {
        this.PanelTitle = PanelTitle;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }
    public JButton getBtnLimpiarr() {
        return BtnLimpiarr;
    }
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    public void mostrarProductos(List<Producto> productos) {

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
    public void setMensajes(ResourceBundle mensajes) {
        this.mensajes = mensajes;

        setTitle(mensajes.getString("titulo.agregarProducto"));

    }


}





