package Modelo;

/**
 * Clase que representa una trampa.
 *
 * @author Kevin
 */
public class Trampa {

    private int posX;           //Posicion de la trampa en el nivel
    private boolean activada;   //Indica si esta activada
    private int dano;           //Dano de la trampa

    /**
     * Constructor predeterminado.
     */
    public Trampa() {
    }

    /**
     * Devuelve la posicion horizontal de la trampa.
     *
     * @return la posicion horizontal de la trampa.
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Establece la posicion horizontal de la trampa.
     *
     * @param posX la nueva posicion horizontal.
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Responde si la trampa esta acticada.
     *
     * @return true si esta activada; false en caso contrario.
     */
    public boolean isActivada() {
        return activada;
    }

    /**
     * Activa o desactiva la tramapa.
     *
     * @param activada true si se activa; false si se desactiva.
     */
    public void setActivada(boolean activada) {
        this.activada = activada;
    }

    /**
     * Devuelve el dano de la trampa.
     *
     * @return el dano de la trampa.
     */
    public int getDano() {
        return dano;
    }

    /**
     * Establece el dano de la trampa.
     *
     * @param dano el nuevo valor del dano.
     */
    public void setDano(int dano) {
        this.dano = dano;
    }

    /**
     * Se activara si el jugador esta cerca.
     *
     * @param posJugador la posicion horizontal del jugador.
     */
    public void actuar(int posJugador) {
        if (posJugador >= posX && posJugador <= posX + 100) {
            activada = true;
        }
    }

}
