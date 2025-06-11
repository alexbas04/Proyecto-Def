/**
 * Autor: Cristian Sebastian Aldea y Alejandro Baschwitz Rodríguez
 * Fecha: 11/06/2025
 */
import java.io.Serializable;
import java.time.LocalDateTime;

public class Vehiculo implements Serializable{
    // Atributos
    private String matricula;
    private double tamanio;
    private LocalDateTime fechaHora;
    private Double velocidad;

    // Constructores
    public Vehiculo(String ma, double tam,LocalDateTime fechH, Double vel) {
        if ((ma != null) && (esMatriculaValida(ma))) {
            matricula = ma;
            tamanio = tam;
            fechaHora = fechH;
            velocidad = vel;
        }
    }

    public Vehiculo(String ma,double tam,LocalDateTime fechH) {
        this(ma,tam,fechH,0.0);
    }

    public Vehiculo(String ma,LocalDateTime fechH, Double vel) {
        this(ma,0.0,fechH,0.0);
    }

    public Vehiculo(String ma, Double vel) {
        this(ma,0.0,null,vel);
    }
    public Vehiculo(String ma) {
        this(ma,0.0,null,0.0);
    }

    private boolean esMatriculaValida(String matricula) {

        if (matricula.length() != 7) { /* Verificar que la matricula esta compuesta por 7 elementos*/
            return false;
        }
        for (int i = 0; i < 4; i++) { /* Verificar que la matricula empieza por 4 numeros*/
            char c = matricula.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        for (int i = 4; i < 7; i++) {/* Verificar que la matricula termina por 3 letras en mayusculas*/
            char c = matricula.charAt(i);
            if (!Character.isUpperCase(c)) {
                return false;
            }
        }
        return true;
    }
    // Getters
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public Double getVelocidad() {
        return velocidad;
    }

    public String getMatricula() {
        return matricula;
    }

    // Setters
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public void setVelocidad(Double velocidad) {
        this.velocidad = velocidad;
    }

    // Métodos toString, equals y hashCode
    @Override
    public String toString() {
        return "Matrícula: " + matricula +
                " | Tamaño: " + tamanio + " m²" +
                (fechaHora != null ? " | FechaHora: " + fechaHora : "") +
                (velocidad != null ? " | Velocidad: " + velocidad + " km/h" : "");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vehiculo otro = (Vehiculo) obj;
        return this.matricula.equals(otro.matricula);
    }

    @Override
    public int hashCode() {
        return matricula.hashCode();
    }

}
