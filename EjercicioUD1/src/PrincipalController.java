import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PrincipalController implements ActionListener, ListSelectionListener {

    private ArrayList<Alumno> alumnos;
    private DefaultListModel<String> modeloLista;
    private Principal vista;
    private int posicion;
    DBHelper dbHelper;

    public PrincipalController(Principal vista) {
        this.vista = vista;//Necesitamos tener una referencia de la ventana del formulario
        anadirListener(this,this);//Aquí llamamos al método que añadirá los listener a los distintos botones
        posicion=-1;/*Cuando inicializamos el formulario, éste está vacío (a no ser que posteriormente importemos información), por
        lo que la única acción que podríamos realizar al presionar sobre el botón guardar sería añadir un nuevo alumno. Indicamos
        esto estableciendo el valor de la variable posición como negativa*/
        this.alumnos=new ArrayList<>();//Inicializamos la lista de alumnos como una lista vacía de la clase Alumno
        modeloLista=new DefaultListModel<>();//Aquí inicializamos la lista que usaremos para representar la información del JList
        vista.listaAlumnos.setModel(modeloLista);/*Aquí establecemos como fuente de datos para nuestro JList la lista creada en el
        paso anterior*/
        dbHelper = new DBHelper();
    }

    private void anadirListener(ActionListener alistener, ListSelectionListener llistener){
        /*En este método únicamente establecemos que la clase que se va a encargar de manejas los eventos lanzados por los botones
        * y el JList sea esta clase*/
        vista.btGuardar.addActionListener(alistener);
        vista.btNuevo.addActionListener(alistener);
        vista.listaAlumnos.addListSelectionListener(llistener);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch(actionCommand){
            case "Nuevo": nuevoAlumno();
                break;
            case "Guardar": guardarAlumno();
                break;
                default:
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {//Primero nos aseguramos que la acción de selección ha finalizado
            int aux=vista.listaAlumnos.getSelectedIndex();/*Cuando finaliza la selección, podemos consultar en nuestro JList,
            haciendo uso de su propiedad SelectedIndex, cuál es el elemento de la lista que se ha seleccionado*/
            if((aux>=0)&&(aux<alumnos.size())) {/*Aquí nos aseguramos que el valor devuelto por selectedIndex se corresponde
            con una posición válida de la lista de alumnos*/
                posicion=aux;//La posición es válida, por lo que la establezco como el nuevo valor de nuestra variable posición
                Alumno alumno = alumnos.get(posicion);/*Seleccionamos el correspondiente alumno de nuestra lista y rellenamos los
                campos Nombre y DNI con su información*/
                vista.tfNombre.setText(alumno.getNombre());
                vista.tfApellidos.setText(alumno.getApellidos());
                vista.tfFNacimiento.setText((alumno.getfNacimiento()));

                for (int i = 0; i < vista.cbCiclo.getItemCount(); i++)
                {
                    String auxiliar = vista.cbCiclo.getItemAt(i);

                    if (auxiliar.equals(alumno.getCiclo()))
                    {
                        vista.cbCiclo.setSelectedIndex(i);
                    }

                }
            }
        }
    }

    private void nuevoAlumno(){
        posicion=-1;/*Cuando presionamos sobre el botón nuevo alumno establecemos el valor de la variable posición como negativa,
        para que cuando presionemos el botón guardar sepamos que nuestra intención es añadir un nuevo alumno*/
        vista.tfApellidos.setText("");//Vamos a insertar un nuevo alumno, así que borramos los campos para escribir nueva información
        vista.tfNombre.setText("");
        vista.tfFNacimiento.setText("");
        vista.cbCiclo.setSelectedIndex(-1);

    }

    private boolean fechaFormateada()
    {
        boolean formateau = true;
        try
        {
            String[] partes = vista.tfFNacimiento.getText().split("-");

                for (int i = 0; i < partes.length ; i++)
                {
                    if (partes[i].length()>2 || partes[i].length() <= 0 )
                    {
                        formateau = false;

                        if(i == 2 && ((Integer.parseInt(partes[i])>0) && partes[i].length() == 4) )
                        {
                            formateau = true;
                        }
                    }
                    boolean numero = false;
                    try
                    {

                        int fecha = Integer.parseInt(partes[i]);
                        if(i <2)
                        {
                            if (i == 1 && (fecha<=12 && fecha >0) )
                            {
                                numero = true;
                            }
                            if(i == 0 && (fecha > 0 && fecha <=31))
                            {
                                numero = true;
                            }
                        }
                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        return formateau;
    }


    private void guardarAlumno(){//Aquí nos quedamos
        String nombre=vista.tfNombre.getText();//Aquí recuperamos la información que hay en el campo de texto nombre
        String apellidos=vista.tfApellidos.getText();//Aquí recuperamos la información que hay en el campo de texto DNI
        String fNacimiento = vista.tfFNacimiento.getText();
        int ciclo = vista.cbCiclo.getSelectedIndex();
        boolean fechaformateada = fechaFormateada();
        boolean mysql = vista.mySQLCheckBox.isSelected();
        boolean psql = vista.PSQLCheckBox.isSelected();


        if(!(nombre.isEmpty()||apellidos.isEmpty()||fNacimiento.isEmpty()||ciclo == -1) && fechaformateada){//Aquí validamos los datos, comprobamos que no son campos vacíos
            if(posicion<0){/*Habíamos establecido que si la posición que teníamos en memoria era negativa es porque
            queríamos insertar un nuevo alumno en nuestra lista de alumnos*/


                modeloLista.add(modeloLista.getSize(),nombre+" "+apellidos+" "+fNacimiento+" "+ciclo);/*Aquí añadimos al final de la lista que
                almacena la información que muestra el JList (sólo almacena Strings) un nuevo alumno en formato String*/
                String pattern = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                Date fecha = null;
                try{
                    fecha = sdf.parse(fNacimiento);
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
                Alumno alumno=new Alumno(nombre,apellidos,fecha,ciclo);//Creamos un objeto de la clase alumno con la información recogida
                alumnos.add(alumno);/*En nuestra lista de alumnos de la clase Alumno añadimos también
                al final de la lista al nuevo alumno*/
                if(mysql)
                {
                    dbHelper.insertarAlumno(true, alumno);
                }
                if(psql)
                {
                    dbHelper.insertarAlumno(false, alumno);
                }
                posicion=alumnos.size()-1;//Nuestra variable posición cambia a la del último alumno que hemos añadido
                vista.listaAlumnos.setSelectedIndex(posicion);//En nuestro JList seleccionamos el alumno que acabamos de añadir
            }
            else{/*Si el valor de la variable posición es mayor o igual que 0 es porque hemos seleccionado un alumno de la lista
            , por lo que nos limitaremos a editar su información*/


                String pattern = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                Date fecha = null;
                try{
                    fecha = sdf.parse(fNacimiento);
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
                modeloLista.set(posicion,nombre+" "+apellidos+" "+fNacimiento+ " "+ciclo);//Aquí editamos el alumno en la posición seleccionada en la lista del JList
                Alumno alumno=new Alumno(nombre,apellidos,fecha,ciclo);//Creamos aquí un objeto de la clase alumno con la nueva información
                if(mysql)
                {
                    dbHelper.modificarUsuario(true, alumno);
                }
                if(psql)
                {
                    dbHelper.modificarUsuario(false, alumno);
                }

                alumnos.set(posicion,alumno);/*Editamos el alumno en nuestra lista de la clase alumno para la posición dada
                con el objeto de la clase alumno que hemos creado anteriormente*/
            }
        }else if(!fechaformateada)
        {

            vista.showError();
        }
    }










}
