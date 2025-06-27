package ec.edu.controlador;

import ec.edu.dao.CarritoDAO;
import ec.edu.dao.ProductoDAO;
import ec.edu.modelo.Carrito;
import ec.edu.modelo.ItemCarrito;
import ec.edu.modelo.Producto;
import ec.edu.vista.CarritoAnadirView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CarritoController {

    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final CarritoAnadirView carritoAnadirView;
    private Carrito carrito;

    public CarritoController(CarritoDAO carritoDAO,
                             ProductoDAO productoDAO,
                             CarritoAnadirView carritoAnadirView) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.carritoAnadirView = carritoAnadirView;
        this.carrito = new Carrito();
        configurarEventosEnVistas();
    }

    private void configurarEventosEnVistas() {
        carritoAnadirView.getbtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoEnCarrito();
            }
        });

        carritoAnadirView.getBtnAñadir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anadirProducto();
            }
        });

        carritoAnadirView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCarrito();
            }
        });

        carritoAnadirView.getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });
    }
    private void limpiarFormulario() {
        carritoAnadirView.getTxtCodigo().setText("");
        carritoAnadirView.getTxtNombre().setText("");
        carritoAnadirView.getTxtPrecio().setText("");
        carritoAnadirView.getCBoxCantidad().setSelectedIndex(0);
        carritoAnadirView.getTxtSubtotal().setText("");
        carritoAnadirView.getTxtIVA().setText("");
        carritoAnadirView.getTxtTotal().setText("");


        DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTblCarrito().getModel();
        modelo.setRowCount(0);

        carrito = new Carrito();
    }


    private void guardarCarrito() {
        carritoDAO.crear(carrito);
        carritoAnadirView.mostrarMensaje("Carrito creado correctamente");
        System.out.println(carritoDAO.listarTodos());
    }

    private void anadirProducto() {

        int codigo = Integer.parseInt(carritoAnadirView.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        int cantidad =  Integer.parseInt(carritoAnadirView.getCBoxCantidad().getSelectedItem().toString());
        carrito.agregarProducto(producto, cantidad);

        cargarProductos();
        mostrarTotales();

    }
    private void buscarProductoEnCarrito() {
        String txtCod = carritoAnadirView.getTxtCodigo().getText().trim();
        if (!txtCod.matches("\\d+")) {
            carritoAnadirView.mostrarMensaje("Código inválido.");
            return;
        }

        int codigo = Integer.parseInt(txtCod);
        Producto producto = productoDAO.buscarPorCodigo(codigo);

        if (producto != null) {
            carritoAnadirView.getTxtNombre().setText(producto.getNombre());
            carritoAnadirView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
        } else {
            carritoAnadirView.mostrarMensaje("Producto no encontrado.");
            carritoAnadirView.limpiarCamposProducto();
        }
    }


    private void cargarProductos(){

        List<ItemCarrito> items = carrito.obtenerItems();
        DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTblCarrito().getModel();
        modelo.setNumRows(0);
        for (ItemCarrito item : items) {
            modelo.addRow(new Object[]{ item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    item.getProducto().getPrecio(),
                    item.getCantidad(),
                    item.getProducto().getPrecio() * item.getCantidad() });
        }
    }

    private void mostrarTotales(){
            Double subtotalValue = carrito.calcularSubtotal();
            Double ivaValue = carrito.calcularIVA();
            Double totalValue = carrito.calcularTotal();

            String subtotal = (subtotalValue != null) ? String.valueOf(subtotalValue) : "0.0";
            String iva = (ivaValue != null) ? String.valueOf(ivaValue) : "0.0";
            String total = (totalValue != null) ? String.valueOf(totalValue) : "0.0";

            carritoAnadirView.getTxtSubtotal().setText(subtotal);
            carritoAnadirView.getTxtIVA().setText(iva);
            carritoAnadirView.getTxtTotal().setText(total);
        }

    }


