package ec.edu.controlador;

import ec.edu.dao.ProductoDAO;
import ec.edu.modelo.Producto;
import ec.edu.util.MensajeInternacionalizacionHandler;
import ec.edu.vista.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;




public class ProductoController {
    private ProductoAnadirView productoAnadirView;
    private ProductoListaView productoListaView;
    private ProductoDAO productoDAO;
    private ProductoModificarView productoModificarView;
    private ProductoEliminarView productoEliminarView;
    private MenuPrincipalView menuPrincipalView;
    private  MensajeInternacionalizacionHandler mensajeHandler;


    public ProductoController(ProductoDAO productoDAO,
                              ProductoAnadirView productoAnadirView,
                              ProductoListaView productoListaView,
                              ProductoModificarView productoModificarView,
                              ProductoEliminarView productoEliminarView,
                              MenuPrincipalView menuPrincipalView,
                              MensajeInternacionalizacionHandler mensajeHandler) {
        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.productoListaView = productoListaView;
        this.productoModificarView = productoModificarView;
        this.productoEliminarView = productoEliminarView;
        this.menuPrincipalView = menuPrincipalView;
        this.mensajeHandler = mensajeHandler;
        configurarEventosMenu();
        System.out.println("[DEBUG] Vista recibida en ProductoController: " + productoAnadirView);
        System.out.println("[DEBUG] Botón guardar en vista: " + productoAnadirView.getBtnGuardar());
        configurarEventos();
    }
    private void configurarEventosMenu() {
        menuPrincipalView.getMenuItemCrearProducto().addActionListener(e -> {
            menuPrincipalView.agregarVentanaInterna(productoAnadirView);
        });

        menuPrincipalView.getMenuItemEliminarProducto().addActionListener(e -> {
            menuPrincipalView.agregarVentanaInterna(productoEliminarView);
        });

        menuPrincipalView.getMenuItemActualizarProducto().addActionListener(e -> {
            menuPrincipalView.agregarVentanaInterna(productoModificarView);
        });

        menuPrincipalView.getMenuItemBuscarProducto().addActionListener(e -> {
            menuPrincipalView.agregarVentanaInterna(productoListaView);
        });
    }

    public ProductoModificarView getProductoModificarView() {
        return productoModificarView;
    }
    public ProductoController(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    private void configurarEventos() {
        productoAnadirView.getBtnGuardar().addActionListener(e -> {
            guardarProducto();
        });

        productoListaView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoPorCodigo();
            }
        });


        productoModificarView.getBtnBuscar().addActionListener(e -> buscarProductoEdicion());

        productoModificarView.getBtnActualizar().addActionListener(e -> actualizarProducto());

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

    public void guardarProducto() {
        System.out.println("[DEBUG] guardarProducto() ejecutándose");
        String txtCod = productoAnadirView.getTxtCodigo().getText().trim();
        String nombre = productoAnadirView.getTxtNombre().getText().trim();
        String txtPrecio = productoAnadirView.getTxtPrecio().getText().trim();


        if (txtCod.isEmpty() || nombre.isEmpty() || txtPrecio.isEmpty()) {
            productoAnadirView.mostrarMensaje("Por favor, complete todos los campos.");
            return;
        }

        if (!txtCod.matches("\\d+")) {
            productoAnadirView.mostrarMensaje("El código debe ser un número entero válido.");
            return;
        }

        if (!txtPrecio.matches("\\d+(\\.\\d+)?")) {
            productoAnadirView.mostrarMensaje("El precio debe ser un número válido.");
            return;
        }

        int codigo = Integer.parseInt(txtCod);
        double precio = Double.parseDouble(txtPrecio);

        Producto nuevoProducto = new Producto(codigo, nombre, precio);
        productoDAO.crear(nuevoProducto);

        productoAnadirView.mostrarMensaje("Producto guardado correctamente");
        productoAnadirView.limpiarCampos();
        productoListaView.cargarDatos(productoDAO.listarTodos());
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
        String txtCod = productoModificarView.getTxtCodigo().getText().trim();
        String nombre = productoModificarView.getTxtNombre().getText().trim();
        String txtPrecio = productoModificarView.getTxtPrecio().getText().trim();

        if (txtCod.isEmpty() || nombre.isEmpty() || txtPrecio.isEmpty()) {
            productoModificarView.mostrarMensaje("Por favor, complete todos los campos.");
            return;
        }

        if (!txtCod.matches("\\d+")) {
            productoModificarView.mostrarMensaje("El código debe ser un número entero válido.");
            return;
        }
        if (!txtPrecio.matches("\\d+(\\.\\d+)?")) {
            productoModificarView.mostrarMensaje("El precio debe ser un número decimal válido.");
            return;
        }

        int codigo = Integer.parseInt(txtCod);
        double precio = Double.parseDouble(txtPrecio);

        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto != null) {
            boolean confirmado = productoModificarView.mostrarMensajePregunta("¿Desea actualizar el producto?");
            if (confirmado) {
                producto.setNombre(nombre);
                producto.setPrecio(precio);
                productoDAO.actualizar(producto);
                productoModificarView.mostrarMensaje("Producto actualizado correctamente");
            } else {
                productoModificarView.mostrarMensaje("Actualización cancelada");
            }
        } else {
            productoModificarView.mostrarMensaje("Producto no encontrado para actualizar");
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
    private void buscarProductoPorCodigo() {
        String textoBusqueda = productoListaView.getTxtBuscar().getText().trim();

        if (textoBusqueda.isEmpty()) {
            productoListaView.cargarDatos(productoDAO.listarTodos());
            return;
        }

        if (!textoBusqueda.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "El código debe ser un número entero válido.");
            return;
        }

        int codigo = Integer.parseInt(textoBusqueda);
        Producto producto = productoDAO.buscarPorCodigo(codigo);

        if (producto != null) {
            productoListaView.cargarDatos(List.of(producto));
        } else {
            productoListaView.cargarDatos(List.of()); // tabla vacía
            JOptionPane.showMessageDialog(null, "Producto no encontrado");
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
        productoAnadirView.getBtnGuardar().addActionListener(e -> {
            System.out.println("[DEBUG] Evento de guardar activado");
            this.guardarProducto();
        });
    }

}

