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
import java.util.ResourceBundle;

public class Main {
    private static UsuarioController usuarioController;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
            LoginView loginView = new LoginView();
            usuarioController = new UsuarioController(usuarioDAO, loginView);
            loginView.setVisible(true);

            final ProductoController[] productoController = new ProductoController[1];
            final CarritoController[] carritoController = new CarritoController[1];
            final UsuarioController[] usuarioControllerMenu = new UsuarioController[1];

            loginView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    Usuario usuarioAutenticado = usuarioController.getUsuarioAutenticado();
                    ResourceBundle mensajes = usuarioController.getMensajes();

                    if (usuarioAutenticado != null) {
                        MenuPrincipalView menuPrincipal = new MenuPrincipalView(mensajes);


                        usuarioControllerMenu[0] = new UsuarioController(usuarioDAO, menuPrincipal, usuarioAutenticado);

                        ProductoDAO productoDAO = new ProductoDAOMemoria();
                        CarritoDAO carritoDAO = new CarritoDAOMemoria();

                        ProductoAnadirView productoAnadirView = new ProductoAnadirView(mensajes);
                        ProductoListaView productoListaView = new ProductoListaView(mensajes);
                        ProductoEliminarView productoEliminarView = new ProductoEliminarView(mensajes);
                        ProductoModificarView productoModificarView = new ProductoModificarView(mensajes);

                        CarritoAnadirView carritoAnadirView = new CarritoAnadirView(mensajes);
                        CarritoListaView carritoListaView = new CarritoListaView();
                        CarritoModificarView carritoModificarView = new CarritoModificarView();

                        productoController[0] = new ProductoController(
                                productoDAO, productoAnadirView,
                                productoListaView, productoModificarView,
                                productoEliminarView
                        );

                        carritoController[0] = new CarritoController(
                                carritoDAO, productoDAO,
                                carritoAnadirView, carritoListaView,
                                carritoModificarView
                        );

                        menuPrincipal.mostrarMensaje(mensajes.getString("mensaje.bienvenida") + ": " + usuarioAutenticado.getUsername());

                        if (usuarioAutenticado.getRol() != null && usuarioAutenticado.getRol().equals(Rol.USUARIO)) {
                            menuPrincipal.deshabilitarMenusAdministrador();
                        }

                        menuPrincipal.configurarMenusPorRol(usuarioAutenticado);

                        configurarEventosMenu(menuPrincipal,
                                productoAnadirView, productoListaView,
                                productoModificarView, productoEliminarView,
                                carritoAnadirView, carritoListaView,
                                carritoModificarView, mensajes,
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
                                              ProductoAnadirView anadirView,
                                              ProductoListaView listaView,
                                              ProductoModificarView modificarView,
                                              ProductoEliminarView eliminarView,
                                              CarritoAnadirView carritoAnadirView,
                                              CarritoListaView carritoListaView,
                                              CarritoModificarView carritoModificarView,
                                              ResourceBundle mensajes,
                                              CarritoController carritoController,
                                              UsuarioController usuarioController) {


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
            if (!carritoListaView.isVisible()) {
                carritoController.cargarCarritosEnTabla();
                menu.getjDesktopPane().add(carritoListaView);
                carritoListaView.setVisible(true);
            } else {
                carritoController.cargarCarritosEnTabla();
                carritoListaView.toFront();
            }
        });

        menu.getMenuItemModificarMiCarrito().addActionListener(e -> {
            carritoModificarView.setCarritoController(carritoController);
            carritoController.cargarCarritosEnComboModificar();

            carritoModificarView.cargarProductosEnTabla();
            carritoModificarView.actualizarTotales();

            if (!carritoModificarView.isVisible()) {
                menu.getjDesktopPane().add(carritoModificarView);
                carritoModificarView.setVisible(true);
            } else {
                carritoModificarView.toFront();
            }
        });


        menu.getMenuItemCambiarContrasenia().addActionListener(e -> {
            System.out.println("Click detectado");
            if (usuarioController != null) {
                usuarioController.mostrarCambiarContrasenia();
            } else {
                System.out.println("usuarioController es null");
            }
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
