package Vista;

import Conexiones.LogWriter;
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
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Pantalla de seleccion de jugador.
 *
 * @author Kevin
 */
public class PantallaSeleccionJugador extends JPanel {

    private static final String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String PATH_SELECTOR_A = "archivos/interfaz/selvar.png";
    private static final String PATH_SELECTOR_AB = "archivos/interfaz/selvab.png";

    private final Dimension d;
    private boolean juego;
    private int seleccionada;
    private final char[] letras;

    private BufferedImage selectorArriba;
    private BufferedImage selectorAbajo;

    /**
     * Constructor predeterminado.
     *
     * @param d el tamano de la ventana.
     * @param juego cierto si se pasara al juego; false si se pasara a la
     * biblioteca.
     */
    public PantallaSeleccionJugador(Dimension d, boolean juego) {
        this.d = d;
        setSize(d);
        setFocusable(true);
        addKeyListener(new ListenerTeclado());

        this.juego = juego;
        seleccionada = 0;
        letras = new char[3];
        letras[0] = 'A';
        letras[1] = 'A';
        letras[2] = 'A';
        cargar();
    }

    /**
     * Carga las imagenes desde las rutas.
     */
    private void cargar() {
        try {
            selectorArriba = ImageIO.read(new File(PATH_SELECTOR_A));
            selectorAbajo = ImageIO.read(new File(PATH_SELECTOR_AB));
        } catch (IOException e) {
            LogWriter.escribirLog("Error al cargar una imagen. E: " + e.getMessage());
        }
    }

    /**
     * Avanza la letra seleccionada.
     */
    private void siguienteLetra() {
        if (letras[seleccionada] != caracteres.charAt(caracteres.length() - 1)) {
            letras[seleccionada]++;
        } else {
            letras[seleccionada] = caracteres.charAt(0);
        }
    }

    /**
     * Retrocede la letra seleccionada.
     */
    private void anteriorLetra() {
        if (letras[seleccionada] != caracteres.charAt(0)) {
            letras[seleccionada]--;
        } else {
            letras[seleccionada] = caracteres.charAt(caracteres.length() - 1);
        }
    }

    /**
     * Selecciona la siguiente letra.
     */
    private void moverDerecha() {
        if (seleccionada != 2) {
            seleccionada++;
        } else {
            seleccionada = 0;
        }
    }

    /**
     * Selecciona la letra anterior
     */
    private void moverIzquierda() {
        if (seleccionada != 0) {
            seleccionada--;
        } else {
            seleccionada = 2;
        }
    }

    /**
     * Muestra la ventana.
     *
     * @param g2d el manejador de graficos.
     */
    public void draw(Graphics2D g2d) {
        requestFocusInWindow();

        Font f = new Font("Letra", Font.BOLD, 56);
        g2d.setFont(f);
        FontMetrics fm = g2d.getFontMetrics(f);

        int y = (d.height - fm.getHeight()) / 2;
        int x0 = (d.width - fm.stringWidth("" + letras[0])) / 4;
        g2d.drawString("" + letras[0], x0, y);
        int x1 = (d.width - fm.stringWidth("" + letras[1])) / 2;
        g2d.drawString("" + letras[1], x1, y);
        int x2 = ((d.width - fm.stringWidth("" + letras[2])) / 4) * 3;
        g2d.drawString("" + letras[2], x2, y);
        int xs = 0;
        switch (seleccionada) {
            case 0:
                xs = x0 - (selectorAbajo.getWidth() - fm.charWidth(letras[seleccionada])) / 2;
                break;
            case 1:
                xs = x1 - (selectorAbajo.getWidth() - fm.charWidth(letras[seleccionada])) / 2;
                break;
            case 2:
                xs = x2 - (selectorAbajo.getWidth() - fm.charWidth(letras[seleccionada])) / 2;
                break;
        }
        g2d.drawImage(selectorArriba, xs, 150, null);
        g2d.drawImage(selectorAbajo, xs, 350, null);
        Font fex = new Font("Explicacion", Font.PLAIN, 16);
        g2d.setFont(fex);
        String controles = "Izquierda/Derecha: Cambiar posicion | Arriba/Abajo: Cambiar letra | Intro: Continuar";
        fm = g2d.getFontMetrics(fex);
        g2d.drawString(controles, (d.width - fm.stringWidth(controles)) / 2, 500);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        draw(g2d);
    }

    private class ListenerTeclado implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    anteriorLetra();
                    break;
                case KeyEvent.VK_DOWN:
                    siguienteLetra();
                    break;
                case KeyEvent.VK_LEFT:
                    moverIzquierda();
                    break;
                case KeyEvent.VK_RIGHT:
                    moverDerecha();
                    break;
                case KeyEvent.VK_ENTER:
                    String jugador = new String(letras);
                    if (juego) {
                        ((VentanaPrincipal) SwingUtilities.getRoot(PantallaSeleccionJugador.this)).irJuego(jugador);
                    } else {
                        ((VentanaPrincipal) SwingUtilities.getRoot(PantallaSeleccionJugador.this)).irBibliotecaSeleccion(jugador);
                    }
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    ((VentanaPrincipal) SwingUtilities.getRoot(PantallaSeleccionJugador.this)).irInicio();
                    break;
            }
            repaint();
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

    }
}
