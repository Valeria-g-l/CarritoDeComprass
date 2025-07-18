package ec.edu.dao.impl;

import ec.edu.dao.ProductoDAO;
import ec.edu.modelo.Producto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Implementación en memoria del DAO de productos.
 *
 * Utiliza una lista estática para mantener productos durante la ejecución del programa.
 * No ofrece persistencia, pero es ideal para pruebas, entornos locales o funciones
 * temporales sin almacenamiento permanente.
 *
 * Soporta operaciones CRUD completas sobre objetos {@link Producto}.
 *
 * @author Valeria
 * @version 1.0
 */
public class ProductoDAOMemoria implements ProductoDAO {
    private static List<Producto> productos = new ArrayList<>();


    public ProductoDAOMemoria() {
        productos = new ArrayList<Producto>();
    }
    /**
     * Agrega un nuevo producto a la lista en memoria.
     *
     * @param producto producto a registrar
     */

    @Override
    public void crear(Producto producto) {
        productos.add(producto);
    }

    /**
     * Busca un producto en la lista por su código único.
     *
     * @param codigo Código del producto a buscar.
     * @return El producto con el código especificado, o null si no se encuentra.
     */

    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }
        return null;
    }
    /**
     * Busca productos cuyo nombre coincida con el especificado (ignorando mayúsculas).
     *
     * @param nombre nombre a buscar
     * @return lista de productos encontrados
     */
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> productosEncontrados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                productosEncontrados.add(producto);
            }
        }
        return productosEncontrados;
    }
    /**
     * Actualiza los datos de un producto existente según su código.
     *
     * @param producto producto actualizado
     */
    @Override
    public void actualizar(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
            }
        }
    }
    /**
     * Elimina un producto de la lista según su código.
     *
     * @param codigo código del producto a eliminar
     */
    @Override
    public void eliminar(int codigo) {
        Iterator<Producto> iterator = productos.iterator();
        while (iterator.hasNext()) {
            Producto producto = iterator.next();
            if (producto.getCodigo() == codigo) {
                iterator.remove();
            }
        }
    }

    /**
     * Lista todos los productos almacenados en memoria.
     *
     * @return lista actual de productos
     */
    @Override
    public List<Producto> listarTodos() {
        return productos;
    }
}
