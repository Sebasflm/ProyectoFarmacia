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
    private JMenu menu;
    private JMenuItem agregarMedicinaItem;
    private JMenuItem verInventarioItem;
    private JMenuItem agregarRecetaItem;
    private JMenuItem despacharRecetaItem;

    public InventarioMedicinasGUI() {
        inventario = new HashMap<>();
        listaRecetas = new ArrayList<>();

        // Configuración de la ventana principal
        setTitle("Inventario de Medicinas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Creación de la barra de menú
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Menú principal
        menu = new JMenu("Menú");
        menuBar.add(menu);

        // Opción 1: Agregar Medicina al Inventario
        agregarMedicinaItem = new JMenuItem("Agregar Medicina al Inventario");
        agregarMedicinaItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarAgregarMedicinaDialog();
            }
        });
        menu.add(agregarMedicinaItem);

        // Opción 2: Ver Inventario
        verInventarioItem = new JMenuItem("Ver Inventario");
        verInventarioItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarInventarioDialog();
            }
        });
        menu.add(verInventarioItem);

        // Opción 3: Agregar Receta Médica
        agregarRecetaItem = new JMenuItem("Agregar Receta Médica");
        agregarRecetaItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarAgregarRecetaDialog();
            }
        });
        menu.add(agregarRecetaItem);

        // Opción 4: Despachar Receta Médica
        despacharRecetaItem = new JMenuItem("Despachar Receta Médica");
        despacharRecetaItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDespacharRecetaDialog();
            }
        });
        menu.add(despacharRecetaItem);

        // Opción 5: Salir
        JMenuItem salirItem = new JMenuItem("Salir");
        salirItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(salirItem);
    }

    private void mostrarAgregarMedicinaDialog() {
        JTextField nombreField = new JTextField();
        JTextField cantidadField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Cantidad:"));
        panel.add(cantidadField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Agregar Medicina al Inventario",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText();
            int cantidad = Integer.parseInt(cantidadField.getText());

            if (inventario.containsKey(nombre)) {
                int cantidadExistente = inventario.get(nombre);
                inventario.put(nombre, cantidadExistente + cantidad);
            } else {
                inventario.put(nombre, cantidad);
            }

            JOptionPane.showMessageDialog(null, "Medicina agregada al inventario.");
        }
    }

    private void mostrarInventarioDialog() {
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

    private void mostrarAgregarRecetaDialog() {
        if (inventario.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El inventario está vacío.");
            return;
        }

        JTextField medicinaField = new JTextField();
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

    private void mostrarDespacharRecetaDialog() {
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