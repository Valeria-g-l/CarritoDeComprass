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

    public CarritoDAOArchivoTexto(String rutaArchivo) {
        archivo = new File(rutaArchivo);
        try {
            if (!archivo.exists()) archivo.createNewFile();
        } catch (IOException e) {
            System.out.println("Error creando archivo de carritos: " + e.getMessage());
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
                String[] partes = linea.split(";");
                if (partes.length >= 4) {
                    Carrito c = new Carrito();
                    c.setCodigo(Integer.parseInt(partes[0]));
                    c.setPropietario(new Usuario(partes[1], "", null)); // contraseña no necesaria aquí
                    c.setFechaCreacion(new GregorianCalendar());
                    c.getFechaCreacion().setTimeInMillis(Long.parseLong(partes[2]));

                    String[] productos = partes[3].split("\\|");
                    for (String p : productos) {
                        if (p.contains("-")) {
                            String[] info = p.split("-");
                            Producto prod = new Producto();
                            prod.setCodigo(Integer.parseInt(info[0]));
                            ItemCarrito item = new ItemCarrito(prod, Integer.parseInt(info[1]));
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
