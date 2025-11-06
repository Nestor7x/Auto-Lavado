package org.example.dao;

import org.example.model.vehiculos;
import java.util.List;

public interface vehiculosDao {
    void crear(vehiculos vehiculos);
    vehiculos leer(int id);
    void actualizar(vehiculos vehiculos);
    void eliminar(int id);
    List<vehiculos> listar();
}