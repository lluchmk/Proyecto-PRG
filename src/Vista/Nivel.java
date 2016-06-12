package Vista;

import Controlador.ColeccionEnemigos;
import Controlador.ColeccionObjetos;
import Controlador.ColeccionTrampas;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Clase que representa un nivel.
 *
 * @author Kevin
 */
public class Nivel {

    private String nivel;                       //El identidicador del nivel
    private BufferedImage fondo;                //La imagen de fondo
    private final ColeccionEnemigos enemigos;   //Coleccion de enemigos del nivel
    private Enemigo_GUI jefe;                   //Jefe del nivel
    private final ColeccionTrampas trampas;     //Coleccion de trampas
    private final ColeccionObjetos objetos;     //Coleccion de objetos

    /**
     * Constructor predeterminado.
     */
    public Nivel() {
        enemigos = new ColeccionEnemigos();
        trampas = new ColeccionTrampas();
        objetos = new ColeccionObjetos();
    }

    /**
     * Devuelve el identidicador del nivel.
     *
     * @return el identidicador del nivel.
     */
    public String getNivel() {
        return nivel;
    }

    /**
     * Establece el identidicador del nivel.
     *
     * @param nivel el identificador del nivel.
     */
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    /**
     * Devuelve la coleccion de enemigos.
     *
     * @return la coleccion de enemigos.
     */
    public ColeccionEnemigos getEnemigos() {
        return enemigos;
    }

    /**
     * Devuelve el jefe del nivel.
     *
     * @return el jefe del nivel.
     */
    public Enemigo_GUI getJefe() {
        return jefe;
    }

    /**
     * Establece el jefe del nivel.
     *
     * @param jefe el jefe.
     */
    public void setJefe(Enemigo_GUI jefe) {
        this.jefe = jefe;
    }

    /**
     * Devuelve la coleccion de trampas.
     *
     * @return la coleccion de trampas.
     */
    public ColeccionTrampas getTrampas() {
        return trampas;
    }

    /**
     * Devuelve la coleccion de objetos.
     *
     * @return la coleccion de objetos.
     */
    public ColeccionObjetos getObjetos() {
        return objetos;
    }

    /**
     * Devuelve el fondo del nivel.
     *
     * @return el fondo del nivel.
     */
    public BufferedImage getFondo() {
        return fondo;
    }

    /**
     * Establece el fondo del nivel.
     *
     * @param fondo el fondo del nivel.
     */
    public void setFondo(BufferedImage fondo) {
        this.fondo = fondo;
    }

    /**
     * Actualiza la informacion de los elementos del nivel.
     *
     * @param PJ el personaje que esta jugando.
     */
    public void actualizar(PJ_GUI PJ) {
        getEnemigos().actualizar(PJ, fondo.getWidth());
        jefe.getEnemigo().actuar(PJ.getPos(), fondo.getWidth(), jefe.getWidth());
        jefe.getEnemigo().reducirIFrames();
        getTrampas().actualizar(PJ);
    }

    /**
     * Dibuja el fodo.
     *
     * @param g2d el manejador de graficos.
     */
    public void dibujarFondo(Graphics2D g2d) {
        g2d.drawImage(fondo, 0, 0, null);
    }

    /**
     * Dibuja los enemigos.
     *
     * @param g2d el manejador de graficos.
     */
    public void dibujarEnemigos(Graphics2D g2d) {
        enemigos.draw(g2d);
        jefe.draw(g2d);
    }

    /**
     * Dibuja las trampas.
     *
     * @param g2d el manejador de graficos.
     */
    public void dibujarTrampas(Graphics2D g2d) {
        trampas.draw(g2d);
    }

    /**
     * Dibuja los objetos.
     *
     * @param g2d el manejador de graficos.
     */
    public void dibujarObjetos(Graphics2D g2d) {
        objetos.draw(g2d);
    }
}
