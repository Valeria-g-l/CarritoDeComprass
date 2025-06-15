package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.ProductoAnadirView;
import ec.edu.ups.vista.ProductoListaView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoController {

    private final ProductoAnadirView productoAnadirView;
    private final ProductoListaView productoListaView;
    private final ProductoDAO productoDAO;

    public ProductoController(ProductoDAO productoDAO,
                              ProductoAnadirView productoAnadirView,
                              ProductoListaView productoListaView) {
        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.productoListaView = productoListaView;
        configurarEventos();
    }

    private void configurarEventos() {
        productoAnadirView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });

        productoListaView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });

    }

    private void guardarProducto() {
        int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
        String nombre = productoAnadirView.getTxtNombre().getText();
        double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText());

        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAnadirView.mostrarMensaje("Producto guardado correctamente");
        productoAnadirView.limpiarCampos();
        productoAnadirView.mostrarProductos(productoDAO.listarTodos());
    }

    private void buscarProducto() {
        String nombre = productoListaView.getTxtBuscar().getText();

        List<Producto> productosEncontrados = productoDAO.buscarPorNombre(nombre);
        productoListaView.cargarDatos(productosEncontrados);
    }

    private void listarProductos() {
        List<Producto> productos = productoDAO.listarTodos();
        productoListaView.cargarDatos(productos);
    }

 /**   private void buscarProductoGestion() {
        String txtCod = productoListaView.getTxtBuscar().getText();
        if (!txtCod.isEmpty()) {
            int codigo = Integer.parseInt(txtCod);
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                productoListaView.cargarDatos(List.of(producto));
            } else {
                productoListaView.mostrarMensaje("Producto no encontrado");
                productoListaView.cargarDatos(List.of());
                productoListaView.limpiarCampos();
            }
        } else {
            productoListaView.mostrarMensaje("Ingresa un código para buscar");
        }
    }

    private void actualizarProducto() {
        int codigo = productoListaView.getCodigoProductoSeleccionado();
        String nombre = productoListaView.getTxtNombre().getText();
        String txtPrecio = productoListaView.getTxtPrecio().getText();

        if (codigo != -1 && !nombre.isEmpty() && !txtPrecio.isEmpty()) {
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                double precio = Double.parseDouble(txtPrecio);
                producto.setNombre(nombre);
                producto.setPrecio(precio);
                productoDAO.actualizar(producto);
                productoListaView.mostrarMensaje("Producto actualizado correctamente");
                productoListaView.cargarDatos(List.of(producto));
            }
        } else {
            productoListaView.mostrarMensaje("Selecciona un producto de la tabla y completa los datos");
        }
    }

    private void eliminarProducto() {
        int codigo = productoListaView.getCodigoProductoSeleccionado();
        if (codigo != -1) {
            productoDAO.eliminar(codigo);
            productoListaView.mostrarMensaje("Producto eliminado correctamente");
            productoListaView.cargarDatos(List.of());
            productoListaView.limpiarCampos();
        } else {
            productoListaView.mostrarMensaje("Selecciona un producto para eliminar");
        }
    }*/


}


