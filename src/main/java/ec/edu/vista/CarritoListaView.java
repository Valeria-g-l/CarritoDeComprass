package ec.edu.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CarritoListaView extends JInternalFrame {
    private JPanel PanelPrincipal;
    private JLabel LblTitulo;
    private JTable TblCarrito;
    private JButton BtnModificar;
    private DefaultTableModel modelo;

    public CarritoListaView() {
        setContentPane(PanelPrincipal);
        setTitle("Productos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);

        modelo = new DefaultTableModel();
        Object[] columnas = {"ID", "Fecha Creación", "Usuario", "Total"};
        modelo.setColumnIdentifiers(columnas);
        TblCarrito.setModel(modelo);

        TblCarrito.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int filaSeleccionada = TblCarrito.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    System.out.println("Fila seleccionada: " + filaSeleccionada);
                }
            }
        });
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public JTable getTblCarrito() {
        return TblCarrito;
    }

    public void setTblCarrito(JTable tblCarrito) {
        TblCarrito = tblCarrito;
    }

    public JPanel getPanelPrincipal() {
        return PanelPrincipal;
    }

    public void setPanelPrincipal(JPanel PanelPrincipal) {
        this.PanelPrincipal = PanelPrincipal;
    }

    public JButton getBtnModificar() {
        return BtnModificar;
    }

    public void setBtnModificar(JButton btnModificar) {
        BtnModificar = btnModificar;
    }

    public void agregarListenerSeleccionFila(javax.swing.event.ListSelectionListener listener) {
        TblCarrito.getSelectionModel().addListSelectionListener(listener);
    }
}
