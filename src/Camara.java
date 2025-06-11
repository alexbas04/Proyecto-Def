/**
 * Autor: Cristian Sebastian Aldea y Alejandro Baschwitz Rodríguez
 * Fecha: 11/06/2025
 */
import java.io.Serializable;

public abstract class Camara implements Serializable{
    // Atributos
    private String matriculaInsertada;
    private EstadoCamara estadoC;

    // Constructor
    public Camara() {
        matriculaInsertada = null;
        estadoC = EstadoCamara.ACTIVADA;
    }

    // Getter
    public String getMatriculaInsertada() {
        return matriculaInsertada;
    }

    // Setters
    public void setMatriculaInsertada(String matriculaInsertada) {
        this.matriculaInsertada = matriculaInsertada;
    }

    public void setEstadoC(EstadoCamara estadoC) {
        this.estadoC = estadoC;
    }

    // Detecta el vehículo
    public void detectarVehiculo(String mI) {
        if ((mI != null) && (!mI.isEmpty()))
            matriculaInsertada = mI;
        estadoC = EstadoCamara.TRANSMISION;
    }

    // toString
    @Override
    public String toString() {
        return "Camara{" +
                ", TamanioInsertado=" +
                ", Matricula Insertada='" + matriculaInsertada + '\'' +
                '}';
    }

    // Método abstracto que heredan los hijos para enviar los datos
    public abstract Vehiculo listoEnvioDatos(SistemaPeaje sistema);

}
