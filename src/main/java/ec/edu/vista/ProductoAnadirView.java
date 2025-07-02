package ec.edu.vista;

import ec.edu.modelo.Producto;
import ec.edu.util.ActualizablePorIdioma;
import ec.edu.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

public class ProductoAnadirView extends  JInternalFrame implements ActualizablePorIdioma {
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
    private MensajeInternacionalizacionHandler mensajeHandler;


    public ProductoAnadirView(MensajeInternacionalizacionHandler handler) {
        this.mensajeHandler = Main.mensajeHandler;
        actualizarTextos(Main.mensajeHandler.getBundle());
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

        ImageIcon iconGuardar = new ImageIcon(getClass().getResource("/imagenes/shield-check.png"));
        btnGuardar.setIcon(new ImageIcon(iconGuardar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon iconCancelar = new ImageIcon(getClass().getResource("/imagenes/cross (1).png"));
        btnCancelar.setIcon(new ImageIcon(iconCancelar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon iconLimpiar = new ImageIcon(getClass().getResource("/imagenes/broom.png"));
        BtnLimpiarr.setIcon(new ImageIcon(iconLimpiar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));




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
                ProductoAnadirView.this.dispose();
            }
        });
    }
    public void actualizarTextos(ResourceBundle Bundle) {
        labelTitulo.setText(mensajeHandler.get("login.Titulo"));
        LblCodigo.setText(mensajeHandler.get("login.LblCodigo"));
        LblNombre.setText(mensajeHandler.get("login.LblNombre"));
        LblPreecio.setText(mensajeHandler.get("login.LblPrecio"));
        btnGuardar.setText(mensajeHandler.get("login.BtnGuardar"));
        btnCancelar.setText(mensajeHandler.get("login.BtnCancelar"));
        BtnLimpiarr.setText(mensajeHandler.get("login.BtnLimpiar"));
    }




}





