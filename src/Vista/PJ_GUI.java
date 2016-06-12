package Vista;

import Conexiones.LogWriter;
import Controlador.Controladror_PJ;
import Controlador.EstadosJugador;
import Modelo.Animacion;
import Modelo.Posicion;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * Interfaz grafica de un personaje jugador. Encargada de gestionar los graficos
 * y los eventos de control.
 *
 * @author kevin
 */
public class PJ_GUI {

    private final int WIDTH = 90;       //Ancho de cada frame de la animacion
    private final int HEIGHT = 100;     //Alto de cada frame de la animacion
    private final String PATH = "archivos/ss/knight.png";
    private final String HB_PATH = "archivos/ss/knighthb.png";

    private final Controladror_PJ PJ;   //El personaje jugador al que representa.

    private final ArrayList<Animacion> frames; //Coleccion de imagenes.

    /**
     * Carga al personaje jugador y carga los frames desde la ruta dada.
     *
     * @param PJ el personaje jugador.
     */
    public PJ_GUI(Controladror_PJ PJ) {
        this.PJ = PJ;
        frames = new ArrayList<>();
        cargarFrames(EstadosJugador.values().length);
    }

    /**
     * Carga las imagenes del jugador.
     *
     * @param nAnimaciones
     */
    private void cargarFrames(int nAnimaciones) {
        try {
            BufferedImage spriteSheet = ImageIO.read(new File(PATH));
            BufferedImage sshb = ImageIO.read(new File(HB_PATH));
            for (int i = 0; i < nAnimaciones; i++) {
                Animacion a = new Animacion(spriteSheet, sshb, i, WIDTH, HEIGHT);
                frames.add(a);
            }
        } catch (IOException ex) {
            LogWriter.escribirLog("No se pudo cargar el spritesheet del PJ. IOExcepcion: " + ex.getMessage());
        }
    }

    /**
     * Devuelve los puntos de vida del personaje.
     *
     * @return los puntos de vida.
     */
    public int getPV() {
        return PJ.getPV();
    }

    /**
     * Devuelve los puntos maximos de vida del personaje.
     *
     * @return los puntos de vida maximos.
     */
    public int getPVMax() {
        return PJ.getPVMax();
    }

    /**
     * Devuelve el dano del personaje.
     *
     * @return el dano del personaje.
     */
    public int getDano() {
        return PJ.getDano();
    }

    /**
     * Recibe dano.
     *
     * @param dano la cantidad de dano a recibir.
     */
    public void recibirDano(int dano) {
        PJ.recibirDano(dano);
    }

    /**
     * Devuelve la posicion del personaje.
     *
     * @return la posicion del personaje.
     */
    public Posicion getPos() {
        return PJ.getPosicion();
    }

    /**
     * Devuelve la anchura del jugador.
     *
     * @return la anchura del jugador.
     */
    public int getWidth() {
        return WIDTH;
    }

    /**
     * Devuelve la altura del jugador.
     *
     * @return la altura del jugador.
     */
    public int getHeight() {
        return HEIGHT;
    }

    /**
     * Devuelve la imagen actual.
     *
     * @return la imagen actual.
     */
    public BufferedImage getImagen() {
        return frames.get(PJ.getEstado().getIdEstado()).getImagen();
    }

    /**
     * Devuelve el hitbox actual.
     *
     * @return el hitbox actual.
     */
    public BufferedImage getHitBox() {
        return frames.get(PJ.getEstado().getIdEstado()).getHitBox();
    }

    /**
     * Devuelve la imagen del objeto seleccionado.
     *
     * @return la imagen del objeto.
     */
    public BufferedImage getImagenObjeto() {
        return PJ.getObjeto().getImagen();
    }

    /**
     * Responde si el personaje esta atacando.
     *
     * @return true si esta atacando; false en caso contrario.
     */
    public boolean isAtacando() {
        return PJ.estaAtacando();
    }

    /**
     * Responde si el jugador esta vivo.
     *
     * @return true si esta vivo; fals en caso contrario.
     */
    public boolean isVivo() {
        return PJ.isVivo();
    }

    /**
     * Anadee un objeto a la coleccion de objetos del personaje.
     *
     * @param o el objeto a anadir.
     */
    public void anadirObjeto(Objeto_GUI o) {
        PJ.anadirObjeto(o);
    }

    /**
     * Responde si el jugador tiene objetos.
     *
     * @return true si tiene objetos; false en caso contrario.
     */
    public boolean tieneObjetos() {
        return PJ.tieneObjetos();
    }

    /**
     * Actualiza la informacion del jugador.
     *
     * @param anchoPantalla la anchura de la pantalla.
     */
    public void actualizar(int anchoPantalla) {
        PJ.actualizar(anchoPantalla, WIDTH);
        //Si se dibuja la ultima imagen de un ataque dejar de atacar
        if (PJ.estaAtacando()) {
            if (frames.get(PJ.getEstado().getIdEstado()).isUltimoFrame()) {
                frames.get(PJ.getEstado().getIdEstado()).resetear();
                PJ.pararAtaque();
            }
        }
    }

    /**
     * Muestra al jugador por pantalla.
     *
     * @param g2d el manejador de graficos.
     */
    public void draw(Graphics2D g2d) {
        g2d.drawImage(frames.get(PJ.getEstado().getIdEstado()).getFrame().getImagen(), PJ.getPosicion().getxPos(), PJ.getPosicion().getyPos(), null);
    }

    /**
     * Actua segun las teclas pulsadas.
     *
     * @param e el evento de teclado.
     */
    public void manejarTeclaPulsada(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                PJ.saltar();
                break;
            case KeyEvent.VK_LEFT:
                PJ.moverIzquierda();
                break;
            case KeyEvent.VK_RIGHT:
                PJ.moverDerecha();
                break;
            case KeyEvent.VK_Z:
                PJ.atacar();
                break;
            case KeyEvent.VK_X:
                PJ.utilizarOjeto();
                break;
            case KeyEvent.VK_A:
                PJ.anteriorObjeto();
                break;
            case KeyEvent.VK_S:
                PJ.siguienteObjeto();
                break;
        }
    }

    /**
     * Actua segun las teclas soltadas.
     *
     * @param e el evento de teclado.
     */
    public void manejarTeclaSoltada(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
                break;
            case KeyEvent.VK_LEFT:
                PJ.pararX();
                break;
            case KeyEvent.VK_RIGHT:
                PJ.pararX();
                break;
        }
    }
}
