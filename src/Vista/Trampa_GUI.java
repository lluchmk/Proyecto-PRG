package Vista;

import Conexiones.LogWriter;
import Modelo.Animacion;
import Modelo.Trampa;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Clase que representa graficamente una trampa.
 *
 * @author Kevin
 */
public class Trampa_GUI {

    private Trampa trampa;          //La trampa
    private String ruta;            //Ruta de la imagen
    private String rutaHb;          //Ruta del hitbox
    private Animacion animacion;    //Animacion de la trampa

    private int width;      //Ancho de la trampa
    private int height;     //Alto de la trampa

    /**
     * Constructor predeterminado. Crea una interfaz para la trampa dada.
     *
     * @param trampa la trampa a la que representara.
     */
    public Trampa_GUI(Trampa trampa) {
        this.trampa = trampa;
    }

    /**
     * Devuelve la trampa que representa.
     *
     * @return la trampa.
     */
    public Trampa getTrampa() {
        return trampa;
    }

    /**
     * Establece la trampa que representa.
     *
     * @param trampa la trampa.
     */
    public void setTrampa(Trampa trampa) {
        this.trampa = trampa;
    }

    /**
     * Establece la ruta de la imagen.
     *
     * @param ruta la ruta de la imagen.
     */
    public void setRuta(String ruta) {
        this.ruta = ruta;
        this.rutaHb = ruta;
    }

    /**
     * Devuelve la posicion horizontal de la trampa.
     *
     * @return la posicion horizontal de la trampa.
     */
    public int getxPos() {
        return trampa.getPosX();
    }

    /**
     * Devuelve la posicion vertical de la trampa.
     *
     * @return la posicion vertical de la trampa.
     */
    public int getyPos() {
        return 433;
    }

    /**
     * Devuelve la anchura de la trampa.
     *
     * @return la anchura de la trampa.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Establece la anchura de la trampa.
     *
     * @param width la nueva anchura de la trampa.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Devuelve la altura de la trampa.
     *
     * @return la altura de la trampa.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Establece la altura de la trampa.
     *
     * @param height la nueva altura de la trampa.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Devuelve el dano de la trampa.
     *
     * @return el dano de la trampa.
     */
    public int getDano() {
        return trampa.getDano();
    }

    /**
     * Devuelve la altura de la trampa.
     *
     * @return la altura de la trampa.
     */
    public BufferedImage getHitBox() {
        return animacion.getHitBox();
    }

    /**
     * Carga las imagenes desde el spritesheet.
     */
    public void cargarFrames() {
        try {
            BufferedImage spriteSheet = ImageIO.read(new File(ruta));
            BufferedImage sshb = ImageIO.read(new File(ruta));
            animacion = new Animacion(spriteSheet, sshb, 0, 100, 100);
        } catch (IOException ex) {
            LogWriter.escribirLog("Error al cargar la imagen de una trampa. E: " + ex.getMessage());
        }
    }

    /**
     * Muestra la trampa por pantalla y la desacctiva si la animacion ha
     * acabado.
     *
     * @param g2d el manejador de graficos.
     */
    public void draw(Graphics2D g2d) {
        if (!trampa.isActivada()) {
            animacion.resetear();
            g2d.drawImage(animacion.getImagen(), trampa.getPosX(), 433, null);
        } else {
            g2d.drawImage(animacion.getFrame().getImagen(), trampa.getPosX(), 433, null);
            if (animacion.isUltimoFrame()) {
                animacion.resetear();
                trampa.setActivada(false);
            }
        }
    }

}
