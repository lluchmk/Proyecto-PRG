package Vista;

import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 * Ventana de la aplicacion donde se mostraran todos los elementos.
 *
 * @author Kevin
 */
public class VentanaPrincipal extends JFrame {

    private static final Dimension DIMENSIONES = new Dimension(800, 600);   //Dimensiones de la ventana

    private final PantallaInicio pi;    //Pantalla de inici

    /**
     * Constructor predeterminado. Crea la ventana de la aplicacion e inicializa
     * las distintas pantallas.
     */
    public VentanaPrincipal() {
        setTitle("Proyecto PRG");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(DIMENSIONES);
        setResizable(false);

        pi = new PantallaInicio(DIMENSIONES);

        getContentPane().add(pi);
    }

    /**
     * Devuelve las dimensiones de la pantalla.
     *
     * @return las dimensiones de la pantalla.
     */
    public Dimension getDimensiones() {
        return DIMENSIONES;
    }

    /**
     * Muestra la pantalla de titulo.
     */
    public void irInicio() {
        getContentPane().removeAll();
        getContentPane().add(pi);
        repaint();
    }

    /**
     * Muestra la pantalla de seleccion de biblioteca.
     *
     * @param jugador el jugador para el que se mostrara la informacion.
     */
    public void irBibliotecaSeleccion(String jugador) {
        getContentPane().removeAll();
        getContentPane().add(new PantallaBibliotecaSeleccion(DIMENSIONES, jugador));
        repaint();
    }

    /**
     * Muestra la pantalla de opciones.
     *
     * @param enemigos
     * @param jugador el jugador del que se vera la informacion.
     */
    public void irBibliotecaContenido(boolean enemigos, String jugador) {
        getContentPane().removeAll();
        getContentPane().add(new ContenidoBiblioteca(DIMENSIONES, enemigos, jugador));
        setVisible(true);
        repaint();
    }

    /**
     * Muestra la pantalla de seleccion de jugador.
     *
     * @param juego cierto si se ira al juego; false si se va a la biblioteca.
     */
    public void irSeleccionJugador(boolean juego) {
        getContentPane().removeAll();
        getContentPane().add(new PantallaSeleccionJugador(DIMENSIONES, juego));
        repaint();
    }

    /**
     * Muestra el juego y lo empieza.
     *
     * @param jugador el nombre del jugador.
     */
    public void irJuego(String jugador) {
        getContentPane().removeAll();
        getContentPane().add(new PantallaJuego(DIMENSIONES, jugador));
        repaint();
    }

    /**
     * Muestra la pantalla de fin.
     *
     * @param victoria cierto si se gano la partida; false en caso contrario.
     * @param puntuacion la puntuacion obtenida.
     * @param jugador el nombre del jugador.
     * @param enemigosDerrotados listado de los enemigos derrotados.
     * @param objetosDescubiertos listado de los objetos decubiertos.
     */
    public void irFin(boolean victoria, int puntuacion, String jugador, ArrayList<String> enemigosDerrotados, ArrayList<String> objetosDescubiertos) {
        getContentPane().removeAll();
        getContentPane().add(new PantallaFin(DIMENSIONES, victoria, puntuacion, jugador, enemigosDerrotados, objetosDescubiertos));
        repaint();
    }
}
