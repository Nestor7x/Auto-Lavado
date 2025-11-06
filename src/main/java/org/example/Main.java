package org.example;

import org.example.dao.*;
import org.example.model.*;
import org.example.util.ConexionBD;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {

        try (Connection connection = ConexionBD.obtenerConexion()) {
            if (connection == null) {
                System.out.println("Error, no se obtuvo conexion");
                return;
            }

            clienteDao clienteDao = new clienteDaoImpl(connection);
            vehiculosDao vehiculosDao = new vehiculosDaoImpl(connection);
            serviciosDao serviciosDao = new serviciosDaoImpl(connection);
            registrosLavadoDao registroDao = new registrosLavadoImpl(connection);

            Scanner scanner = new Scanner(System.in);
            int opcion;
            do {
                System.out.println("\n-------MENU-------");
                System.out.println("1. Gestionar Clientes");
                System.out.println("2. Gestionar Vehiculos");
                System.out.println("3. Gestionar Servicios");
                System.out.println("4. Gestionar Registros de Lavado");
                System.out.println("0. Salir");
                System.out.print("Cual opcion desea elegir:");
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1 -> menuClientes(scanner, clienteDao);
                    case 2 -> menuVehiculos(scanner, vehiculosDao, clienteDao);
                    case 3 -> menuServicios(scanner, serviciosDao);
                    case 4 -> menuRegistros(scanner, registroDao, vehiculosDao, serviciosDao);
                    case 0 -> System.out.println("Saliendo...");
                    default -> System.out.println("Opción no válida.");
                }
            } while (opcion != 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------- CLIENTES ----------
    private static void menuClientes(Scanner scanner, clienteDao dao) {
        int op;
        do {
            System.out.println("\n-------SUBMENU GESTION-------");
            System.out.println("1. Registrar Clientes");
            System.out.println("2. Consultar Clientes");
            System.out.println("3. Actualizar Clientes");
            System.out.println("4. Eliminar Clientes");
            System.out.println("5. Listar Clientes");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            op = Integer.parseInt(scanner.nextLine());

            switch (op) {
                case 1 -> {
                    System.out.print("Enter para id automatico o introduce el id personalizado");
                    String idStr = scanner.nextLine().trim();
                    int id = 0;
                    if (!idStr.isEmpty()) id = Integer.parseInt(idStr);

                    System.out.print("Nombre: "); String nombre = scanner.nextLine();
                    System.out.print("Apellido: "); String apellido = scanner.nextLine();
                    System.out.print("Teléfono: "); String tel = scanner.nextLine();
                    System.out.print("Email: "); String email = scanner.nextLine();
                    System.out.print("Dirección: "); String dir = scanner.nextLine();

                    clientes c = new clientes(id, nombre, apellido, tel, email, dir);
                    dao.crear(c);
                    System.out.println("Registro exitoso, su ID es: " + c.getClienteID());
                }
                case 2 -> {
                    System.out.println("Lista de clientes por ID:");
                    dao.listar().forEach(cl -> System.out.println("ID=" + cl.getClienteID() + " -> " + cl.getNombre() + " " + cl.getApellido()));
                    System.out.print("Ingrese ID del cliente que desea consultar: ");
                    int idc = Integer.parseInt(scanner.nextLine());
                    clientes c = dao.leer(idc);
                    System.out.println(c != null ? c : "Cliente no encontrado.");
                }
                case 3 -> {
                    System.out.print("Ingrese ID del cliente a actualizar: ");
                    int idu = Integer.parseInt(scanner.nextLine());
                    clientes c = dao.leer(idu);
                    if (c != null) {
                        System.out.print("Nuevo nombre (" + c.getNombre() + "): "); String n = scanner.nextLine(); if (!n.isEmpty()) c.setNombre(n);
                        System.out.print("Nuevo apellido (" + c.getApellido() + "): "); String a = scanner.nextLine(); if (!a.isEmpty()) c.setApellido(a);
                        System.out.print("Nuevo teléfono (" + c.getTelefono() + "): "); String t = scanner.nextLine(); if (!t.isEmpty()) c.setTelefono(t);
                        System.out.print("Nuevo email (" + c.getEmail() + "): "); String e = scanner.nextLine(); if (!e.isEmpty()) c.setEmail(e);
                        System.out.print("Nueva dirección (" + c.getDireccion() + "): "); String d = scanner.nextLine(); if (!d.isEmpty()) c.setDireccion(d);
                        dao.actualizar(c);
                        System.out.println("Cliente actualizado.");
                    } else System.out.println("Cliente no encontrado.");
                }
                case 4 -> {
                    System.out.print("Ingrese ID del cliente a eliminar: ");
                    int idd = Integer.parseInt(scanner.nextLine());
                    dao.eliminar(idd);
                    System.out.println("Cliente eliminado.");
                }
                case 5 -> {
                    List<clientes> lista = dao.listar();
                    if (lista.isEmpty()) System.out.println("No existen clientes");
                    else lista.forEach(System.out::println);
                }
            }
        } while (op != 0);
    }

    private static void menuVehiculos(Scanner scanner, vehiculosDao dao, clienteDao clienteDAO) {
        int op;
        do {
            System.out.println("\n-------GESTION VEHICULOS-------");
            System.out.println("1. Registrar Vehículo");
            System.out.println("2. Consultar Vehículo");
            System.out.println("3. Actualizar Vehículo");
            System.out.println("4. Eliminar Vehículo");
            System.out.println("5. Listar Vehículos");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            op = Integer.parseInt(scanner.nextLine());

            switch (op) {
                case 1 -> {
                    System.out.print("Enter para id automatico o introduce el id personalizado");
                    String idStr = scanner.nextLine().trim();
                    int id = 0;
                    if (!idStr.isEmpty()) id = Integer.parseInt(idStr);

                    System.out.print("ID del cliente: ");
                    int clienteId = Integer.parseInt(scanner.nextLine());

                    System.out.print("Marca: "); String marca = scanner.nextLine();
                    System.out.print("Modelo: "); String modelo = scanner.nextLine();
                    System.out.print("Placa: "); String placa = scanner.nextLine();
                    System.out.print("Color: "); String color = scanner.nextLine();
                    System.out.print("Tipo: "); String tipo = scanner.nextLine();

                    vehiculos v = new vehiculos(id, clienteId, marca, modelo, placa, color, tipo);
                    dao.crear(v);
                    System.out.println("Vehículo registrado. ID: " + v.getVehiculoID());
                }
                case 2 -> {
                    System.out.println("Vehículos:");
                    dao.listar().forEach(veh -> System.out.println("ID=" + veh.getVehiculoID() + " -> " + veh.getPlaca() + " / " + veh.getMarca()));
                    System.out.print("Ingrese ID del vehículo a consultar: ");
                    int idv = Integer.parseInt(scanner.nextLine());
                    vehiculos v = dao.leer(idv);
                    System.out.println(v != null ? v : "Vehículo no encontrado.");
                }
                case 3 -> {
                    System.out.print("Ingrese ID del vehículo a actualizar: ");
                    int idu = Integer.parseInt(scanner.nextLine());
                    vehiculos v = dao.leer(idu);
                    if (v != null) {
                        System.out.print("Nueva marca (" + v.getMarca() + "): "); String m = scanner.nextLine(); if (!m.isEmpty()) v.setMarca(m);
                        System.out.print("Nuevo modelo (" + v.getModelo() + "): "); String mo = scanner.nextLine(); if (!mo.isEmpty()) v.setModelo(mo);
                        System.out.print("Nueva placa (" + v.getPlaca() + "): "); String p = scanner.nextLine(); if (!p.isEmpty()) v.setPlaca(p);
                        System.out.print("Nuevo color (" + v.getColor() + "): "); String c = scanner.nextLine(); if (!c.isEmpty()) v.setColor(c);
                        System.out.print("Nuevo tipo (" + v.getTipo() + "): "); String t = scanner.nextLine(); if (!t.isEmpty()) v.setTipo(t);
                        dao.actualizar(v);
                        System.out.println("Vehículo actualizado.");
                    } else System.out.println("Vehículo no encontrado.");
                }
                case 4 -> {
                    System.out.print("Ingrese ID del vehículo a eliminar: ");
                    int idd = Integer.parseInt(scanner.nextLine());
                    dao.eliminar(idd);
                    System.out.println("Vehículo eliminado.");
                }
                case 5 -> {
                    List<vehiculos> lista = dao.listar();
                    if (lista.isEmpty()) System.out.println("No hay vehículos.");
                    else lista.forEach(System.out::println);
                }
            }
        } while (op != 0);
    }

    private static void menuServicios(Scanner scanner, serviciosDao dao) {
        int op;
        do {
            System.out.println("\n-------GESTION SERVICIOS-------");
            System.out.println("1. Registrar Servicio");
            System.out.println("2. Consultar Servicio");
            System.out.println("3. Actualizar Servicio");
            System.out.println("4. Eliminar Servicio");
            System.out.println("5. Listar Servicios (ver IDs)");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            op = Integer.parseInt(scanner.nextLine());

            switch (op) {
                case 1 -> {
                    System.out.print("Enter para id automatico o introduce el id personalizado");
                    String idStr = scanner.nextLine().trim();
                    int id = 0;
                    if (!idStr.isEmpty()) id = Integer.parseInt(idStr);

                    System.out.print("Nombre: "); String nombre = scanner.nextLine();
                    System.out.print("Precio: "); double precio = Double.parseDouble(scanner.nextLine());

                    servicios s = new servicios(id, nombre, precio);
                    dao.crear(s);
                    System.out.println("Servicio registrado. ID: " + s.getServicioID());
                }
                case 2 -> {
                    System.out.println("Servicios");
                    dao.listar().forEach(serv -> System.out.println("ID=" + serv.getServicioID() + " -> " + serv.getNombre() + " $" + serv.getPrecio()));
                    System.out.print("Ingrese ID del servicio que desea consultar: ");
                    int ids = Integer.parseInt(scanner.nextLine());
                    servicios s = dao.leer(ids);
                    System.out.println(s != null ? s : "Servicio no encontrado.");
                }
                case 3 -> {
                    System.out.print("Ingrese ID del servicio a actualizar: ");
                    int idu = Integer.parseInt(scanner.nextLine());
                    servicios s = dao.leer(idu);
                    if (s != null) {
                        System.out.print("Nuevo nombre (" + s.getNombre() + "): "); String n = scanner.nextLine(); if (!n.isEmpty()) s.setNombre(n);
                        System.out.print("Nuevo precio (" + s.getPrecio() + "): "); String p = scanner.nextLine(); if (!p.isEmpty()) s.setPrecio(Double.parseDouble(p));
                        dao.actualizar(s);
                        System.out.println("Servicio actualizado.");
                    } else System.out.println("Servicio no encontrado.");
                }
                case 4 -> {
                    System.out.print("Ingrese ID del servicio a eliminar: ");
                    int idd = Integer.parseInt(scanner.nextLine());
                    dao.eliminar(idd);
                    System.out.println("Servicio eliminado.");
                }
                case 5 -> {
                    List<servicios> lista = dao.listar();
                    if (lista.isEmpty()) System.out.println("No hay servicios.");
                    else lista.forEach(System.out::println);
                }
            }
        } while (op != 0);
    }

    private static void menuRegistros(Scanner scanner, registrosLavadoDao dao, vehiculosDao vehDAO, serviciosDao servDAO) {
        int op;
        do {
            System.out.println("\n-------GESTION REGISTROS LAVADO-------");
            System.out.println("1. Registrar Lavado");
            System.out.println("2. Consultar Registro");
            System.out.println("3. Actualizar Registro");
            System.out.println("4. Eliminar Registro");
            System.out.println("5. Listar Registros (ver IDs)");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            op = Integer.parseInt(scanner.nextLine());

            switch (op) {
                case 1 -> {
                    System.out.print("Enter para id automatico o introduce el id personalizado");
                    String idStr = scanner.nextLine().trim();
                    int id = 0;
                    if (!idStr.isEmpty()) id = Integer.parseInt(idStr);

                    System.out.println("Vehículos:");
                    vehDAO.listar().forEach(v -> System.out.println("ID=" + v.getVehiculoID() + " -> " + v.getPlaca()));
                    System.out.print("Ingrese ID del vehículo: "); int idVeh = Integer.parseInt(scanner.nextLine());

                    System.out.println("Servicios:");
                    servDAO.listar().forEach(s -> System.out.println("ID=" + s.getServicioID() + " -> " + s.getNombre()));
                    System.out.print("Ingrese ID del servicio: "); int idServ = Integer.parseInt(scanner.nextLine());

                    System.out.print("Fecha lavado (YYYY-MM-DD): "); LocalDate fecha = LocalDate.parse(scanner.nextLine());
                    System.out.print("Hora inicio (HH:MM): "); LocalTime inicio = LocalTime.parse(scanner.nextLine());
                    System.out.print("Hora fin (HH:MM): "); LocalTime fin = LocalTime.parse(scanner.nextLine());
                    System.out.print("Precio total: "); double precio = Double.parseDouble(scanner.nextLine());

                    registrosLavado r = new registrosLavado(id, idVeh, idServ, fecha, inicio, fin, precio);
                    dao.crear(r);
                    System.out.println("Registro creado. ID: " + r.getRegistroID());
                }
                case 2 -> {
                    System.out.println("Registros:");
                    dao.listar().forEach(reg -> System.out.println("ID=" + reg.getRegistroID() + " -> VehiculoID=" + reg.getVehiculoID()));
                    System.out.print("Ingrese ID del registro a consultar: ");
                    int idq = Integer.parseInt(scanner.nextLine());
                    registrosLavado r = dao.leer(idq);
                    System.out.println(r != null ? r : "Registro no encontrado.");
                }
                case 3 -> {
                    System.out.print("Ingrese ID del registro a actualizar: ");
                    int idu = Integer.parseInt(scanner.nextLine());
                    registrosLavado r = dao.leer(idu);
                    if (r != null) {
                        System.out.print("Nuevo vehiculoID (" + r.getVehiculoID() + "): "); String nv = scanner.nextLine(); if (!nv.isEmpty()) r.setVehiculoID(Integer.parseInt(nv));
                        System.out.print("Nuevo servicioID (" + r.getServicioID() + "): "); String ns = scanner.nextLine(); if (!ns.isEmpty()) r.setServicioID(Integer.parseInt(ns));
                        System.out.print("Nueva fecha (" + r.getFechaLavado() + "): "); String nf = scanner.nextLine(); if (!nf.isEmpty()) r.setFechaLavado(LocalDate.parse(nf));
                        System.out.print("Nueva hora inicio (" + r.getHoraInicio() + "): "); String ni = scanner.nextLine(); if (!ni.isEmpty()) r.setHoraInicio(LocalTime.parse(ni));
                        System.out.print("Nueva hora fin (" + r.getHoraFin() + "): "); String nf2 = scanner.nextLine(); if (!nf2.isEmpty()) r.setHoraFin(LocalTime.parse(nf2));
                        System.out.print("Nuevo precio (" + r.getPrecioTotal() + "): "); String np = scanner.nextLine(); if (!np.isEmpty()) r.setPrecioTotal(Double.parseDouble(np));
                        dao.actualizar(r);
                        System.out.println("Registro actualizado.");
                    } else System.out.println("Registro no encontrado.");
                }
                case 4 -> {
                    System.out.print("Ingrese ID del registro a eliminar: ");
                    int idd = Integer.parseInt(scanner.nextLine());
                    dao.eliminar(idd);
                    System.out.println("Registro eliminado.");
                }
                case 5 -> {
                    List<registrosLavado> lista = dao.listar();
                    if (lista.isEmpty()) System.out.println("No hay registros.");
                    else lista.forEach(System.out::println);
                }
            }
        } while (op != 0);
    }
}