package ec.edu.vista;

import ec.edu.controlador.ProductoController;
import ec.edu.modelo.Producto;
import ec.edu.util.ActualizablePorIdioma;
import ec.edu.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

public class ProductoListaView extends JInternalFrame implements ActualizablePorIdioma {
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
    private ProductoController productoController;

    /**
     * Asigna el controlador de producto a esta vista.
     *
     * @param productoController Controlador que manejará la lógica de producto.
     */
    public void setProductoController(ProductoController productoController) {
        this.productoController = productoController;
    }



    public ProductoListaView(MensajeInternacionalizacionHandler handler) {
        this.mensajeHandler = Main.mensajeHandler;
        actualizarTextos(Main.mensajeHandler.getBundle());
        setContentPane(PanelTerciario);
        setTitle("Productos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);

        ImageIcon iconBuscar = new ImageIcon(getClass().getResource("/Imagenes/search.png"));
        btnBuscar.setIcon(new ImageIcon(iconBuscar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        ImageIcon iconCancelar = new ImageIcon(getClass().getResource("/Imagenes/cross (1).png"));
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

    public void actualizarTextos(ResourceBundle Bundle) {
        lblTitule.setText(mensajeHandler.get("titulo"));
        lblBuscar.setText(mensajeHandler.get("buscar"));
        btnBuscar.setText(mensajeHandler.get("buscar"));
        btnCancelar.setText(mensajeHandler.get("cancelar"));
    }


}





