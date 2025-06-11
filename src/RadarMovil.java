/**
 * Autor: Cristian Sebastian Aldea y Alejandro Baschwitz Rodríguez
 * Fecha: 11/06/2025
 */
import java.io.Serializable;

public class RadarMovil extends Camara implements Serializable{
    /*---ATRIBUTOS---*/
    private double velocidadDetectada;

    /*---CONSTRUCTOR---*/
    public RadarMovil() {
        super();
        velocidadDetectada = 0.0;
    }

    /*---METODO para detectar el vehiculo---*/
    public void detectarVehiculo(String mI, double velocidad) {
        velocidadDetectada = velocidad;
        super.detectarVehiculo(mI);

    }

    /*---GETTER---*/
    public double getVelocidadDetectada() {
        return velocidadDetectada;
    }

    /*---Funcion del metodo padre---*/
    @Override
    public Vehiculo listoEnvioDatos(SistemaPeaje sistema) {
        Vehiculo v = new Vehiculo(getMatriculaInsertada(), 0.0, null, velocidadDetectada);
        // Resetear la cámara tras mandar los datos al sistema
        setMatriculaInsertada(null);
        velocidadDetectada = 0.0;
        setEstadoC(EstadoCamara.ACTIVADA);
        return v;
    }

    /*---METODO toString---*/
    @Override
    public String toString() {
        return "RadarMovil{" +
                "velocidadDetectada=" + velocidadDetectada +
                '}';
    }
}
