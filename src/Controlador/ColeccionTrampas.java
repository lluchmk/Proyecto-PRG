package Controlador;

import Vista.PJ_GUI;
import Vista.Trampa_GUI;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Coleccion que almacena una serie de trampas.
 *
 * @author Kevin
 */
public class ColeccionTrampas {

    private final ArrayList<Trampa_GUI> trampas;    //La coleccion de trampas

    /**
     * Constructor predeterminado. Inicializa la coleccion.
     */
    public ColeccionTrampas() {
        trampas = new ArrayList<>();
    }

    /**
     * Anade una trampa a la colecicon.
     *
     * @param t la trampa a anadir.
     */
    public void anadirTrampa(Trampa_GUI t) {
        trampas.add(t);
    }

    /**
     * Devuelve la ultima trampa de la coleccion.
     *
     * @return la ultima trampa de la coleccion.
     */
    public Trampa_GUI getUltima() {
        return trampas.get(trampas.size() - 1);
    }

    /**
     * Devuleve la coleccion de trampas.
     *
     * @return la coleccion de trampas.
     */
    public ArrayList<Trampa_GUI> getTrampas() {
        return trampas;
    }

    /**
     * Actuliza las trapas de la coleccion, haciendolas saltar si hay un
     * personaje cerca.
     *
     * @param PJ personaje de la partida.
     */
    public void actualizar(PJ_GUI PJ) {
        for (Trampa_GUI trampa : trampas) {
            trampa.getTrampa().actuar(PJ.getPos().getxPos());
        }
    }

    /**
     * Muestra las trampas en pantalla.
     *
     * @param g2d el manejador de graficos.
     */
    public void draw(Graphics2D g2d) {
        for (Trampa_GUI trampa : trampas) {
            trampa.draw(g2d);
        }
    }
}
