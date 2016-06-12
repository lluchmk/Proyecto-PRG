package Modelo;

import Controlador.ColeccionObjetos;
import Vista.Objeto_GUI;

/**
 * Clase que representa un jugador.
 *
 * @author Kevin
 */
public class Jugador extends Personaje {

    private final ColeccionObjetos objetos;
    private int objetoSeleccionado;

    /**
     * Crea un jugador con los datos dados.
     *
     * @param pos posicion inicial del jugador.
     * @param pv puntos de vida del jugador.
     * @param dano dano del jugador.
     */
    public Jugador(Posicion pos, int pv, int dano) {
        super(pos, pv, dano);
        objetoSeleccionado = -1;
        objetos = new ColeccionObjetos();
    }

    /**
     * Devuelve el objeto seleccionado.
     *
     * @return el objeto seleccionado.
     */
    public Objeto_GUI getObjeto() {
        return objetos.getObjeto(objetoSeleccionado);
    }

    /**
     * Anade un objeto a la coleccion.
     *
     * @param o el objeto a anadir.
     */
    public void anadirObjeto(Objeto_GUI o) {
        objetos.anadirObjeto(o);
        objetoSeleccionado++;
    }

    /**
     * Utiliza el objeto seleccionado.
     */
    public void utilizarObjeto() {
        if (objetoSeleccionado > -1) {
            objetos.utilizarObjeto(this, objetoSeleccionado);
            objetoSeleccionado--;
        }
    }

    /**
     * Selecciona el siguiente objeto de la coleccion.
     */
    public void siguienteObjeto() {
        objetoSeleccionado++;
        if (objetoSeleccionado >= objetos.cantidadObjeos()) {
            objetoSeleccionado = 0;
        }
    }

    /**
     * Selecciona el objeto anterior de la coleccion.
     */
    public void anteriorObjeto() {
        objetoSeleccionado--;
        if (objetoSeleccionado < 0) {
            objetoSeleccionado = objetos.cantidadObjeos() - 1;
        }
    }

    /**
     * Responde si el jugador tiene objetos.
     *
     * @return true si tiene objetos; false en caso contrario.
     */
    public boolean tieneObjetos() {
        return objetoSeleccionado != -1;
    }
}
