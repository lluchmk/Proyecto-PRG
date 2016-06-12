package Modelo.Objetos;

import Modelo.Jugador;
import Modelo.Objeto;

/**
 * Una pocion curativa que restaurara parte de los puntos de vida del jugador.
 *
 * @author Kevin
 */
public class Pocion extends Objeto {
    
    public Pocion() {
        super();
    }
    
    @Override
    public void usar(Jugador PJ) {
        PJ.setPv(PJ.getPv() + 16);
    }

    @Override
    public String getTipo() {
        return "Pocion";
    }
}
