package Vista;

import Conexiones.ConexionMySQL;
import Conexiones.LogWriter;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Pantalla que muestra el contenido de la biblioteca. Se encarga de cargar la
 * base de datos y mostrar la informacion leida.
 *
 * @author Kevin
 */
public class ContenidoBiblioteca extends JPanel {

    private static final String P_INTERROGANTES = "archivos/imagenes/interrogantes.jpg";

    private final Dimension dim;
    private final boolean enemigos; //Cierto si se muestran enemigos; false si se muestran objetos
    private final String jugador;   //Jugador para el que se muestra la informacion
    private ConexionMySQL cmsql;    //Conexion con la base de datos

    private ArrayList<ImageIcon> imagenes;      //Imagenes de cada elemento
    private ArrayList<String> nombres;          //Nombre de cada elemento
    private ArrayList<String> descripciones;    //Descripcion de cada elemento
    private int seleccionado;                   //Elemento seleccioniado

    private boolean bdconectada;    //Indica si la base de datos esta conectada
    private boolean cargado;        //Indica si se ha cargado informacion

    /**
     * Crea el contenido de la biblioteca.
     *
     * @param d tamaño de la ventana.
     * @param enemigos cierto si se muestran enemigos; false si se muestran
     * objetos.
     * @param jugador el jugadir del que se muestra la informacion.
     */
    public ContenidoBiblioteca(Dimension d, boolean enemigos, String jugador) {
        initComponents();
        lSelI.setIcon(new ImageIcon("archivos/interfaz/selhi.png"));
        lSelI.setVisible(true);
        lSelD.setIcon(new ImageIcon("archivos/interfaz/selhd.png"));
        lSelD.setVisible(true);
        lNombreJugador.setText(jugador);
        dim = d;
        setSize(dim);
        setFocusable(true);
        addKeyListener(new ListenerTeclado());

        this.enemigos = enemigos;
        this.jugador = jugador;

        iniciar();
        repaint();
    }

    /**
     * Inicializa elementos y carga informacion.
     */
    private void iniciar() {
        seleccionado = 0;
        imagenes = new ArrayList<>();
        nombres = new ArrayList<>();
        descripciones = new ArrayList<>();

        cargarConexionMySQL();
        if (bdconectada) {
            int idJugador = getIdJugador();
            cargarDatos(idJugador);
            seleccionActual();
        } else {
            seleccionarNoCarga();
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
     * Obtiene el identidicador del jugador. Devuelve -1 si no se pudo cargar.
     *
     * @return el identificador del jugador.
     */
    private int getIdJugador() {
        try {
            String consultaIdJugador = "Select id FROM Jugadores WHERE Nombre = '" + jugador + "';";
            ResultSet rs = cmsql.ejecutarConsulta(consultaIdJugador);
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return -1;
            }
        } catch (SQLException ex) {
            LogWriter.escribirLog("Error de SQL. E: " + ex.getMessage());
            return -1;
        }
    }

    /**
     * Carga los datos correspondientes desde la base de datos.
     *
     * @param idJugador identificador del jugador.
     */
    private void cargarDatos(int idJugador) {
        if (idJugador != -1) {
            if (enemigos) {
                cargarEnemigos(idJugador);
            } else {
                cargarObjetos(idJugador);
            }
        }
    }

    /**
     * Carga los enemigos descubiertos por el jugador.
     *
     * @param idJugador el identificador del jugador.
     */
    private void cargarEnemigos(int idJugador) {
        try {
            ConexionMySQL cmsql2 = new ConexionMySQL();
            String consultaEnemigosDesc = "SELECT enemigo FROM Enemigos_descubiertos WHERE jugador = " + idJugador + ";";
            ResultSet rs = cmsql.ejecutarConsulta(consultaEnemigosDesc);
            //Si no hay enemigos descubiertos
            if (rs.isAfterLast()) {
                cargado = false;
            } else {
                cargado = true;
                while (rs.next()) {
                    int idEnemigo = rs.getInt("enemigo");
                    //Carga imagen
                    String consultaImagen = "SELECT RutaImagen FROM Enemigos WHERE id = " + idEnemigo + ";";
                    ResultSet rsEnemigos = cmsql2.ejecutarConsulta(consultaImagen);
                    rsEnemigos.next();
                    String rutaImagen = rsEnemigos.getString("RutaImagen");
                    ImageIcon imagen = new ImageIcon(rutaImagen);
                    imagen = new ImageIcon(imagen.getImage().getScaledInstance(250, -1, java.awt.Image.SCALE_DEFAULT));
                    imagenes.add(imagen);
                    //Carga nombre
                    String consultaNombre = "SELECT Nombre FROM Enemigos WHERE id = " + idEnemigo + ";";
                    rsEnemigos = cmsql2.ejecutarConsulta(consultaNombre);
                    rsEnemigos.next();
                    String nombre = rsEnemigos.getString("Nombre");
                    nombres.add(nombre);
                    //Carga descripcion
                    String consultaDesc = "SELECT Descripcion FROM Enemigos WHERE id = " + idEnemigo + ";";
                    rsEnemigos = cmsql2.ejecutarConsulta(consultaDesc);
                    rsEnemigos.next();
                    String desc = rsEnemigos.getString("Descripcion");
                    descripciones.add(desc);
                }
            }
        } catch (IOException ex) {
            LogWriter.escribirLog("Error al leer el archivo de configuracion de la BD. E: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            LogWriter.escribirLog("Error al cargar el controlador de la BD. E: " + ex.getMessage());
        } catch (SQLException ex) {
            LogWriter.escribirLog("Error de SQL. E: " + ex.getMessage());
        }

    }

    /**
     * Carga los objetos descubiertos por el jugador.
     *
     * @param idJugador el identificador del jugador.
     */
    private void cargarObjetos(int idJugador) {
        try {
            ConexionMySQL cmsql2 = new ConexionMySQL();
            String consultaEnemigosDesc = "SELECT objeto FROM Objetos_descubiertos WHERE jugador = " + idJugador + ";";
            ResultSet rs = cmsql.ejecutarConsulta(consultaEnemigosDesc);
            if (rs.isAfterLast()) {
                cargado = false;
            } else {
                cargado = true;
                while (rs.next()) {
                    int idObjeto = rs.getInt("objeto");
                    //Carga imagen
                    String consultaImagen = "SELECT RutaImagen FROM Objetos WHERE id = " + idObjeto + ";";
                    ResultSet rsObjetos = cmsql2.ejecutarConsulta(consultaImagen);
                    rsObjetos.next();
                    String rutaImagen = rsObjetos.getString("RutaImagen");
                    ImageIcon imagen = new ImageIcon(rutaImagen);
                    imagen = new ImageIcon(imagen.getImage().getScaledInstance(250, -1, java.awt.Image.SCALE_DEFAULT));
                    imagenes.add(imagen);
                    //Carga nombre
                    String consultaNombre = "SELECT Nombre FROM Objetos WHERE id = " + idObjeto + ";";
                    rsObjetos = cmsql2.ejecutarConsulta(consultaNombre);
                    rsObjetos.next();
                    String nombre = rsObjetos.getString("Nombre");
                    nombres.add(nombre);
                    //Carga descripcion
                    String consultaDesc = "SELECT Descripcion FROM Objetos WHERE id = " + idObjeto + ";";
                    rsObjetos = cmsql2.ejecutarConsulta(consultaDesc);
                    rsObjetos.next();
                    String desc = rsObjetos.getString("Descripcion");
                    descripciones.add(desc);
                }
            }
        } catch (IOException ex) {
            LogWriter.escribirLog("Error al leer el archivo de configuracion de la BD. E: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            LogWriter.escribirLog("Error al cargar el controlador de la BD. E: " + ex.getMessage());
        } catch (SQLException ex) {
            LogWriter.escribirLog("Error de SQL. E: " + ex.getMessage());
        }
    }

    /**
     * Actualiza la informacion en pantalla con la del elemento seleccionado.
     */
    private void seleccionActual() {
        if (cargado) {
            lImagen.setIcon(imagenes.get(seleccionado));
            lNombre.setText(nombres.get(seleccionado));
            lDesc.setText(descripciones.get(seleccionado));
        } else {
            seleccionarVacio();
        }
    }

    /**
     * Informa de que el jugador introducido no ha descubierto nada todavia.
     */
    private void seleccionarVacio() {
        ImageIcon inter = new ImageIcon(P_INTERROGANTES);
        inter = new ImageIcon(inter.getImage().getScaledInstance(250, -1, java.awt.Image.SCALE_DEFAULT));
        lImagen.setIcon(inter);
        lNombre.setText("Nada descubierto");
        if (enemigos) {
            lDesc.setText("Acaba con enemigos para descubrir informacion sobre ellos.");
        } else {
            lDesc.setText("Consigue objetos para descubrir informacion sobre ellos.");
        }
    }

    /**
     * Informa de que la base de datos no esta conectada.
     */
    private void seleccionarNoCarga() {
        ImageIcon inter = new ImageIcon(P_INTERROGANTES);
        inter = new ImageIcon(inter.getImage().getScaledInstance(250, -1, java.awt.Image.SCALE_DEFAULT));
        lImagen.setIcon(inter);
        lNombre.setText("BD no conectada");
        if (enemigos) {
            lDesc.setText("La base de datos debe estar conectada para poder mostrar informacion sobre los enemigos.");
        } else {
            lDesc.setText("La base de datos debe estar conectada para poder mostrar informacion sobre los objetos.");
        }
    }

    /**
     * Selecciona el siguiente elemento.
     */
    private void siguienteItem() {
        seleccionado++;
        if (seleccionado > nombres.size() - 1) {
            seleccionado = 0;
        }
        seleccionActual();
    }

    /**
     * Selecciona el elemento anterior.
     */
    private void anteriorItem() {
        seleccionado--;
        if (seleccionado < 0) {
            seleccionado = nombres.size() - 1;
        }
        seleccionActual();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw();
    }

    private void draw() {
        requestFocusInWindow();
    }

    private class ListenerTeclado implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (cargado) {
                        anteriorItem();
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (cargado) {
                        siguienteItem();
                    }
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    ((VentanaPrincipal) SwingUtilities.getRoot(ContenidoBiblioteca.this)).irBibliotecaSeleccion(jugador);
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lTextoJugador = new javax.swing.JLabel();
        lNombreJugador = new javax.swing.JLabel();
        lImagen = new javax.swing.JLabel();
        lSelI = new javax.swing.JLabel();
        lSelD = new javax.swing.JLabel();
        lNombre = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        lDesc = new javax.swing.JTextArea();

        setMaximumSize(new java.awt.Dimension(800, 600));
        setMinimumSize(new java.awt.Dimension(800, 600));

        lTextoJugador.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lTextoJugador.setText("Jugador:");

        lNombreJugador.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N

        lSelI.setIcon(new javax.swing.ImageIcon("/home/vesprada/NetBeansProjects/Proyecto-PRG/archivos/interfaz/selhi.png")); // NOI18N
        lSelI.setMaximumSize(new java.awt.Dimension(50, 50));
        lSelI.setMinimumSize(new java.awt.Dimension(50, 50));

        lSelD.setIcon(new javax.swing.ImageIcon("/home/vesprada/NetBeansProjects/Proyecto-PRG/archivos/interfaz/selhd.png")); // NOI18N

        lNombre.setEditable(false);
        lNombre.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lNombre.setFocusable(false);
        lNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lNombreActionPerformed(evt);
            }
        });

        lDesc.setEditable(false);
        lDesc.setColumns(20);
        lDesc.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lDesc.setLineWrap(true);
        lDesc.setRows(5);
        lDesc.setWrapStyleWord(true);
        lDesc.setFocusable(false);
        jScrollPane1.setViewportView(lDesc);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(233, 233, 233)
                            .addComponent(lTextoJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(57, 57, 57)
                            .addComponent(lNombreJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(118, 118, 118)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lSelI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(82, 82, 82)
                                    .addComponent(lImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(lNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(96, 96, 96)
                            .addComponent(lSelD))))
                .addContainerGap(153, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lTextoJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(lImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(lSelI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lNombreJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(106, 106, 106)
                        .addComponent(lSelD, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addComponent(lNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lNombreActionPerformed

    }//GEN-LAST:event_lNombreActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea lDesc;
    private javax.swing.JLabel lImagen;
    private javax.swing.JTextField lNombre;
    private javax.swing.JLabel lNombreJugador;
    private javax.swing.JLabel lSelD;
    private javax.swing.JLabel lSelI;
    private javax.swing.JLabel lTextoJugador;
    // End of variables declaration//GEN-END:variables
}
