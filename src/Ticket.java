/**
 * Autor: Cristian Sebastian Aldea y Alejandro Baschwitz Rodríguez
 * Fecha: 11/06/2025
 */
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ticket implements Serializable {
    // Atributos
    private String matricula;
    private double tamanio;
    private LocalDateTime fechaHora;
    private double importe;

    // Constructor
    public Ticket(String m, double t, LocalDateTime fH, double i) {
        if ((m != null) && (!m.isEmpty()) && (fH!=null) && (t>0.0) && (i>0.0)) {
            matricula = m;
            tamanio = t;
            fechaHora = fH;
            importe = i;
        }
    }

    // Getters
    public String getMatricula() {
        return matricula;
    }

    public double getTamanio() {
        return tamanio;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public double getImporte() {
        return importe;
    }

    // Formatea la fecha
    private String fechaString(LocalDateTime f) {
        return f.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // toString
    @Override
    public String toString() {
        return "Ticket{" +
                "Matricula= '" + matricula + '\'' +
                ", Tamaño= " + tamanio +
                ", Fecha y Hora= " + fechaString(fechaHora) +
                ", Importe= " + importe +
                '}';
    }
}
