package ec.edu.controlador;

import ec.edu.dao.CarritoDAO;
import ec.edu.dao.ProductoDAO;
import ec.edu.modelo.Carrito;
import ec.edu.modelo.ItemCarrito;
import ec.edu.modelo.Producto;
import ec.edu.modelo.Usuario;
import ec.edu.util.FormateadorUtils;
import ec.edu.util.MensajeInternacionalizacionHandler;
import ec.edu.vista.CarritoAnadirView;
import ec.edu.vista.CarritoListaView;
import ec.edu.vista.CarritoModificarView;
import ec.edu.vista.MenuPrincipalView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Controlador encargado de gestionar la lógica de los carritos de compras.
 * Coordina la interacción entre vistas, modelos y DAOs.
 * Permite crear, modificar, listar y guardar carritos del usuario autenticado.
 */
public class CarritoController {
    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final CarritoAnadirView carritoAnadirView;
    private CarritoListaView carritoListaView;
    private CarritoModificarView carritoModificarView;
    private final MensajeInternacionalizacionHandler mensajeHandler;
    private final MenuPrincipalView menuPrincipalView;
    private final Usuario usuarioAutenticado;
    private Carrito carritoActual;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Constructor del controlador de carritos.
     * Inicializa referencias a DAOs, vistas, usuario y manejador de mensajes.
     * Configura los controladores de eventos y carga datos iniciales en las vistas.
     *
     * @param carritoDAO           DAO para acceder a datos de carritos.
     * @param productoDAO          DAO para acceder a datos de productos.
     * @param carritoAnadirView    Vista para añadir productos a carritos.
     * @param carritoListaView     Vista para listar carritos del usuario.
     * @param carritoModificarView Vista para modificar carritos existentes.
     * @param mensajeHandler       Manejador de mensajes para internacionalización.
     * @param menuPrincipalView    Vista principal que contiene menú y ventanas internas.
     * @param usuarioAutenticado   Usuario actualmente autenticado en el sistema.
     */
    public CarritoController(CarritoDAO carritoDAO,
                             ProductoDAO productoDAO,
                             CarritoAnadirView carritoAnadirView,
                             CarritoListaView carritoListaView,
                             CarritoModificarView carritoModificarView,
                             MensajeInternacionalizacionHandler mensajeHandler,
                             MenuPrincipalView menuPrincipalView,
                             Usuario usuarioAutenticado) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.mensajeHandler = mensajeHandler;
        this.menuPrincipalView = menuPrincipalView;
        this.carritoAnadirView = carritoAnadirView;
        this.carritoListaView = carritoListaView;
        this.carritoModificarView = carritoModificarView;
        this.usuarioAutenticado = usuarioAutenticado;

        this.carritoAnadirView.setCarritoController(this);
        this.carritoListaView.setCarritoController(this);
        this.carritoModificarView.setCarritoController(this);

        cargarCarritosEnTabla();
        cargarCarritosEnComboModificar();
        cargarCarritosEnTabla();
        configurarEventosMenu();
        configurarEventos();
    }

    /**
     * Configura los eventos de los ítems del menú principal relacionados con los carritos.
     */
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

    /**
     * Configura los eventos de la vista de añadir carrito.
     */
    private void configurarEventos() {
        carritoAnadirView.getBtnBuscar().addActionListener(e -> buscarProducto());
        carritoAnadirView.getBtnAñadir().addActionListener(e -> agregarProductoAlCarrito());
        carritoAnadirView.getBtnGuardar().addActionListener(e -> guardarCarrito());
        carritoAnadirView.getBtnLimpiar().addActionListener(e -> carritoAnadirView.limpiarCampos());
    }

    /**
     * Busca un producto por su código ingresado en la interfaz y muestra su información.
     */
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

    /**
     * Añade un producto al carrito actual o actualiza su cantidad si ya existe.
     */
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

        if (carritoActual == null) {
            carritoActual = new Carrito();
            carritoActual.setPropietario(usuarioAutenticado);
        }

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

    /**
     * Actualiza la tabla de productos en la vista y muestra totales actualizados.
     */
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

    /**
     * Guarda el carrito actual en la base de datos (nuevo o actualización).
     */
    private void guardarCarrito() {
        if (carritoActual == null || carritoActual.obtenerItems().isEmpty()) {
            carritoAnadirView.mostrarMensaje("No puedes guardar un carrito vacío.");
            return;
        }

        if (carritoActual.getCodigo() == 0) {
            carritoActual.setPropietario(usuarioAutenticado);
            carritoDAO.crear(carritoActual);
        } else {
            carritoDAO.actualizar(carritoActual);
        }

        carritoAnadirView.mostrarMensaje("Carrito guardado correctamente!");
        actualizarTablaYTotales();
        cargarCarritosEnTabla();
        cargarCarritosEnComboModificar();
        carritoActual = null;
    }

    /**
     * Carga en la tabla todos los carritos del usuario autenticado.
     */
    public void cargarCarritosEnTabla() {
        DefaultTableModel modelo = carritoListaView.getModelo();
        modelo.setRowCount(0);
        List<Carrito> carritos = carritoDAO.listarPorUsuario(usuarioAutenticado);

        for (Carrito c : carritos) {
            modelo.addRow(new Object[]{
                    c.getCodigo(),
                    new SimpleDateFormat("dd/MM/yyyy").format(c.getFechaCreacion().getTime()),
                    c.getPropietario().getUsername(),
                    c.calcularTotal()
            });
        }
    }

    /**
     * Carga en el combo box los carritos del usuario para modificarlos.
     */
    public void cargarCarritosEnComboModificar() {
        List<Carrito> carritos = carritoDAO.listarPorUsuario(usuarioAutenticado);
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

    /**
     * Establece como "actual" el carrito correspondiente al código recibido.
     *
     * @param codigoCarrito código del carrito a establecer como actual.
     */
    public void establecerCarritoActual(int codigoCarrito) {
        Carrito c = carritoDAO.buscarPorCodigo(codigoCarrito);
        if (c != null) {
            carritoActual = c;
        }
    }

    /**
     * Actualiza los totales en la vista de modificación.
     */
    public void cargarProductosCarritoEnModificar() {
        carritoModificarView.actualizarTotales();
    }

    /**
     * Modifica la cantidad de un producto específico dentro del carrito actual.
     *
     * @param codigoProducto Código del producto a modificar.
     * @param nuevaCantidad  Nueva cantidad para el producto.
     * @return true si se modificó correctamente, false si no se encontró el producto.
     */
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

    /**
     * Elimina un producto del carrito actual según su código.
     *
     * @param codigoProducto Código del producto a eliminar.
     * @return true si se eliminó correctamente, false si no se encontró el producto.
     */
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

    /**
     * Agrega un producto al carrito actual desde la vista de modificación.
     *
     * @param codigoProducto Código del producto a agregar.
     * @param cantidad       Cantidad del producto a agregar.
     * @return true si se agregó correctamente, false si no se encontró el producto o hubo error.
     */
    public boolean agregarProductoACarrito(String codigoProducto, int cantidad) {
        try {
            int codigo = Integer.parseInt(codigoProducto);
            Producto producto = productoDAO.buscarPorCodigo(codigo);

            if (producto == null) {
                JOptionPane.showMessageDialog(null, "Producto no encontrado.");
                return false;
            }
            for (ItemCarrito item : carritoActual.getItems()) {
                if (item.getProducto().getCodigo() == codigo) {
                    item.setCantidad(item.getCantidad() + cantidad);
                    carritoDAO.actualizar(carritoActual);
                    carritoModificarView.actualizarTotales();
                    return true;
                }
            }
            carritoActual.getItems().add(new ItemCarrito(producto, cantidad));
            carritoDAO.actualizar(carritoActual);
            carritoModificarView.actualizarTotales();
            return true;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Código inválido.");
            return false;
        }
    }

    /**
     * Obtiene el carrito actual en uso.
     *
     * @return Carrito actual.
     */
    public Carrito getCarrito() {
        return carritoActual;
    }

    /**
     * Establece el carrito actual en uso.
     *
     * @param carrito Carrito a establecer como actual.
     */
    public void setCarrito(Carrito carrito) {
        this.carritoActual = carrito;
    }

    /**
     * Establece la vista de lista de carritos.
     *
     * @param carritoListaView Vista de lista de carritos.
     */
    public void setCarritoListaView(CarritoListaView carritoListaView) {
        this.carritoListaView = carritoListaView;
    }

    /**
     * Establece la vista de modificación de carritos.
     *
     * @param carritoModificarView Vista de modificación de carritos.
     */
    public void setCarritoModificarView(CarritoModificarView carritoModificarView) {
        this.carritoModificarView = carritoModificarView;
        this.carritoModificarView.setCarritoController(this);
    }
}
