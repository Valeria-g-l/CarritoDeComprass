package ec.edu.dao.impl;

import ec.edu.dao.CarritoDAO;
import ec.edu.modelo.Carrito;
import ec.edu.modelo.ItemCarrito;
import ec.edu.modelo.Producto;
import ec.edu.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class CarritoDAOArchivoTexto implements CarritoDAO {
    private final File archivo;
    private int secuenciaCodigo = 1;
    private List<Producto> productosDisponibles = new ArrayList<>();

    /**
     * Constructor que inicializa el archivo donde se guardarán los carritos.
     * Si el archivo no existe, se crea uno nuevo.
     *
     * @param rutaArchivo Ruta completa al archivo de texto para almacenar carritos.
     */
    public CarritoDAOArchivoTexto(String rutaArchivo) {
        archivo = new File(rutaArchivo);
        try {
            if (!archivo.exists()) archivo.createNewFile();
        } catch (IOException e) {
            System.out.println("Error creando archivo de carritos: " + e.getMessage());
        }
    }
    public void cargarProductosDesdeArchivo(String rutaProductos) {
        productosDisponibles.clear(); // limpia antes de cargar

        try (BufferedReader br = new BufferedReader(new FileReader(rutaProductos))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println("[DEBUG] Línea leída: " + linea);
                String[] partes = linea.trim().split(",");
                if (partes.length == 3) {
                    try {
                        int codigo = Integer.parseInt(partes[0].trim());
                        String nombre = partes[1].trim();
                        double precio = Double.parseDouble(partes[2].trim());

                        Producto p = new Producto(codigo, nombre, precio);
                        productosDisponibles.add(p);
                        System.out.println("[DEBUG] Producto cargado: " + p.getCodigo() + " - " + p.getNombre() + " - $" + p.getPrecio());
                    } catch (NumberFormatException ex) {
                        System.out.println("[ERROR] Formato inválido en línea: " + linea);
                    }
                } else {
                    System.out.println("[ERROR] Línea mal formada: " + linea);
                }
            }
            System.out.println("[DEBUG] Total productos disponibles: " + productosDisponibles.size());
        } catch (IOException e) {
            System.out.println("Error cargando productos: " + e.getMessage());
        }
    }



    @Override
    public void crear(Carrito carrito) {
        carrito.setCodigo(secuenciaCodigo++);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            StringBuilder linea = new StringBuilder();
            linea.append(carrito.getCodigo()).append(";");
            linea.append(carrito.getPropietario().getUsername()).append(";");
            linea.append(carrito.getFechaCreacion().getTimeInMillis()).append(";");

            for (ItemCarrito item : carrito.getItems()) {
                linea.append(item.getProducto().getCodigo())
                        .append("-")
                        .append(item.getCantidad())
                        .append("|");
            }
            bw.write(linea.toString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error guardando carrito: " + e.getMessage());
        }
    }

    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for (Carrito c : listarTodos()) {
            if (c.getCodigo() == codigo) return c;
        }
        return null;
    }

    @Override
    public List<Carrito> listarPorUsuario(Usuario usuario) {
        List<Carrito> carritos = new ArrayList<>();
        for (Carrito c : listarTodos()) {
            if (c.getPropietario().getUsername().equals(usuario.getUsername())) {
                carritos.add(c);
            }
        }
        return carritos;
    }

    @Override
    public void actualizar(Carrito carritoModificado) {
        List<Carrito> carritos = listarTodos();
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carritoModificado.getCodigo()) {
                carritos.set(i, carritoModificado);
                break;
            }
        }
        escribirTodos(carritos);
    }

    @Override
    public void eliminar(int codigo) {
        List<Carrito> carritos = listarTodos();
        carritos.removeIf(c -> c.getCodigo() == codigo);
        escribirTodos(carritos);
    }

    @Override
    public List<Carrito> listarTodos() {
        List<Carrito> carritos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] partes = linea.split(";");
                if (partes.length >= 4) {
                    Carrito c = new Carrito();
                    c.setCodigo(Integer.parseInt(partes[0]));
                    c.setPropietario(new Usuario(partes[1], "", null));
                    c.setFechaCreacion(new GregorianCalendar());
                    c.getFechaCreacion().setTimeInMillis(Long.parseLong(partes[2]));

                    String[] productos = partes[3].split("\\|");
                    for (String p : productos) {
                        if (p.contains("-")) {
                            String[] info = p.split("-");
                            int codigoProducto = Integer.parseInt(info[0]);
                            int cantidad = Integer.parseInt(info[1]);

                            Producto prod = productosDisponibles.stream()
                                    .filter(prodTemp -> prodTemp.getCodigo() == codigoProducto)
                                    .findFirst()
                                    .orElse(null);

                            if (prod == null) {
                                System.out.println("[ERROR] Producto no encontrado: " + codigoProducto);
                                continue;
                            }

                            ItemCarrito item = new ItemCarrito(prod, cantidad);
                            c.getItems().add(item);
                        }
                    }
                    carritos.add(c);
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo carritos: " + e.getMessage());
        }
        return carritos;
    }



    private void escribirTodos(List<Carrito> carritos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false))) {
            for (Carrito carrito : carritos) {
                StringBuilder linea = new StringBuilder();
                linea.append(carrito.getCodigo()).append(";");
                linea.append(carrito.getPropietario().getUsername()).append(";");
                linea.append(carrito.getFechaCreacion().getTimeInMillis()).append(";");

                for (ItemCarrito item : carrito.getItems()) {
                    linea.append(item.getProducto().getCodigo())
                            .append("-")
                            .append(item.getCantidad())
                            .append("|");
                }
                bw.write(linea.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error escribiendo carritos: " + e.getMessage());
        }
    }
}