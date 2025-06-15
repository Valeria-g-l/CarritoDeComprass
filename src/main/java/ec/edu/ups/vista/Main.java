package ec.edu.ups.vista;

import ec.edu.ups.CarritoCompras;
import ec.edu.ups.VentanaP;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                ProductoAnadirView productoView = new ProductoAnadirView();
                ProductoListaView productoListaView = new ProductoListaView();
                ProductoDAO productoDAO = new ProductoDAOMemoria();
                ProductoModificarView productoModificarView = new ProductoModificarView();
                new ProductoController(productoDAO, productoView, productoListaView);
                VentanaP ventana = new VentanaP();
                ventana.setVisible(true);

                CarritoCompras carritos = new CarritoCompras();
                carritos.setLocation(1100,250);
                carritos.setVisible(true);
            }

        });
    }
}
