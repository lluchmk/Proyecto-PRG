package Vista;

import Conexiones.LogWriter;
import Modelo.Animacion;
import Modelo.Enemigo;
import Modelo.Posicion;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * Clase que maneja los graficos de un enemigo.
 *
 * @author Kevin
 */
public class Enemigo_GUI {

    private Enemigo enemigo;    //Enemigo al que representa

    private final ArrayList<Animacion> frames;  //Coleccion de imagenes del enemigo

    private String ruta;            //Ruta de las imagenes del enemigos
    private String rutahb;          //Ruta de las imagenes del hitbox
    private int width;              //Anchura del enemigo
    private int height;             //Altura del enemigo
    private int nAnimaciones;       //Numero de animaciones del enemigo

    /**
     * Constructor predeterminado.
     */
    public Enemigo_GUI() {
        frames = new ArrayList<>();
    }

    /**
     * Devuelve el enemigo al que representa.
     *
     * @return el enemigo.s
     */
    public Enemigo getEnemigo() {
        return enemigo;
    }

    /**
     * Establece el enemigo al que representa.
     *
     * @param e el enemigo al que representara.
     */
    public void setEnemigo(Enemigo e) {
        enemigo = e;
    }

    /**
     * Devuelve la ruta del spritesheet del enemigo.
     *
     * @return la ruta del spritesheet del enemigo.
     */
    public String getRuta() {
        return ruta;
    }

    /**
     * Establace la ruta del spritesheet del enemigo.
     *
     * @param ruta la ruta del spritesheet del enemigo.
     */
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    /**
     * Establece la ruta del hitbox del enemigo.
     *
     * @param ruta la ruta del hitbox del enemigo.
     */
    public void setRutaHb(String ruta) {
        this.rutahb = ruta;
    }

    /**
     * Devuelve la posicion del enemigo.
     *
     * @return la posicion del enemigo.
     */
    public Posicion getPosicion() {
        return enemigo.getPosicion();
    }

    /**
     * Devuelve la anchura del enemigo.
     *
     * @return la anchura del enemigo.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Establece la anchura del enemigo.
     *
     * @param width la nueva anchura del enemigo.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Devuelve la altura del enemigo.
     *
     * @return la altura del enemigo.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Establece la altura del enemigo.
     *
     * @param height la nueva altura del enemigo.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Devuelve la imagen actual.
     *
     * @return la imagen actual.
     */
    public BufferedImage getImagen() {
        return frames.get(enemigo.getEstadoActual()).getImagen();
    }

    /**
     * Devuelve el hitbox actual.
     *
     * @return el hitbox actual.
     */
    public BufferedImage getHitbox() {
        return frames.get(enemigo.getEstadoActual()).getHitBox();
    }

    /**
     * Devuelve la cantidad de animaciones del enemigo.
     *
     * @return la cantidad de animaciones.
     */
    public int getnAnimaciones() {
        return nAnimaciones;
    }

    /**
     * Establece la cantidad de animaciones del enemigo.
     *
     * @param nAnimaciones la cantidad de animaciones.
     */
    public void setnAnimaciones(int nAnimaciones) {
        this.nAnimaciones = nAnimaciones;
    }

    /**
     * Establece la animacion actual.
     *
     * @param animacionActual la animacion actual.
     */
    public void setAnimacionActual(int animacionActual) {
        enemigo.setEstadoActual(animacionActual);
    }

    /**
     * Devuelve la anuimacion actual.
     *
     * @return la animacion actual.
     */
    public int getAnimacionActual() {
        return enemigo.getEstadoActual();
    }

    /**
     * Responde si el enemigo esta atacando.
     *
     * @return true si esta atacando; false en caso contrario.
     */
    public boolean isAtacando() {
        return enemigo.getEstadoActual() == Enemigo.ATTACK_L || enemigo.getEstadoActual() == Enemigo.ATTACK_R;
    }

    /**
     * Carga las imagenes del enemigo.
     */
    public void cargarFrames() {
        try {
            BufferedImage spriteSheet = ImageIO.read(new File(ruta));
            BufferedImage sshb = ImageIO.read(new File(rutahb));
            for (int i = 0; i < nAnimaciones; i++) {
                Animacion a = new Animacion(spriteSheet, sshb, i, width, height);
                frames.add(a);
            }
        } catch (IOException ex) {
            LogWriter.escribirLog("No se pudo cargar el spritesheet del PJ. IOExcepcion: " + ex.getMessage());
        }
    }

    /**
     * Muestra al enemigo por pantalla.
     *
     * @param g2d el manejador de graficos.
     */
    public void draw(Graphics2D g2d) {
        g2d.drawImage(frames.get(getAnimacionActual()).getFrame().getImagen(), enemigo.getPosicion().getxPos(), enemigo.getPosicion().getyPos(), null);
        if (enemigo.isAtacando() && frames.get(getAnimacionActual()).isUltimoFrame()) {
            frames.get(getAnimacionActual()).resetear();
            enemigo.pararAtaque();
        }
    }
}
