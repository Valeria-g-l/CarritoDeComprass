package ec.edu.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
/**
 * Representa un carrito de compras dentro del sistema.
 *
 * Contiene una lista de productos seleccionados por el usuario, junto con su cantidad.
 *
 * El carrito permite operaciones como agregar, eliminar y vaciar productos,
 * y calcula subtotal, IVA y total automáticamente.
 *
 * Está asociado a un usuario propietario y registra la fecha de creación.
 *
 * @author Valeria
 * @version 1.0
 */
public class Carrito implements Serializable {
    private static final long serialVersionUID = 1L;

    private int codigo;
    private GregorianCalendar fechaCreacion;
    private List<ItemCarrito> items;
    private Usuario propietario;

    /**
     * Obtiene el usuario propietario del carrito.
     *
     * @return El usuario que posee este carrito.
     */
    public Usuario getPropietario() {
        return propietario;
    }

    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }
    /**
     * Constructor que inicializa un carrito vacío con fecha actual.
     */
    public Carrito() {
        items = new ArrayList<>();
        fechaCreacion = new GregorianCalendar();
    }
    /**
     * Obtiene el código identificador del carrito.
     *
     * @return código del carrito
     */
    public int getCodigo() {
        return codigo;
    }
    /**
     * Asigna el código identificador al carrito.
     *
     * @param codigo nuevo código
     */

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    /**
     * Obtiene la fecha de creación del carrito.
     *
     * @return fecha en formato GregorianCalendar
     */

    public GregorianCalendar getFechaCreacion() {
        return fechaCreacion;
    }
    /**
     * Asigna la fecha de creación del carrito.
     *
     * @param fechaCreacion fecha a registrar
     */

    public void setFechaCreacion(GregorianCalendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    /**
     * Agrega un producto al carrito. Si ya existe, suma la cantidad.
     *
     * @param producto producto a agregar
     * @param cantidad cantidad deseada
     */
    public void agregarProducto(Producto producto, int cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == producto.getCodigo()) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        items.add(new ItemCarrito(producto, cantidad));
    }


    /**
     * Elimina un producto del carrito según su código.
     *
     * @param codigoProducto código del producto a eliminar
     */
    public void eliminarProducto(int codigoProducto) {
        Iterator<ItemCarrito> it = items.iterator();
        while (it.hasNext()) {
            if (it.next().getProducto().getCodigo() == codigoProducto) {
                it.remove();
                break;
            }
        }
    }
    /**
     * Retorna la lista actual de ítems del carrito.
     *
     * @return lista de {@link ItemCarrito}
     */
    public List<ItemCarrito> getItems() {
        return items;
    }

    public void vaciarCarrito() {
        items.clear();
    }
    /**
     * Calcula el subtotal del carrito (suma de precio * cantidad).
     *
     * @return valor subtotal
     */

    public double calcularSubtotal() {
        double subtotal = 0.0;
        for (ItemCarrito item : items) {
            subtotal += item.getProducto().getPrecio() * item.getCantidad();
        }
        return subtotal;
    }

    /**
     * Calcula el IVA del carrito (12% del subtotal).
     *
     * @return valor del IVA
     */
    public double calcularIVA() {
        double subtotal = calcularSubtotal();
        return subtotal * 0.12;
    }
    /**
     * Calcula el total a pagar (subtotal + IVA).
     *
     * @return total del carrito
     */
    public double calcularTotal() {
        return calcularSubtotal() + calcularIVA();
    }
    /**
     * Devuelve los ítems del carrito. Equivalente a {@link #getItems()}.
     *
     * @return lista de ítems
     */

    public List<ItemCarrito> obtenerItems() {
    return items;}
    /**
     * Representación en texto del carrito con total incluido.
     *
     * @return descripción textual del carrito
     */
    @Override
    public String toString() {
        return "Carrito #" + codigo + " - Total: $" + String.format("%.2f", calcularTotal());
    }

}

