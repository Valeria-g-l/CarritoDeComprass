package ec.edu.vista;

import ec.edu.controlador.RegistroController;
import ec.edu.modelo.Rol;
import ec.edu.modelo.Usuario;
import ec.edu.util.FondoUtils;
import ec.edu.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

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

    private JDesktopPane jDesktopPane;

    public MenuPrincipalView(MensajeInternacionalizacionHandler handler) {
        this.mensajeHandler = handler;

        jDesktopPane = new JDesktopPane();
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

        FondoUtils.ponerFondo(this, "/Imagenes/img1.gif");
        this.setLocationRelativeTo(null);

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
        if (!ventana.isVisible()) {
            jDesktopPane.add(ventana);
            ventana.setVisible(true);
            try {
                ventana.setSelected(true);
            } catch (java.beans.PropertyVetoException e) {
                e.printStackTrace();
            }
        }
    }
}
