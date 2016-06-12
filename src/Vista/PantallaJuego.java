package Vista;

import Conexiones.LogWriter;
import Controlador.Colisiones;
import Controlador.Controladror_PJ;
import Controlador.FactoryNiveles;
import Excepciones.ExcepcionCargaNivel;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.SwingUtilities;

/**
 * Pantalla de juego.
 *
 * @author Kevin
 */
public class PantallaJuego extends JPanel implements Runnable {

    private final int V_WIDTH;          //Ancho de la ventana

    private final String jugador;       //Nombre del jugador
    private PJ_GUI PJ;                  //Perosnaje principal
    private final int idNivel;          //Identificador del nivel actual
    private Nivel nivel;                //Nivel actual
    private Rectangle cam;              //Rectangulo que representa la zona del nivel lque se ve

    private ArrayList<String> enemigosDescubiertos;
    private final ArrayList<String> objetosDescubiertos;

    private Thread animador;

    private boolean fin = false;

    private int puntuacion;

    /**
     * Constructor predeterminado. Crea la pantalla y muestra la informacion.
     *
     * @param d las dimensiones de la pantalla.
     * @param jugador el jugador que esta realizando la partida.
     */
    public PantallaJuego(Dimension d, String jugador) {
        setSize(d);
        V_WIDTH = d.width;
        setFocusable(true);
        addKeyListener(new Listener());

        this.jugador = jugador;
        enemigosDescubiertos = new ArrayList<>();
        objetosDescubiertos = new ArrayList<>();
        puntuacion = 0;
        idNivel = 1;

        cargar();
    }

    /**
     * Carga toda la informacino del juego.
     */
    public final void cargar() {

        PJ = new PJ_GUI(new Controladror_PJ());
        try {
            nivel = FactoryNiveles.getNivel("" + idNivel);
        } catch (ExcepcionCargaNivel e) {
            LogWriter.escribirLog(e.getMessage());
        }
        cam = new Rectangle(0, 0, 800, 600);
    }

    /**
     * Actualiza la parte del nivel que se vera.
     *
     * @param g el manejador de graficos.
     */
    private void actualizarCamara(Graphics2D g) {
        int maxOffX = nivel.getFondo().getWidth() - V_WIDTH;
        int minOffX = 0;

        cam.x = PJ.getPos().getxPos() - V_WIDTH / 2;

        if (cam.x > maxOffX) {
            cam.x = maxOffX;
        } else if (cam.x < minOffX) {
            cam.x = minOffX;
        }

        g.translate(-cam.x, -cam.y);
    }

    /**
     * Muestra los elementos visuales en pantalla.
     *
     * @param g el manejador de graficos.
     */
    public void draw(Graphics g) {
        requestFocusInWindow();
        Graphics2D g2d = (Graphics2D) g;

        //Utililzar el rectangulo para representar la parte visible del fondo del nivel
        actualizarCamara(g2d);
        //Dibujar el fondo
        nivel.dibujarFondo(g2d);
        //Dibujar enemigos
        nivel.dibujarEnemigos(g2d);
        //Dibujar las trampas
        nivel.dibujarTrampas(g2d);
        //Dibujar los objetos
        nivel.dibujarObjetos(g2d);
        //Dibuja la interfaz
        dibujarHUD(g2d);
        //Dibujar al PJ
        PJ.draw(g2d);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    @Override
    public void addNotify() {
        super.addNotify();

        animador = new Thread(this);
        animador.start();
    }

    @Override
    public void run() {
        final int FPS = 30;
        final int SKIP = 1000 / FPS;

        long siguiente = System.currentTimeMillis();
        long sleep;

        //Bucle principal de la aplicacion
        while (!fin) {
            //Acualiza datos
            PJ.actualizar(nivel.getFondo().getWidth());
            nivel.actualizar(PJ);
            colisiones();
            if (!PJ.isVivo()) {
                fin = true;
                enemigosDescubiertos = nivel.getEnemigos().getEnemigosDerrotados();
                ((VentanaPrincipal) SwingUtilities.getRoot(this)).irFin(false, puntuacion, jugador, enemigosDescubiertos, objetosDescubiertos);
            }
            if (!nivel.getJefe().getEnemigo().isVivo()) {
                fin = true;
                enemigosDescubiertos = nivel.getEnemigos().getEnemigosDerrotados();
                enemigosDescubiertos.add(nivel.getJefe().getEnemigo().getTipo());
                puntuacion += nivel.getJefe().getEnemigo().getPuntos();
                ((VentanaPrincipal) SwingUtilities.getRoot(this)).irFin(true, puntuacion, jugador, enemigosDescubiertos, objetosDescubiertos);
            }
            //Muestra la informacion por pantalla
            repaint();

            //Control de FPS
            siguiente += SKIP;
            sleep = siguiente - System.currentTimeMillis();
            if (sleep >= 0) {
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException ex) {
                    LogWriter.escribirLog(ex.getMessage());
                }
            }
        }
    }

    /**
     * Comprueba las colisiones y actua en consecuencia.
     */
    private void colisiones() {
        //Colisiones entre el jugador y los enemigos
        for (Enemigo_GUI enemigo : nivel.getEnemigos().getEnemigos()) {
            if (Colisiones.colisionan(PJ.getHitBox(), PJ.getPos().getxPos(), PJ.getPos().getyPos(),
                    enemigo.getHitbox(), enemigo.getPosicion().getxPos(), enemigo.getPosicion().getyPos())) {
                if (PJ.isAtacando()) {
                    enemigo.getEnemigo().recibirDano(PJ.getDano());
                    if (!enemigo.getEnemigo().isVivo()) {
                        puntuacion += enemigo.getEnemigo().getPuntos();
                    }
                }
                if (enemigo.isAtacando()) {
                    PJ.recibirDano(enemigo.getEnemigo().getDano());
                }
            }
        }
        //Colisiones entre el jugador y el jefe del nivel
        if (Colisiones.colisionan(PJ.getHitBox(), PJ.getPos().getxPos(), PJ.getPos().getyPos(),
                nivel.getJefe().getHitbox(), nivel.getJefe().getEnemigo().getPosicion().getxPos(), nivel.getJefe().getEnemigo().getPosicion().getyPos())) {
            if (PJ.isAtacando()) {
                nivel.getJefe().getEnemigo().recibirDano(PJ.getDano());
            }
            if (nivel.getJefe().isAtacando()) {
                PJ.recibirDano(nivel.getJefe().getEnemigo().getDano());
            }

        }
        //Colisiones entre el jugador y las trampas
        for (Trampa_GUI trampa : nivel.getTrampas().getTrampas()) {
            if (Colisiones.colisionan(PJ.getHitBox(), PJ.getPos().getxPos(), PJ.getPos().getyPos(),
                    trampa.getHitBox(), trampa.getxPos(), trampa.getyPos())) {
                PJ.recibirDano(trampa.getDano());
            }
        }
        //Colisiones entre el jugador y los objetos
        Iterator i = nivel.getObjetos().getObjetos().iterator();
        while (i.hasNext()) {
            Objeto_GUI objeto = (Objeto_GUI) i.next();
            if (Colisiones.colisionan(PJ.getHitBox(), PJ.getPos().getxPos(), PJ.getPos().getyPos(),
                    objeto.getHitBox(), objeto.getPos().getxPos(), objeto.getPos().getyPos())) {
                PJ.anadirObjeto(objeto);
                objetosDescubiertos.add(objeto.getObjeto().getTipo());
                i.remove();
            }
        }
    }

    /**
     * Muestra informacion sobre la partida en pantalla.
     *
     * @param g2d el manejador de graficos.
     */
    private void dibujarHUD(Graphics2D g2d) {
        int dispv = V_WIDTH / 10;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(cam.x + 15, 20, dispv, 10);
        g2d.setColor(Color.RED);
        int pvpix = PJ.getPV() * dispv / PJ.getPVMax();
        g2d.fillRect(cam.x + 15, 20, pvpix, 10);
        if (PJ.tieneObjetos()) {
            g2d.drawImage(PJ.getImagenObjeto(), cam.x + 20, cam.y + 35, this);
        }
    }

    private class Listener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            PJ.manejarTeclaPulsada(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            PJ.manejarTeclaSoltada(e);
        }

    }
}
