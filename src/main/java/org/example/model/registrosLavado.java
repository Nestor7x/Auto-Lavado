package org.example.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class registrosLavado {
    private int registroID;
    private int vehiculoID;
    private int servicioID;
    private LocalDate fechaLavado;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private double precioTotal;

    public registrosLavado() {}

    public registrosLavado(int registroID, int vehiculoID, int servicioID, LocalDate fechaLavado, LocalTime horaInicio, LocalTime horaFin, double precioTotal) {
        this.registroID = registroID;
        this.vehiculoID = vehiculoID;
        this.servicioID = servicioID;
        this.fechaLavado = fechaLavado;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.precioTotal = precioTotal;
    }

    public int getRegistroID() { return registroID; }
    public void setRegistroID(int registroID) { this.registroID = registroID; }

    public int getVehiculoID() { return vehiculoID; }
    public void setVehiculoID(int vehiculoID) { this.vehiculoID = vehiculoID; }

    public int getServicioID() { return servicioID; }
    public void setServicioID(int servicioID) { this.servicioID = servicioID; }

    public LocalDate getFechaLavado() { return fechaLavado; }
    public void setFechaLavado(LocalDate fechaLavado) { this.fechaLavado = fechaLavado; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public double getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(double precioTotal) { this.precioTotal = precioTotal; }

    @Override
    public String toString() {
        return "registrosLavado{" +
                "registroID=" + registroID +
                ", vehiculoID=" + vehiculoID +
                ", servicioID=" + servicioID +
                ", fechaLavado=" + fechaLavado +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                ", precioTotal=" + precioTotal +
                '}';
    }
}