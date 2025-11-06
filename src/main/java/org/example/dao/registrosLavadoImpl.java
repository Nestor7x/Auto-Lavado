package org.example.dao;

import org.example.model.registrosLavado;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class registrosLavadoImpl implements registrosLavadoDao {
    private final Connection connection;

    public registrosLavadoImpl(Connection connection) { this.connection = connection; }

    @Override
    public void crear(registrosLavado r) {
        String sqlWithId = "INSERT INTO registrosLavado (registroID, vehiculoID, servicioID, fechaLavado, horaInicio, horaFin, precioTotal) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlNoId = "INSERT INTO registrosLavado (vehiculoID, servicioID, fechaLavado, horaInicio, horaFin, precioTotal) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            if (r.getRegistroID() > 0) {
                try (PreparedStatement ps = connection.prepareStatement(sqlWithId)) {
                    ps.setInt(1, r.getRegistroID());
                    ps.setInt(2, r.getVehiculoID());
                    ps.setInt(3, r.getServicioID());
                    ps.setDate(4, Date.valueOf(r.getFechaLavado()));
                    ps.setTime(5, Time.valueOf(r.getHoraInicio()));
                    ps.setTime(6, Time.valueOf(r.getHoraFin()));
                    ps.setDouble(7, r.getPrecioTotal());
                    ps.executeUpdate();
                }
            } else {
                try (PreparedStatement ps = connection.prepareStatement(sqlNoId, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, r.getVehiculoID());
                    ps.setInt(2, r.getServicioID());
                    ps.setDate(3, Date.valueOf(r.getFechaLavado()));
                    ps.setTime(4, Time.valueOf(r.getHoraInicio()));
                    ps.setTime(5, Time.valueOf(r.getHoraFin()));
                    ps.setDouble(6, r.getPrecioTotal());
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (keys.next()) r.setRegistroID(keys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public registrosLavado leer(int id) {
        String sql = "SELECT * FROM registrosLavado WHERE registroID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new registrosLavado(
                            rs.getInt("registroID"),
                            rs.getInt("vehiculoID"),
                            rs.getInt("servicioID"),
                            rs.getDate("fechaLavado").toLocalDate(),
                            rs.getTime("horaInicio").toLocalTime(),
                            rs.getTime("horaFin").toLocalTime(),
                            rs.getDouble("precioTotal")
                    );
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public void actualizar(registrosLavado r) {
        String sql = "UPDATE registrosLavado SET vehiculoID=?, servicioID=?, fechaLavado=?, horaInicio=?, horaFin=?, precioTotal=? WHERE registroID=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, r.getVehiculoID());
            ps.setInt(2, r.getServicioID());
            ps.setDate(3, Date.valueOf(r.getFechaLavado()));
            ps.setTime(4, Time.valueOf(r.getHoraInicio()));
            ps.setTime(5, Time.valueOf(r.getHoraFin()));
            ps.setDouble(6, r.getPrecioTotal());
            ps.setInt(7, r.getRegistroID());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM registrosLavado WHERE registroID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public List<registrosLavado> listar() {
        List<registrosLavado> lista = new ArrayList<>();
        String sql = "SELECT * FROM registrosLavado";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new registrosLavado(
                        rs.getInt("registroID"),
                        rs.getInt("vehiculoID"),
                        rs.getInt("servicioID"),
                        rs.getDate("fechaLavado").toLocalDate(),
                        rs.getTime("horaInicio").toLocalTime(),
                        rs.getTime("horaFin").toLocalTime(),
                        rs.getDouble("precioTotal")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
}