package ec.edu.dao.impl;

import ec.edu.dao.ProductoDAO;
import ec.edu.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOArchivoTexto implements ProductoDAO {
    private final File archivo;

    public ProductoDAOArchivoTexto(String ruta) {
        archivo = new File(ruta);
        try {
            if (!archivo.exists()) archivo.createNewFile();
        } catch (IOException e) {
            System.out.println("Error creando archivo: " + e.getMessage());
        }
    }

    @Override
    public void crear(Producto producto) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(producto.getCodigo() + "," + producto.getNombre() + "," + producto.getPrecio());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error escribiendo producto: " + e.getMessage());
        }
    }

    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto p : listarTodos()) {
            if (p.getCodigo() == codigo) return p;
        }
        return null;
    }

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

    @Override
    public void eliminar(int codigo) {
        List<Producto> productos = listarTodos();
        productos.removeIf(p -> p.getCodigo() == codigo);
        escribirTodo(productos);
    }

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
