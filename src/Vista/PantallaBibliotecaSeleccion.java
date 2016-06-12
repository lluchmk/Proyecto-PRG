package Vista;

import Conexiones.LogWriter;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Pantalla de seleccion de biblioteca.
 *
 * @author Kevin
 */
public class PantallaBibliotecaSeleccion extends JPanel {

    private static final int POSICION_ENEMIGOS = 0;
    private static final int POSICION_OBJETOS = 1;

    private final Dimension dim;

    private static final String PATH_SELECTOR = "archivos/interfaz/selhd.png";
    private BufferedImage selector;

    private int posicionSelector;

    private final String jugador;

    /**
     * Constructor predeterminado. Crea la pantalla y muestra la informacion.
     *
     * @param d las dimensiones de la pantalla.
     * @param jugador el jugador del que se mostrara la niformacion.
     */
    public PantallaBibliotecaSeleccion(Dimension d, String jugador) {
        dim = d;
        setSize(dim);
        setFocusable(true);
        addKeyListener(new ListenerTeclado());
        this.jugador = jugador;

        posicionSelector = 0;
        cargar();
    }

    /**
     * Carga las imagenes necesarias.
     */
    private void cargar() {
        try {
            selector = ImageIO.read(new File(PATH_SELECTOR));
        } catch (IOException ex) {
            LogWriter.escribirLog("Error al cargar la imagen del selector. E: " + ex.getMessage());
        }
    }

    /**
     * Muestra la pantalla de seleccion.
     *
     * @param g el manejador de graficos.
     */
    private void draw(Graphics g) {
        requestFocusInWindow();

        Font f = new Font("Fuente", Font.BOLD, 22);
        g.setFont(f);
        FontMetrics fm = g.getFontMetrics(f);
        String sEnemigos = "ENEMIGOS";
        String sObjetos = "OBJETOS";
        int xe = (dim.width - fm.stringWidth(sEnemigos)) / 2;
        int ye = (dim.height - fm.getHeight()) / 3;
        g.drawString(sEnemigos, xe, ye);
        int xo = (dim.width - fm.stringWidth(sObjetos)) / 2;
        int yo = ((dim.height - fm.getHeight()) / 3) * 2;
        g.drawString(sObjetos, xo, yo);

        int xs = dim.width / 4;
        int ys = 0;
        switch (posicionSelector) {
            case POSICION_ENEMIGOS:
                ys = ye - (selector.getHeight() - fm.getHeight()) / 2 - selector.getHeight() / 2;
                break;
            case POSICION_OBJETOS:
                ys = yo - (selector.getHeight() - fm.getHeight()) / 2 - selector.getHeight() / 2;
                break;
        }

        g.drawImage(selector, xs, ys, null);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    /**
     * Mueve el selector hacia la posicion inmediatamente superior. Si se sube
     * desde la primera opcion se movera a la ultima.
     */
    private void selectorArriba() {
        if (posicionSelector == POSICION_ENEMIGOS) {
            posicionSelector = POSICION_OBJETOS;
        } else {
            posicionSelector = POSICION_ENEMIGOS;
        }
    }

    /**
     * Mueve el selector hacia la posicion inmediatamente inferior. Si se baja
     * desde la ultima opcion se movera a la primera.
     */
    private void selectorAbajo() {
        if (posicionSelector == POSICION_OBJETOS) {
            posicionSelector = POSICION_ENEMIGOS;
        } else {
            posicionSelector = POSICION_OBJETOS;
        }
    }

    private class ListenerTeclado implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

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
                        case POSICION_ENEMIGOS:
                            ((VentanaPrincipal) SwingUtilities.getRoot(PantallaBibliotecaSeleccion.this)).irBibliotecaContenido(true, jugador);
                            break;
                        case POSICION_OBJETOS:
                            ((VentanaPrincipal) SwingUtilities.getRoot(PantallaBibliotecaSeleccion.this)).irBibliotecaContenido(false, jugador);
                            break;
                    }
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    ((VentanaPrincipal) SwingUtilities.getRoot(PantallaBibliotecaSeleccion.this)).irSeleccionJugador(false);
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

    }
}
