/**
 * Autor: Cristian Sebastian Aldea y Alejandro Baschwitz Rodríguez
 * Fecha: 11/06/2025
 */
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CamaraPeaje extends Camara implements Serializable{
    // Atributos
    private double tamanioInsertado;
    private LocalDateTime fechaHora;

    // Constructor
    public CamaraPeaje() {
        super();
        tamanioInsertado = 0;
        fechaHora = null;
    }

    // Detecta el vehículo e introduce los datos
    public void detectarVehiculo(String mI, double tI, String fech) {
        super.detectarVehiculo(mI);
        if ((tI != 0.0))
            tamanioInsertado = tI;
        fechaHora = LocalDateTime.parse(fech, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // Getters
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public double getTamanioInsertado() {
        return tamanioInsertado;
    }

    // Setters
    public void setTamanioInsertado(double tamanioInsertado) {
        this.tamanioInsertado = tamanioInsertado;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    // Implementación del método abstracto para enviar los datos
    @Override
    public Vehiculo listoEnvioDatos(SistemaPeaje sistema) {
        Vehiculo v = new Vehiculo(getMatriculaInsertada(), getTamanioInsertado(), getFechaHora(), null);
        // Resetear la cámara tras mandar los datos al sistema
        setMatriculaInsertada(null);
        setTamanioInsertado(0.0);
        setFechaHora(null);
        setEstadoC(EstadoCamara.ACTIVADA);
        return v;
    }


}
