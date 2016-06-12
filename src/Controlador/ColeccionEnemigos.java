package Controlador;

import Vista.Enemigo_GUI;
import Vista.PJ_GUI;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Coleccion que almacena una serie de enemigos.
 *
 * @author Kevin
 */
public class ColeccionEnemigos {

    private final ArrayList<Enemigo_GUI> enemigos;      //La coleccion de enemigos
    private final ArrayList<String> enemigosDerrotados; //Listado de los enemigos derrotados

    /**
     * Constructor predeterminado. Inicializa la coleccion.
     */
    public ColeccionEnemigos() {
        enemigos = new ArrayList<>();
        enemigosDerrotados = new ArrayList<>();
    }

    /**
     * Anade un enemigo a la coleccion.
     *
     * @param e el enemigo a anadir.
     */
    public void anadirEnemigo(Enemigo_GUI e) {
        enemigos.add(e);
    }

    /**
     * Devuelve el ultimo enemigo de la coleccion.
     *
     * @return el ultimo enemigo de la coleccion.
     */
    public Enemigo_GUI getUltimo() {
        return enemigos.get(enemigos.size() - 1);
    }

    /**
     * Responde si quedan enemigos en la coleccion.
     *
     * @return true si quedan enemigos; false en caso contrario.
     */
    public boolean quedanEnemigos() {
        return !enemigos.isEmpty();
    }

    /**
     * Actualiza a los enemigos de la coleccion. Calcula su posicion, actua si
     * hay colisiones entre un enemigo y un jugador y actualiza la informacion
     * de la partida si un enemigo es derrotado.
     *
     * @param PJ personaje de la partida.
     * @param anchoNivel anchura del nivel.
     */
    public void actualizar(PJ_GUI PJ, int anchoNivel) {
        for (Enemigo_GUI enemigo : enemigos) {
            enemigo.getEnemigo().actuar(PJ.getPos(), anchoNivel, enemigo.getWidth());
            enemigo.getEnemigo().reducirIFrames();
        }

        //Elimina a los enemigos derrotados
        Iterator<Enemigo_GUI> i = enemigos.iterator();
        while (i.hasNext()) {
            Enemigo_GUI next = i.next();
            if (!next.getEnemigo().isVivo()) {
                String tipo = next.getEnemigo().getTipo();
                if (!enemigosDerrotados.contains(tipo)) {
                    enemigosDerrotados.add(tipo);
                }
                i.remove();
            }
        }
    }

    /**
     * Devuelve la coleccion de enemigos.
     *
     * @return la coleccion de enemigos.
     */
    public ArrayList<Enemigo_GUI> getEnemigos() {
        return enemigos;
    }

    /**
     * Devuelve una coleccion que contiene el tipo de todos los enemigos
     * derrotados.
     *
     * @return los tipos de los enemigos derrotados.
     */
    public ArrayList<String> getEnemigosDerrotados() {
        return enemigosDerrotados;
    }

    /**
     * Muestra los enemigos por pantalla.
     *
     * @param g2d manejador de graficos.
     */
    public void draw(Graphics2D g2d) {
        for (Enemigo_GUI enemigo_GUI : enemigos) {
            enemigo_GUI.draw(g2d);
        }
    }
}
