import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

public class Principal {
    JTextField tfNombre;
    JTextField tfApellidos;
    JList listaAlumnos;
    JTextField tfFNacimiento;
    JComboBox cbCiclo;
    JButton btNuevo;
    JButton btGuardar;
    JLabel lbNombre;
    JLabel lbApellidos;
    JLabel lbFNacimiento;
    JLabel lbCiclo;
    JPanel panelPrincipal;
    private JScrollBar scrollBar1;
    JButton btPrimero;
    JButton btAnterior;
    JButton btSiguiente;
    JButton btUltimo;
    JButton btExportar;

    public Principal() {
        JFrame frame = new JFrame("Principal");
        frame.setContentPane(panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listaAlumnos = new JList<>(listModel);
        frame.add(new JScrollPane(listaAlumnos));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        cbCiclo.addItem("DAM");
        cbCiclo.addItem("DAW");
        cbCiclo.addItem("TURISMO");
        btGuardar.addActionListener(new ActionListener() {

            Alumno newAlumno;
            DateFormat format = new SimpleDateFormat("dd-mm-yy");
            String message;

            @Override //Guardar Alumno
            public void actionPerformed(ActionEvent e) {

                boolean formateau = false;
                Date fechaAlumno = null;
                try{
                    String[] partes = tfFNacimiento.getText().toString().split("-");
                    boolean melo = true; //boolean de si esta bien hecha la fecha
                    if ((melo))
                    {
                        for (int i = 0; i < partes.length ; i++)
                        {
                            if (partes[i].length()>2)
                            {
                                melo = false;
                                message = "Solo dos numeros por campo";
                            }
                        }
                        if (melo)
                        {
                            fechaAlumno = format.parse(tfFNacimiento.getText().toString());
                            formateau = true;
                        }
                    }
                }catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                if (formateau)
                {
                     newAlumno = new Alumno(tfNombre.getText().toString(),tfApellidos.getText().toString(),fechaAlumno,cbCiclo.getSelectedItem().toString());
                }else
                {
                    JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
                }

                //añadir a la lista
                ArrayList<Alumno> asdkad = new ArrayList<>();
                listaAlumnos.add(newAlumno.toString());


            }
        });
    }

    public void setTfNombre(JTextField tfNombre) {
        this.tfNombre = tfNombre;
    }

    public JTextField getTfNombre() {
        return tfNombre;
    }

    public JTextField getTfApellidos() {
        return tfApellidos;
    }

    public void setTfApellidos(JTextField tfApellidos) {
        this.tfApellidos = tfApellidos;
    }

    public JList getListaAlumnos() {
        return listaAlumnos;
    }

    public void setListaAlumnos(JList listaAlumnos) {
        this.listaAlumnos = listaAlumnos;
    }

    public JTextField getTfFNacimiento() {
        return tfFNacimiento;
    }

    public void setTfFNacimiento(JTextField tfFNacimiento) {
        this.tfFNacimiento = tfFNacimiento;
    }

    public JComboBox getCbCiclo() {
        return cbCiclo;
    }

    public void setCbCiclo(JComboBox cbCiclo) {
        this.cbCiclo = cbCiclo;
    }

    public JButton getBtNuevo() {
        return btNuevo;
    }

    public void setBtNuevo(JButton btNuevo) {
        this.btNuevo = btNuevo;
    }

    public JButton getBtGuardar() {
        return btGuardar;
    }

    public void setBtGuardar(JButton btGuardar) {
        this.btGuardar = btGuardar;
    }

    public JLabel getLbNombre() {
        return lbNombre;
    }

    public void setLbNombre(JLabel lbNombre) {
        this.lbNombre = lbNombre;
    }

    public JLabel getLbApellidos() {
        return lbApellidos;
    }

    public void setLbApellidos(JLabel lbApellidos) {
        this.lbApellidos = lbApellidos;
    }

    public JLabel getLbFNacimiento() {
        return lbFNacimiento;
    }

    public void setLbFNacimiento(JLabel lbFNacimiento) {
        this.lbFNacimiento = lbFNacimiento;
    }

    public JLabel getLbCiclo() {
        return lbCiclo;
    }

    public void setLbCiclo(JLabel lbCiclo) {
        this.lbCiclo = lbCiclo;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JButton getBtPrimero() {
        return btPrimero;
    }

    public void setBtPrimero(JButton btPrimero) {
        this.btPrimero = btPrimero;
    }

    public JButton getBtAnterior() {
        return btAnterior;
    }

    public void setBtAnterior(JButton btAnterior) {
        this.btAnterior = btAnterior;
    }

    public JButton getBtSiguiente() {
        return btSiguiente;
    }

    public void setBtSiguiente(JButton btSiguiente) {
        this.btSiguiente = btSiguiente;
    }

    public JButton getBtUltimo() {
        return btUltimo;
    }

    public void setBtUltimo(JButton btUltimo) {
        this.btUltimo = btUltimo;
    }

    public JButton getBtExportar() {
        return btExportar;
    }

    public void setBtExportar(JButton btExportar) {
        this.btExportar = btExportar;
    }
}
