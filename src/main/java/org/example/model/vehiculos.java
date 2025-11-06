package org.example.model;

public class vehiculos {
    private int vehiculoID;
    private int clienteID;
    private String marca;
    private String modelo;
    private String placa;
    private String color;
    private String tipo;

    public vehiculos() {}

    public vehiculos(int vehiculoID, int clienteID, String marca, String modelo, String placa, String color, String tipo) {
        this.vehiculoID = vehiculoID;
        this.clienteID = clienteID;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.color = color;
        this.tipo = tipo;
    }

    public int getVehiculoID() { return vehiculoID; }
    public void setVehiculoID(int vehiculoID) { this.vehiculoID = vehiculoID; }

    public int getClienteID() { return clienteID; }
    public void setClienteID(int clienteID) { this.clienteID = clienteID; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    @Override
    public String toString() {
        return "vehiculos{" +
                "vehiculoID=" + vehiculoID +
                ", clienteID=" + clienteID +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", placa='" + placa + '\'' +
                ", color='" + color + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}