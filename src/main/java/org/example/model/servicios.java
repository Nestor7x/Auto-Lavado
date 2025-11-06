package org.example.model;

public class servicios {
    private int servicioID;
    private String nombre;
    private double precio;

    public servicios() {}

    public servicios(int servicioID, String nombre, double precio) {
        this.servicioID = servicioID;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getServicioID() { return servicioID; }
    public void setServicioID(int servicioID) { this.servicioID = servicioID; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    @Override
    public String toString() {
        return "servicios{" +
                "servicioID=" + servicioID +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                '}';
    }
}