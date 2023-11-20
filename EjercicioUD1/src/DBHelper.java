import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DBHelper {

    public Connection conectarMySQL(String basedatos, String usuario, String password){
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//Utilizamos el conector  para MySQL
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/"+basedatos,
                    usuario, password);//Obtenemos la conexión
        } catch (ClassNotFoundException cnfe) {//Manejo de excepciones
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return conexion;
    }

    public Connection conectarPostgreSQL(String basedatos, String usuario, String password){
        Connection conexion = null;
        try {
            Class.forName("org.postgresql.Driver");//Utilizamos el conector  para Postgres
            conexion = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/"+basedatos,
                    usuario, password);//Obtenemos la conexión
        } catch (ClassNotFoundException cnfe) {//Manejo de excepciones
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return conexion;
    }

    public void desconectar(Connection conexion){
        try {
            conexion.close();//Cerramos la conexión que nos pasan como argumento
            conexion = null;
        } catch (SQLException sqle) {//Manejo de excepciones
            sqle.printStackTrace();
        }
    }

    public void pruebaConexion(boolean mysql){
        Connection conexion;
        if(mysql){//El argumento que recibimos en este método nos indica si la base de datos es de tipo MySQL o PostgreSQL
            conexion=this.conectarMySQL("ejemplostema2","root","luisrodriguez");
        }
        else{
            conexion=this.conectarPostgreSQL("ejemplostema2","postgres","postgres");
        }
        ArrayList<Alumno> Alumnos=this.getAlumnos(conexion);//Aquí recuperamos la lista de ususarios usando el método que creamos anteriormente
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        if(mysql) {//Imprimos la información en consola
            System.out.println("MySQL:");
        }
        else{
            System.out.println("PostgreSQL:");
        }
        for(Alumno Alumno:Alumnos){
            String fechan="";
            fechan=sdf.format(Alumno.getFechaNacimiento());
            System.out.println("Ide: "+ Alumno.getIdAlumno()+" Nombre: "+ Alumno.getNombre()+" Apellidos: "+ Alumno.getApellidos()
                    +" Alumno: "+ Alumno.getUsername()+" Password: "+ Alumno.getPassword()+" DNI: "+ Alumno.getDni()
                    +" Fecha de nacimiento: "+fechan);
        }
        this.desconectar(conexion);//Nos desconectamos de la base de datos
    }

    public Alumno getAlumnos(Connection conexion, long ide){
        String sentenciaSql = "SELECT idalumno, nombre, apellidos, fechanacimiento , ciclo" +
                "FROM Alumno WHERE idalumno=?";//Creamos aquí la consulta incluyendo el parámetro de búsqueda
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        ArrayList<Alumno> alumnos=new ArrayList<>();//Creamos una lista vacía de Alumnos
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);//Preparamos la sentencia
            sentencia.setLong(1, ide);//Le introducimos el parámetro de búsqueda
            resultado = sentencia.executeQuery();
            while (resultado.next()) {//Creamos el Alumno asignándole valores a sus variables siguiendo el orden de los campos en la consulta
                Alumno alumno=new Alumno();
                alumno.setIdAlumno(resultado.getLong(1));
                alumno.setNombre(resultado.getString(2));
                alumno.setApellidos(resultado.getString(3));
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                alumno.setFechaNacimiento(sdf.parse(resultado.getString(4)));
                alumno.setCiclo(5);
                alumnos.add(alumno);//Añadimos el Alumno a la lista de Alumnos, no debiendo haber más de un Alumno
            }
        } catch (SQLException sqle) {//Manejo de excepciones
            sqle.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();//Cierre de consulta
                    resultado.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        }
        if(alumnos.isEmpty()){//Si no hemos obtenido ningún Alumno devolvemos nulo
            return null;
        }
        else{//Si la lista no está vacía devolvemos el primer Alumno
            return alumnos.get(0);
        }
    }


}


