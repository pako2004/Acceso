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
    JCheckBox mySQLCheckBox;
    JCheckBox PSQLCheckBox;
    JButton eliminarButton;
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
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        cbCiclo.addItem("DAM");
        cbCiclo.addItem("DAW");
        cbCiclo.addItem("TURISMO");

    }

    public void showError()
    {
        JOptionPane.showMessageDialog(new JFrame(), "formato dd-mm-yy", "Dialog", JOptionPane.ERROR_MESSAGE);
    }

}
