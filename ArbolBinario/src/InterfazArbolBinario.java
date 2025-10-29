import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InterfazArbolBinario extends JFrame {
    private ArbolBinario arbol;
    private JTextArea terminal;
    private JTextField inputDato;
    private PanelArbol panelArbol;

    public InterfazArbolBinario() {
        arbol = new ArbolBinario();
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        setTitle("Árbol Binario - Visualización y Operaciones");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void crearComponentes() {
        // Panel superior con controles
        JPanel panelControles = crearPanelControles();
        add(panelControles, BorderLayout.NORTH);

        // Panel central dividido
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        // Panel izquierdo: visualización del árbol
        panelArbol = new PanelArbol();
        JScrollPane scrollArbol = new JScrollPane(panelArbol);
        scrollArbol.setPreferredSize(new Dimension(700, 600));
        splitPane.setLeftComponent(scrollArbol);

        // Panel derecho: terminal de resultados
        terminal = new JTextArea();
        terminal.setEditable(false);
        terminal.setFont(new Font("Monospaced", Font.PLAIN, 14));
        terminal.setBackground(new Color(40, 40, 40));
        terminal.setForeground(Color.GREEN);
        JScrollPane scrollTerminal = new JScrollPane(terminal);
        scrollTerminal.setPreferredSize(new Dimension(450, 600));
        splitPane.setRightComponent(scrollTerminal);

        splitPane.setDividerLocation(700);
        add(splitPane, BorderLayout.CENTER);

        escribirEnTerminal("=== Sistema de Árbol Binario Ordenado ===");
        escribirEnTerminal("Ingrese números y presione los botones para ejecutar operaciones.\n");
    }

    private JPanel crearPanelControles() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(new Color(230, 230, 250));

        // Campo de entrada
        panel.add(new JLabel("Valor:"));
        inputDato = new JTextField(10);
        panel.add(inputDato);

        // Botones de operaciones
        agregarBoton(panel, "Agregar", e -> agregarDato());
        agregarBoton(panel, "Eliminar", e -> eliminarDato());
        agregarBoton(panel, "Buscar", e -> buscarDato());
        agregarBoton(panel, "Inorden", e -> mostrarInorden());
        agregarBoton(panel, "Preorden", e -> mostrarPreorden());
        agregarBoton(panel, "Postorden", e -> mostrarPostorden());
        agregarBoton(panel, "Amplitud", e -> mostrarAmplitud());
        agregarBoton(panel, "Info", e -> mostrarInformacion());
        agregarBoton(panel, "Limpiar", e -> limpiarArbol());

        return panel;
    }

    private void agregarBoton(JPanel panel, String texto, ActionListener listener) {
        JButton boton = new JButton(texto);
        boton.addActionListener(listener);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(boton);
    }

    private void agregarDato() {
        try {
            int dato = Integer.parseInt(inputDato.getText().trim());
            if (arbol.existeDato(dato)) {
                escribirEnTerminal("⚠ El valor " + dato + " ya existe en el árbol.");
            } else {
                arbol.agregarDato(dato);
                escribirEnTerminal("✓ Agregado: " + dato);
                panelArbol.actualizarArbol(arbol);
                inputDato.setText("");
            }
        } catch (NumberFormatException ex) {
            escribirEnTerminal("✗ Error: Ingrese un número válido.");
        }
    }

    private void eliminarDato() {
        try {
            int dato = Integer.parseInt(inputDato.getText().trim());
            if (arbol.existeDato(dato)) {
                arbol.eliminarDato(dato);
                escribirEnTerminal("✓ Eliminado: " + dato);
                panelArbol.actualizarArbol(arbol);
                inputDato.setText("");
            } else {
                escribirEnTerminal("⚠ El valor " + dato + " no existe en el árbol.");
            }
        } catch (NumberFormatException ex) {
            escribirEnTerminal("✗ Error: Ingrese un número válido.");
        }
    }

    private void buscarDato() {
        try {
            int dato = Integer.parseInt(inputDato.getText().trim());
            if (arbol.existeDato(dato)) {
                int nivel = arbol.obtenerNivel(dato);
                escribirEnTerminal("✓ El valor " + dato + " EXISTE en el árbol (Nivel: " + nivel + ")");
            } else {
                escribirEnTerminal("✗ El valor " + dato + " NO existe en el árbol.");
            }
        } catch (NumberFormatException ex) {
            escribirEnTerminal("✗ Error: Ingrese un número válido.");
        }
    }

    private void mostrarInorden() {
        if (arbol.estaVacio()) {
            escribirEnTerminal("⚠ El árbol está vacío.");
            return;
        }
        escribirEnTerminal("Recorrido Inorden: " + arbol.recorrerInorden());
    }

    private void mostrarPreorden() {
        if (arbol.estaVacio()) {
            escribirEnTerminal("⚠ El árbol está vacío.");
            return;
        }
        escribirEnTerminal("Recorrido Preorden: " + arbol.recorrerPreorden());
    }

    private void mostrarPostorden() {
        if (arbol.estaVacio()) {
            escribirEnTerminal("⚠ El árbol está vacío.");
            return;
        }
        escribirEnTerminal("Recorrido Postorden: " + arbol.recorrerPostorden());
    }

    private void mostrarAmplitud() {
        if (arbol.estaVacio()) {
            escribirEnTerminal("⚠ El árbol está vacío.");
            return;
        }
        escribirEnTerminal("Recorrido por Amplitud: " + arbol.imprimirAmplitud());
    }

    private void mostrarInformacion() {
        if (arbol.estaVacio()) {
            escribirEnTerminal("⚠ El árbol está vacío.");
            return;
        }
        escribirEnTerminal("\n--- INFORMACIÓN DEL ÁRBOL ---");
        escribirEnTerminal("Peso (nodos totales): " + arbol.obtenerPeso());
        escribirEnTerminal("Altura: " + arbol.obtenerAltura());
        escribirEnTerminal("Cantidad de hojas: " + arbol.contarHojas());
        escribirEnTerminal("Nodo menor: " + arbol.obtenerNodoMenor());
        escribirEnTerminal("Nodo mayor: " + arbol.obtenerNodoMayor());
        escribirEnTerminal("-----------------------------\n");
    }

    private void limpiarArbol() {
        int opcion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de que desea borrar todo el árbol?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION
        );
        if (opcion == JOptionPane.YES_OPTION) {
            arbol.borrarArbol();
            panelArbol.actualizarArbol(arbol);
            escribirEnTerminal("✓ Árbol borrado completamente.");
        }
    }

    private void escribirEnTerminal(String mensaje) {
        terminal.append(mensaje + "\n");
        terminal.setCaretPosition(terminal.getDocument().getLength());
    }

    // Clase interna para dibujar el árbol
    class PanelArbol extends JPanel {
        private ArbolBinario arbolActual;
        private final int RADIO_NODO = 30;
        private final int SEPARACION_HORIZONTAL = 60;
        private final int SEPARACION_VERTICAL = 80;

        public PanelArbol() {
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(1500, 1000));
        }

        public void actualizarArbol(ArbolBinario arbol) {
            this.arbolActual = arbol;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (arbolActual != null && !arbolActual.estaVacio()) {
                NodoArbol raiz = arbolActual.getRaiz();
                int anchoPanel = getWidth();
                dibujarArbol(g2d, raiz, anchoPanel / 2, 50, anchoPanel / 4);
            } else {
                g2d.setFont(new Font("Arial", Font.BOLD, 24));
                g2d.setColor(Color.GRAY);
                g2d.drawString("Árbol vacío - Agregue nodos para visualizar", 300, 300);
            }
        }

        private void dibujarArbol(Graphics2D g2d, NodoArbol nodo, int x, int y, int offsetX) {
            if (nodo == null) return;

            // Dibujar líneas hacia los hijos primero
            if (nodo.izquierdo != null) {
                int xIzq = x - offsetX;
                int yIzq = y + SEPARACION_VERTICAL;
                g2d.setColor(new Color(100, 100, 100));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(x, y, xIzq, yIzq);
                dibujarArbol(g2d, nodo.izquierdo, xIzq, yIzq, offsetX / 2);
            }

            if (nodo.derecho != null) {
                int xDer = x + offsetX;
                int yDer = y + SEPARACION_VERTICAL;
                g2d.setColor(new Color(100, 100, 100));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(x, y, xDer, yDer);
                dibujarArbol(g2d, nodo.derecho, xDer, yDer, offsetX / 2);
            }

            // Dibujar el nodo actual
            g2d.setColor(new Color(70, 130, 180));
            g2d.fillOval(x - RADIO_NODO, y - RADIO_NODO, RADIO_NODO * 2, RADIO_NODO * 2);
            
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawOval(x - RADIO_NODO, y - RADIO_NODO, RADIO_NODO * 2, RADIO_NODO * 2);

            // Dibujar el valor
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            FontMetrics fm = g2d.getFontMetrics();
            String valor = String.valueOf(nodo.dato);
            int anchoTexto = fm.stringWidth(valor);
            int altoTexto = fm.getAscent();
            g2d.drawString(valor, x - anchoTexto / 2, y + altoTexto / 3);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfazArbolBinario ventana = new InterfazArbolBinario();
            ventana.setVisible(true);
        });
    }
}
