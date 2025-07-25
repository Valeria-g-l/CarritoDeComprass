package ec.edu.dao;

import ec.edu.modelo.Carrito;
import ec.edu.modelo.Usuario;

import java.util.List;

public interface CarritoDAO {

    void crear(Carrito carrito);

    Carrito buscarPorCodigo(int codigo);

    void actualizar(Carrito carrito);

    void eliminar(int codigo);

    List<Carrito> listarTodos();

    List<Carrito> listarPorUsuario(Usuario usuario);

}
