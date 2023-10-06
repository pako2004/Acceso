import javax.swing.*;
import java.awt.*;

public class Principal {
    JTextField tfNombre;
    JTextField tfApellidos;
    JList listaAlumnos;
    JTextField tfFNacimiento;
    JComboBox<String> cbCiclo;
    JButton btNuevo;
    JButton btGuardar;
    JLabel lNombre;
    JLabel lApellidos;
    JLabel lFNacimiento;
    JLabel lCiclo;
    JPanel panelPrincipal;
    private JScrollBar scrollBar1;
    JButton btPrimero;
    JButton btAnterior;
    JButton btSiguiente;
    JButton btUltimo;
    JButton btExportar;

    List l= new List();

    public Principal() {
        JFrame frame = new JFrame("Principal");
        frame.setContentPane(panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        //DefaultListModel<String> listModel = new DefaultListModel<>();
        //frame.add(new JScrollPane(listaAlumnos));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        /*l.add("DAM");
        l.add("DAW");
        l.add("TURISMO");*/
        cbCiclo.addItem("DAM");
        cbCiclo.addItem("DAW");
        cbCiclo.addItem("TURISMO");

    }

}
