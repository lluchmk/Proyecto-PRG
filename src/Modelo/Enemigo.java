package Modelo;

/**
 * Clase abstracta que representa un enemigo.
 *
 * @author Kevin
 */
public abstract class Enemigo extends Personaje {

    public static final int IDLE_R = 0;
    public static final int IDLE_L = 1;
    public static final int RUN_R = 2;
    public static final int RUN_L = 3;
    public static final int ATTACK_R = 4;
    public static final int ATTACK_L = 5;

    private int puntos;         //Puntos que otorga el enemigo al ser derrotado
    private int vista;          //Alcance de la vision del enemigod
    private int estadoActual;   //Estado del enemigo

    /**
     * Constructor predeterminado. Inicializa al enemigo:
     */
    public Enemigo() {
        super();
    }

    /**
     * Devuleve la puntuiacino del enemigo.
     *
     * @return la puntuiacino del enemigo.
     */
    public int getPuntos() {
        return puntos;
    }

    /**
     * Estabvlece la puntuacion del enemigo.
     *
     * @param puntos la puntuacion del enemigo.
     */
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    /**
     * Devuelve el alcance visual del enemigo.
     *
     * @return el alcance visual del enemigo.
     */
    public int getVista() {
        return vista;
    }

    /**
     * Establece el alcance visual del enemigo.
     *
     * @param vista el alcance visual del enemigo.
     */
    public void setVista(int vista) {
        this.vista = vista;
    }

    /**
     * Devuelve el estado actuald el enemigo.
     *
     * @return el estado actuald el enemigo.
     */
    public int getEstadoActual() {
        return estadoActual;
    }

    /**
     * Establece el estado actual del enemigo.
     *
     * @param estadoActual el nuevo estado del enemigo.
     */
    public void setEstadoActual(int estadoActual) {
        this.estadoActual = estadoActual;
    }

    /**
     * Responde si el enemigo esta atacando.
     *
     * @return true si esta atacando; false en caso contrario.
     */
    public boolean isAtacando() {
        return estadoActual == ATTACK_L || estadoActual == ATTACK_R;
    }

    /**
     * Deja de atacar.
     */
    public void pararAtaque() {
        switch (estadoActual) {
            case ATTACK_L:
                estadoActual = IDLE_L;
                break;
            case ATTACK_R:
                estadoActual = IDLE_R;
                break;
        }
    }

    /**
     * Mueve al enemigo horizontalmente.
     *
     * @param derecha indica la direccion. True si se desplaza a la derecha;
     * false a la izquierda.
     * @param anchoNivel anchura del nivel.
     * @param anchoEne anchura del enemigo.
     */
    public void moverX(boolean derecha, int anchoNivel, int anchoEne) {
        if (derecha) {
            if (getPosicion().getxPos() + anchoEne < anchoNivel) {
                getPosicion().modificarXPos(getVX());
            }
        } else {
            if (getPosicion().getxPos() > 0) {
                getPosicion().modificarXPos(-getVX());
            }
        }
    }

    /**
     * Mueve al enemigo verticalmente.
     *
     * @param arriba indica la direccion. True si se desplaza arriba; false
     * abajo.
     */
    public void moverY(boolean arriba) {
        getPosicion().modificarYPos((arriba ? -getVY() : getVY()));
        if (arriba) {
            if (getPosicion().getyPos() < 0) {
                getPosicion().modificarYPos(-getVY());
            }
        } else {
            if (getPosicion().getyPos() < 560) {
                getPosicion().modificarYPos(getVY());
            }
        }
    }

    /**
     * Hace actuar al enemigo. Cada enemigo actuara de un forma determinada
     * distinta.
     *
     * @param posJugador la posicion horizontal del jugador.
     * @param anchoNivel anchura del nivel.
     * @param anchoEne anchura del enemigo.
     */
    public abstract void actuar(Posicion posJugador, int anchoNivel, int anchoEne);

    /**
     * Devuelve el nombre del tipo de enemigo.
     *
     * @return el nombre del tipo de enemigo.
     */
    public abstract String getTipo();
}
