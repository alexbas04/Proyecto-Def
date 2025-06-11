/**
 * Autor: Cristian Sebastian Aldea y Alejandro Baschwitz Rodríguez
 * Fecha: 11/06/2025
 */
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CamaraPeaje extends Camara implements Serializable{
    /*---ATRIBUTOS---*/
    private double tamanioInsertado;
    private LocalDateTime fechaHora;

    /*---CONSTRUCTOR---*/
    public CamaraPeaje() {
        super();
        tamanioInsertado = 0;
        fechaHora = null;
    }

    /*---METODO para detectar el vehiculo---*/
    public void detectarVehiculo(String mI, double tI, String fech) {
        super.detectarVehiculo(mI);
        if ((tI != 0.0))
            tamanioInsertado = tI;
        fechaHora = LocalDateTime.parse(fech, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /*---GETTERS---*/
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public double getTamanioInsertado() {
        return tamanioInsertado;
    }

    /*---SETTERS---*/
    public void setTamanioInsertado(double tamanioInsertado) {
        this.tamanioInsertado = tamanioInsertado;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    /*---METODO heredado de la clase padre para enviar los datos---*/
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
