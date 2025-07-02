package ec.edu.vista;

import ec.edu.modelo.Producto;
import ec.edu.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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
    private MensajeInternacionalizacionHandler mensajeHandler;


    public ProductoListaView(MensajeInternacionalizacionHandler handler) {
        this.mensajeHandler = handler;
        actualizarTextos();
        setContentPane(PanelTerciario);
        setTitle("Productos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);

        ImageIcon iconBuscar = new ImageIcon(getClass().getResource("/imagenes/search.png"));
        btnBuscar.setIcon(new ImageIcon(iconBuscar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon iconCancelar = new ImageIcon(getClass().getResource("/imagenes/cross (1).png"));
        btnCancelar.setIcon(new ImageIcon(iconCancelar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

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

    public void actualizarTextos() {
        lblTitule.setText(mensajeHandler.get("titulo"));
        lblBuscar.setText(mensajeHandler.get("buscar"));
        btnBuscar.setText(mensajeHandler.get("buscar"));
        btnCancelar.setText(mensajeHandler.get("cancelar"));
    }


}





