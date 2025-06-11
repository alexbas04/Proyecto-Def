/**
 * Autor: Cristian Sebastian Aldea y Alejandro Baschwitz Rodríguez
 * Fecha: 11/06/2025
 */
import java.io.Serializable;
import java.time.LocalDateTime;

public class CamaraTramo extends Camara implements Serializable{
    /*---ATRIBUTOS---*/
    private LocalDateTime fechaHora;

    /*---CONSTRUCTOR---*/
    public CamaraTramo() {
        super();
        fechaHora = null;
    }

    /*---GETTER---*/
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /*---SETTER---*/
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    /*---METODO para detectar el vehiculo---*/
    public void detectarVehiculo(String mI) {
        super.detectarVehiculo(mI);
        fechaHora = LocalDateTime.now();
    }

    /*---METODO heredado de la clase padre para enviar los datos---*/
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
