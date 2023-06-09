import javax.swing.*;

public class Main {
        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    InventarioMedicinasGUI gui = new InventarioMedicinasGUI();
                    // Centrar la ventana en la pantalla
                    gui.setLocationRelativeTo(null);
                    gui.setVisible(true);
                }
            });
        }
        }


