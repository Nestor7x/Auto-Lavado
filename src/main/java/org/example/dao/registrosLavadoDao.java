package org.example.dao;

import org.example.model.registrosLavado;
import java.util.List;

public interface registrosLavadoDao {
    void crear(registrosLavado registro);
    registrosLavado leer(int id);
    void actualizar(registrosLavado registro);
    void eliminar(int id);
    List<registrosLavado> listar();
}