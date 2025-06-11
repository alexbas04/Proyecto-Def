/**
 * Autor: Cristian Sebastian Aldea y Alejandro Baschwitz Rodríguez
 * Fecha: 11/06/2025
 */
import java.io.*;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDateTime;
import java.io.Serializable;

public class SistemaPeaje implements Serializable{
    // Atributos
    private int contadorNumeroVehiculos;
    private List<Camara> camaras;
    private List<Multa> multas;
    private List<Ticket> tickets;
    private Set<Vehiculo> cochesDentroPeaje;

    // Constructor
    public SistemaPeaje() {
        contadorNumeroVehiculos = 0;
        multas = new ArrayList<>();
        tickets = new ArrayList<>();
        camaras = new ArrayList<>();
        cochesDentroPeaje = new HashSet<>();
        // Se instalan cuatro cámaras de cada tipo, una por carril
        for (int i = 0; i < 4; i++) { // 4 cámaras de peaje en la salida
            camaras.add(new CamaraPeaje());
        }

        for (int i = 0; i < 4; i++) { // 4 cámaras de tramo en la entrada
            camaras.add(new CamaraTramo());
        }

        for (int i = 0; i < 4; i++) { // 4 radares móviles en posiciones intermedias
            camaras.add(new RadarMovil());
        }
    }

    // Getter
    public List<Camara> getCamaras() {
        return camaras;
    }

    public Set<Vehiculo> getCochesDentroPeaje() {
        return cochesDentroPeaje;
    }

    public boolean procesarCamara(Camara c) {
        if (c != null && c.getMatriculaInsertada() != null) {
            Vehiculo v = c.listoEnvioDatos(this);
            // Si la cámara es CamaraTramo → añadir a cochesDentroPeaje
            if (c instanceof CamaraTramo) {
                if(!cochesDentroPeaje.contains(v)) {
                    contadorNumeroVehiculos++;
                    cochesDentroPeaje.add(v);
                }else{
                    return false;
                }
            }
            // Si la cámara es CamaraPeaje → eliminar de cochesDentroPeaje
            if (c instanceof CamaraPeaje) {
                if(cochesDentroPeaje.contains(v)){
                    cochesDentroPeaje.removeIf(vehiculo -> vehiculo.getMatricula().equalsIgnoreCase(v.getMatricula()));
                }else {
                    return false;
                }
            }
            //Si la cámara es RadarMovil → generamos una multa cuando proceda
            if (c instanceof RadarMovil) {
                generarMultaRadarMovil(v.getMatricula(), v.getVelocidad());
            }
        }
        return true;
    }


    public void menuPrincipal() {
        System.out.println("\n--------[ MENU PRINCIPAL ]--------\n");
        System.out.println("INDICA QUE TIPO DE USUARIO ERES:\n");
        System.out.println("[1.] Operario \n");
        System.out.println("[2.] Agente de la autoridad\n");
        System.out.println("[3.] Conductor\n");
        System.out.println("[4.] Salir\n");
        System.out.print("--> Seleccione una opción (o -1 para volver): ");
    }
    public void menuOperario() {
        System.out.print("\n\t Selecciona una opción para continuar:\n");
        System.out.println("[1.] Introducir los datos de un vehículo que ACCEDE a la autopista \n");
        System.out.println("[2.] Introducir los datos de un vehículo que SALE de la autopista\n");
        System.out.println("[3.] Consulta de multas de velocidad media\n");
        System.out.println("[4.] Generar ticket de peaje\n");
        System.out.println("[5.] Consultar peajes de un determinado vehículo\n");
        System.out.println("[6.] Volver al menu principal\n");
        System.out.print("--> Seleccione una opción (o -1 para volver): ");
    }

    public void menuAgentes() {
        System.out.print("\n\t Selecciona una opción para continuar: \n");
        System.out.println("[1.] Generar una Multa \n");
        System.out.println("[2.] Volver al menu principal\n");
        System.out.print("--> Seleccione una opción (o -1 para volver): ");
    }

    public void menuConductores() {
        System.out.print("\n\t Selecciona una opción para continuar: \n");
        System.out.println("[1.] Consultar el historial de multas \n");
        System.out.println("[2.] Consultar las multas pendientes de pago\n");
        System.out.println("[3.] Pagar las multas\n");
        System.out.println("[4.] Volver al menu principal\n");
        System.out.print("--> Seleccione una opción (o -1 para volver): ");
    }

    public void generarMultaRadarMovil(String matricula, double velocidad) {

        if (velocidad > 120) {
            Multa multa = new Multa(matricula, velocidad, TipoRadar.RADAR_MOVIL);
            registrarMulta(multa);
            System.out.println("Se ha generado una multa de radar móvil.");
        } else {
            System.out.println("El vehículo no supera el límite de velocidad para multa.");
        }
    }

    // Crear ticket de peaje
    public Ticket generarTicket(String matricula, double tamanio, LocalDateTime fechaHora) {
        double importe = calcularImportePeaje(tamanio, fechaHora);
        Ticket ticket = new Ticket(matricula, tamanio, fechaHora, importe);
        tickets.add(ticket);
        return ticket;
    }

    private double calcularImportePeaje(double tamanio, LocalDateTime fechaHora) {
        int hora = fechaHora.getHour();

        if (tamanio < 10) { // Turismo
            if (hora >= 0 && hora <= 11) {
                return 2.5;
            } else {
                return 3.0;
            }
        } else if (tamanio >= 10 && tamanio < 20) { // Pesado1
            if (hora >= 0 && hora <= 11) {
                return 4.0;
            } else {
                return 5.0;
            }
        } else { // Pesado2 (tamanio >= 20)
            if (hora >= 0 && hora <= 11) {
                return 6.5;
            } else {
                return 8.0;
            }
        }
    }

    // Registrar multa
    public void registrarMulta(Multa multa) {
        multas.add(multa);
    }

    // Buscar multas por matrícula
    public List<Multa> buscarMultas(String matricula) {
        List<Multa> resultado = new ArrayList<>();
        for (Multa m : multas) {
            if (m.getMatricula().equalsIgnoreCase(matricula)) {
                resultado.add(m);
            }
        }
        resultado.sort(Comparator
                .comparing(Multa::estaPagada) // false (no pagadas) va primero
                .thenComparing(Comparator.comparing(Multa::getFechaHora).reversed())); // fechas recientes primero
        return resultado;
    }

    // Buscar multas pendientes
    public List<Multa> buscarMultasPendientes(String matricula) {
        List<Multa> resultado = new ArrayList<>();
        for (Multa m : multas) {
            if (m.getMatricula().equalsIgnoreCase(matricula) && !m.estaPagada()) {
                resultado.add(m);
            }
        }
        resultado.sort(Comparator.comparing(Multa::getFechaHora).reversed());
        return resultado;
    }

    // Pagar multa
    public void pagarMulta(Multa multa) {
        multa.setPagada(true);
    }

    // Consultar tickets de un vehículo
    public List<Ticket> buscarTickets(String matricula) {
        List<Ticket> resultado = new ArrayList<>();
        for (Ticket t : tickets) {
            if (t.getMatricula().equalsIgnoreCase(matricula)) {
                resultado.add(t);
            }
        }
        return resultado;
    }

    // Generar fichero de historial de tickets
    public void generarFicheroTicketVehiculo(String matricula) {
        List<Ticket> historial = buscarTickets(matricula);

        if (historial.isEmpty()) {
            System.out.println("No hay tickets para el vehículo " + matricula);
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fechaConsulta = LocalDateTime.now().format(formatter);
        String nombreFichero = "datos/" + matricula + "-" + fechaConsulta + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreFichero))) {
            writer.write("Historial de peajes para vehículo: " + matricula + "\n");
            writer.write("Número de veces que ha transitado: " + historial.size() + "\n\n");

            DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

            for (Ticket t : historial) {
                writer.write("- " + t.getFechaHora().format(formatoHora) + "\n");
            }

            System.out.println("Historial de tickets generado: " + nombreFichero);
        } catch (IOException e) {
            System.out.println("Error al escribir el fichero: " + e.getMessage());
        }
    }
    public void registrarSalidaYCalcularMultaTramo(CamaraPeaje cp) {

        if (cochesDentroPeaje.contains(new Vehiculo(cp.getMatriculaInsertada()))) {
            LocalDateTime fechaEntrada = null;
            LocalDateTime fechaSalida = cp.getFechaHora();
            for(Vehiculo v: cochesDentroPeaje){
                if(v.getMatricula().equalsIgnoreCase(cp.getMatriculaInsertada())){
                    fechaEntrada = v.getFechaHora();
                }
            }

            Duration duracion = Duration.between(fechaEntrada, fechaSalida);
            double horas = duracion.toMinutes() / 60.0;
            double distanciaKm = 100.0; // o puedes hacer que sea configurable

            double velocidadMedia = distanciaKm / horas;

            if (velocidadMedia > 120) {
                Multa multaTramo = new Multa(cp.getMatriculaInsertada(), velocidadMedia, TipoRadar.RADAR_TRAMO);
                registrarMulta(multaTramo);
                System.out.println("¡Multa por TRAMO! Velocidad media = " + velocidadMedia + " km/h");
            } else {
                System.out.println("Velocidad media aceptable: " + velocidadMedia + " km/h");
            }
            cochesDentroPeaje.remove(new Vehiculo(cp.getMatriculaInsertada()));
            return;
        }


        System.out.println("Vehículo no encontrado en el tramo.");
    }

    // Guardar el sistema completo
    public void guardarSistemaPeaje(String nombreFichero) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nombreFichero))) {
            out.writeObject(this);
            System.out.println("Sistema de peaje guardado en " + nombreFichero);
        } catch (IOException e) {
            System.out.println("Error al guardar el sistema de peaje: " + e.getMessage());
        }
    }

    // Cargar el sistema completo
    public static SistemaPeaje cargarSistemaPeaje(String nombreFichero) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(nombreFichero))) {
            SistemaPeaje sistema = (SistemaPeaje) in.readObject();
            System.out.println("Sistema de peaje cargado desde " + nombreFichero);
            return sistema;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No se pudo cargar el sistema de peaje: " + e.getMessage());
            return new SistemaPeaje(); // Si no existe o hay error, devolver sistema nuevo vacío
        }
    }

}