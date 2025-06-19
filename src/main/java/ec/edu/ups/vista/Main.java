package ec.edu.ups.vista;

import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                MenuPrincipalView menuPrincipal = new MenuPrincipalView();


                ProductoAnadirView productoAnadirView = new ProductoAnadirView();
                ProductoListaView productoListaView = new ProductoListaView();
                ProductoEliminarView productoEliminarView = new ProductoEliminarView();
                ProductoModificarView productoModificarView = new ProductoModificarView();


                ProductoDAO productoDAO = new ProductoDAOMemoria();
                new ProductoController(productoDAO, productoAnadirView, productoListaView);


                configurarEventosMenu(menuPrincipal, productoAnadirView, productoListaView,
                        productoModificarView, productoEliminarView);
            }
        });
    }

    private static void configurarEventosMenu(MenuPrincipalView menu,
                                              ProductoAnadirView anadirView,
                                              ProductoListaView listaView,
                                              ProductoModificarView modificarView,
                                              ProductoEliminarView eliminarView) {

        menu.getMenuItemCrearProducto().addActionListener(e -> {
            anadirView.setVisible(true);
            menu.getjDesktopPane().add(anadirView);
        });

        menu.getMenuItemBuscarProducto().addActionListener(e -> {
            listaView.setVisible(true);
            menu.getjDesktopPane().add(listaView);
        });

        menu.getMenuItemActualizarProducto().addActionListener(e -> {
            modificarView.setVisible(true);
            menu.getjDesktopPane().add(modificarView);
        });

        menu.getMenuItemEliminarProducto().addActionListener(e -> {
            eliminarView.setVisible(true);
            menu.getjDesktopPane().add(eliminarView);
        });

    }
}



