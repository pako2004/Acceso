import javax.swing.*;

public class PantallaRecetas {
    private JButton ButtonExpo;
    private JTextField textFieldNombre;
    private JTextArea textAreaDesc;
    private JList list1;
    private JButton buttonPlus;
    private JButton buttonQuitar;

    /*

    *   try {
            // Crear una instancia de DocumentBuilderFactory
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // Crear un nuevo documento XML
            Document doc = docBuilder.newDocument();

            // Crear el elemento raíz 'receta'
            Element recetaElement = doc.createElement("receta");
            doc.appendChild(recetaElement);

            // Datos de la receta
            String nombreReceta = "Tarta de manzana";
            String descripcionReceta = "Una deliciosa tarta de manzana casera.";
            ArrayList<String> ingredientesReceta = new ArrayList<>();
            ingredientesReceta.add("3 manzanas");
            ingredientesReceta.add("200g de harina");
            ingredientesReceta.add("100g de azúcar");
            ingredientesReceta.add("2 huevos");
            ingredientesReceta.add("1 cucharadita de canela");

            // Agregar elementos para nombre, descripción e ingredientes
            Element nombreElement = doc.createElement("nombre");
            Text nombreText = doc.createTextNode(nombreReceta);
            nombreElement.appendChild(nombreText);
            recetaElement.appendChild(nombreElement);

            Element descripcionElement = doc.createElement("descripcion");
            Text descripcionText = doc.createTextNode(descripcionReceta);
            descripcionElement.appendChild(descripcionText);
            recetaElement.appendChild(descripcionElement);

            Element ingredientesElement = doc.createElement("ingredientes");
            recetaElement.appendChild(ingredientesElement);

            for (String ingrediente : ingredientesReceta) {
                Element ingredienteElement = doc.createElement("ingrediente");
                Text ingredienteText = doc.createTextNode(ingrediente);
                ingredienteElement.appendChild(ingredienteText);
                ingredientesElement.appendChild(ingredienteElement);
            }

            // Exportar el documento XML a un archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("receta.xml"));
            transformer.transform(source, result);

            System.out.println("Receta exportada a receta.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    * */


}
