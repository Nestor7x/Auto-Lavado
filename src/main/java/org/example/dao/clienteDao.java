package org.example.dao;

import org.example.model.clientes;
import java.util.List;

public interface clienteDao {
    void crear(clientes clientes);
    clientes leer(int id);
    void actualizar(clientes clientes);
    void eliminar(int id);
    List<clientes> listar();
}