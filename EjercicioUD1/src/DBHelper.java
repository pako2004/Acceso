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
        ArrayList<Alumno> Alumnos=this.getUsuarios(conexion);//Aquí recuperamos la lista de ususarios usando el método que creamos anteriormente
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        if(mysql) {//Imprimos la información en consola
            System.out.println("MySQL:");
        }
        else{
            System.out.println("PostgreSQL:");
        }
        /*for(Alumno Alumno:Alumnos){
            String fechan="";
            fechan=sdf.format(Alumno.getFechaNacimiento());
            System.out.println("Ide: "+ Alumno.getIdAlumno()+" Nombre: "+ Alumno.getNombre()+" Apellidos: "+ Alumno.getApellidos()
                    +" Alumno: "+ " Fecha de nacimiento: "+fechan+" Ciclo: ");
        }*/
        this.desconectar(conexion);//Nos desconectamos de la base de datos
    }

    public void insertarAlumno(boolean mysql, Alumno alumno){
        Connection conexion;
        if(mysql){//De nuevo elegimos el tipo de conexión que realizamos
            conexion=this.conectarMySQL("ejemplostema2","root","luisrodriguez");
        }
        else{
            conexion=this.conectarPostgreSQL("ejemplostema2","postgres","postgres");
        }
        String sentenciaSql = "INSERT INTO alumno (nombre, apellidos, fechanacimiento, ciclo) " +
                "VALUES (?,?,?,?,?)";//Aquí generamos la sentencia de inserción de datos
        PreparedStatement sentencia = null;
        try {//De nuevo, en el orden en que hayamos escrito los parámetros en la consulta asociaremos los valores que les correspondan
            sentencia = conexion.prepareStatement(sentenciaSql);
            //sentencia.setLong(1,alumno.getIdAlumno());
            sentencia.setString(2,alumno.getNombre());
            sentencia.setString(3,alumno.getApellidos());
            sentencia.setDate(4,alumno.getSQLNacimiento());
            sentencia.setInt(5, alumno.getCiclo());
            sentencia.executeUpdate();
        } catch (SQLException sqle) {//Manejo de excepciones
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {//Cierre de la sentencia
                    sentencia.close();
                    this.desconectar(conexion);
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }

    public void modificarUsuario(boolean mysql, Alumno usuario){
        Connection conexion;
        if(mysql){//Aquí seleccionamos si usamos MySQL o PostgreSQL
            conexion=this.conectarMySQL("ejemplostema2","root","poflo123");
        }
        else{
            conexion=this.conectarPostgreSQL("ejemplostema2","aplicacion","poflo123");
        }
        String sentenciaSql = "UPDATE usuario SET username=?, password=?, nombre=?, apellidos=?, dni=?, fechanacimiento=?" +
                " WHERE idusuario=?";//Aquí generamos la sentencia añadiendo una claúsula de búsqueda por idUsuario
        PreparedStatement sentencia = null;
        try {//Insertamos los parámetros en la sentencia teniendo en cuenta el orden en que se han escrito anteriormente
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(2,usuario.getNombre());
            sentencia.setString(3,usuario.getApellidos());
            sentencia.setDate(4,usuario.getSQLNacimiento());
            sentencia.setInt(5,usuario.getCiclo());
            sentencia.executeUpdate();//Ejecución de la sentencia
        } catch (SQLException sqle) {//Gestión de excepciones
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();//Cierre de la sentencia
                    this.desconectar(conexion);
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
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

    public ArrayList<Alumno> getUsuarios(Connection conexion){
        String sentenciaSql = "SELECT idusuario, username, password, nombre, apellidos, dni, fechanacimiento FROM usuario";//Creamos la sentencia
        PreparedStatement sentencia = null;//Clase auxiliar para crear la consulta y pasarla al conector
        ResultSet resultado = null;//La ejecución de la consulta nos va a devolver un SET
        ArrayList<Alumno> alumnos=new ArrayList<>();//Creamos nuestro arraylist de usuarios vacío
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);//Pasamos la consulta al conector
            resultado = sentencia.executeQuery();//Ejecutamos la consulta
            while (resultado.next()) {/*Mientras haya filas que recorrer, iremos creando nuevos usuarios asignándole a cada variable de la clase usuario su
            correspondiente columna*/
                Alumno alumno=new Alumno();//El orden en que pusimos las columnas en la sentencia será el orden que usemos para recuperarlas
                alumno.setIdAlumno(resultado.getLong(1));
                alumno.setNombre(resultado.getString(2));
                alumno.setApellidos(resultado.getString(3));
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                alumno.setFechaNacimiento(sdf.parse(resultado.getString(4)));
                alumno.setCiclo(5);
                alumnos.add(alumno);
            }
        } catch (SQLException sqle) {//Manejo de excepciones
            sqle.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();//Cerramos la sentencia y el resultado
                    resultado.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        }
        return alumnos;
    }



}


