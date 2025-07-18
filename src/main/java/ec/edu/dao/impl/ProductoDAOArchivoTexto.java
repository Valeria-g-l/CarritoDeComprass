package ec.edu.dao.impl;

import ec.edu.dao.ProductoDAO;
import ec.edu.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Implementación del DAO de productos usando persistencia en archivo de texto plano.
 *
 * Cada producto se guarda como una línea en formato: código,nombre,precio.
 * Permite realizar operaciones CRUD sobre la entidad {@link Producto}, manteniendo
 * los datos accesibles sin uso de base de datos.
 *
 * Ideal para aplicaciones locales que requieren almacenamiento simple y legible.
 *
 * @author Valeria
 * @version 1.0
 */

public class ProductoDAOArchivoTexto implements ProductoDAO {
    private final File archivo;
    /**
     * Constructor que inicializa el archivo de texto para almacenar productos.
     * Si el archivo no existe, se crea uno nuevo en la ruta especificada.
     *
     * @param ruta Ruta del archivo de texto para productos.
     */
    public ProductoDAOArchivoTexto(String ruta) {
        archivo = new File(ruta);
        try {
            if (!archivo.exists()) archivo.createNewFile();
        } catch (IOException e) {
            System.out.println("Error creando archivo: " + e.getMessage());
        }
    }

    /**
     * Crea un nuevo producto y lo agrega al archivo.
     *
     * @param producto el producto a guardar
     */
    @Override
    public void crear(Producto producto) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(producto.getCodigo() + "," + producto.getNombre() + "," + producto.getPrecio());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error escribiendo producto: " + e.getMessage());
        }
    }

    /**
     * Busca un producto por su código único.
     *
     * @param codigo código identificador del producto
     * @return el producto encontrado o null si no existe
     */

    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto p : listarTodos()) {
            if (p.getCodigo() == codigo) return p;
        }
        return null;
    }
    /**
     * Busca productos por nombre exacto (sin importar mayúsculas).
     *
     * @param nombre nombre del producto a buscar
     * @return lista de productos que coinciden con el nombre
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
     * Actualiza el nombre y precio de un producto existente.
     *
     * @param productoActualizado producto con datos modificados
     */
    @Override
    public void actualizar(Producto productoActualizado) {
        List<Producto> productos = listarTodos();
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == productoActualizado.getCodigo()) {
                productos.set(i, productoActualizado);
                break;
            }
        }
        escribirTodo(productos);
    }
    /**
     * Elimina un producto del archivo por su código.
     *
     * @param codigo código del producto a eliminar
     */
    @Override
    public void eliminar(int codigo) {
        List<Producto> productos = listarTodos();
        productos.removeIf(p -> p.getCodigo() == codigo);
        escribirTodo(productos);
    }
    /**
     * Lista todos los productos disponibles en el archivo.
     *
     * @return lista completa de productos
     */
    @Override
    public List<Producto> listarTodos() {
        List<Producto> productos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    Producto p = new Producto(Integer.parseInt(partes[0]), partes[1], Double.parseDouble(partes[2]));
                    productos.add(p);
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo productos: " + e.getMessage());
        }
        return productos;
    }
    /**
     * Sobrescribe el archivo con la lista completa de productos.
     *
     * @param productos lista actualizada de productos
     */
    private void escribirTodo(List<Producto> productos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false))) {
            for (Producto p : productos) {
                bw.write(p.getCodigo() + "," + p.getNombre() + "," + p.getPrecio());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error reescribiendo productos: " + e.getMessage());
        }
    }
}
