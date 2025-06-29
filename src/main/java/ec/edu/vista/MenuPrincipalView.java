package ec.edu.vista;

import ec.edu.modelo.Rol;
import ec.edu.modelo.Usuario;

import javax.swing.*;

public class MenuPrincipalView extends JFrame {
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

    public MenuPrincipalView() {
        jDesktopPane = new JDesktopPane();
        menuBar = new JMenuBar();

        menuProducto = new JMenu("Producto");
        menuCarrito = new JMenu("Carrito");
        menuSalir = new JMenu("Salir");


        menuItemCrearProducto = new JMenuItem("Crear Producto");
        menuItemEliminarProducto = new JMenuItem("Eliminar Producto");
        menuItemActualizarProducto = new JMenuItem("Actualizar Producto");
        menuItemBuscarProducto = new JMenuItem("Buscar Producto");

        menuItemCrearCarrito = new JMenuItem("Crear Carrito");
        menuItemVerMisCarritos = new JMenuItem("Ver Mis Carritos");
        menuItemModificarMiCarrito = new JMenuItem("Modificar Mi Carrito");
        menuItemCambiarContrasenia = new JMenuItem("Cambiar Contraseña");
        menuItemCerrarSesion = new JMenuItem("Cerrar Sesión");


        menuBar.add(menuProducto);
        menuBar.add(menuCarrito);
        menuBar.add(menuSalir);


        menuProducto.add(menuItemCrearProducto);
        menuProducto.add(menuItemEliminarProducto);
        menuProducto.add(menuItemActualizarProducto);
        menuProducto.add(menuItemBuscarProducto);

        menuCarrito.add(menuItemCrearCarrito);
        menuCarrito.add(menuItemVerMisCarritos);
        menuCarrito.add(menuItemModificarMiCarrito);
        menuSalir.add(menuItemCambiarContrasenia);
        menuSalir.add(menuItemCerrarSesion);


        setJMenuBar(menuBar);
        setContentPane(jDesktopPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sistema de Carrito de Compras en Línea");
        setExtendedState(JFrame.MAXIMIZED_BOTH);

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
