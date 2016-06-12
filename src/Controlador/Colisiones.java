package Controlador;

import Conexiones.LogWriter;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Clase encargada de detectar colisiones.
 *
 * @author Kevin
 */
public class Colisiones {

    /**
     * Responde si el jugador y un enemigo colisionan.
     *
     * @param hb1 hitbox del primer elemento.
     * @param x1 posicion horizontal del primer elemento.
     * @param y1 posicion vertical del primer elemento.
     * @param hb2 hitbox del segundo elemento.
     * @param x2 posicion horizontal del segundo elemento.
     * @param y2 posicion vertical del segundo elemento.
     * @return true si colisionan; false en caso contrario.
     */
    public static boolean colisionan(BufferedImage hb1, int x1, int y1, BufferedImage hb2, int x2, int y2) {
        if (colisionGeneral(hb1, x1, y1, hb2, x2, y2)) {
            return colisionPorPixel(hb1, x1, y1, hb2, x2, y2);
        }
        return false;
    }

    /**
     * Responde si un jugador y un enemigo estan relativamente cerca.
     *
     * @param hb1 hitbox del primer elemento.
     * @param x1 posicion horizontal del primer elemento.
     * @param y1 posicion vertical del primer elemento.
     * @param hb2 hitbox del segundo elemento.
     * @param x2 posicion horizontal del segundo elemento.
     * @param y2 posicion vertical del segundo elemento.
     * @return cierto si estan cerca; false en caso contrario.
     */
    private static boolean colisionGeneral(BufferedImage hb1, int x1, int y1, BufferedImage hb2, int x2, int y2) {
        Rectangle r1 = new Rectangle(x1, y1, hb1.getWidth(), hb1.getHeight());
        Rectangle r2 = new Rectangle(x2, y2, hb2.getWidth(), hb2.getHeight());

        return r1.intersects(r2);
    }

    /**
     * Responde si el jugador y el enemigo colisionan.
     *
     * @param hb1 hitbox del primer elemento.
     * @param x1 posicion horizontal del primer elemento.
     * @param y1 posicion vertical del primer elemento.
     * @param hb2 hitbox del segundo elemento.
     * @param x2 posicion horizontal del segundo elemento.
     * @param y2 posicion vertical del segundo elemento.
     * @return cierto si colisionan; false en caso contrario.
     */
    private static boolean colisionPorPixel(BufferedImage hb1, int x1, int y1, BufferedImage hb2, int x2, int y2) {
        // Inicializacion
        double width1 = x1 + hb1.getWidth() - 1,
                height1 = y1 + hb1.getHeight() - 1,
                width2 = x2 + hb2.getWidth() - 1,
                height2 = y2 + hb2.getHeight() - 1;

        int xstart = (int) Math.max(x1, x2),
                ystart = (int) Math.max(y1, y2),
                xend = (int) Math.min(width1, width2),
                yend = (int) Math.min(height1, height2);

        // Rectangulo de interseccion
        int toty = Math.abs(yend - ystart);
        int totx = Math.abs(xend - xstart);

        for (int y = 1; y < toty - 1; y++) {
            int ny = Math.abs(ystart - (int) y1) + y;
            int ny1 = Math.abs(ystart - (int) y2) + y;

            for (int x = 1; x < totx - 1; x++) {
                int nx = Math.abs(xstart - (int) x1) + x;
                int nx1 = Math.abs(xstart - (int) x2) + x;
                try {
                    //Si hay pixeles no trasparentes
                    if (((hb1.getRGB(nx, ny) & 0xFF000000) != 0x00)
                            && ((hb2.getRGB(nx1, ny1) & 0xFF000000) != 0x00)) {
                        //Hay colision
                        return true;
                    }
                } catch (Exception e) {
                    LogWriter.escribirLog("Error al calcular colisiones: " + e.getMessage());
                }
            }
        }

        return false;
    }
}
