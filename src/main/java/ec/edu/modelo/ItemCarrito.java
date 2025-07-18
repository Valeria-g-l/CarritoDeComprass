package ec.edu.modelo;

import java.io.Serializable;
/**
 * Representa un ítem dentro del carrito de compras.
 *
 * Contiene un producto y la cantidad que el usuario desea adquirir.
 * Calcula su propio subtotal basado en el precio y cantidad.
 *
 * Esta clase es serializable para permitir su persistencia en archivos.
 *
 * @author Valeria
 * @version 1.0
 */
public class ItemCarrito implements Serializable {
    private static final long serialVersionUID = 1L;
    private Producto producto;
    private int cantidad;

    public ItemCarrito() {
    }
    /**
     * Constructor que inicializa un ItemCarrito con un producto y su cantidad.
     *
     * @param producto Producto asociado al item.
     * @param cantidad Cantidad del producto en el carrito.
     */
    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }
    /**
     * Asigna el producto a este ítem.
     *
     * @param producto producto deseado
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    /**
     * Asigna la cantidad de productos en este ítem.
     *
     * @param cantidad número de unidades
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    /**
     * Devuelve el producto asociado a este ítem.
     *
     * @return el producto correspondiente
     */
    public Producto getProducto() {
        return producto;
    }
    /**
     * Devuelve la cantidad de productos en este ítem.
     *
     * @return número de unidades
     */
    public int getCantidad() {
        return cantidad;
    }
    /**
     * Calcula el subtotal del ítem (precio unitario * cantidad).
     *
     * @return valor subtotal del ítem
     */

    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }
    /**
     * Devuelve una representación textual del ítem.
     *
     * @return texto con formato: Producto x cantidad = $subtotal
     */
    @Override
    public String toString() {
        return producto.toString() + " x " + cantidad + " = $" + getSubtotal();
    }

}

