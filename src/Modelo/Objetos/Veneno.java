package Modelo.Objetos;

import Modelo.Jugador;
import Modelo.Objeto;

/**
 * Un peligroso veneno.
 *
 * @author Kevin
 */
public class Veneno extends Objeto {

    @Override
    public void usar(Jugador PJ) {
        PJ.setPv(PJ.getPv() - 5);
    }

    @Override
    public String getTipo() {
        return "Veneno";
    }

}
