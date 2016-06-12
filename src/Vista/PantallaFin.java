package Vista;

import Conexiones.ConexionMySQL;
import Conexiones.LogWriter;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * Pantalla de fin de partida.
 *
 * @author Kevin
 */
public class PantallaFin extends JPanel {

    private final Dimension dim;

    private ConexionMySQL cmsql;
    private boolean bdconectada = false;

    private static final String P_FONDO_M = "archivos/fondos/muerto.png";
    private static final String P_FONDO_V = "archivos/fondos/victoria.png";
    private BufferedImage fondo;

    private final boolean victoria;
    private final String jugador;
    private int idJugador;
    private final int puntuacion;
    private final ArrayList<String> enemigosDerrotados;
    private final ArrayList<String> objetosDescubiertos;

    private final Timer temp;
    private boolean pasable;

    /**
     * Constructor predeterminado. Crea una pantalla de inicio y carga y muestra
     * las imagenes. Actualiza además la base de datos.
     *
     * @param d las dimensiones de la pantalla.
     * @param victoria si se gano la partida.
     * @param puntuacion la puntuacion que se alcanzo en la partida.
     * @param jugador el jugador que ha realizado la partida.
     * @param enemigosDerrotados una lista con los enemigos que ha derrotado el
     * jugador.
     * @param objetosDescubiertos una lista con los objetos descubiertos por el
     * jugador.
     */
    public PantallaFin(Dimension d, boolean victoria, int puntuacion, String jugador,
            ArrayList<String> enemigosDerrotados, ArrayList<String> objetosDescubiertos) {
        dim = d;
        setSize(dim);
        setFocusable(true);

        this.victoria = victoria;
        this.jugador = jugador;
        this.puntuacion = puntuacion;
        this.enemigosDerrotados = enemigosDerrotados;
        this.objetosDescubiertos = objetosDescubiertos;

        addKeyListener(new ListenerTeclado());
        temp = new Timer(300, new ListenerTemporizador());
        pasable = false;
        long inic = System.currentTimeMillis();
        cargarFondo();
        cargarConexionMySQL();
        actualizarBD();
        long tcarga = System.currentTimeMillis() - inic;
        temp.setInitialDelay(3000 - (int) tcarga);
        temp.start();
    }

    /**
     * Carga las imagenes desde las rutas.
     */
    private void cargarFondo() {
        try {
            if (victoria) {
                fondo = ImageIO.read(new File(P_FONDO_V));
            } else {
                fondo = ImageIO.read(new File(P_FONDO_M));
            }
        } catch (IOException e) {
            LogWriter.escribirLog("Error al cargar una imagen. E: " + e.getMessage());
        }
    }

    /**
     * Carga la conexion con la base de datos.
     */
    private void cargarConexionMySQL() {
        try {
            cmsql = new ConexionMySQL();
            bdconectada = true;
        } catch (IOException ex) {
            LogWriter.escribirLog("Error al leer el archivo de configuracion de SQL. E: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            LogWriter.escribirLog("Error al cargar el controladro MySQL. E: " + ex.getMessage());
        } catch (SQLException ex) {
            LogWriter.escribirLog("Error de MySQL. E: " + ex.getMessage());
        }
    }

    /**
     * Actualiza la base de datos con la informacion nueva.
     */
    private void actualizarBD() {
        if (bdconectada) {
            obtenerIdJugador();
            actualizarJugadorBD();
            actualizarPuntuacionBD();
            actualizarEnemigosBD();
            actualizarObjetosBD();

        }
    }

    /**
     * Busca el identificador del jugador.
     */
    private void obtenerIdJugador() {
        try {
            String consultaJugador = "SELECT id FROM Jugadores WHERE Nombre='" + jugador + "';";
            ResultSet set = cmsql.ejecutarConsulta(consultaJugador);
            if (!set.next()) {
                idJugador = -1;
            } else {
                idJugador = set.getInt("id");
            }
        } catch (SQLException ex) {
            LogWriter.escribirLog("Error de MySQL al leer el id del jugador. E: " + ex.getMessage());
            idJugador = -1;
        }
    }

    /**
     * Anade el jugador en la base de datos si no existe.
     */
    private void actualizarJugadorBD() {
        if (idJugador == -1) {
            try {
                String creaJugador = "INSERT INTO Jugadores (Nombre) VALUES ('" + jugador + "');";
                cmsql.ejecutaSentencia(creaJugador);
                obtenerIdJugador();
            } catch (SQLException ex) {
                LogWriter.escribirLog("Error de MySQL al actualizar el jugador. E: " + ex.getMessage());
            }
        }
    }

    /**
     * Anade la puntuacion del jugador a al abase de datos.
     */
    private void actualizarPuntuacionBD() {
        if (idJugador != -1) {
            try {
                String insertarPuntuacion = "INSERT INTO Puntuaciones (Puntuacion, Jugador) VALUES ("
                        + puntuacion + ", " + idJugador + ");";
                cmsql.ejecutaSentencia(insertarPuntuacion);
            } catch (SQLException ex) {
                LogWriter.escribirLog("Error de MySQL al actualizar la puntuacion. E: " + ex.getMessage());
            }
        }
    }

    /**
     * Actualiza los enemigos descubiertos por el jugador.
     */
    private void actualizarEnemigosBD() {
        if (idJugador != -1) {
            for (String enemigo : enemigosDerrotados) {
                try {
                    String consultaEnemigo = "SELECT id FROM Enemigos WHERE Nombre='" + enemigo + "';";
                    ResultSet set = cmsql.ejecutarConsulta(consultaEnemigo);
                    if (set.next()) {
                        int idEnemigo = set.getInt("id");
                        try {
                            ConexionMySQL cmsql2 = new ConexionMySQL();
                            String consultaDescubiertos = "SELECT * FROM Enemigos_descubiertos WHERE enemigo = " + idEnemigo + " AND jugador = " + idJugador + ";";

                            ResultSet set2 = cmsql2.ejecutarConsulta(consultaDescubiertos);
                            if (!set2.next()) {
                                String sentenciaEnemigos = "INSERT INTO Enemigos_descubiertos VALUES (" + idEnemigo + ", " + idJugador + ");";
                                cmsql.ejecutaSentencia(sentenciaEnemigos);
                            }
                        } catch (IOException ex) {
                            LogWriter.escribirLog("Error al cargar base de datos. E" + ex.getMessage());
                        } catch (ClassNotFoundException ex) {
                            LogWriter.escribirLog("Error al cargar el controladro de la base de datos. E" + ex.getMessage());
                        }
                    }
                } catch (SQLException ex) {
                    LogWriter.escribirLog("Error al leer el id del enemigo " + enemigo + ". E: " + ex.getMessage());
                }
            }
        }
    }

    /**
     * Actualiza los objetos descubiertos por el jugador.
     */
    private void actualizarObjetosBD() {
        if (idJugador != -1) {
            for (String objeto : objetosDescubiertos) {
                try {
                    String consultaEnemigo = "SELECT id FROM Objetos WHERE Nombre='" + objeto + "';";
                    ResultSet set = cmsql.ejecutarConsulta(consultaEnemigo);
                    if (set.next()) {
                        int idObjeto = set.getInt("id");
                        try {
                            ConexionMySQL cmsql2 = new ConexionMySQL();
                            String consultaDescubiertos = "SELECT * FROM Objetos_descubiertos WHERE objeto = " + idObjeto + " AND jugador = " + idJugador + ";";

                            ResultSet set2 = cmsql2.ejecutarConsulta(consultaDescubiertos);
                            if (!set2.next()) {
                                String sentenciaEnemigos = "INSERT INTO Objetos_descubiertos VALUES (" + idObjeto + ", " + idJugador + ");";
                                cmsql.ejecutaSentencia(sentenciaEnemigos);
                            }
                        } catch (IOException ex) {
                            LogWriter.escribirLog("Error al cargar base de datos. E" + ex.getMessage());
                        } catch (ClassNotFoundException ex) {
                            LogWriter.escribirLog("Error al cargar el controladro de la base de datos. E" + ex.getMessage());
                        }
                    }
                } catch (SQLException ex) {
                    LogWriter.escribirLog("Error al leer el id del objeto " + objeto + ". E: " + ex.getMessage());
                }
            }
        }
    }

    /**
     * Muestra la ventana.
     *
     * @param g el manejador de graficos.
     */
    public void draw(Graphics g) {
        requestFocusInWindow();
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(fondo, 0, 0, null);
        Font f = new Font("Fuente", Font.BOLD, 22);
        FontMetrics fm = g2d.getFontMetrics(f);
        g2d.setFont(f);
        if (pasable) {
            String s_pasable = "Pulse un boton para continuar";
            int x = (dim.width - fm.stringWidth(s_pasable)) / 2;
            int y = (dim.height - fm.getHeight()) / 2 + 30;
            g2d.drawString(s_pasable, x, y);
        }
        String s_puntuacion = "Puntuacion: ";
        int fx = (dim.width - fm.stringWidth(s_puntuacion)) / 2;
        int fy = ((dim.height - fm.getHeight()) / 4) * 3;
        g2d.drawString("Puntuacion: ", fx, fy);
        g2d.drawString("" + puntuacion, fx, fy + 50);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private class ListenerTeclado implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (pasable) {
                ((VentanaPrincipal) SwingUtilities.getRoot(PantallaFin.this)).irInicio();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    private class ListenerTemporizador implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            pasable = true;
            temp.stop();
            repaint();
        }
    }
}
