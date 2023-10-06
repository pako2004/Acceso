import java.io.Serializable;
import java.util.Date;

public class Alumno implements Serializable {
    private static final long serialVersionUID = 1L;
    private String apellidos;
    private String nombre;
    private String fNacimiento;
    private String ciclo;

    public Alumno() {

    }

    public Alumno(String appellidos, String nombre, String fnacimiento,String ciclo ) {
        this.apellidos = appellidos;
        this.nombre = nombre;
        this.fNacimiento = fnacimiento;
        this.ciclo= ciclo;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getfNacimiento() {return fNacimiento;}

    public void setfNacimiento(String fNacimiento) {this.fNacimiento = fNacimiento;}

    public String getCiclo() {return ciclo;}

    public void setCiclo(String ciclo) {this.ciclo = ciclo;}

}
