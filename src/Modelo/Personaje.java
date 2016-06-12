package Modelo;

/**
 * Clase que representa un personaje generico,
 *
 * @author Kevin
 */
public abstract class Personaje {

    private final Posicion pos; //Posicion del personaje
    private int velX, velY;     //Velocidades horizontal y vertical del personaje

    private int pvmax;          //Puntos de vida maximos
    private int pv;             //Puntos de vida actuales
    private int dano;           //Dano que realiza
    private  boolean vivo;      //Indica si esta vivo
    private final int iFrames;        //Frames de invencibilidad
    private int iFramesAct;     //Frames de invencibilidad actuales

    /**
     * Constructor predeterminado. Crea un personaje en la posicion 0, 0 y con 4
     * frames de invencibilidad.
     */
    public Personaje() {
        pos = new Posicion(0, 0);
        vivo = true;
        iFrames = 15;
    }

    /**
     * Crea un personaje en la posicion dada y con las caracteristicas dadas.
     *
     * @param pos posicion del personaje.
     * @param pv puntos de vida del personaje.
     * @param dano dano del personaje.
     */
    public Personaje(Posicion pos, int pv, int dano) {
        this.pos = pos;
        velX = velY = 0;
        this.pvmax = this.pv = pv;
        this.dano = dano;
        vivo = true;
        iFrames = 15;
    }

    /**
     * Devuleve la posicion del personaje.
     *
     * @return l aposicion del personaje.
     */
    public Posicion getPosicion() {
        return pos;
    }

    /**
     * Devuleve la velocidad horizontal del personaje.
     *
     * @return la velocidad horizontal del personaje.
     */
    public int getVX() {
        return velX;
    }

    /**
     * Devuleve la velocidad vertical del personaje.
     *
     * @return la velocidad vertical del personaje.
     */
    public int getVY() {
        return velY;
    }

    /**
     * Establece la velocidad horizontal del personaje.
     *
     * @param vx la nueva velocidad horizontal del personaje.
     */
    public void setVX(int vx) {
        velX = vx;
    }

    /**
     * Establece la velocidad vertical del personaje.
     *
     * @param vy la nueva velocidad vertical del personaje.
     */
    public void setVY(int vy) {
        velY = vy;
    }

    /**
     * Devuelve los puntos de vida maximos del personaje.
     *
     * @return los puntos de vida del personaje.
     */
    public int getPvMax() {
        return pvmax;
    }

    /**
     * Establece los puntos de vida maximos del personaje.
     *
     * @param pvmax los nuevos puntos de vida del personaje.
     */
    public void setPvMax(int pvmax) {
        this.pvmax = pvmax;
    }
    
    /**
     * Devuelve los puntos de vida del personaje.
     *
     * @return los puntos de vida del personaje.
     */
    public int getPv() {
        return pv;
    }

    /**
     * Establece los puntos de vida del personaje.
     *
     * @param pv los nuevos puntos de vida del personaje.
     */
    public void setPv(int pv) {
        this.pv = pv;
        if (this.pv > pvmax) {
            setPv(pvmax);
        }
    }

    /**
     * Devuelve el dano del personaje.
     *
     * @return el dano del personaje.
     */
    public int getDano() {
        return dano;
    }

    /**
     * Establece el dano del personaje.
     *
     * @param dano el nuevo dano del personaje.
     */
    public void setDano(int dano) {
        this.dano = dano;
    }

    /**
     * Hace que el personaje reciba dano si no se encuentra en sus frames de
     * invencibilidad.
     *
     * @param dano que recibira el personaje.
     */
    public void recibirDano(int dano) {
        if (iFramesAct <= 0) {
            pv -= dano;
            if (pv <= 0) {
                vivo = false;
            }
            iFramesAct = iFrames;
        }
    }

    /**
     * Reduce en uno los frames de invencibilidad actuales del personaje.
     */
    public void reducirIFrames() {
        if (iFramesAct > 0) {
            iFramesAct--;
        }
    }

    /**
     * Responde si el personaje esta vivo.
     *
     * @return cierto si esta vivo; false en caso contrario.
     */
    public boolean isVivo() {
        return vivo;
    }
}
