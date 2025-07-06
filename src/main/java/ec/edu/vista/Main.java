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
import ec.edu.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static MensajeInternacionalizacionHandler mensajeHandler = new MensajeInternacionalizacionHandler("es", "EC");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
            ProductoDAO productoDAO = new ProductoDAOMemoria();
            CarritoDAO carritoDAO = new CarritoDAOMemoria();

            iniciarSesion(usuarioDAO, productoDAO, carritoDAO);
        });
    }

    private static void iniciarSesion(UsuarioDAO usuarioDAO, ProductoDAO productoDAO, CarritoDAO carritoDAO) {
        LoginView loginView = new LoginView(mensajeHandler);
        UsuarioController loginController = new UsuarioController(usuarioDAO, loginView, mensajeHandler);
        loginView.setVisible(true);

        loginView.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                Usuario usuarioAutenticado = loginController.getUsuarioAutenticado();

                if (usuarioAutenticado != null) {
                    ProductoAnadirView productoAnadirView = new ProductoAnadirView(mensajeHandler);
                    ProductoListaView productoListaView = new ProductoListaView(mensajeHandler);
                    ProductoEliminarView productoEliminarView = new ProductoEliminarView(mensajeHandler);
                    ProductoModificarView productoModificarView = new ProductoModificarView(mensajeHandler);

                    CarritoAnadirView carritoAnadirView = new CarritoAnadirView(mensajeHandler);
                    CarritoListaView carritoListaView = new CarritoListaView(mensajeHandler);
                    CarritoModificarView carritoModificarView = new CarritoModificarView(mensajeHandler);

                    MenuPrincipalView menuPrincipal = new MenuPrincipalView(mensajeHandler, null, null, null);
                    UsuarioController usuarioControllerMenu = new UsuarioController(usuarioDAO, menuPrincipal, usuarioAutenticado, mensajeHandler);

                    ProductoController productoController = new ProductoController(
                            productoDAO, productoAnadirView, productoListaView,
                            productoModificarView, productoEliminarView, menuPrincipal, mensajeHandler
                    );
                    CarritoController carritoController = new CarritoController(
                            carritoDAO, productoDAO, carritoAnadirView, carritoListaView,
                            carritoModificarView, mensajeHandler, menuPrincipal
                    );

                    productoAnadirView.setProductoController(productoController);
                    productoListaView.setProductoController(productoController);
                    productoModificarView.setProductoController(productoController);
                    productoEliminarView.setProductoController(productoController);

                    carritoAnadirView.setCarritoController(carritoController);
                    carritoListaView.setCarritoController(carritoController);
                    carritoModificarView.setCarritoController(carritoController);

                    menuPrincipal.setProductoController(productoController);

                    if (usuarioAutenticado.getRol() == Rol.USUARIO) {
                        menuPrincipal.deshabilitarMenusAdministrador();
                    }

                    menuPrincipal.configurarMenusPorRol(usuarioAutenticado);

                    configurarEventosMenu(menuPrincipal,
                            productoAnadirView, productoListaView,
                            productoModificarView, productoEliminarView,
                            carritoAnadirView, carritoListaView,
                            carritoModificarView, mensajeHandler,
                            productoController, carritoController, usuarioControllerMenu);

                    menuPrincipal.setVisible(true);

                    // Manejar logout
                    menuPrincipal.getMenuItemCerrarSesion().addActionListener(evt -> {
                        int confirmacion = JOptionPane.showConfirmDialog(
                                menuPrincipal,
                                mensajeHandler.get("confirmacion.cerrarSesion"),
                                mensajeHandler.get("titulo.cerrarSesion"),
                                JOptionPane.YES_NO_OPTION
                        );
                        if (confirmacion == JOptionPane.YES_OPTION) {
                            menuPrincipal.dispose();
                            iniciarSesion(usuarioDAO, productoDAO, carritoDAO); // 👈 vuelve a iniciar sesión
                        }
                    });

                } else {
                    JOptionPane.showMessageDialog(null, "No se inició sesión. Saliendo...");
                    System.exit(0);
                }
            }
        });
    }

    public static void cerrarVentanaExistente(Class<? extends JInternalFrame> clase, JDesktopPane desktopPane) {
        for (JInternalFrame ventana : desktopPane.getAllFrames()) {
            if (clase.isInstance(ventana)) {
                ventana.dispose();
                break;
            }
        }
    }

    private static void configurarEventosMenu(MenuPrincipalView menu,
                                              ProductoAnadirView productoAnadirView,
                                              ProductoListaView productoListaView,
                                              ProductoModificarView productoModificarView,
                                              ProductoEliminarView productoEliminarView,
                                              CarritoAnadirView carritoAnadirView,
                                              CarritoListaView carritoListaView,
                                              CarritoModificarView carritoModificarView,
                                              MensajeInternacionalizacionHandler mensajes,
                                              ProductoController productoController,
                                              CarritoController carritoController,
                                              UsuarioController usuarioController) {

        menu.getMenuItemCrearProducto().addActionListener(e -> {
            cerrarVentanaExistente(ProductoAnadirView.class, menu.getjDesktopPane());
            menu.agregarVentanaInterna(productoAnadirView);
        });

        menu.getMenuItemBuscarProducto().addActionListener(e -> {
            cerrarVentanaExistente(ProductoListaView.class, menu.getjDesktopPane());
            menu.agregarVentanaInterna(productoListaView);
        });

        menu.getMenuItemActualizarProducto().addActionListener(e -> {
            cerrarVentanaExistente(ProductoModificarView.class, menu.getjDesktopPane());
            menu.agregarVentanaInterna(productoModificarView);
        });

        menu.getMenuItemEliminarProducto().addActionListener(e -> {
            cerrarVentanaExistente(ProductoEliminarView.class, menu.getjDesktopPane());
            menu.agregarVentanaInterna(productoEliminarView);
        });

        menu.getMenuItemCambiarContrasenia().addActionListener(e -> {
            if (usuarioController != null) {
                usuarioController.mostrarCambiarContrasenia();
            }
        });
    }
}
