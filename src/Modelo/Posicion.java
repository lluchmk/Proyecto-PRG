package Modelo;

/**
 * Una posicion determinada por una coordenada horizontal y una vertical.
 *
 * @author Kevin
 */
public class Posicion {

    private int xPos;   //Posicion horizontal
    private int yPos;   //Posicion vertical

    /**
     * Crea una nueva posicion el las coordenadas (0,0).
     */
    public Posicion() {
        xPos = 0;
        yPos = 0;
    }

    /**
     * Crea una nueva posicion en las coordenadas dadas.
     *
     * @param xPos posicion horizontal.
     * @param yPos posicion vertical.
     */
    public Posicion(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    /**
     * Devuelve la posicion horizontal.
     *
     * @return la posicion horizontal.
     */
    public int getxPos() {
        return xPos;
    }

    /**
     * Establce la posicion horizontal.
     *
     * @param xPos la nueva posicion horizontal.
     */
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    /**
     * Devuelve la posicion vertical.
     *
     * @return la posicion vertical.
     */
    public int getyPos() {
        return yPos;
    }

    /**
     * Establce la posicion vertical.
     *
     * @param yPos la nueva posicion vertical.
     */
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    /**
     * Modifica la posicion horizontal en una cantidad dada.
     *
     * @param dx la diferencia de la posicion horizontal
     */
    public void modificarXPos(int dx) {
        xPos += dx;
    }

    /**
     * Modifica la posicion vertical en una cantidad dada.
     *
     * @param dy la diferencia de la posicion vertical
     */
    public void modificarYPos(int dy) {
        yPos += dy;
    }
}
