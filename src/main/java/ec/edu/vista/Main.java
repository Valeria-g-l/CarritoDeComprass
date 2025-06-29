package ec.edu.vista;

import ec.edu.controlador.CarritoController;
import ec.edu.controlador.ProductoController;
import ec.edu.controlador.UsuarioController;
import ec.edu.dao.CarritoDAO;
import ec.edu.dao.ProductoDAO;
import ec.edu.dao.UsuarioDAO;
import ec.edu.dao.impl.CarritoDAOMemoria;
import ec.edu.dao.impl.ProductoDAOMemoria;
import ec.edu.dao.impl.UsuarioDAOMemoria;
import ec.edu.modelo.Rol;
import ec.edu.modelo.Usuario;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
            LoginView loginView = new LoginView();
            loginView.setVisible(true);

            UsuarioController usuarioController = new UsuarioController(usuarioDAO, loginView);

            loginView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    Usuario usuarioAutenticado = usuarioController.getUsuarioAutenticado();

                    if (usuarioAutenticado != null) {


                        String idiomaSeleccionado = loginView.getIdiomaSeleccionado();
                        Locale locale;
                        switch (idiomaSeleccionado) {
                            case "English":
                                locale = new Locale("en");
                                break;
                            case "Français":
                                locale = new Locale("fr");
                                break;
                            default:
                                locale = new Locale("es");
                        }
                        ResourceBundle mensajes = ResourceBundle.getBundle("messages", locale);

                        ProductoDAO productoDAO = new ProductoDAOMemoria();
                        CarritoDAO carritoDAO = new CarritoDAOMemoria();


                        MenuPrincipalView menuPrincipal = new MenuPrincipalView(mensajes);
                        ProductoAnadirView productoAnadirView = new ProductoAnadirView(mensajes);
                        ProductoListaView productoListaView = new ProductoListaView(mensajes);
                        ProductoEliminarView productoEliminarView = new ProductoEliminarView(mensajes);
                        ProductoModificarView productoModificarView = new ProductoModificarView(mensajes);
                        CarritoAnadirView carritoAnadirView = new CarritoAnadirView(mensajes);

                        ProductoController productoController = new ProductoController(productoDAO,
                                productoAnadirView, productoListaView, productoModificarView, productoEliminarView);
                        CarritoController carritoController = new CarritoController(carritoDAO, productoDAO, carritoAnadirView);


                        menuPrincipal.mostrarMensaje(mensajes.getString("bienvenido") + ": " + usuarioAutenticado.getUsername());

                        if (usuarioAutenticado.getRol().equals(Rol.USUARIO)) {
                            menuPrincipal.deshabilitarMenusAdministrador();
                        }

                        configurarEventosMenu(menuPrincipal, productoAnadirView, productoListaView,
                                productoModificarView, productoEliminarView, carritoAnadirView);

                        menuPrincipal.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se inició sesión. Saliendo...");
                        System.exit(0);
                    }
                }
            });
        });
    }

    private static void configurarEventosMenu(MenuPrincipalView menu,
                                              ProductoAnadirView anadirView,
                                              ProductoListaView listaView,
                                              ProductoModificarView modificarView,
                                              ProductoEliminarView eliminarView,
                                              CarritoAnadirView carritoAnadirView) {
        menu.getMenuItemCrearProducto().addActionListener(e -> {
            if (!anadirView.isVisible()) {
                menu.getjDesktopPane().add(anadirView);
                anadirView.setVisible(true);
            }
        });

        menu.getMenuItemBuscarProducto().addActionListener(e -> {
            if (!listaView.isVisible()) {
                menu.getjDesktopPane().add(listaView);
                listaView.setVisible(true);
            }
        });

        menu.getMenuItemActualizarProducto().addActionListener(e -> {
            if (!modificarView.isVisible()) {
                menu.getjDesktopPane().add(modificarView);
                modificarView.setVisible(true);
            }
        });

        menu.getMenuItemEliminarProducto().addActionListener(e -> {
            if (!eliminarView.isVisible()) {
                menu.getjDesktopPane().add(eliminarView);
                eliminarView.setVisible(true);
            }
        });

        menu.getMenuItemCrearCarrito().addActionListener(e -> {
            if (!carritoAnadirView.isVisible()) {
                menu.getjDesktopPane().add(carritoAnadirView);
                carritoAnadirView.setVisible(true);
            }
        });


        menu.getMenuItemVerMisCarritos().addActionListener(e -> {
            System.out.println(" Ver mis carritos aún no implementado");
        });

        menu.getMenuItemModificarMiCarrito().addActionListener(e -> {
            System.out.println(" Modificar mis carritos aún no implementado");
        });

        menu.getMenuItemCambiarContrasenia().addActionListener(e -> {
            System.out.println(" Cambiar contraseña aún no implementado");
        });

        menu.getMenuItemCerrarSesion().addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(
                    menu,
                    mensajes.getString("confirmacion.cerrarSesion"),
                    mensajes.getString("titulo.cerrarSesion"),
                    JOptionPane.YES_NO_OPTION
            );
            if (confirmacion == JOptionPane.YES_OPTION) {
                menu.dispose();
                LoginView nuevoLogin = new LoginView();
                nuevoLogin.setVisible(true);
                new UsuarioController(new UsuarioDAOMemoria(), nuevoLogin);
            }
        });
    }
}
