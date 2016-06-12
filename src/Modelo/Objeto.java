package Modelo;

/**
 * Clase que representa un objeto utilizable por el jugador.
 *
 * @author Kevin
 */
public abstract class Objeto {

    private final Posicion pos;

    /**
     * Constructor predeterminado.
     */
    public Objeto() {
        pos = new Posicion();
    }

    /**
     * Devuelve la posicion del objeto.
     *
     * @return la posicion del objeto.
     */
    public Posicion getPosicion() {
        return pos;
    }

    /**
     * Establece la posicion horizontal del objeto.
     *
     * @param xpos la posicion horizontal del objeto.
     */
    public void setXPos(int xpos) {
        pos.setxPos(xpos);
    }

    /**
     * Establece la posicion vertical del objeto.
     *
     * @param ypos la posicion vertical del objeto.
     */
    public void setYPos(int ypos) {
        pos.setyPos(ypos);
    }

    /**
     * Hace que el Jugador pasado utilize el objeto pasado.
     *
     * @param PJ el jugador que usara el objeto.
     */
    public abstract void usar(Jugador PJ);

    /**
     * Devuelve el tipo del objeto.
     *
     * @return el tipo del objeto.
     */
    public abstract String getTipo();
}
