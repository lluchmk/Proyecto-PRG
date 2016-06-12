package Modelo;

import java.awt.image.BufferedImage;

/**
 * Clase que representa un unico frame.
 *
 * @author Kevin
 */
public class Frame {

    private final BufferedImage img;    //Imagen del frame
    private final BufferedImage hitBox;
    
    /**
     * Crea un frame a partir de la imagen dada,
     *
     * @param img la imagen del frame.
     * @param hitbox la imagen que representa el hitBox.
     */
    public Frame(BufferedImage img, BufferedImage hitbox) {
        this.img = img;
        this.hitBox = hitbox;
    }

    /**
     * Devuelve la imagen del frame.
     *
     * @return l aimagen del frame.
     */
    public BufferedImage getImagen() {
        return img;
    }

    /**
     * Devuleve el hitbox del frame.
     *
     * @return el hitbox del frame.
     */
    public BufferedImage getHitBox() {
        return hitBox;
    }
}
