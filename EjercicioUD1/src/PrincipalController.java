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
import java.util.ArrayList;

public class PrincipalController implements ActionListener, ListSelectionListener {

    private ArrayList<Alumno> alumnos;
    private DefaultListModel<String> modeloLista;
    private Principal vista;
    private int posicion;

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
    }

    private void anadirListener(ActionListener alistener, ListSelectionListener llistener){
        /*En este método únicamente establecemos que la clase que se va a encargar de manejas los eventos lanzados por los botones
        * y el JList sea esta clase*/
        vista.btExportar.addActionListener(alistener);
        vista.btGuardar.addActionListener(alistener);
        vista.btNuevo.addActionListener(alistener);
        vista.listaAlumnos.addListSelectionListener(llistener);
        vista.btPrimero.addActionListener(alistener);
        vista.btAnterior.addActionListener(alistener);
        vista.btSiguiente.addActionListener(alistener);
        vista.btUltimo.addActionListener(alistener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch(actionCommand){
            case "Nuevo": nuevoAlumno();
                break;
            case "Guardar": guardarAlumno();
                break;
            case "Eliminar": eliminarAlumno();
                break;
           // case "Importar": importarAlumnos();
           //     break;
            case "Exportar": exportarAlumnos();
                break;
            case "Siguiente": siguiente();
            break;
            case "Anterior": anterior();
                break;
            case "Primero": primero();
                break;
            case "Ultimo": ultimo();
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
        String ciclo = vista.cbCiclo.getSelectedItem().toString();
        boolean fechaformateada = fechaFormateada();


        if(!(nombre.isEmpty()||apellidos.isEmpty()||fNacimiento.isEmpty()||ciclo.isEmpty()) && fechaformateada){//Aquí validamos los datos, comprobamos que no son campos vacíos
            if(posicion<0){/*Habíamos establecido que si la posición que teníamos en memoria era negativa es porque
            queríamos insertar un nuevo alumno en nuestra lista de alumnos*/


                modeloLista.add(modeloLista.getSize(),nombre+" "+apellidos+" "+fNacimiento+" "+ciclo);/*Aquí añadimos al final de la lista que
                almacena la información que muestra el JList (sólo almacena Strings) un nuevo alumno en formato String*/
                Alumno alumno=new Alumno(nombre,apellidos,fNacimiento,ciclo);//Creamos un objeto de la clase alumno con la información recogida
                alumnos.add(alumno);/*En nuestra lista de alumnos de la clase Alumno añadimos también
                al final de la lista al nuevo alumno*/
                posicion=alumnos.size()-1;//Nuestra variable posición cambia a la del último alumno que hemos añadido
                vista.listaAlumnos.setSelectedIndex(posicion);//En nuestro JList seleccionamos el alumno que acabamos de añadir
            }
            else{/*Si el valor de la variable posición es mayor o igual que 0 es porque hemos seleccionado un alumno de la lista
            , por lo que nos limitaremos a editar su información*/
                modeloLista.set(posicion,nombre+" "+apellidos+" "+fNacimiento+ " "+ciclo);//Aquí editamos el alumno en la posición seleccionada en la lista del JList
                Alumno alumno=new Alumno(nombre,apellidos,fNacimiento,ciclo);//Creamos aquí un objeto de la clase alumno con la nueva información
                alumnos.set(posicion,alumno);/*Editamos el alumno en nuestra lista de la clase alumno para la posición dada
                con el objeto de la clase alumno que hemos creado anteriormente*/
            }
        }else if(!fechaformateada)
        {

            vista.showError();
        }
    }

    private void eliminarAlumno(){
        if(posicion>=0) {//Primero comprobamos que la posición se corresponde con la de algún alumno de la lista
            if (JOptionPane.showConfirmDialog(null, "¿Estás seguro?", "Eliminar alumno",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {/*Aquí mostramos un diálogo de confirmación
                        al usuario, para darle la opción de cancelar la operación antes de borrar el alumno insertado. Si el
                        usuario presiona sobre el botón SÍ, continuamos con la operación de borrar un alumno*/
                modeloLista.remove(posicion);//Borramos al alumno de la lista del JList
                alumnos.remove(posicion);//Borramos al alumno de nuestra lista de la clase Alumno
                if(posicion>=alumnos.size()){/*Si hemos borrado al alumno que tenemos en la última posición, el tamaño de la
                    lista pasará a ser mayor o igual que la posición que teníamos seleccionada. Ya que hemos borrado el último
                    alumno, el comportamiento que tendrá nuestra aplicación será como si hubiéramos pulsado sobre el botón nuevo*/
                    posicion=-1;
                    vista.tfApellidos.setText("");
                    vista.tfNombre.setText("");
                }
                else{//Si borramos a un alumno que no sea el último, el alumno que iba después pasará a ocupar su posición
                    vista.listaAlumnos.setSelectedIndex(posicion);
                    Alumno alumno=alumnos.get(posicion);
                    vista.tfApellidos.setText(alumno.getApellidos());
                    vista.tfNombre.setText(alumno.getNombre());

                }
            }
        }
    }


    public void leerFicheroTextoPlano(String ruta) {
        alumnos.clear();//Borramos la información que pudiéramos tener almacenada en las distintas listas
        modeloLista.clear();
        BufferedReader buffer = null;
        try {
            buffer = new BufferedReader(new FileReader(new File(ruta)));//Aquí abrimos el fichero desde su ubicación
            String linea = null;
            int i=0;
            while ((linea = buffer.readLine()) != null) {//Aquí leemos el fichero de texto línea a línea
                String[] partes=linea.split(":");/*Al escribir el fichero establecimos que se escribiría con el siguiente
                formato DNI:Nombre , por tanto los : separan el dato del DNI del dato del nombre*/
                if(partes.length==2) {//Comprobamos que efectivamente el fichero tiene el formato adecuado
                    String apellidos = partes[1];//En la primera posición debería estar almacenado el DNI del alumno
                    String nombre = partes[0];//En la segunda posición debería estar almacenado el nombre del alumno
                    String fnacimiento = partes[2];
                    String ciclo = partes[3];
                    Alumno alumno = new Alumno(nombre,apellidos,fnacimiento,ciclo);//Creamos el nuevo alumno con la información obtenida
                    alumnos.add(alumno);//Añadimos el alumno a nuestra lista de alumnos
                    modeloLista.add(i, nombre + " " + apellidos+" "+fnacimiento+" "+ciclo);//Añadimos el alumno a la lista del JList
                    ++i;//Para añadir un objeto a un DefaultListModel (la lista del JList) necesitamos controlar en qué posición se añade
                }
            }
        } catch (FileNotFoundException fnfe) {//Manejo de excepciones
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();//Se cierra el buffer si no ha habido problemas
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    private void siguiente()
    {
        if(posicion < modeloLista.getSize() -1 )
        {
            posicion++;
            vista.listaAlumnos.setSelectedIndex(posicion);
        }
    }

    private void anterior()
    {
        if(posicion > 0)
        {
            posicion--;
            vista.listaAlumnos.setSelectedIndex(posicion);
        }
    }
private void primero()
{
    vista.listaAlumnos.setSelectedIndex(0);
}
private  void ultimo()
{
    vista.listaAlumnos.setSelectedIndex(modeloLista.getSize()-1);
}

    private void exportarAlumnos(){
        if(!alumnos.isEmpty()) {//Comprobamos que hay alumnos que exportar al fichero
            JFileChooser fileChooser = new JFileChooser();/*Volvemos a abrir el diálogo para seleccionar el fichero y el resto del
            código es muy similar al de importar alumnos*/
            fileChooser.setDialogTitle("Especifica a qué archivo vas a exportar los datos");
            int userSelection = fileChooser.showSaveDialog(vista.btExportar);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String ruta=fileToSave.getAbsolutePath();
                String[] partes=ruta.split("\\.");
                if(partes[partes.length-1].equals("txt")){
                    escribirFicheroTextoPlano(alumnos,ruta);
                }
                else if(partes[partes.length-1].equals("xml")){
                    escribirFicheroXML(alumnos,ruta);
                }
                else{
                    serializarAlumnos(alumnos,ruta);
                    ruta=partes[0]+".dat";
                }
            }
        }
    }

    public void serializarAlumnos(ArrayList<Alumno> alumnos, String ruta){
        ObjectOutputStream serializador = null;
        try {
            serializador = new ObjectOutputStream(new FileOutputStream(ruta));//Creamos un objetoutputstream a partir de la ruta especificada
            serializador.writeObject(alumnos);/*Escribimos en el fichero la lista de alumnos, recordemos que la clase alumno implementa la
            interfaz serializable*/
        } catch (IOException ioe) {//Manejo de excepciones
            ioe.printStackTrace();
        } finally {
            if (serializador != null)
                try {
                    serializador.close();//Si no hay problemas, cerramos el objectoutputstream
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
        }
    }

    public void escribirFicheroXML(ArrayList<Alumno> alumnos, String ruta) {//Este código está bien documentado en los ejemplos del tema
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation dom = builder.getDOMImplementation();
            Document documento = dom.createDocument(null, "Alumnos", null);
            Element raiz = documento.createElement("alumnos");
            documento.getDocumentElement().appendChild(raiz);
            Element nodoProducto = null, nodoDatos = null;
            Text texto = null;
            for (Alumno alumno : alumnos) {
                nodoProducto = documento.createElement("alumno");
                raiz.appendChild(nodoProducto);
                nodoDatos = documento.createElement("nombre");
                nodoProducto.appendChild(nodoDatos);
                texto = documento.createTextNode(alumno.getApellidos());
                nodoDatos.appendChild(texto);
                nodoDatos = documento.createElement("apellidos");
                nodoProducto.appendChild(nodoDatos);
                texto = documento.createTextNode(alumno.getNombre());
                nodoDatos.appendChild(texto);
                nodoDatos = documento.createElement("FNacimiento");
                nodoProducto.appendChild(nodoDatos);
                texto = documento.createTextNode(alumno.getfNacimiento());
                nodoDatos.appendChild(texto);
                nodoDatos = documento.createElement("ciclos");
                nodoProducto.appendChild(nodoDatos);
                texto = documento.createTextNode(alumno.getCiclo());
                nodoDatos.appendChild(texto);
            }
            Source fuente = new DOMSource(documento);
            Result resultado = new StreamResult(new java.io.File(ruta));
            Transformer transformador = TransformerFactory.newInstance().newTransformer();
            transformador.setOutputProperty(OutputKeys.INDENT, "yes");
            transformador.transform(fuente, resultado);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public void escribirFicheroTextoPlano(ArrayList<Alumno> alumnos, String ruta) {
        FileWriter fichero = null;
        PrintWriter escritor = null;

        try {
            fichero = new FileWriter(ruta);//Aquí abrimos el fichero con un objeto de la clase FileWriter
            escritor = new PrintWriter(fichero);/*Creamos una instancia de la clase PrintWriter sobre el filewriter anterior para imprimir
            los datos en el fichero*/
            for (Alumno alumno : alumnos) {//Recorremos la lista de alumnos
                escritor.println(alumno.getApellidos()+" "+alumno.getNombre()+" "+alumno.getfNacimiento()+" "+alumno.getCiclo());/*El formato que vamos a utilizar para escribir cada línea del fichero
                es el de DNI:Nombre*/
            }
        } catch (IOException ioe) {//Manejo de excepciones
            ioe.printStackTrace();
        } finally {
            if (fichero != null) {
                try {
                    fichero.close();//Cerramos el flujo de información si no ha habido problemas
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

}
