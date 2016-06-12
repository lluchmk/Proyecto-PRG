package Controlador;

import Modelo.Jugador;
import Vista.Objeto_GUI;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Coleccion que almacena una serie de objetos.
 *
 * @author Kevin
 */
public class ColeccionObjetos {

    private final ArrayList<Objeto_GUI> objetos;    //La coleccion de objetos

    /**
     * Constructor predeterminado. Inicializa la coleccion.
     */
    public ColeccionObjetos() {
        objetos = new ArrayList<>();
    }

    /**
     * Anade un objeto a la colecicon.
     *
     * @param o el objeto a anadir.
     */
    public void anadirObjeto(Objeto_GUI o) {
        objetos.add(o);
    }

    /**
     * Elimina un objeto de la coleccion.
     *
     * @param o el objeto a eliminar.
     */
    public void eliminarObjeto(Objeto_GUI o) {
        objetos.remove(o);
    }

    /**
     * Devuelve el objeto de la posicion indicada.
     *
     * @param posObjeto la posicion del objeto.
     * @return el objeto de la posicion indicada.
     */
    public Objeto_GUI getObjeto(int posObjeto) {
        return objetos.get(posObjeto);
    }

    /**
     * Devuelve el ultimo objeto de la coleccion.
     *
     * @return el ultimo objeto de la coleccion.
     */
    public Objeto_GUI getUltimo() {
        return objetos.get(objetos.size() - 1);
    }

    /**
     * Devuelve la coleccion de objetos.
     *
     * @return la coleccion de objetos.
     */
    public ArrayList<Objeto_GUI> getObjetos() {
        return objetos;
    }

    /**
     * Devuelve la cantidad de objetos que contiene la coleccion.
     *
     * @return la cantidad de objetos que contiene la coleccion.
     */
    public int cantidadObjeos() {
        return objetos.size();
    }

    /**
     * Utiliza un objeto en el Jugador y lo elimina de la coleccion.
     *
     * @param PJ el jugador que utiliza el objeto.
     * @param posObjeto la posicion del objeto en la coleccion.
     */
    public void utilizarObjeto(Jugador PJ, int posObjeto) {
        objetos.get(posObjeto).getObjeto().usar(PJ);
        objetos.remove(posObjeto);
    }

    /**
     * Muestra los objetos en pantalla.
     *
     * @param g2d el manejador de graficos.
     */
    public void draw(Graphics2D g2d) {
        for (Objeto_GUI objeto : objetos) {
            objeto.draw(g2d);
        }
    }
}
