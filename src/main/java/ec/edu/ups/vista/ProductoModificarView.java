package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProductoModificarView extends JFrame {

    // Panel principal (form)
    private JPanel PanelCuatro;

    // Componentes de la vista
    private JTextField textField1; // Campo para buscar por código
    private JButton buscarButton;
    private JTable table1;
    private JButton eliminarButton;
    private JTextField txtName;
    private JTextField txtPrecio;
    private JButton modificarButton;

    // Otros paneles auxiliares si los usas
    private JPanel Panel1;
    private JLabel LblName;
    private JLabel LblPrecio;


    private JLabel LblCodigo;

    // Modelo para la tabla
    private DefaultTableModel modelo;

    public ProductoModificarView() {
        setTitle("Modificar Producto");
        setContentPane(PanelCuatro);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Configurar la tabla
        modelo = new DefaultTableModel(new Object[]{"Código", "Nombre", "Precio"}, 0);
        table1.setModel(modelo);

        setVisible(true);
    }

    // ------------------- Métodos útiles -------------------

    public void cargarDatos(List<Producto> listaProductos) {
        modelo.setRowCount(0); // Limpia la tabla
        for (Producto producto : listaProductos) {
            modelo.addRow(new Object[]{
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getPrecio()
            });
        }
    }

    public void limpiarCampos() {
        textField1.setText("");
        txtName.setText("");
        txtPrecio.setText("");
    }

    // ------------------- Getters -------------------

    public JTextField getTxtCodigo() {
        return textField1;
    }

    public JTextField getTxtNombre() {
        return txtName;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public JButton getBuscarButton() {
        return buscarButton;
    }

    public JButton getModificarButton() {
        return modificarButton;
    }

    public JButton getEliminarButton() {
        return eliminarButton;
    }

    public JTable getTable() {
        return table1;
    }

    public JPanel getPanel1() {
        return Panel1;
    }

    public void setPanel1(JPanel panel1) {
        this.Panel1 = panel1;
    }

    public JLabel getLblName() {
        return LblName;
    }

    public void setLblName(JLabel lblName) {
        LblName = lblName;
    }

    public JLabel getLblPrecio() {
        return LblPrecio;
    }

    public void setLblPrecio(JLabel lblPrecio) {
        LblPrecio = lblPrecio;
    }
    public JLabel getLblCodigo() {
        return LblCodigo;
    }

    public void setLblCodigo(JLabel lblCodigo) {
        LblCodigo = lblCodigo;
    }
}
