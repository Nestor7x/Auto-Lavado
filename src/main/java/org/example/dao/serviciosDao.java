package org.example.dao;

import org.example.model.servicios;
import java.util.List;

public interface serviciosDao {
    void crear(servicios servicios);
    servicios leer(int id);
    void actualizar(servicios servicios);
    void eliminar(int id);
    List<servicios> listar();
}