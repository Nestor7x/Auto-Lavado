package org.example;

import org.example.dao.*;
import org.example.model.*;
import org.example.util.ConexionBD;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        try (Connection conn = ConexionBD.obtenerConexion()) {
            if (conn == null) {
                System.out.println("No fue posible establecer conexión con la base de datos.");
                return;
            }

            clienteDao clienteDAO = new clienteDaoImpl(conn);
            vehiculosDao vehiculoDAO = new vehiculosDaoImpl(conn);
            serviciosDao servicioDAO = new serviciosDaoImpl(conn);
            registrosLavadoDao registroDAO = new registrosLavadoImpl(conn);

            Scanner sc = new Scanner(System.in);
            int opcion;

            do {
                opcion = mostrarMenuPrincipal(sc);
                switch (opcion) {
                    case 1 -> gestionarClientes(sc, clienteDAO);
                    case 2 -> gestionarVehiculos(sc, vehiculoDAO);
                    case 3 -> gestionarServicios(sc, servicioDAO);
                    case 4 -> gestionarRegistros(sc, registroDAO);
                    case 0 -> System.out.println("Programa finalizado.");
                    default -> System.out.println("Opción inválida.");
                }
            } while (opcion != 0);

        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    private static int mostrarMenuPrincipal(Scanner sc) {
        System.out.println("\n===== MENÚ =====");
        System.out.println("1. Clientes");
        System.out.println("2. Vehículos");
        System.out.println("3. Servicios");
        System.out.println("4. Registros de Lavado");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
        return Integer.parseInt(sc.nextLine());
    }

    private static void gestionarClientes(Scanner sc, clienteDao dao) {
        int op;
        do {
            op = mostrarMenu("CLIENTES", sc);
            switch (op) {
                case 1 -> registrarCliente(sc, dao);
                case 2 -> mostrarClientes(dao);
                case 3 -> actualizarCliente(sc, dao);
                case 4 -> eliminarCliente(sc, dao);
            }
        } while (op != 0);
    }

    private static void gestionarVehiculos(Scanner sc, vehiculosDao dao) {
        int op;
        do {
            op = mostrarMenu("VEHÍCULOS", sc);
            switch (op) {
                case 1 -> registrarVehiculo(sc, dao);
                case 2 -> mostrarVehiculos(dao);
                case 3 -> actualizarVehiculo(sc, dao);
                case 4 -> eliminarVehiculo(sc, dao);
            }
        } while (op != 0);
    }

    private static void gestionarServicios(Scanner sc, serviciosDao dao) {
        int op;
        do {
            op = mostrarMenu("SERVICIOS", sc);
            switch (op) {
                case 1 -> registrarServicio(sc, dao);
                case 2 -> listarServicios(dao);
                case 3 -> actualizarServicio(sc, dao);
                case 4 -> eliminarServicio(sc, dao);
            }
        } while (op != 0);
    }

    private static void gestionarRegistros(Scanner sc, registrosLavadoDao dao) {
        int op;
        do {
            op = mostrarMenu("REGISTROS DE LAVADO", sc);
            switch (op) {
                case 1 -> registrarLavado(sc, dao);
                case 2 -> listarRegistros(dao);
                case 3 -> actualizarLavado(sc, dao);
                case 4 -> eliminarLavado(sc, dao);
            }
        } while (op != 0);
    }

    private static int mostrarMenu(String titulo, Scanner sc) {
        System.out.println("\n--- " + titulo + " ---");
        System.out.println("1. Registrar");
        System.out.println("2. Consultar/Listar");
        System.out.println("3. Actualizar");
        System.out.println("4. Eliminar");
        System.out.println("0. Volver");
        System.out.print("Opción: ");
        return Integer.parseInt(sc.nextLine());
    }

    // ---- CLIENTES ----
    private static void registrarCliente(Scanner sc, clienteDao dao) {
        System.out.print("Nombre: "); String nombre = sc.nextLine();
        System.out.print("Apellido: "); String apellido = sc.nextLine();
        System.out.print("Teléfono: "); String tel = sc.nextLine();
        System.out.print("Email: "); String email = sc.nextLine();
        System.out.print("Dirección: "); String dir = sc.nextLine();

        clientes c = new clientes(0, nombre, apellido, tel, email, dir);
        dao.crear(c);
        System.out.println("Cliente agregado correctamente.");
    }

    private static void mostrarClientes(clienteDao dao) {
        List<clientes> lista = dao.listar();
        if (lista.isEmpty()) System.out.println("No hay clientes registrados.");
        else lista.forEach(System.out::println);
    }

    private static void actualizarCliente(Scanner sc, clienteDao dao) {
        System.out.print("Ingrese ID del cliente a modificar: ");
        int id = Integer.parseInt(sc.nextLine());
        clientes c = dao.leer(id);
        if (c == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.print("Nuevo nombre (" + c.getNombre() + "): ");
        String nuevo = sc.nextLine();
        if (!nuevo.isEmpty()) c.setNombre(nuevo);

        System.out.print("Nuevo teléfono (" + c.getTelefono() + "): ");
        String tel = sc.nextLine();
        if (!tel.isEmpty()) c.setTelefono(tel);

        dao.actualizar(c);
        System.out.println("Datos actualizados correctamente.");
    }

    private static void eliminarCliente(Scanner sc, clienteDao dao) {
        System.out.print("ID a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());
        dao.eliminar(id);
        System.out.println("Cliente eliminado.");
    }

    // ---- VEHÍCULOS ----
    private static void registrarVehiculo(Scanner sc, vehiculosDao dao) {
        System.out.print("ID Cliente: "); int idCliente = Integer.parseInt(sc.nextLine());
        System.out.print("Marca: "); String marca = sc.nextLine();
        System.out.print("Modelo: "); String modelo = sc.nextLine();
        System.out.print("Placa: "); String placa = sc.nextLine();

        vehiculos v = new vehiculos(0, idCliente, marca, modelo, placa, "", "");
        dao.crear(v);
        System.out.println("Vehículo registrado correctamente.");
    }

    private static void mostrarVehiculos(vehiculosDao dao) {
        List<vehiculos> lista = dao.listar();
        if (lista.isEmpty()) System.out.println("No hay vehículos registrados.");
        else lista.forEach(System.out::println);
    }

    private static void actualizarVehiculo(Scanner sc, vehiculosDao dao) {
        System.out.print("ID del vehículo a modificar: ");
        int id = Integer.parseInt(sc.nextLine());
        vehiculos v = dao.leer(id);
        if (v == null) {
            System.out.println("Vehículo no encontrado.");
            return;
        }

        System.out.print("Nueva marca (" + v.getMarca() + "): ");
        String nueva = sc.nextLine();
        if (!nueva.isEmpty()) v.setMarca(nueva);

        dao.actualizar(v);
        System.out.println("Vehículo actualizado.");
    }

    private static void eliminarVehiculo(Scanner sc, vehiculosDao dao) {
        System.out.print("ID del vehículo: ");
        int id = Integer.parseInt(sc.nextLine());
        dao.eliminar(id);
        System.out.println("Vehículo eliminado.");
    }

    // ---- SERVICIOS ----
    private static void registrarServicio(Scanner sc, serviciosDao dao) {
        System.out.print("Nombre del servicio: "); String nombre = sc.nextLine();
        System.out.print("Precio: "); double precio = Double.parseDouble(sc.nextLine());

        servicios s = new servicios(0, nombre, precio);
        dao.crear(s);
        System.out.println("Servicio agregado.");
    }

    private static void listarServicios(serviciosDao dao) {
        List<servicios> lista = dao.listar();
        if (lista.isEmpty()) System.out.println("No hay servicios.");
        else lista.forEach(System.out::println);
    }

    private static void actualizarServicio(Scanner sc, serviciosDao dao) {
        System.out.print("ID del servicio: ");
        int id = Integer.parseInt(sc.nextLine());
        servicios s = dao.leer(id);
        if (s == null) {
            System.out.println("Servicio no encontrado.");
            return;
        }

        System.out.print("Nuevo nombre (" + s.getNombre() + "): ");
        String nuevo = sc.nextLine();
        if (!nuevo.isEmpty()) s.setNombre(nuevo);

        dao.actualizar(s);
        System.out.println("Servicio actualizado.");
    }

    private static void eliminarServicio(Scanner sc, serviciosDao dao) {
        System.out.print("ID del servicio: ");
        int id = Integer.parseInt(sc.nextLine());
        dao.eliminar(id);
        System.out.println("Servicio eliminado.");
    }

    // ---- REGISTROS ----
    private static void registrarLavado(Scanner sc, registrosLavadoDao dao) {
        System.out.print("ID vehículo: "); int idVeh = Integer.parseInt(sc.nextLine());
        System.out.print("ID servicio: "); int idServ = Integer.parseInt(sc.nextLine());
        registrosLavado r = new registrosLavado(
                0,
                idVeh,
                idServ,
                java.time.LocalDate.now(),
                java.time.LocalTime.now(),
                java.time.LocalTime.now().plusMinutes(30),
                0.0
        );

        dao.crear(r);
        System.out.println("Registro guardado.");
    }

    private static void listarRegistros(registrosLavadoDao dao) {
        List<registrosLavado> lista = dao.listar();
        if (lista.isEmpty()) System.out.println("No hay registros.");
        else lista.forEach(System.out::println);
    }

    private static void actualizarLavado(Scanner sc, registrosLavadoDao dao) {
        System.out.print("ID del registro: ");
        int id = Integer.parseInt(sc.nextLine());
        registrosLavado r = dao.leer(id);
        if (r == null) {
            System.out.println("Registro no encontrado.");
            return;
        }

        System.out.print("Nuevo ID servicio (" + r.getServicioID() + "): ");
        String nuevo = sc.nextLine();
        if (!nuevo.isEmpty()) r.setServicioID(Integer.parseInt(nuevo));

        dao.actualizar(r);
        System.out.println("Registro actualizado.");
    }

    private static void eliminarLavado(Scanner sc, registrosLavadoDao dao) {
        System.out.print("ID del registro: ");
        int id = Integer.parseInt(sc.nextLine());
        dao.eliminar(id);
        System.out.println("Registro eliminado.");
    }
}
