/**
 * Autor: Cristian Sebastian Aldea y Alejandro Baschwitz Rodríguez
 * Fecha: 11/06/2025
 */
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Multa implements Serializable {
    /*---ATRIBUTOS---*/
    private String matricula;
    private LocalDateTime fechaHora;  // Se toma desde la cámara
    private double velocidad;
    private double importe;
    private boolean pagada;
    private TipoRadar tipoRadar;

    /*---CONSTRUCTOR---*/
    public Multa(String matricula, double velocidad, TipoRadar tipoRadar) {
        this.matricula = matricula;
        this.fechaHora = LocalDateTime.now();
        this.velocidad = velocidad;
        this.tipoRadar = tipoRadar;
        this.pagada = false;
        this.importe = calcularImporte();
    }


    /*---METODO para calcular importes según la Tabla 2 ---*/
    private double calcularImporte() {
        if (tipoRadar == TipoRadar.RADAR_TRAMO) {
            if (velocidad <= 130) return 150;
            else if (velocidad <= 140) return 300;
            else if (velocidad <= 150) return 700;
            else return 1500;
        } else { // RADAR_MOVIL
            if (velocidad <= 130) return 100;
            else if (velocidad <= 140) return 150;
            else if (velocidad <= 150) return 230;
            else return 350;
        }
    }

    /*---GETTERS---*/
    public String getMatricula() {
        return matricula;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public double getImporte() {
        return importe;
    }

    public TipoRadar getTipoRadar() {
        return tipoRadar;
    }

    public boolean estaPagada() {
        return pagada;
    }

    /*---SETTER---*/
    public void setPagada(boolean pagada) {
        this.pagada = pagada;
    }

    /*---METODO para formatear la fecha---*/
    private String fechaString(LocalDateTime f){
        return f.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /*---METODO toString---*/
    @Override
    public String toString() {
        return "Multa [" +
                " Matricula= '" + matricula + '\'' +
                ", Fecha y Hora= " + fechaString(fechaHora) +
                ", Velocidad= " + String.format("%.2f", velocidad) +
                " km/h, Importe= " + importe +
                "€, Pagada= " + (pagada ? "Sí" : "No") +
                ", TipoRadar= " + tipoRadar + ']';
    }
}
