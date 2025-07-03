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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static MensajeInternacionalizacionHandler mensajeHandler = new MensajeInternacionalizacionHandler("es", "EC");
    private static UsuarioController usuarioController;
    public static void cerrarVentanaExistente(Class<? extends JInternalFrame> clase, JDesktopPane desktopPane) {
        for (JInternalFrame ventana : desktopPane.getAllFrames()) {
            if (clase.isInstance(ventana)) {
                ventana.dispose();
                break;
            }
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
            LoginView loginView = new LoginView(mensajeHandler);
            usuarioController = new UsuarioController(usuarioDAO, loginView, mensajeHandler);
            MenuPrincipalView menuView = new MenuPrincipalView(mensajeHandler);
            loginView.setVisible(true);

            final ProductoController[] productoController = new ProductoController[1];
            final CarritoController[] carritoController = new CarritoController[1];
            final UsuarioController[] usuarioControllerMenu = new UsuarioController[1];

            loginView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    Usuario usuarioAutenticado = usuarioController.getUsuarioAutenticado();

                    if (usuarioAutenticado != null) {
                        MenuPrincipalView menuPrincipal = new MenuPrincipalView(Main.mensajeHandler);

                        usuarioControllerMenu[0] = new UsuarioController(usuarioDAO, menuPrincipal, usuarioAutenticado,mensajeHandler);

                        ProductoDAO productoDAO = new ProductoDAOMemoria();
                        CarritoDAO carritoDAO = new CarritoDAOMemoria();

                        ProductoAnadirView productoAnadirView = new ProductoAnadirView(Main.mensajeHandler);
                        ProductoListaView productoListaView = new ProductoListaView(Main.mensajeHandler);
                        ProductoEliminarView productoEliminarView = new ProductoEliminarView(Main.mensajeHandler);
                        ProductoModificarView productoModificarView = new ProductoModificarView(Main.mensajeHandler);

                        CarritoAnadirView carritoAnadirView = new CarritoAnadirView(Main.mensajeHandler);
                        CarritoListaView carritoListaView = new CarritoListaView(mensajeHandler);
                        CarritoModificarView carritoModificarView = new CarritoModificarView(mensajeHandler);

                        productoController[0] = new ProductoController(
                                productoDAO, productoAnadirView,
                                productoListaView, productoModificarView,
                                productoEliminarView, menuPrincipal, mensajeHandler
                        );

                        carritoController[0] = new CarritoController(
                                carritoDAO, productoDAO,
                                carritoAnadirView, carritoListaView,
                                carritoModificarView, mensajeHandler,menuPrincipal
                        );
                        productoAnadirView.setProductoController(productoController[0]);
                        productoListaView.setProductoController(productoController[0]);
                        productoModificarView.setProductoController(productoController[0]);
                        productoEliminarView.setProductoController(productoController[0]);

                        carritoModificarView.setCarritoController(carritoController[0]);
                        carritoListaView.setCarritoController(carritoController[0]);
                        carritoAnadirView.setCarritoController(carritoController[0]);


                        menuPrincipal.mostrarMensaje(Main.mensajeHandler.get("mensaje.bienvenida") + ": " + usuarioAutenticado.getUsername());

                        if (usuarioAutenticado.getRol() != null && usuarioAutenticado.getRol().equals(Rol.USUARIO)) {
                            menuPrincipal.deshabilitarMenusAdministrador();
                        }

                        menuPrincipal.configurarMenusPorRol(usuarioAutenticado);

                        configurarEventosMenu(menuPrincipal,
                                productoAnadirView, productoListaView,
                                productoModificarView, productoEliminarView,
                                carritoAnadirView, carritoListaView,
                                carritoModificarView, Main.mensajeHandler,
                                carritoController[0], usuarioControllerMenu[0]);

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
                                              ProductoAnadirView productoAnadirView,
                                              ProductoListaView productoListaView,
                                              ProductoModificarView productoModificarView,
                                              ProductoEliminarView productoEliminarView,
                                              CarritoAnadirView carritoAnadirView,
                                              CarritoListaView carritoListaView,
                                              CarritoModificarView carritoModificarView,
                                              MensajeInternacionalizacionHandler mensajes,
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

        menu.getMenuItemCrearCarrito().addActionListener(e -> {
            cerrarVentanaExistente(CarritoAnadirView.class, menu.getjDesktopPane());
            menu.agregarVentanaInterna(carritoAnadirView);
        });

        menu.getMenuItemVerMisCarritos().addActionListener(e -> {
            cerrarVentanaExistente(CarritoListaView.class, menu.getjDesktopPane());
            carritoController.setCarritoListaView(carritoListaView);
            carritoController.cargarCarritosEnTabla();
            menu.agregarVentanaInterna(carritoListaView);
        });


        menu.getMenuItemModificarMiCarrito().addActionListener(e -> {
            cerrarVentanaExistente(CarritoModificarView.class, menu.getjDesktopPane());
            carritoModificarView.setCarritoController(carritoController);
            carritoController.setCarritoModificarView(carritoModificarView);
            carritoController.cargarCarritosEnComboModificar();
            carritoModificarView.cargarProductosEnTabla();
            carritoModificarView.actualizarTotales();
            menu.agregarVentanaInterna(carritoModificarView);
        });



        menu.getMenuItemCambiarContrasenia().addActionListener(e -> {
            if (usuarioController != null) {
                usuarioController.mostrarCambiarContrasenia();
            }
        });

        menu.getMenuItemCerrarSesion().addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(
                    menu,
                    mensajes.get("confirmacion.cerrarSesion"),
                    mensajes.get("titulo.cerrarSesion"),
                    JOptionPane.YES_NO_OPTION
            );
            if (confirmacion == JOptionPane.YES_OPTION) {
                menu.dispose();
                LoginView nuevoLogin = new LoginView(mensajeHandler);
                nuevoLogin.setVisible(true);
                new UsuarioController(new UsuarioDAOMemoria(), nuevoLogin, mensajeHandler);
            }
        });

    }




}