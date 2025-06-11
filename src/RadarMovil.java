/**
 * Autor: Cristian Sebastian Aldea y Alejandro Baschwitz Rodríguez
 * Fecha: 11/06/2025
 */
import java.io.Serializable;

public class RadarMovil extends Camara implements Serializable{
    // Atributos
    private double velocidadDetectada;

    // Constructor
    public RadarMovil() {
        super();
        velocidadDetectada = 0.0;
    }

    // Detecta el vehículo
    public void detectarVehiculo(String mI, double velocidad) {
        velocidadDetectada = velocidad;
        super.detectarVehiculo(mI);

    }

    // Getter
    public double getVelocidadDetectada() {
        return velocidadDetectada;
    }

    // Implementación del método abstracto
    @Override
    public Vehiculo listoEnvioDatos(SistemaPeaje sistema) {
        Vehiculo v = new Vehiculo(getMatriculaInsertada(), 0.0, null, velocidadDetectada);
        // Resetear la cámara tras mandar los datos al sistema
        setMatriculaInsertada(null);
        velocidadDetectada = 0.0;
        setEstadoC(EstadoCamara.ACTIVADA);
        return v;
    }

    // toString
    @Override
    public String toString() {
        return "RadarMovil{" +
                "velocidadDetectada=" + velocidadDetectada +
                '}';
    }
}
