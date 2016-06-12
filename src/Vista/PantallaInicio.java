package Vista;

import Conexiones.LogWriter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Pantalla inicial de la aplicacion. Desde esta pantalla se podra iniciar una
 * partida o navegar hacia los distintos submenus.
 *
 * @author Kevin
 */
public class PantallaInicio extends JPanel {

    //Constantes para la posicion del selector de opcion
    private static final int POS_INICIO = 0;
    private static final int POS_BIBLIOTECA = 1;
    private static final int POS_CONTROLES = 2;

    private final Dimension dim;    //Dimension de la ventana
    private int posicionSelector;   //Posicion actual del selector

    //Rutas de las imagenes utilizadas y las propias imagenes
    private static final String FONDO_P = "archivos/pantallainicio/fondoinicio.jpg";
    private BufferedImage fondo;

    private static final String SELECTOR_P = "archivos/interfaz/selhd.png";
    private BufferedImage selector;

    /**
     * Constructor predeterminado. Crea una pantalla de inicio y carga y muestra
     * las imagenes.
     *
     * @param d las dimensiones de la pantalla.
     */
    public PantallaInicio(Dimension d) {
        dim = d;
        setSize(dim);
        setFocusable(true);
        addKeyListener(new ListenerTeclado());
        posicionSelector = POS_INICIO;
        cargar();
    }

    /**
     * Carga las imagenes desde las rutas.
     */
    private void cargar() {
        try {
            fondo = ImageIO.read(new File(FONDO_P));
            selector = ImageIO.read(new File(SELECTOR_P));
        } catch (IOException e) {
            LogWriter.escribirLog("Error al cargar una imagen. E: " + e.getMessage());
        }
    }

    /**
     * Mueve el selector hacia la posicion inmediatamente superior. Si se sube
     * desde la primera opcion se movera a la ultima.
     */
    private void selectorArriba() {
        posicionSelector--;
        if (posicionSelector < 0) {
            posicionSelector = POS_CONTROLES;
        }
    }

    /**
     * Mueve el selector hacia la posicion inmediatamente inferior. Si se baja
     * desde la ultima opcion se movera a la primera.
     */
    private void selectorAbajo() {
        posicionSelector++;
        if (posicionSelector > POS_CONTROLES) {
            posicionSelector = POS_INICIO;
        }
    }

    /**
     * Muestra los elementos visuales en pantalla.
     *
     * @param g el manejador de graficos.
     */
    public void draw(Graphics g) {
        requestFocusInWindow();
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(fondo, 0, 0, null);
        Font ft = new Font("Titulo", Font.BOLD | Font.ITALIC, 44);
        g2d.setFont(ft);
        g2d.setColor(Color.WHITE);
        FontMetrics fm = g2d.getFontMetrics(ft);
        g2d.drawString("PROYECTO PRG", (dim.width - fm.stringWidth("PROYECTO PRG")) / 2, 200);
        switch (posicionSelector) {
            case POS_INICIO:
                g2d.drawImage(selector, 230, 295, null);
                break;
            case POS_BIBLIOTECA:
                g2d.drawImage(selector, 230, 395, null);
                break;
            case POS_CONTROLES:
                g2d.drawImage(selector, 230, 495, null);
                break;
        }
        Font f = new Font("Fuente", Font.BOLD, 22);
        g2d.setFont(f);
        g2d.drawString("INICIO", 300, 300 + selector.getHeight() / 2);
        g2d.drawString("BIBLIOTECA", 300, 400 + selector.getHeight() / 2);
        g2d.drawString("CONTROLES", 300, 500 + selector.getHeight() / 2);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    /**
     * Gestor de eventos de teclado.
     */
    private class ListenerTeclado implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    selectorArriba();
                    repaint();
                    break;
                case KeyEvent.VK_DOWN:
                    selectorAbajo();
                    repaint();
                    break;
                case KeyEvent.VK_ENTER:
                    switch (posicionSelector) {
                        case POS_INICIO:
                            ((VentanaPrincipal) SwingUtilities.getRoot(PantallaInicio.this)).irSeleccionJugador(true);
                            break;
                        case POS_BIBLIOTECA:
                            ((VentanaPrincipal) SwingUtilities.getRoot(PantallaInicio.this)).irSeleccionJugador(false);
                            break;
                        case POS_CONTROLES:
                            String controles = "Flechas -> Movimiento\n"
                                    + "Z -> Atacar\n"
                                    + "X -> Usar objeto seleccionado\n"
                                    + "A/S -> Seleccionar objeto Anterior/Siguiente\n"
                                    + "Retroceso -> Pantalla anterior";
                            JOptionPane.showMessageDialog(PantallaInicio.this, controles, "Controles", JOptionPane.PLAIN_MESSAGE);
                            break;
                    }
                    break;
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
}
