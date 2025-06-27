package ec.edu.servicio;

import ec.edu.modelo.ItemCarrito;
import ec.edu.modelo.Producto;

import java.util.List;

public interface CarritoService {

    void agregarProducto(Producto producto, int cantidad);

    void eliminarProducto(int codigoProducto);

    void vaciarCarrito();

    double calcularTotal();

    List<ItemCarrito> obtenerItems();

    boolean estaVacio();

}

