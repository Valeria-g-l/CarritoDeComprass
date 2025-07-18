package ec.edu.dao.impl;

import ec.edu.dao.CarritoDAO;
import ec.edu.modelo.Carrito;
import ec.edu.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Implementación en memoria del DAO de carritos.
 *
 * Almacena carritos en una lista interna sin persistencia permanente.
 * Permite operaciones CRUD (crear, leer, actualizar, eliminar) sobre carritos
 * en tiempo de ejecución, útil para pruebas o entornos temporales.
 *
 * Asigna códigos únicos a cada carrito automáticamente y permite consultar
 * carritos por usuario. Ideal como versión ligera y rápida comparada
 * con implementaciones en archivos binarios o texto.
 *
 * @author Valeria
 * @version 1.0
 */
public class CarritoDAOMemoria implements CarritoDAO {

    private List<Carrito> carritos;
    private int secuenciaCodigo = 1;
    private List<Carrito> carritos1 = new ArrayList<>();
    /**
     * Lista todos los carritos pertenecientes a un usuario específico.
     *
     * @param usuario Usuario del cual se quieren obtener los carritos.
     * @return Lista de carritos que pertenecen al usuario indicado.
     */
    @Override
    public List<Carrito> listarPorUsuario(Usuario usuario) {
        List<Carrito> resultado = new ArrayList<>();
        for (Carrito c : carritos) {
            if (c.getPropietario() != null &&
                    c.getPropietario().getUsername().equals(usuario.getUsername())) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public CarritoDAOMemoria() {
        this.carritos = new ArrayList<Carrito>();
    }

    @Override
    public void crear(Carrito carrito) {
        carrito.setCodigo(secuenciaCodigo++);
        carritos.add(carrito);
        System.out.println("Carrito creado: " + carrito.getCodigo());
    }

    /**
     * Busca un carrito según su código único.
     *
     * @param codigo el identificador del carrito
     * @return carrito encontrado o null si no existe
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for (Carrito carrito : carritos) {
            if (carrito.getCodigo() == codigo) {
                return carrito;
            }
        }
        return null;
    }
    /**
     * Actualiza un carrito existente dentro de la lista.
     *
     * @param carrito el carrito con datos modificados
     */
    @Override
    public void actualizar(Carrito carrito) {
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carrito.getCodigo()) {
                carritos.set(i, carrito);
                break;
            }
        }
    }
    /**
     * Elimina un carrito de la lista según su código.
     *
     * @param codigo el identificador del carrito a eliminar
     */
    @Override
    public void eliminar(int codigo) {
        Iterator<Carrito> iterator = carritos.iterator();
        while (iterator.hasNext()) {
            Carrito carrito = iterator.next();
            if (carrito.getCodigo() == codigo) {
                iterator.remove();
            }
        }
    }
    /**
     * Devuelve una lista con todos los carritos almacenados actualmente.
     *
     * @return lista completa de carritos
     */
    @Override
    public List<Carrito> listarTodos() {
        System.out.println("Total carritos en memoria: " + carritos.size());
        return carritos;
    }
}
