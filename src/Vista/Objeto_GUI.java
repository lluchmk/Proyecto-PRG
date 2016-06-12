package Vista;

import Conexiones.LogWriter;
import Modelo.Objeto;
import Modelo.Posicion;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Clase que representa graficamente un objeto.
 *
 * @author Kevin
 */
public class Objeto_GUI {

    private Objeto o;           //Objeto al que representa
    private String ruta;        //Ruta de la imagen
    private BufferedImage img;  //Imagen grafica del objeto

    /**
     * Constructor predeterminado.
     */
    public Objeto_GUI() {
    }

    /**
     * Devuelve el objeto.
     *
     * @return el lobjeto.
     */
    public Objeto getObjeto() {
        return o;
    }

    /**
     * Establece el objeto al que represental
     *
     * @param o el objeto.
     */
    public void setObjeto(Objeto o) {
        this.o = o;
    }

    /**
     * Devuelve la posicion del objeto.
     *
     * @return la posicion.
     */
    public Posicion getPos() {
        return o.getPosicion();
    }

    /**
     * Establece la ruta de la imagen del objeto.
     *
     * @param ruta la ruta de la imagen.
     */
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    /**
     * Devuelve la imagen del objeto.
     *
     * @return la imagen del objeto.
     */
    public BufferedImage getImagen() {
        return img;
    }

    /**
     * Devuelve el hitbox del objeto.
     *
     * @return el hitbox.
     */
    public BufferedImage getHitBox() {
        return img;
    }

    /**
     * Carga la imagen.
     */
    public void cargar() {
        try {
            img = ImageIO.read(new File(ruta));
        } catch (IOException ex) {
            LogWriter.escribirLog("Error al cargar la imagen de un objeto. E: " + ex.getMessage());
        }
    }

    /**
     * Muestra el objeto por pantalla.
     *
     * @param g2d el manejador de graficos.
     */
    public void draw(Graphics2D g2d) {
        g2d.drawImage(img, o.getPosicion().getxPos(), o.getPosicion().getyPos(), null);
    }
}
