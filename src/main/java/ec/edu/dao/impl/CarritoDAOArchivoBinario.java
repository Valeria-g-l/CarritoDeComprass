package ec.edu.dao.impl;

import ec.edu.dao.CarritoDAO;
import ec.edu.modelo.Carrito;
import ec.edu.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO de carritos utilizando persistencia en archivo binario.
 * Permite crear, buscar, actualizar, eliminar y listar carritos, manteniendo los
 * datos serializados en disco.
 *
 * Asigna códigos únicos a los carritos creados y permite listar por usuario.
 * Utiliza `ObjectInputStream` y `ObjectOutputStream` para manipular los datos.
 *
 * @author Valeria
 * @version 1.0
 */
public class CarritoDAOArchivoBinario implements CarritoDAO {//implementamos CarritoDAO
    private final File archivo;
    private int secuenciaCodigo = 1;

    public CarritoDAOArchivoBinario(String rutaArchivo) {
        archivo = new File(rutaArchivo);
        try {
            if (!archivo.exists()) archivo.createNewFile();
        } catch (IOException e) {
            System.out.println("Error creando archivo binario de carritos: " + e.getMessage());
        }
    }
    /**
     * Guarda un nuevo carrito en la lista persistente.
     * Se asigna un código único automáticamente.
     *
     * @param carrito el nuevo carrito a persistir
     */

    @Override
    public void crear(Carrito carrito) {
        carrito.setCodigo(secuenciaCodigo++);
        List<Carrito> carritos = listarTodos();
        carritos.add(carrito);
        guardarLista(carritos);
    }

    /**
     * Busca un carrito por su código único.
     *
     * @param codigo código del carrito
     * @return el carrito encontrado o null si no existe
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for (Carrito c : listarTodos()) {
            if (c.getCodigo() == codigo) return c;
        }
        return null;
    }

    /**
     * Actualiza los datos de un carrito existente.
     *
     * @param carritoModificado carrito con datos actualizados
     */
    @Override
    public void actualizar(Carrito carritoModificado) {
        List<Carrito> carritos = listarTodos();
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carritoModificado.getCodigo()) {
                carritos.set(i, carritoModificado);
                break;
            }
        }
        guardarLista(carritos);
    }
    /**
     * Elimina un carrito según su código.
     *
     * @param codigo identificador del carrito a eliminar
     */

    @Override
    public void eliminar(int codigo) {
        List<Carrito> carritos = listarTodos();
        carritos.removeIf(c -> c.getCodigo() == codigo);
        guardarLista(carritos);
    }

    /**
     * Lista todos los carritos almacenados en el archivo binario.
     *
     * @return lista completa de carritos
     */

    @Override
    public List<Carrito> listarTodos() {
        if (!archivo.exists() || archivo.length() == 0) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<Carrito>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Error leyendo carritos binarios: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    /**
     * Lista los carritos pertenecientes a un usuario específico.
     *
     * @param usuario el propietario de los carritos
     * @return lista de carritos asociados al usuario
     */
    @Override
    public List<Carrito> listarPorUsuario(Usuario usuario) {
        List<Carrito> carritos = listarTodos();
        List<Carrito> resultado = new ArrayList<>();
        for (Carrito c : carritos) {
            if (c.getPropietario() != null &&
                    c.getPropietario().getUsername().equals(usuario.getUsername())) {
                resultado.add(c);
            }
        }
        return resultado;
    }
    /**
     * Guarda la lista completa de carritos en el archivo binario.
     *
     * @param carritos lista actualizada que será serializada
     */

    private void guardarLista(List<Carrito> carritos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(carritos);
        } catch (IOException e) {
            System.out.println("Error escribiendo carritos binarios: " + e.getMessage());
        }
    }
}
