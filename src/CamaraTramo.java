/**
 * Autor: Cristian Sebastian Aldea y Alejandro Baschwitz Rodríguez
 * Fecha: 11/06/2025
 */
import java.io.Serializable;
import java.time.LocalDateTime;

public class CamaraTramo extends Camara implements Serializable{
    // Atributos
    private LocalDateTime fechaHora;

    // Constructor
    public CamaraTramo() {
        super();
        fechaHora = null;
    }

    // Getter
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    // Setter
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    // Detecta el vehículo
    public void detectarVehiculo(String mI) {
        super.detectarVehiculo(mI);
        fechaHora = LocalDateTime.now();
    }

    // Implementación del método abstracto para enviar los datos
    @Override
    public Vehiculo listoEnvioDatos(SistemaPeaje sistema) {
        Vehiculo v = new Vehiculo(getMatriculaInsertada(), getFechaHora(), null);
        // Resetear la cámara tras mandar los datos al sistema
        setMatriculaInsertada(null);
        setFechaHora(null);
        setEstadoC(EstadoCamara.ACTIVADA);
        return v;
    }
}
