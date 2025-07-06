package ec.edu.controlador;

import ec.edu.dao.CarritoDAO;
import ec.edu.dao.ProductoDAO;
import ec.edu.modelo.Carrito;
import ec.edu.modelo.ItemCarrito;
import ec.edu.modelo.Producto;
import ec.edu.util.FormateadorUtils;
import ec.edu.util.MensajeInternacionalizacionHandler;
import ec.edu.vista.CarritoAnadirView;
import ec.edu.vista.CarritoListaView;
import ec.edu.vista.CarritoModificarView;
import ec.edu.vista.MenuPrincipalView;

import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


public class CarritoController {
    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final CarritoAnadirView carritoAnadirView;
    private   CarritoListaView carritoListaView;
    private CarritoModificarView carritoModificarView;
    private final MensajeInternacionalizacionHandler mensajeHandler;
    private final MenuPrincipalView menuPrincipalView;

    private Carrito carritoActual;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public CarritoController(CarritoDAO carritoDAO,
                             ProductoDAO productoDAO,
                             CarritoAnadirView carritoAnadirView,
                             CarritoListaView carritoListaView,
                             CarritoModificarView carritoModificarView,
                             MensajeInternacionalizacionHandler mensajeHandler,
                             MenuPrincipalView menuPrincipalView) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.mensajeHandler = mensajeHandler;
        this.menuPrincipalView = menuPrincipalView;
        this.carritoAnadirView = carritoAnadirView;
        this.carritoListaView = carritoListaView;
        this.carritoModificarView = carritoModificarView;

        this.carritoAnadirView.setCarritoController(this);
        this.carritoListaView.setCarritoController(this);
        this.carritoModificarView.setCarritoController(this);


        carritoActual = new Carrito();
        carritoDAO.crear(carritoActual);
        cargarCarritosEnTabla();
        cargarCarritosEnComboModificar();
        cargarCarritosEnTabla();
        configurarEventosMenu();
        configurarEventos();
    }
    private void configurarEventosMenu() {

            menuPrincipalView.getMenuItemCrearCarrito().addActionListener(e -> {
                menuPrincipalView.agregarVentanaInterna(carritoAnadirView);
            });

            menuPrincipalView.getMenuItemModificarMiCarrito().addActionListener(e -> {
                menuPrincipalView.agregarVentanaInterna(carritoModificarView);
                cargarProductosCarritoEnModificar();
            });

            menuPrincipalView.getMenuItemVerMisCarritos().addActionListener(e -> {
                menuPrincipalView.agregarVentanaInterna(carritoListaView);
                cargarCarritosEnTabla();
            });
    }



    private void configurarEventos() {
        carritoAnadirView.getBtnBuscar().addActionListener(e -> buscarProducto());

        carritoAnadirView.getBtnAñadir().addActionListener(e -> agregarProductoAlCarrito());

        carritoAnadirView.getBtnGuardar().addActionListener(e -> guardarCarrito());

        carritoAnadirView.getBtnLimpiar().addActionListener(e -> carritoAnadirView.limpiarCampos());

    }

    private void buscarProducto() {
        String codigoStr = carritoAnadirView.getTxtCodigo().getText().trim();
        if (!codigoStr.matches("\\d+")) {
            carritoAnadirView.mostrarMensaje("Ingresa un código válido (solo números).");
            return;
        }
        int codigo = Integer.parseInt(codigoStr);
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto == null) {
            carritoAnadirView.mostrarMensaje("Producto no encontrado.");
            carritoAnadirView.getTxtNombre().setText("");
            carritoAnadirView.getTxtPrecio().setText("");
        } else {
            carritoAnadirView.getTxtNombre().setText(producto.getNombre());
            carritoAnadirView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
        }
    }

    private void agregarProductoAlCarrito() {
        String codigoStr = carritoAnadirView.getTxtCodigo().getText().trim();
        if (!codigoStr.matches("\\d+")) {
            carritoAnadirView.mostrarMensaje("Código inválido.");
            return;
        }
        int codigo = Integer.parseInt(codigoStr);

        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto == null) {
            carritoAnadirView.mostrarMensaje("Producto no encontrado, no se puede añadir.");
            return;
        }

        int cantidad = (int) carritoAnadirView.getCBoxCantidad().getSelectedItem();

        boolean encontrado = false;
        for (ItemCarrito item : carritoActual.obtenerItems()) {
            if (item.getProducto().getCodigo() == codigo) {
                item.setCantidad(item.getCantidad() + cantidad);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            carritoActual.agregarProducto(producto, cantidad);
        }

        actualizarTablaYTotales();
        carritoAnadirView.limpiarCampos();
    }

    private void actualizarTablaYTotales() {
        DefaultTableModel modelo = carritoAnadirView.getModelo();
        modelo.setRowCount(0);
        Locale locale = mensajeHandler.getLocale();

        for (ItemCarrito item : carritoActual.obtenerItems()) {
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    FormateadorUtils.formatearMoneda(item.getProducto().getPrecio(), locale),
                    item.getCantidad(),
                    FormateadorUtils.formatearMoneda(item.getSubtotal(), locale)
            });

        }

        carritoAnadirView.actualizarTotalesFormateados(
                carritoActual.calcularSubtotal(),
                carritoActual.calcularIVA(),
                carritoActual.calcularTotal()
        );

    }

    private void guardarCarrito() {
        carritoDAO.actualizar(carritoActual);
        carritoAnadirView.mostrarMensaje("Carrito guardado correctamente!");
        carritoActual = new Carrito();
        carritoDAO.crear(carritoActual);
        actualizarTablaYTotales();
        carritoModificarView.getCBoxCarritos().addItem(carritoActual);

    }

    public void cargarCarritosEnTabla() {
        DefaultTableModel modelo = carritoListaView.getModelo();
        modelo.setRowCount(0);
        List<Carrito> carritos = carritoDAO.listarTodos();

        for (Carrito c : carritos) {
            modelo.addRow(new Object[]{
                    c.getCodigo(),
                    new SimpleDateFormat("dd/MM/yyyy").format(c.getFechaCreacion().getTime()),
                    "UsuarioX",
                    c.calcularTotal()
            });
        }
    }

    public void cargarCarritosEnComboModificar() {
        List<Carrito> carritos = carritoDAO.listarTodos();

        carritoModificarView.getCBoxCarritos().removeAllItems();

        for (Carrito c : carritos) {
            carritoModificarView.getCBoxCarritos().addItem(c);
        }

        if (!carritos.isEmpty()) {
            establecerCarritoActual(carritos.get(0).getCodigo());
            carritoModificarView.cargarProductosEnTabla();
            carritoModificarView.actualizarTotales();
        }
    }


    public void establecerCarritoActual(int codigoCarrito) {
        Carrito c = carritoDAO.buscarPorCodigo(codigoCarrito);
        if (c != null) {
            carritoActual = c;
        }
    }

    public void cargarProductosCarritoEnModificar() {
        carritoModificarView.actualizarTotales();
    }

    public boolean modificarCantidadProductoEnCarrito(String codigoProducto, int nuevaCantidad) {
        for (ItemCarrito item : carritoActual.getItems()) {
            if (item.getProducto().getCodigo() == Integer.parseInt(codigoProducto)) {
                item.setCantidad(nuevaCantidad);
                carritoDAO.actualizar(carritoActual);
                carritoModificarView.actualizarTotales();
                return true;
            }
        }
        return false;
    }

    public boolean eliminarProductoDelCarrito(String codigoProducto) {
        Iterator<ItemCarrito> iterator = carritoActual.getItems().iterator();

        while (iterator.hasNext()) {
            ItemCarrito item = iterator.next();
            if (item.getProducto().getCodigo() == Integer.parseInt(codigoProducto)) {
                iterator.remove();
                carritoDAO.actualizar(carritoActual);
                return true;
            }
        }
        return false;
    }

    public Carrito getCarrito() {
        return carritoActual;
    }
    public void setCarrito(Carrito carrito) {
        this.carritoActual = carrito;
    }
    public void setCarritoListaView(CarritoListaView carritoListaView) {
        this.carritoListaView = carritoListaView;
    }

    public void setCarritoModificarView(CarritoModificarView carritoModificarView) {
        this.carritoModificarView = carritoModificarView;
        this.carritoModificarView.setCarritoController(this);
    }



}