package ec.edu.vista;

import ec.edu.controlador.CarritoController;
import ec.edu.controlador.ProductoController;
import ec.edu.dao.CarritoDAO;
import ec.edu.dao.ProductoDAO;
import ec.edu.dao.impl.ProductoDAOMemoria;
import ec.edu.modelo.Carrito;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                MenuPrincipalView menuPrincipal = new MenuPrincipalView();


                ProductoAnadirView productoAnadirView = new ProductoAnadirView();
                ProductoListaView productoListaView = new ProductoListaView();
                ProductoEliminarView productoEliminarView = new ProductoEliminarView();
                ProductoModificarView productoModificarView = new ProductoModificarView();
                CarritoAnadirView carritoAnadirView = new CarritoAnadirView();


                ProductoDAO productoDAO = new ProductoDAOMemoria();
                CarritoDAO carritoDAO = new CarritoDAO() {
                    @Override
                    public void crear(Carrito carrito) {

                    }

                    @Override
                    public Carrito buscarPorCodigo(int codigo) {
                        return null;
                    }

                    @Override
                    public void actualizar(Carrito carrito) {

                    }

                    @Override
                    public void eliminar(int codigo) {

                    }

                    @Override
                    public List<Carrito> listarTodos() {
                        return List.of();
                    }
                };
                new ProductoController(productoDAO, productoAnadirView, productoListaView, productoModificarView, productoEliminarView);

                new CarritoController(carritoDAO, productoDAO, carritoAnadirView);


                configurarEventosMenu(menuPrincipal, productoAnadirView, productoListaView,
                        productoModificarView, productoEliminarView, carritoAnadirView);
            }
        });
    }

    private static void configurarEventosMenu(MenuPrincipalView menu,
                                              ProductoAnadirView anadirView,
                                              ProductoListaView listaView,
                                              ProductoModificarView modificarView,
                                              ProductoEliminarView eliminarView,
                                              CarritoAnadirView AnadirView) {

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

        menu.getMenuItemCrearCarrito().addActionListener(ev -> {
            AnadirView.setVisible(true);
            menu.getjDesktopPane().add(AnadirView);

        });

    }
}



