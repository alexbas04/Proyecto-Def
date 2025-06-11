/**
 * Autor: Cristian Sebastian Aldea y Alejandro Baschwitz Rodríguez
 * Fecha: 11/06/2025
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Cargamos el sistema completo
        SistemaPeaje sistema = SistemaPeaje.cargarSistemaPeaje("datos/peaje.dat");
        Scanner scanner = new Scanner(System.in);

        boolean salir = false;
        while (!salir) {
            sistema.menuPrincipal();
            int opcionPrincipal = scanner.nextInt();
            scanner.nextLine();

            switch (opcionPrincipal) {
                case 1: // Operario
                    boolean volverOperario = false;
                    while (!volverOperario) {
                        sistema.menuOperario();
                        int opcionOperario = scanner.nextInt();
                        scanner.nextLine();
                        if (opcionOperario == -1) {
                            volverOperario = true;
                            continue;
                        }

                        switch (opcionOperario) {
                            case 1:
                                System.out.println("Selecciona una de las siguientes cámaras de TRAMO para la ENTRADA: ");/* Aqui mostramos todas las camaras de tramo disponibles */
                                for (int i = 0; i < sistema.getCamaras().size(); i++) {
                                    if (sistema.getCamaras().get(i) instanceof CamaraTramo) {
                                        System.out.println("[ " + i + " ] " + sistema.getCamaras().get(i).getClass().getSimpleName());
                                    }
                                }
                                System.out.print("Introduzca el número de la cámara que deseas seleccionar: ");
                                int camaraEntradaIndex = scanner.nextInt();
                                scanner.nextLine();

                                System.out.print("Introduzca una matrícula (Ejemplo:1111AAA): ");
                                String matriculaEntrada = scanner.nextLine();
                                if ((matriculaEntrada !=null) && (!matriculaEntrada.isEmpty()) ) {
                                    if (camaraEntradaIndex >= 0 && camaraEntradaIndex < sistema.getCamaras().size() &&
                                            sistema.getCamaras().get(camaraEntradaIndex) instanceof CamaraTramo) {

                                        CamaraTramo camaraEntradaTramo = (CamaraTramo) sistema.getCamaras().get(camaraEntradaIndex);

                                        camaraEntradaTramo.detectarVehiculo(matriculaEntrada);
                                        if(sistema.procesarCamara(camaraEntradaTramo)){
                                            System.out.println("La entrada al peaje se ha registrado con exito");
                                        }else{
                                            System.out.println("La entrada al peaje no se ha podido registrar, ya que el vehículo ya se encuentra dentro del peaje");
                                        }

                                    } else {
                                        System.out.println("Error al seleccionar la camara de tramo, ya que no has seleccionado una camara valida");
                                    }
                                } else {
                                    System.out.println("La matricula introducida no es valida");
                                }break;

                            case 2:
                                System.out.println("Selecciona una de las siguientes cámaras de PEAJE para la SALIDA: ");/* Aqui mostramos todas las camaras de peaje disponibles */
                                for (int i = 0; i < sistema.getCamaras().size(); i++) {
                                    if (sistema.getCamaras().get(i) instanceof CamaraPeaje) {
                                        System.out.println("[ " + i + " ] " + sistema.getCamaras().get(i).getClass().getSimpleName());
                                    }
                                }

                                System.out.print("Introduzca el número de la cámara que deseas seleccionar: ");
                                int camaraSalidaIndex = scanner.nextInt();
                                scanner.nextLine();

                                System.out.println("Lista de los vehículos que están actualmente dentro del peaje: ");
                                Set<Vehiculo> cochesDentro = sistema.getCochesDentroPeaje(); /*Es la lista con los vehículos que han entrado con el case 1*/
                                List<String> aux2= new ArrayList<>();
                                if (cochesDentro.isEmpty()) {
                                    System.out.println("No hay vehículos detectados dentro del peaje.");
                                    break;
                                } else {
                                    int i = 0;
                                    for (Vehiculo v : cochesDentro) {
                                        System.out.println("[ " + i + " ] " + v.getMatricula());
                                        i++;
                                        aux2.add(v.getMatricula());
                                    }
                                }
                                System.out.print("Selecciona el índice de la matricula de la salida: ");
                                int opcionLista= Integer.parseInt(scanner.nextLine());
                                String matriculaSalida =aux2.get(opcionLista);
                                Vehiculo aux = new Vehiculo(matriculaSalida);
                                if (!cochesDentro.contains(aux)){
                                    System.out.println("No se ha podido encontrar el vehículo dentro del sistema. No se ha registrado la salida.");
                                }
                                String fechaSalida;
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                boolean fechaValida;

                                do {
                                    System.out.print("Introduzca la fecha de Salida (yyyy-MM-dd HH:mm:ss): ");
                                    fechaSalida = scanner.nextLine();
                                    fechaValida = true;
                                    try {
                                        LocalDateTime.parse(fechaSalida, formatter);
                                    } catch (DateTimeParseException e) {
                                        System.out.println("El formato es incorrecto. Intentelo de nuevo.");
                                        fechaValida = false;
                                    }
                                } while (!fechaValida);

                                System.out.print("Introduzca el tamaño del vehículo (m2): ");
                                double tamanioSalida = scanner.nextDouble();
                                scanner.nextLine();

                                if (camaraSalidaIndex >= 0 && camaraSalidaIndex < sistema.getCamaras().size() &&
                                        sistema.getCamaras().get(camaraSalidaIndex) instanceof CamaraPeaje) {

                                    CamaraPeaje camaraSalidaPeaje = (CamaraPeaje) sistema.getCamaras().get(camaraSalidaIndex);

                                    camaraSalidaPeaje.detectarVehiculo(matriculaSalida, tamanioSalida,fechaSalida);
                                    System.out.println("La salida del peaje se ha registrado con exito");
                                    sistema.registrarSalidaYCalcularMultaTramo(camaraSalidaPeaje);
                                } else {
                                    System.out.println("Error al seleccionar la camara de peaje");
                                }
                                break;
                            case 3:
                                System.out.print("Introduzca la matrícula del vehículo que deseas consultar (Ejemplo:1111AAA): ");
                                String matriculaMultas = scanner.nextLine();
                                List<Multa> multas = sistema.buscarMultas(matriculaMultas);
                                if (multas.isEmpty()) { /*Comprobar si tiene multas, y si las tiene imprimirlas*/
                                    System.out.println("No hay multas de este vehículo.");
                                } else {
                                    for (Multa m : multas) {
                                        System.out.println(m);
                                    }
                                }
                                break;
                            case 4:
                                System.out.println("Camaras de SALIDA con vehículo pendiente de procesar:");/*Todos los vehículos pendientes de procesar del case 2*/
                                boolean hayPendientesMatricula = false;

                                for (Camara c : sistema.getCamaras()) {
                                    if (c instanceof CamaraPeaje) {
                                        CamaraPeaje cp = (CamaraPeaje) c;
                                        if (cp.getMatriculaInsertada() != null) {
                                            System.out.println("Camaras de peaje con vehículo pendiente: " + cp.getMatriculaInsertada());
                                            hayPendientesMatricula = true;
                                        }
                                    }
                                }

                                if (!hayPendientesMatricula) {
                                    System.out.println("No hay cámaras con vehículos pendientes.");
                                    break;
                                }

                                System.out.print("Introduzca la matrícula para generar el ticket (Ejemplo:1111AAA): ");
                                String matriculaTicket = scanner.nextLine();
                                scanner.nextLine();

                                CamaraPeaje camaraConVehiculo = null;
                                for (Camara c : sistema.getCamaras()) {
                                    if (c instanceof CamaraPeaje) {
                                        CamaraPeaje cp = (CamaraPeaje) c;
                                        if (matriculaTicket.equalsIgnoreCase(cp.getMatriculaInsertada())) {
                                            camaraConVehiculo = cp;
                                            break;
                                        }
                                    }
                                }

                                if (camaraConVehiculo != null) {
                                    sistema.generarTicket(camaraConVehiculo.getMatriculaInsertada(), camaraConVehiculo.getTamanioInsertado(), camaraConVehiculo.getFechaHora());
                                    System.out.println("Ticket generado para matrícula " + matriculaTicket);
                                    sistema.procesarCamara(camaraConVehiculo);
                                } else {
                                    System.out.println("No se ha encontrado ninguna cámara de peaje con esa matrícula pendiente.");
                                }
                                break;

                            case 5:
                                System.out.print("Introduzca una matrícula para consultar sus tickets de peaje (Ejemplo:1111AAA): ");
                                String matriculaConsultaTickets = scanner.nextLine();
                                sistema.generarFicheroTicketVehiculo(matriculaConsultaTickets);
                                break;
                            case 6:
                                volverOperario = true;
                                break;

                            default:
                                System.out.println("Opción no válida.");
                        }
                    }
                    break;

                case 2: // Agente
                    boolean volverAgente = false;
                    while (!volverAgente) {
                        sistema.menuAgentes();
                        int opcionAgente = scanner.nextInt();
                        scanner.nextLine();
                        if (opcionAgente == -1) {
                            volverAgente = true;
                            continue;
                        }

                        switch (opcionAgente) {
                            case 1:
                                System.out.println("Selecciona una de las siguientes cámaras RADAR MÓVIL: ");
                                for (int i = 0; i < sistema.getCamaras().size(); i++) {
                                    if (sistema.getCamaras().get(i) instanceof RadarMovil) {
                                        System.out.println("[ " + i + " ] RadarMovil");
                                    }
                                }
                                System.out.print("Introduzca el número de la cámara que deseas seleccionar: ");
                                int camaraRadarIndex = scanner.nextInt();
                                scanner.nextLine();

                                if (camaraRadarIndex >= 0 && camaraRadarIndex < sistema.getCamaras().size()
                                        && sistema.getCamaras().get(camaraRadarIndex) instanceof RadarMovil) {

                                    RadarMovil radar = (RadarMovil) sistema.getCamaras().get(camaraRadarIndex);

                                    System.out.print("Introduzca la matrícula del vehículo (Ejemplo:1111AAA): ");
                                    String matriculaMultaMovil = scanner.nextLine();

                                    System.out.print("Introduzca la velocidad (km/h): ");
                                    double velocidad = scanner.nextDouble();
                                    scanner.nextLine();

                                    if (velocidad>120) {
                                        radar.detectarVehiculo(matriculaMultaMovil, velocidad);
                                        if (sistema.procesarCamara(radar)) {
                                            System.out.println("Vehículo procesado correctamente por Radar Móvil.");
                                        } else {
                                            System.out.println("No se ha podido procesar la cámara.");
                                        }
                                    } else {System.out.println("No se puede generar una multa si la velocidad no supera la velocidad maxima permitida(120km/h)");}
                                } else {
                                    System.out.println("Cámara no válida.");
                                }

                            case 2:
                                volverAgente = true;
                                break;
                            default:
                                System.out.println("Opción no válida.");
                        }
                    }
                    break;

                case 3: // Conductor
                    boolean volverConductor = false;
                    while (!volverConductor) {
                        sistema.menuConductores();
                        int opcionConductor = scanner.nextInt();
                        scanner.nextLine();
                        if (opcionConductor == -1) {
                            volverConductor = true;
                            continue;
                        }

                        switch (opcionConductor) {
                            case 1:
                                System.out.print("Introduzca la matrícula (Ejemplo:1111AAA): ");
                                String matriculaHistorial = scanner.nextLine();
                                List<Multa> historial = sistema.buscarMultas(matriculaHistorial);
                                if (historial.isEmpty()) {
                                    System.out.println("No hay multas.");
                                } else {
                                    for (Multa m : historial) {
                                        System.out.println(m);
                                    }
                                }
                                break;
                            case 2:
                                System.out.print("Introduzca la matrícula (Ejemplo:1111AAA): ");
                                String matriculaPendientes = scanner.nextLine();
                                List<Multa> pendientes = sistema.buscarMultasPendientes(matriculaPendientes);
                                if (pendientes.isEmpty()) {
                                    System.out.println("No hay multas pendientes.");
                                } else {
                                    for (Multa m : pendientes) {
                                        System.out.println(m);
                                    }
                                }
                                break;
                            case 3:
                                System.out.print("Introduzca la matrícula (Ejemplo:1111AAA): ");
                                String matriculaPagar = scanner.nextLine();
                                List<Multa> multasAPagar = sistema.buscarMultasPendientes(matriculaPagar);
                                if (multasAPagar.isEmpty()) {
                                    System.out.println("No hay multas pendientes.");
                                } else {
                                    // Mostrar las multas con un número
                                    System.out.println("Multas pendientes:");
                                    for (int i = 0; i < multasAPagar.size(); i++) {
                                        System.out.println("[ " + i + " ] " + multasAPagar.get(i));
                                    }

                                    System.out.print("Introduzca el número de la multa que quieres pagar (o -1 para cancelar): ");
                                    int opcionMulta = scanner.nextInt();
                                    scanner.nextLine();

                                    if (opcionMulta == -1) {
                                        System.out.println("Pago cancelado.");
                                    } else if (opcionMulta >= 0 && opcionMulta < multasAPagar.size()) {
                                        Multa multaSeleccionada = multasAPagar.get(opcionMulta);
                                        sistema.pagarMulta(multaSeleccionada);
                                        System.out.println("Multa pagada: " + multaSeleccionada);
                                    } else {
                                        System.out.println("Opción no válida.");
                                    }
                                }
                                break;
                            case 4:
                                volverConductor = true;
                                break;
                            default:
                                System.out.println("Opción no válida.");
                        }
                    }
                    break;
                case 4: // Salida del sistema
                    sistema.guardarSistemaPeaje("datos/peaje.dat"); /*Antes de salir guardamos los datos en el fichero peaje*/
                    System.out.println("Datos guardados correctamente. Saliendo...");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
        scanner.close();
    }
}
