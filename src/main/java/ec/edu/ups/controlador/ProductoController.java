package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.ProductoAnadirView;
import ec.edu.ups.vista.ProductoModificarView;
import ec.edu.ups.vista.ProductoEliminarView;
import ec.edu.ups.vista.ProductoListaView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class ProductoController {
    private ProductoAnadirView productoAnadirView;
    private ProductoListaView productoListaView;
    private ProductoDAO productoDAO;
    private ProductoModificarView productoModificarView;
    private ProductoEliminarView productoEliminarView;


    public ProductoController(ProductoDAO productoDAO,
                              ProductoAnadirView productoAnadirView,
                              ProductoListaView productoListaView ) {
        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.productoListaView = productoListaView;
        this.productoModificarView = productoModificarView;
        this.productoEliminarView = productoEliminarView;
        configurarEventos();
        System.out.println("Vista modificar es null? " + (productoModificarView == null));
    }
    public ProductoModificarView getProductoModificarView() {
        return productoModificarView;
    }
    public ProductoController(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    private void configurarEventos() {
        productoAnadirView.getBtnGuardar().addActionListener(new ActionListener() {
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

        productoListaView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarProductos();
            }
        });

        productoModificarView.getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoEdicion();
            }
        });

        productoModificarView.getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProducto();
            }
        });

        productoEliminarView.getBtnEliminar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });
        productoEliminarView.getBtnBuscar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoEliminar();
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

    private void buscarProductoEdicion() {
        String txtCod = productoModificarView.getTxtCodigo().getText();
        if (!txtCod.isEmpty()) {
            int codigo = Integer.parseInt(txtCod);
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                productoModificarView.getTxtNombre().setText(producto.getNombre());
                productoModificarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            } else {
                productoModificarView.mostrarMensaje("Producto no encontrado");
                productoModificarView.limpiarCampos();
            }
        } else {
            productoModificarView.mostrarMensaje("Ingresa un código para buscar");
        }
    }

    private void actualizarProducto() {
        String txtCod = productoModificarView.getTxtCodigo().getText();
        int codigo = Integer.parseInt(txtCod);
        String nombre = productoModificarView.getTxtNombre().getText();
        String txtPrecio = productoModificarView.getTxtPrecio().getText();
        if (codigo != -1 ) {
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                boolean confirmado = productoModificarView.mostrarMensajePregunta("¿Desea actualizar el producto?");
                if (confirmado) {
                    double precio = Double.parseDouble(txtPrecio);
                    producto.setNombre(nombre);
                    producto.setPrecio(precio);
                    productoDAO.actualizar(producto);
                    productoModificarView.mostrarMensaje("Producto actualizado correctamente");
                }else{
                    productoEliminarView.mostrarMensaje("Actualización cancelada");
                }
            }
        } else {
            productoModificarView.mostrarMensaje("Ingrese un código de producto válido");
        }
    }

    private void eliminarProducto() {
        String text_codigo = productoEliminarView.getTxtCodigo().getText();
        int codigo = Integer.parseInt(text_codigo);
        if (codigo != -1) {
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                boolean confirmado = productoEliminarView.mostrarMensajePregunta("¿Desea eliminar el producto?");
                if (confirmado) {
                    productoDAO.eliminar(codigo);
                    productoEliminarView.mostrarMensaje("Producto eliminado correctamente");
                    productoEliminarView.limpiarCampos();
                } else {
                    productoEliminarView.mostrarMensaje("Eliminación cancelada");
                }
            } else {
                productoEliminarView.mostrarMensaje("Producto no encontrado");
            }
        } else {
            productoEliminarView.mostrarMensaje("Ingrese un código de producto válido");
        }
    }

    private void buscarProductoEliminar() {
            String txtCod = productoEliminarView.getTxtCodigo().getText();
            if (!txtCod.isEmpty()) {
                int codigo = Integer.parseInt(txtCod);
                Producto producto = productoDAO.buscarPorCodigo(codigo);
                if (producto != null) {
                    productoEliminarView.mostrarMensaje("Producto encontrado: " + producto.getNombre());
                } else {
                    productoEliminarView.mostrarMensaje("Producto no encontrado");
                    productoEliminarView.limpiarCampos();
                }
            } else {
                productoEliminarView.mostrarMensaje("Ingresa un código para buscar");
            }
        }

    public void setProductoEditarView(ProductoModificarView productoEditarView) {
        this.productoModificarView = productoEditarView;
    }

    public void setProductoEliminarView(ProductoEliminarView productoEliminarView) {
        this.productoEliminarView = productoEliminarView;
    }

    public void setProductoListaView(ProductoListaView productoListaView) {
        this.productoListaView = productoListaView;
    }

    public void setProductoAnadirView(ProductoAnadirView productoAnadirView) {
        this.productoAnadirView = productoAnadirView;
    }
}


