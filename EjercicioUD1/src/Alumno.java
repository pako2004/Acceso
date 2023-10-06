import java.util.Date;

public class Alumno {

    private String nombre;
    private String apellidos;
    private Date fNacimiento;
    private  String ciclo;


    public Alumno(String nombre, String apellidos, Date fNacimiento, String ciclo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fNacimiento = fNacimiento;
        this.ciclo = ciclo;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getfNacimiento() {
        return fNacimiento;
    }

    public void setfNacimiento(Date fNacimiento) {
        this.fNacimiento = fNacimiento;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    @Override
    public String toString() {
        return nombre + " " + apellidos  +" " + fNacimiento + " " +ciclo;

    }
}
