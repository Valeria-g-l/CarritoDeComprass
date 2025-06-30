package ec.edu.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CarritoListaView extends JInternalFrame {
    private JPanel PanelPrincipal;
    private JLabel LblTitulo;
    private JTable TblCarrito;
    private DefaultTableModel modelo;

    public CarritoListaView() {
        setContentPane(PanelPrincipal);
        setTitle("Carritos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);


        modelo = new DefaultTableModel();
        Object[] columnas = {"ID", "Fecha Creación", "Usuario", "Total"};
        modelo.setColumnIdentifiers(columnas);
        TblCarrito.setModel(modelo);
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
}
