package Controlador;

import Modelo.Jugador;
import Modelo.Posicion;
import Vista.Objeto_GUI;

/**
 * Clase controladora de un jugador, lee y modifica los datos del jugador.
 *
 * @author Kevin
 */
public class Controladror_PJ {

    private final Jugador PJ;   //El jugador al que maneja
    private EstadosJugador estado;  //Estado actual del jugador.

    /**
     * Crea un nuevo jugador y su controlador.
     */
    public Controladror_PJ() {
        PJ = new Jugador(new Posicion(0, 415), 50, 8);
        estado = EstadosJugador.IDLE_R;
    }

    /**
     * Devuelve la posicion del jugador.
     *
     * @return la posicion del jugador.
     */
    public Posicion getPosicion() {
        return PJ.getPosicion();
    }

    /**
     * Devuleve la velociodad horizontal del jugador.
     *
     * @return la velocidad horizontal del jugador.
     */
    public int getxVel() {
        return PJ.getVX();
    }

    /**
     * Devuelve los puntos de vida del personaje.
     *
     * @return los puntos de vida del personaje.
     */
    public int getPV() {
        return PJ.getPv();
    }

    /**
     * Devuelve los puntos de vida maximos del personaje.
     *
     * @return los puntos de vida maximos del personaje.
     */
    public int getPVMax() {
        return PJ.getPvMax();
    }

    /**
     * Devuleve el dano del jugador.
     *
     * @return el dano del jugador.
     */
    public int getDano() {
        return PJ.getDano();
    }

    /**
     * Hace dano al jugador.
     *
     * @param dano la cantidad de dano que se le hace al jugador.
     */
    public void recibirDano(int dano) {
        PJ.recibirDano(dano);
    }

    /**
     * Devuleve cierto si el jugador esta vivo (tiene puntos de vida positivos).
     *
     * @return cierto si el jugador eta vivo; false en caso contrario.
     */
    public boolean isVivo() {
        return PJ.isVivo();
    }

    /**
     * Devuelve el estado del jugador.
     *
     * @return el estado del jugador.
     */
    public EstadosJugador getEstado() {
        return estado;
    }

    /**
     * Establece el estado del jugador.
     *
     * @param estado el estado del jugador.
     */
    public void setEstado(EstadosJugador estado) {
        this.estado = estado;
    }
    
    /**
     * Devuelve el objeto seleccionado.
     *
     * @return el objeto seleccionado.
     */
    public Objeto_GUI getObjeto() {
        return PJ.getObjeto();
    }

    /**
     * Establece al jugador como moviendose hacia la derecha. Actualiza su
     * velocidad horizontal y el estado.
     */
    public void moverDerecha() {
        PJ.setVX(10);
        switch (estado) {
            case IDLE_L:
            case IDLE_R:
                estado = EstadosJugador.RUN_R;
                break;
            case RUN_L:
                estado = EstadosJugador.RUN_R;
                break;
            case AIR_L:
            case AIR_R:
                estado = EstadosJugador.AIR_R;
                break;
        }
    }

    /**
     * Establece al jugador como moviendose hacia la izqueirda. Actualiza su
     * velocidad horizontal y el estado.
     */
    public void moverIzquierda() {
        PJ.setVX(-10);
        switch (estado) {
            case IDLE_L:
            case IDLE_R:
                estado = EstadosJugador.RUN_L;
                break;
            case RUN_R:
                estado = EstadosJugador.RUN_L;
                break;
            case AIR_L:
            case AIR_R:
                estado = EstadosJugador.AIR_L;
                break;
        }
    }

    /**
     * Establece al jugador como atacando.
     */
    public void atacar() {
        switch (estado) {
            case IDLE_L:
            case RUN_L:
                estado = EstadosJugador.ATTACK_L;
                break;
            case IDLE_R:
            case RUN_R:
                estado = EstadosJugador.ATTACK_R;
                break;
            case AIR_R:
                estado = EstadosJugador.AIR_R_ATTACK;
                break;
            case AIR_L:
                estado = EstadosJugador.AIR_L_ATTACK;
                break;
        }
    }

    /**
     * Deja de atacar.
     */
    public void pararAtaque() {
        switch (estado) {
            case ATTACK_R:
                estado = EstadosJugador.IDLE_R;
                break;
            case AIR_R_ATTACK:
                estado = EstadosJugador.AIR_R;
                break;
            case ATTACK_L:
                estado = EstadosJugador.IDLE_L;
                break;
            case AIR_L_ATTACK:
                estado = EstadosJugador.AIR_L;
                break;
        }
    }

    public boolean estaAtacando() {
        switch (estado) {
            case ATTACK_L:
            case AIR_L_ATTACK:
            case ATTACK_R:
            case AIR_R_ATTACK:
                return true;
            default:
                return false;
        }
    }

    /**
     * Hace saltar al personaje. Actualiza la velocidad vertical y el estado.
     */
    public void saltar() {
        //Asigna una velocidad vertical hacia arriba
        switch (estado) {
            case IDLE_L:
                estado = EstadosJugador.AIR_L;
                PJ.setVY(-20);
                break;
            case IDLE_R:
                estado = EstadosJugador.AIR_R;
                PJ.setVY(-20);
                break;
            case RUN_L:
                estado = EstadosJugador.AIR_L;
                PJ.setVY(-20);
                break;
            case RUN_R:
                estado = EstadosJugador.AIR_R;
                PJ.setVY(-20);
                break;
        }
    }

    /**
     * Anade un objeto a la coleccion del personaje.
     *
     * @param o el obejto a anadir.
     */
    public void anadirObjeto(Objeto_GUI o) {
        PJ.anadirObjeto(o);
    }

    /**
     * Utiliza el objeto seleccionado.
     */
    public void utilizarOjeto() {
        PJ.utilizarObjeto();
    }

    /**
     * Selecciona el siguiente objeto de la coleccion.
     */
    public void siguienteObjeto() {
        PJ.siguienteObjeto();
    }

    /**
     * Selecciona el objeto anterior de la coleccion.
     */
    public void anteriorObjeto() {
        PJ.anteriorObjeto();
    }

    /**
     * Responde si el jugador tiene objetos.
     *
     * @return true si tiene objetos; false en caso contrario.
     */
    public boolean tieneObjetos() {
        return PJ.tieneObjetos();
    }
    
    /**
     * Para el movimiento horizontal del personaje. Actualiza la velocidad y el
     * estado.
     */
    public void pararX() {
        PJ.setVX(0);
        switch (estado) {
            case RUN_L:
                estado = EstadosJugador.IDLE_L;
                break;
            case RUN_R:
                estado = EstadosJugador.IDLE_R;
                break;
            case AIR_L:
                estado = EstadosJugador.AIR_L;
                break;
            case AIR_R:
                estado = EstadosJugador.AIR_R;
                break;
        }
    }

    /**
     * Para el movimiento vertical del personaje. Actualiza la velocidad
     * vertical.
     */
    public void pararY() {
        PJ.setVY(0);
    }

    /**
     * Actualiza la informacion del personaje. Cambia la posicion y recalcula
     * los frames de invencibilidad
     *
     * @param anchoNivel ancho del nivel.
     * @param anchoPJ ancho del personaje.
     */
    public void actualizar(int anchoNivel, int anchoPJ) {
        actualizarPos(anchoNivel, anchoPJ);
        PJ.reducirIFrames();
    }

    /**
     * Actualiza la posicion del personaje.
     *
     * @param anchoNivel ancho del nivel.
     * @param anchoPJ ancho del personaje.
     */
    public void actualizarPos(int anchoNivel, int anchoPJ) {

        switch (estado) {
            case IDLE_L:
            case IDLE_R:
                break;
            case RUN_R:
                if (PJ.getPosicion().getxPos() + anchoPJ < anchoNivel) {
                    PJ.getPosicion().modificarXPos(PJ.getVX());
                }
                break;
            case RUN_L:
                if (PJ.getPosicion().getxPos() > 0) {
                    PJ.getPosicion().modificarXPos(PJ.getVX());
                }
                break;
            //Actualiza la velocidad vertical si esta en el aire
            case AIR_R:
            case AIR_R_ATTACK:
                if (PJ.getPosicion().getxPos() + anchoPJ < anchoNivel) {
                    PJ.getPosicion().modificarXPos(PJ.getVX());
                }
                PJ.getPosicion().modificarYPos(PJ.getVY());
                if (PJ.getVY() < 0) {
                    PJ.setVY(PJ.getVY() + 2);
                } else if (PJ.getVY() == 0 || PJ.getVY() > 0) {
                    PJ.setVY(PJ.getVY() + 2);
                    if (PJ.getPosicion().getyPos() >= 415) {
                        //Ha llegado al suelo
                        pararY();
                        estado = EstadosJugador.RUN_R;
                    }
                }
                break;
            case AIR_L:
            case AIR_L_ATTACK:
                if (PJ.getPosicion().getxPos() > 0) {
                    PJ.getPosicion().modificarXPos(PJ.getVX());
                }
                PJ.getPosicion().modificarYPos(PJ.getVY());
                if (PJ.getVY() < 0) {
                    PJ.setVY(PJ.getVY() + 2);
                } else if (PJ.getVY() == 0 || PJ.getVY() > 0) {
                    PJ.setVY(PJ.getVY() + 2);
                    if (PJ.getPosicion().getyPos() >= 415) {
                        //Ha llegado al suelo
                        pararY();
                        estado = EstadosJugador.RUN_L;
                    }
                }
                break;
        }
    }
}
