import java.io.Serializable;
import java.sql.SQLType;
import java.util.Date;

public class Alumno implements Serializable {
    private static final long serialVersionUID = 1L;
    private long idAlumno;
    private String apellidos;
    private String nombre;
    private String fNacimiento;
    private Date fechaNacimiento;

    private int ciclo;


    public Alumno() {

    }

    public Alumno(String nombre, String appellidos, Date fnacimiento,int ciclo ) {
        this.nombre = nombre;
        this.apellidos = appellidos;
        this.fechaNacimiento = fnacimiento;
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

    public int getCiclo() {return ciclo;}

    public void setCiclo(int ciclo) {this.ciclo = ciclo;}

    public long getIdAlumno() {return idAlumno;}

    public void setIdAlumno(long idAlumno) {this.idAlumno = idAlumno;}

    public Date getFechaNacimiento() {return fechaNacimiento;}

    public void setFechaNacimiento(Date fechaNacimiento) {this.fechaNacimiento = fechaNacimiento;}

    public java.sql.Date getSQLNacimiento(){
        return new java.sql.Date(this.fechaNacimiento.getTime());
    }


}

