package Modelo.Enemigos;

import Modelo.Enemigo;
import Modelo.Posicion;
import java.util.Random;

/**
 * Clase que representa un murcielago
 *
 * @author Kevin
 */
public class Murcielago extends Enemigo {

    private int framesMismaDireccion; //Cantidad de frames que se ha desplazado en la misma direccion
    private int dirx; //Direccion en la que se esta desplazando horizontalmente. 0 -> derecha; 1 -> izquierda

    /**
     * Constructor predeterminado.
     */
    public Murcielago() {
        super();
        framesMismaDireccion = 0;
    }

    /**
     * Sobreescrito para representar que el murciela nunca estara en estado
     * IDLE.
     *
     * @param estadoActual el nuevo estado del enemigo.
     */
    @Override
    public void setEstadoActual(int estadoActual) {
        super.setEstadoActual(estadoActual - 2);
    }

    /**
     * Sobreescrito para indicar que el murcielago siempre esta atacando.
     *
     * @return siempre true;
     */
    @Override
    public boolean isAtacando() {
        return true;
    }

    @Override
    public void actuar(Posicion posJugador, int anchoNivel, int anchoEne) {
        //Jugador a la izquierda
        if (getPosicion().getxPos() >= posJugador.getxPos()) {
            //Jugador dentro de la vista
            if (getPosicion().getxPos() - posJugador.getxPos() < getVista()) {
                moverX(false, anchoNivel, anchoEne);
                setEstadoActual(Enemigo.RUN_L);
            } else {
                moverseRandom(anchoNivel, anchoEne);
            }
        } else { //Jugador a la derecha
            //Jugador dento de la vista
            if (posJugador.getxPos() - getPosicion().getxPos() < getVista()) {
                moverX(true, anchoNivel, anchoEne);
                setEstadoActual(Enemigo.RUN_R);
            } else {
                moverseRandom(anchoNivel, anchoEne);
            }
        }
    }

    /**
     * Determina la direccion del movimiento aleatoriamente.
     *
     * @param anchoNivel anchura del nivel.
     * @param anchoEne anchura del enemigo.
     */
    public void moverseRandom(int anchoNivel, int anchoEne) {
        moverX(dirx == 0, anchoNivel, anchoEne);
        framesMismaDireccion++;
        if (framesMismaDireccion >= 15) {
            Random r = new Random();
            dirx = r.nextInt(2);
            super.setEstadoActual(dirx);
            framesMismaDireccion = 0;
        }
    }

    @Override
    public String getTipo() {
        return "Murcielago";
    }

}
