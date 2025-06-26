package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoListaView extends JInternalFrame {
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tblProductos;
    private JLabel lblBuscar;
    private DefaultTableModel modelo;
    private JPanel PanelTerciario;
    private JButton btnCancelar;
    private JPanel lblTitulo;
    private JLabel lblTitule;


    public ProductoListaView() {
        setContentPane(PanelTerciario);
        setTitle("Productos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);

        modelo = new DefaultTableModel();
        Object[] columnas = {"Codigo", "Nombre", "Precio"};
        modelo.setColumnIdentifiers(columnas);
        tblProductos.setModel(modelo);

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public void setTxtBuscar(JTextField txtBuscar) {
        this.txtBuscar = txtBuscar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    public JPanel getPanelTerciario() {
        return PanelTerciario;
    }

    public void setPanelTerciario(JPanel panelTerciario) {
        PanelTerciario = panelTerciario;
    }

    public void cargarDatos(List<Producto> listaProductos) {
        modelo.setNumRows(0);

        for (Producto producto : listaProductos) {
            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getPrecio()
            };
            modelo.addRow(fila);
        }
    }


}





