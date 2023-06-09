import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventarioMedicinasGUI extends JFrame {
    private Map<String, Integer> inventario;
    private List<RecetaMedica> listaRecetas;

    private JButton agregarMedicinaButton;
    private JButton verInventarioButton;
    private JButton agregarRecetaButton;
    private JButton despacharRecetaButton;


    public InventarioMedicinasGUI() {
        inventario = new HashMap<>();
        listaRecetas = new ArrayList<>();

        // Configuración de la ventana principal
        setTitle("Inventario de Medicinas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        // Creación de los botones
        agregarMedicinaButton = new JButton("Agregar Medicina al Inventario");
        verInventarioButton = new JButton("Ver Inventario");
        agregarRecetaButton = new JButton("Agregar Receta Médica");
        despacharRecetaButton = new JButton("Despachar Receta Médica");


        // Agregar los botones a la ventana
        add(agregarMedicinaButton);
        add(verInventarioButton);
        add(agregarRecetaButton);
        add(despacharRecetaButton);


        // Acción del botón "Agregar Medicina al Inventario"
        agregarMedicinaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarMedicina();
            }
        });

        // Acción del botón "Ver Inventario"
        verInventarioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inventario();
            }
        });

        // Acción del botón "Agregar Receta Médica"
        agregarRecetaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarReceta();
            }
        });

        // Acción del botón "Despachar Receta Médica"
        despacharRecetaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                despacharReceta();
            }
        });
    }

    private void agregarMedicina() {
        JTextField ingresarNombre= new JTextField();
        JTextField ingresarCantidad= new JTextField();

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Nombre:"));
        panel.add(ingresarNombre);
        panel.add(new JLabel("Cantidad:"));
        panel.add(ingresarCantidad);

        int result = JOptionPane.showConfirmDialog(null, panel, "Agregar Medicina al Inventario",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nombre = ingresarNombre.getText();
            int cantidad = Integer.parseInt(ingresarCantidad.getText());

            if (inventario.containsKey(nombre)) {
                int cantidadExistente = inventario.get(nombre);
                inventario.put(nombre, cantidadExistente + cantidad);
            } else {
                inventario.put(nombre, cantidad);
            }

            JOptionPane.showMessageDialog(null, "Medicina agregada al inventario.");
        }
    }

    private void inventario() {
        if (inventario.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El inventario está vacío.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Inventario:\n");

        for (Map.Entry<String, Integer> entry : inventario.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);

        JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Ver Inventario",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void agregarReceta() {
        if (inventario.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El inventario está vacío.");
            return;
        }

        JTextField cantidadField = new JTextField();
        JTextField nombreClienteField = new JTextField();
        JTextField cedulaField = new JTextField();

        // Obtener una lista de medicinas disponibles en el inventario
        String[] medicinas = inventario.keySet().toArray(new String[0]);

        JComboBox<String> medicinaComboBox = new JComboBox<>(medicinas);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Medicina:"));
        panel.add(medicinaComboBox);
        panel.add(new JLabel("Cantidad:"));
        panel.add(cantidadField);
        panel.add(new JLabel("Nombre del Cliente:"));
        panel.add(nombreClienteField);
        panel.add(new JLabel("Número de Cédula:"));
        panel.add(cedulaField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Agregar Receta Médica",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String medicinaSeleccionada = (String) medicinaComboBox.getSelectedItem();
            int cantidad = Integer.parseInt(cantidadField.getText());
            String nombreCliente = nombreClienteField.getText();
            String cedula = cedulaField.getText();

            if (inventario.containsKey(medicinaSeleccionada)) {
                int cantidadExistente = inventario.get(medicinaSeleccionada);
                if (cantidad <= cantidadExistente) {
                    // Descontar la cantidad de medicina del inventario
                    inventario.put(medicinaSeleccionada, cantidadExistente - cantidad);

                    // Agregar la receta médica a la lista
                    RecetaMedica recetaMedica = new RecetaMedica(medicinaSeleccionada, cantidad, nombreCliente, cedula);
                    listaRecetas.add(recetaMedica);

                    JOptionPane.showMessageDialog(null, "Receta médica agregada.");
                } else {
                    JOptionPane.showMessageDialog(null, "No hay suficiente cantidad de medicina en el inventario.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "La medicina seleccionada no está en el inventario.");
            }
        }
    }

    private void despacharReceta() {
        if (inventario.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El inventario está vacío.");
            return;
        }

        if (listaRecetas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay recetas médicas en el inventario.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Recetas Médicas:\n");

        for (int i = 0; i < listaRecetas.size(); i++) {
            RecetaMedica recetaMedica = listaRecetas.get(i);
            sb.append("Receta ").append(i + 1).append(":\n");
            sb.append("Medicina: ").append(recetaMedica.getMedicina()).append("\n");
            sb.append("Cantidad: ").append(recetaMedica.getCantidad()).append("\n");
            sb.append("Nombre del Cliente: ").append(recetaMedica.getNombreCliente()).append("\n");
            sb.append("Número de Cédula: ").append(recetaMedica.getCedula()).append("\n");
            sb.append("\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);

        JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Despachar Receta Médica",
                JOptionPane.PLAIN_MESSAGE);

        // Obtener el número de receta a despachar
        String input = JOptionPane.showInputDialog(null, "Ingrese el número de receta a despachar:");
        if (input != null) {
            try {
                int numeroReceta = Integer.parseInt(input);
                if (numeroReceta >= 1 && numeroReceta <= listaRecetas.size()) {
                    // Despachar la receta médica seleccionada
                    RecetaMedica recetaMedica = listaRecetas.get(numeroReceta - 1);
                    listaRecetas.remove(numeroReceta - 1);

                    JOptionPane.showMessageDialog(null, "Receta médica despachada:\n" +
                            "Medicina: " + recetaMedica.getMedicina() + "\n" +
                            "Cantidad: " + recetaMedica.getCantidad() + "\n" +
                            "Nombre del Cliente: " + recetaMedica.getNombreCliente() + "\n" +
                            "Número de Cédula: " + recetaMedica.getCedula());
                } else {
                    JOptionPane.showMessageDialog(null, "Número de receta inválido.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Número de receta inválido.");
            }
        }
    }}