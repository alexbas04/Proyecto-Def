/**
 * Autor: Cristian Sebastian Aldea y Alejandro Baschwitz Rodríguez
 * Fecha: 11/06/2025
 */
import java.io.Serializable;

public abstract class Camara implements Serializable{
    /*---ATRIBUTOS---*/
    private String matriculaInsertada;
    private EstadoCamara estadoC;

    /*---CONSTRUCTOR---*/
    public Camara() {
        matriculaInsertada = null;
        estadoC = EstadoCamara.ACTIVADA;
    }

    /*---GETTER---*/
    public String getMatriculaInsertada() {
        return matriculaInsertada;
    }

    /*---SETTERS---*/
    public void setMatriculaInsertada(String matriculaInsertada) {
        this.matriculaInsertada = matriculaInsertada;
    }

    public void setEstadoC(EstadoCamara estadoC) {
        this.estadoC = estadoC;
    }

    /*---METODO para detectar el vehiculo---*/
    public void detectarVehiculo(String mI) {
        if ((mI != null) && (!mI.isEmpty()))
            matriculaInsertada = mI;
        estadoC = EstadoCamara.TRANSMISION;
    }

    /*---METODO toString---*/
    @Override
    public String toString() {
        return "Camara{" +
                ", TamanioInsertado=" +
                ", Matricula Insertada='" + matriculaInsertada + '\'' +
                '}';
    }

    /*---METODO abstracto que heredan los hijos para enviar los datos---*/
    public abstract Vehiculo listoEnvioDatos(SistemaPeaje sistema);

}
