package ec.edu.vista;

import ec.edu.controlador.CarritoController;
import ec.edu.controlador.ProductoController;
import ec.edu.controlador.RegistroController;
import ec.edu.controlador.UsuarioController;
import ec.edu.modelo.Rol;
import ec.edu.modelo.Usuario;
import ec.edu.util.ActualizablePorIdioma;
import ec.edu.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.ResourceBundle;

import static ec.edu.vista.Main.cerrarVentanaExistente;

public class MenuPrincipalView extends JFrame {
    private MensajeInternacionalizacionHandler mensajeHandler;
    private JMenuBar menuBar;

    private JMenu menuProducto;
    private JMenu menuCarrito;
    private JMenu menuSalir;

    private JMenuItem menuItemCrearProducto;
    private JMenuItem menuItemEliminarProducto;
    private JMenuItem menuItemActualizarProducto;
    private JMenuItem menuItemBuscarProducto;

    private JMenuItem menuItemCrearCarrito;
    private JMenuItem menuItemVerMisCarritos;
    private JMenuItem menuItemModificarMiCarrito;
    private JMenuItem menuItemCambiarContrasenia;
    private JMenuItem menuItemCerrarSesion;

    private MiJDesktopPane jDesktopPane;


    private  ProductoController productoController;
    private final CarritoController carritoController;
    private final UsuarioController usuarioController;

    public void setProductoController(ProductoController productoController) {
        this.productoController = productoController;
    }

    public MenuPrincipalView(MensajeInternacionalizacionHandler handler,
                             ProductoController productoController,
                             CarritoController carritoController,
                             UsuarioController usuarioController) {
        this.mensajeHandler = handler;
        this.productoController = productoController;
        this.carritoController = carritoController;
        this.usuarioController = usuarioController;

        jDesktopPane = new MiJDesktopPane();
        setContentPane(jDesktopPane);
        menuBar = new JMenuBar();

        menuProducto = new JMenu();
        menuCarrito = new JMenu();
        menuSalir = new JMenu();

        menuItemCrearProducto = new JMenuItem();
        menuItemEliminarProducto = new JMenuItem();
        menuItemActualizarProducto = new JMenuItem();
        menuItemBuscarProducto = new JMenuItem();
        menuItemCrearCarrito = new JMenuItem();
        menuItemVerMisCarritos = new JMenuItem();
        menuItemModificarMiCarrito = new JMenuItem();
        menuItemCambiarContrasenia = new JMenuItem();
        menuItemCerrarSesion = new JMenuItem();

        menuProducto.add(menuItemCrearProducto);
        menuProducto.add(menuItemEliminarProducto);
        menuProducto.add(menuItemActualizarProducto);
        menuProducto.add(menuItemBuscarProducto);

        menuCarrito.add(menuItemCrearCarrito);
        menuCarrito.add(menuItemVerMisCarritos);
        menuCarrito.add(menuItemModificarMiCarrito);

        menuSalir.add(menuItemCambiarContrasenia);
        menuSalir.add(menuItemCerrarSesion);

        menuBar.add(menuProducto);
        menuBar.add(menuCarrito);
        menuBar.add(menuSalir);

        setJMenuBar(menuBar);
        setContentPane(jDesktopPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);


        configurarMenuIdiomas();
        actualizarTextos();
    }

    private void configurarMenuIdiomas() {
        JMenu menuIdiomas = new JMenu(mensajeHandler.get("menu.idiomas"));

        JMenuItem esp = new JMenuItem("Español");
        JMenuItem ing = new JMenuItem("English");
        JMenuItem fra = new JMenuItem("Français");
        JMenuItem por = new JMenuItem("Português");

        esp.addActionListener(e -> cambiarIdioma("es", "EC"));
        ing.addActionListener(e -> cambiarIdioma("en", "US"));
        fra.addActionListener(e -> cambiarIdioma("fr", "FR"));
        por.addActionListener(e -> cambiarIdioma("pt", "PT"));

        menuIdiomas.add(esp);
        menuIdiomas.add(ing);
        menuIdiomas.add(fra);
        menuIdiomas.add(por);

        menuBar.add(menuIdiomas);
    }

    private void cambiarIdioma(String lang, String country) {
        mensajeHandler.setLenguaje(lang, country);
        actualizarTextos();

        for (JInternalFrame frame : jDesktopPane.getAllFrames()) {
            if (frame instanceof ActualizablePorIdioma) {
                ((ActualizablePorIdioma) frame).actualizarTextos(mensajeHandler.getBundle());
            }
        }
    }

    private void actualizarTextos() {
        setTitle(mensajeHandler.get("app.titulo"));

        menuProducto.setText(mensajeHandler.get("menu.producto"));
        menuCarrito.setText(mensajeHandler.get("menu.carrito"));
        menuSalir.setText(mensajeHandler.get("menu.salir"));

        menuItemCrearProducto.setText(mensajeHandler.get("menu.producto.crear"));
        menuItemEliminarProducto.setText(mensajeHandler.get("menu.producto.eliminar"));
        menuItemActualizarProducto.setText(mensajeHandler.get("menu.producto.actualizar"));
        menuItemBuscarProducto.setText(mensajeHandler.get("menu.producto.buscar"));

        menuItemCrearCarrito.setText(mensajeHandler.get("menu.carrito.crear"));
        menuItemVerMisCarritos.setText(mensajeHandler.get("menu.carrito.ver"));
        menuItemModificarMiCarrito.setText(mensajeHandler.get("menu.carrito.modificar"));

        menuItemCambiarContrasenia.setText(mensajeHandler.get("menu.salir.cambiarContrasenia"));
        menuItemCerrarSesion.setText(mensajeHandler.get("menu.salir.cerrarSesion"));
    }

    public JMenuItem getMenuItemCrearProducto() {
        return menuItemCrearProducto;
    }

    public JMenuItem getMenuItemEliminarProducto() {
        return menuItemEliminarProducto;
    }

    public JMenuItem getMenuItemActualizarProducto() {
        return menuItemActualizarProducto;
    }

    public JMenuItem getMenuItemBuscarProducto() {
        return menuItemBuscarProducto;
    }

    public JMenuItem getMenuItemCrearCarrito() {
        return menuItemCrearCarrito;
    }

    public JMenuItem getMenuItemVerMisCarritos() {
        return menuItemVerMisCarritos;
    }

    public JMenuItem getMenuItemModificarMiCarrito() {
        return menuItemModificarMiCarrito;
    }

    public JMenuItem getMenuItemCambiarContrasenia() {
        return menuItemCambiarContrasenia;
    }

    public JMenuItem getMenuItemCerrarSesion() {
        return menuItemCerrarSesion;
    }

    public JDesktopPane getjDesktopPane() {
        return jDesktopPane;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void deshabilitarMenusAdministrador() {
        menuItemCrearProducto.setEnabled(false);
        menuItemEliminarProducto.setEnabled(false);
        menuItemActualizarProducto.setEnabled(false);
        menuItemBuscarProducto.setEnabled(false);
    }

    public void configurarMenusPorRol(Usuario usuario) {
        if (usuario.getRol() == Rol.USUARIO) {
            menuProducto.setVisible(false);
        } else if (usuario.getRol() == Rol.ADMINISTRADOR) {
            menuProducto.setVisible(true);
            menuCarrito.setVisible(true);
        }
    }

    public void agregarVentanaInterna(JInternalFrame ventana) {
        try {

            cerrarVentanaExistente(ventana.getClass(), jDesktopPane);

            ventana.setSize(800, 600);
            ventana.setLocation(
                    (jDesktopPane.getWidth() - ventana.getWidth()) / 2,
                    (jDesktopPane.getHeight() - ventana.getHeight()) / 2
            );


            jDesktopPane.add(ventana, JLayeredPane.DEFAULT_LAYER);

            ventana.setVisible(true);
            ventana.toFront();
            if (ventana instanceof ActualizablePorIdioma) {
                ((ActualizablePorIdioma)ventana).actualizarTextos(mensajeHandler.getBundle());
            }

        } catch (Exception e) {
            System.err.println("Error al agregar ventana: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al abrir la ventana",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }




}