package Modelo.Enemigos;

import Modelo.Enemigo;
import Modelo.Posicion;

/**
 * Clase que representa a un dragon.
 *
 * @author Kevin
 */
public class Dragon extends Enemigo {

    @Override
    public void actuar(Posicion posJugador, int anchoNivel, int anchoEne) {
        if (getPosicion().getxPos() >= posJugador.getxPos()) {
            //Jugador a la izquierda del enemigo y dentro de la vista
            if (getPosicion().getxPos() - posJugador.getxPos() < getVista()) {
                if (getPosicion().getxPos() - posJugador.getxPos() < 50) {
                    setEstadoActual(Enemigo.ATTACK_L);
                } else {
                    moverX(false, anchoNivel, anchoEne);
                    setEstadoActual(Enemigo.RUN_L);
                }
            }
        } else {
            //Jugador a la derecha del enemigo y dento de la vista
            if (posJugador.getxPos() - getPosicion().getxPos() < getVista()) {
                if (posJugador.getxPos() - getPosicion().getxPos() < 40) {
                    setEstadoActual(ATTACK_L);
                } else {
                    moverX(true, anchoNivel, anchoEne);
                    setEstadoActual(Enemigo.RUN_R);
                }
            }
        }
    }

    @Override
    public String getTipo() {
        return "Dragon";
    }

}
