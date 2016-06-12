package Modelo;

import Conexiones.LogWriter;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.util.ArrayList;

/**
 * Secuencia de Frames que componen una animacion.
 *
 * @author Kevin
 */
public class Animacion {

    private final ArrayList<Frame> frames;  //Coleccion de Frames
    private int actual;                     //Frame actual
    private int framesAct;            //Numero de veces que se ha mostrado el frame actual
    private int nFrames;              //Numero de Frames de la animacion
    private boolean ultimoFrame;      //Indica si esta en el ultimo frame de una animacion
    private final int nAnimacion;     //Numero de la animacion
    private final int WIDTH;          //Ancho de cada frame
    private final int HEIGHT;         //Alto de cada frame

    /**
     * Carga una animacion a partir del spritesheet completo.
     *
     * @param spriteSheet la imagen que contiene las animacines.
     * @param hitbox la imagen que contine los hitboxes.
     * @param nAnimacion el numero de la animacin en el spritesheep.
     * @param w ancho de cada frame de la animacion.
     * @param h alto de cada frame de la animacion.
     */
    public Animacion(BufferedImage spriteSheet, BufferedImage hitbox, int nAnimacion, int w, int h) {
        frames = new ArrayList<>();
        actual = 0;
        framesAct = 0;
        ultimoFrame = false;
        nFrames = 0;
        this.nAnimacion = nAnimacion;
        this.WIDTH = w;
        this.HEIGHT = h;
        cargar(spriteSheet, hitbox);
    }

    /**
     * Carga los frames de la imagen desde el spritesheet.
     *
     * @param spriteSheet el spriteSheet desde el que cargar la animacion.
     */
    private void cargar(BufferedImage spriteSheet, BufferedImage sshib) {
        final int max = 100000;
        int i = 0;
        boolean finalDeImagen = false;

        while (!finalDeImagen && i < max) {
            try {
                Frame f = new Frame(spriteSheet.getSubimage(i * WIDTH, nAnimacion * HEIGHT, WIDTH, HEIGHT),
                        sshib.getSubimage(i * WIDTH, nAnimacion * HEIGHT, WIDTH, HEIGHT));
                frames.add(f);
                nFrames++;
                i++;
                if (i >= max) {
                    LogWriter.escribirLog("Se alcanzo el limite de carga de imagen al cargar la imagen " + spriteSheet.toString());
                }
            } catch (RasterFormatException e) {
                finalDeImagen = true;
            }
        }
    }

    /**
     * Devuelve el frame actual de la animacion y la avanza al siguiente frame.
     *
     * @return el frame actual de la animacion.
     */
    public Frame getFrame() {
        //Obtener la imagen y avanzar el iterador
        Frame f = frames.get(actual);
        framesAct++;
        if (framesAct >= 3) {
            actual++;
            framesAct = 0;
        }
        if (actual >= nFrames) {
            actual = 0;
            ultimoFrame = true;
        } else {
            ultimoFrame = false;
        }
        return f;
    }

    /**
     * Devuleve la imagen actual de la animacion.
     *
     * @return la imagen actual de la animacion.
     */
    public BufferedImage getImagen() {
        return frames.get(actual).getImagen();
    }

    /**
     * Devuleve el hitbox actual de la animacion.
     *
     * @return el hitbox actual de la animacion.
     */
    public BufferedImage getHitBox() {
        return frames.get(actual).getHitBox();
    }

    /**
     * Devuelve el ancho de un frame de la animacion.
     *
     * @return el ancho de un frame de la animacion.
     */
    public int getWidth() {
        return WIDTH;
    }

    /**
     * Devuelve el alto de un frame de la animacion.
     *
     * @return el alto de un frame de la animacion.
     */
    public int getHeight() {
        return HEIGHT;
    }

    public void resetear() {
        actual = 0;
        framesAct = 0;
        ultimoFrame = false;
    }

    /**
     * Responde si esta en el ultimo frame de la animacion
     *
     * @return true si esta en el ultimo frame de la animacion; false en caso
     * contrario.
     */
    public boolean isUltimoFrame() {
        return ultimoFrame;
    }
}
