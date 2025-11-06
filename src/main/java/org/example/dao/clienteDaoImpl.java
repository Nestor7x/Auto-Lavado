package org.example.dao;

import org.example.model.clientes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class clienteDaoImpl implements clienteDao{
private final Connection connection;

    public clienteDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void crear(clientes clientes) {
        String sqlWithId = "INSERT INTO clientes (clienteID, nombre, apellido, telefono, email, direccion) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlNoId = "INSERT INTO clientes (nombre, apellido, telefono, email, direccion) VALUES (?, ?, ?, ?, ?)";

        try {
            if (clientes.getClienteID() > 0) {
                try (PreparedStatement ps = connection.prepareStatement(sqlWithId)) {
                    ps.setInt(1, clientes.getClienteID());
                    ps.setString(2, clientes.getNombre());
                    ps.setString(3, clientes.getApellido());
                    ps.setString(4, clientes.getTelefono());
                    ps.setString(5, clientes.getEmail());
                    ps.setString(6, clientes.getDireccion());
                    ps.executeUpdate();
                }
            } else {
                try (PreparedStatement ps = connection.prepareStatement(sqlNoId, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, clientes.getNombre());
                    ps.setString(2, clientes.getApellido());
                    ps.setString(3, clientes.getTelefono());
                    ps.setString(4, clientes.getEmail());
                    ps.setString(5, clientes.getDireccion());
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (keys.next()) clientes.setClienteID(keys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public clientes leer(int id) {
        String sql = "SELECT * FROM clientes WHERE clienteID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new clientes(
                            rs.getInt("clienteID"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("telefono"),
                            rs.getString("email"),
                            rs.getString("direccion")
                    );
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public void actualizar(clientes clientes) {
        String sql = "UPDATE clientes SET nombre=?, apellido=?, telefono=?, email=?, direccion=? WHERE clienteID=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, clientes.getNombre());
            ps.setString(2, clientes.getApellido());
            ps.setString(3, clientes.getTelefono());
            ps.setString(4, clientes.getEmail());
            ps.setString(5, clientes.getDireccion());
            ps.setInt(6, clientes.getClienteID());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM clientes WHERE clienteID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public List<clientes> listar() {
        List<clientes> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new clientes(
                        rs.getInt("clienteID"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("direccion")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }


}
