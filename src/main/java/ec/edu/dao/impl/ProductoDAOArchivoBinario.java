package ec.edu.dao.impl;

import ec.edu.dao.ProductoDAO;
import ec.edu.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación del DAO de productos utilizando persistencia en archivo binario.
 *
 * Proporciona operaciones básicas CRUD sobre la clase {@link Producto} mediante
 * serialización y deserialización del archivo especificado. Si el archivo no existe
 * al momento de inicialización, se crea automáticamente.
 *
 * Esta clase es ideal para entornos de escritorio donde se requiere almacenamiento
 * local sin conexión a base de datos.
 *
 * @author Valeria
 * @version 1.0
 */
public class ProductoDAOArchivoBinario implements ProductoDAO {
    private final File archivo;
    /**
     * Constructor que inicializa el DAO de productos utilizando un archivo binario.
     * Si el archivo no existe, se crea uno nuevo en la ruta especificada.
     *
     * @param rutaArchivo Ruta del archivo binario donde se almacenan los productos.
     */

    public ProductoDAOArchivoBinario(String rutaArchivo) {
        this.archivo = new File(rutaArchivo);
        try {
            if (!archivo.exists()) archivo.createNewFile();
        } catch (IOException e) {
            System.out.println("Error creando archivo de productos: " + e.getMessage());
        }
    }
    /**
     * Crea un nuevo producto y lo agrega al archivo binario.
     *
     * @param producto producto a persistir
     */
    @Override
    public void crear(Producto producto) {
        List<Producto> productos = listarTodos();
        productos.add(producto);
        guardarLista(productos);
    }

    /**
     * Busca un producto según su código.
     *
     * @param codigo código del producto
     * @return producto encontrado, o null si no existe
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto p : listarTodos()) {
            if (p.getCodigo() == codigo) return p;
        }
        return null;
    }
    /**
     * Busca productos que coincidan con el nombre especificado (case insensitive).
     *
     * @param nombre nombre del producto a buscar
     * @return lista de productos encontrados
     */
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> encontrados = new ArrayList<>();
        for (Producto p : listarTodos()) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                encontrados.add(p);
            }
        }
        return encontrados;
    }
    /**
     * Actualiza los datos de un producto existente según su código.
     *
     * @param productoModificado producto con los nuevos datos
     */
    @Override
    public void actualizar(Producto productoModificado) {
        List<Producto> productos = listarTodos();
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == productoModificado.getCodigo()) {
                productos.set(i, productoModificado);
                break;
            }
        }
        guardarLista(productos);
    }
    /**
     * Elimina un producto por su código del archivo binario.
     *
     * @param codigo código del producto a eliminar
     */
    @Override
    public void eliminar(int codigo) {
        List<Producto> productos = listarTodos();
        productos.removeIf(p -> p.getCodigo() == codigo);
        guardarLista(productos);
    }

    /**
     * Lista todos los productos guardados en el archivo binario.
     *
     * @return lista de productos actuales
     */
    @Override
    public List<Producto> listarTodos() {
        if (!archivo.exists() || archivo.length() == 0) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<Producto>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Error leyendo productos binarios: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    /**
     * Guarda la lista completa de productos en el archivo binario, sobrescribiendo su contenido.
     *
     * @param productos lista actualizada de productos
     */
    private void guardarLista(List<Producto> productos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(productos);
        } catch (IOException e) {
            System.out.println("Error escribiendo productos binarios: " + e.getMessage());
        }
    }
}
