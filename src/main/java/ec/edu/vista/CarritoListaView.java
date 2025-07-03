package ec.edu.vista;

import ec.edu.controlador.CarritoController;
import ec.edu.util.ActualizablePorIdioma;
import ec.edu.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;


public class CarritoListaView extends JInternalFrame implements ActualizablePorIdioma {
    private MensajeInternacionalizacionHandler mensajeHandler;
    private JPanel PanelPrincipal;
    private JLabel LblTitulo;
    private JTable TblCarrito;
    private CarritoController carritoController;


    private JButton BtnCancelar;
    private DefaultTableModel modelo;

    public CarritoListaView(MensajeInternacionalizacionHandler handler) {
        this.mensajeHandler = Main.mensajeHandler;
        actualizarTextos(Main.mensajeHandler.getBundle());
        setContentPane(PanelPrincipal);
        setTitle("Carritos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        modelo = new DefaultTableModel();
        Object[] columnas = {"ID", "Fecha Creación", "Usuario", "Total"};
        modelo.setColumnIdentifiers(columnas);
        TblCarrito.setModel(modelo);

        BtnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        ImageIcon iconCancelar = new ImageIcon(getClass().getResource("/imagenes/cross (1).png"));
        BtnCancelar.setIcon(new ImageIcon(iconCancelar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

    }
    public JButton getBtnCancelar() {
        return BtnCancelar;
    }

    public void setBtnCancelar(JButton btnCancelar) {
        BtnCancelar = btnCancelar;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public JTable getTblCarrito() {
        return TblCarrito;
    }



    public int obtenerCodigoCarritoSeleccionado() {
        int fila = TblCarrito.getSelectedRow();
        if (fila >= 0) {
            return (int) modelo.getValueAt(fila, 0);
        }
        return -1;
    }

    public void actualizarTextos(ResourceBundle Bundle) {
        LblTitulo.setText(mensajeHandler.get("titulo"));
        BtnCancelar.setText(mensajeHandler.get("cancelar"));
    }

    public void setCarritoController(CarritoController carritoController) {
        this.carritoController = carritoController;
    }
}