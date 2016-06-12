package Conexiones;

import Controlador.FactoryEnemigos;
import Excepciones.ExcepcionCargaEnemigo;
import Modelo.Objeto;
import Modelo.Objetos.Pocion;
import Modelo.Objetos.Veneno;
import Modelo.Trampa;
import Vista.Enemigo_GUI;
import Vista.Nivel;
import Vista.Objeto_GUI;
import Vista.Trampa_GUI;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Manejador de la lectura del fichero de niveles.
 *
 * @author Kevin
 */
public class XMLNiveles extends DefaultHandler {

    private boolean leer = false;
    private boolean bFondo = false;

    private boolean bEnemigo = false;
    private boolean bTipoEnemigo = false;
    private boolean bPosXEnemigo = false;

    private boolean bTrampa = false;
    private boolean bRutaTrampa = false;
    private boolean bPosTrampa = false;
    private boolean bDanoTrampa = false;

    private boolean bObjeto = false;
    private boolean bTipoObjeto = false;
    private boolean bRutaObjeto = false;
    private boolean bPosObjeto = false;

    private boolean bJefe = false;
    private boolean bTipoJefe = false;
    private boolean bPosJefe = false;

    private final String idNivel;
    private final Nivel actual;

    /**
     * Crea un manejador para leer un nivel del fichero.
     *
     * @param id identificador del nivel a leer.
     * @param n nivel en el que se guardara la informacion.
     */
    public XMLNiveles(String id, Nivel n) {
        super();
        this.idNivel = id;
        actual = n;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //Datos del nivel
        if (qName.equalsIgnoreCase("nivel")) {
            String id = attributes.getValue("id");
            if (id.equalsIgnoreCase(idNivel)) {
                leer = true;
            }
            actual.setNivel(id);
        } else if (qName.equalsIgnoreCase("fondo")) {
            bFondo = true;
        } else if (qName.equalsIgnoreCase("enemigo")) {
            bEnemigo = true;
        } else if (qName.equalsIgnoreCase("tipoEnemigo")) {
            bTipoEnemigo = true;
        } else if (qName.equalsIgnoreCase("posx")) {
            if (bEnemigo) {
                bPosXEnemigo = true;
            } else {
                bPosJefe = true;
            }
        } else if (qName.equalsIgnoreCase("trampa")) {
            bTrampa = true;
        } else if (qName.equalsIgnoreCase("ruta")) {
            bRutaTrampa = true;
        } else if (qName.equalsIgnoreCase("posT")) {
            bPosTrampa = true;
        } else if (qName.equalsIgnoreCase("dano")) {
            bDanoTrampa = true;
        } else if (qName.equalsIgnoreCase("objeto")) {
            bObjeto = true;
        } else if (qName.equalsIgnoreCase("tipoObjeto")) {
            bTipoObjeto = true;
        } else if (qName.equalsIgnoreCase("rutaObjeto")) {
            bRutaObjeto = true;
        } else if (qName.equalsIgnoreCase("posO")) {
            bPosObjeto = true;
        } else if (qName.equalsIgnoreCase("jefe")) {
            bJefe = true;
        } else if (qName.equalsIgnoreCase("tipoJefe")) {
            bTipoJefe = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("nivel")) {
            leer = false;
        } else if (leer && qName.equalsIgnoreCase("enemigo")) {
            bEnemigo = false;
        } else if (leer && qName.equalsIgnoreCase("trampa")) {
            bTrampa = false;
        } else if (leer && qName.equalsIgnoreCase("objeto")) {
            bObjeto = false;
        } else if (leer && qName.equalsIgnoreCase("jefe")) {
            bJefe = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (leer) {
            if (bFondo) {
                try {
                    String ruta = new String(ch, start, length);
                    BufferedImage fondo = ImageIO.read(new File(ruta));
                    actual.setFondo(fondo);
                } catch (IOException ex) {
                    LogWriter.escribirLog("Fallo de carga del fondo del nivel: " + ex.getMessage());
                }
                bFondo = false;
            } else if (bEnemigo) {
                if (bTipoEnemigo) {
                    //Leer enemigo
                    String tipo = new String(ch, start, length);
                    //Utilizar el FactoryEnemigos para crear una instancia
                    try {
                        Enemigo_GUI e = new Enemigo_GUI();
                        switch (tipo) {
                            case "Trasgo":
                                e = FactoryEnemigos.getEnemigo(tipo);
                                break;
                            case "Murcielago":
                                e = FactoryEnemigos.getEnemigo(tipo);
                                break;
                        }
                        actual.getEnemigos().anadirEnemigo(e);
                        bTipoEnemigo = false;
                    } catch (ExcepcionCargaEnemigo e) {
                        LogWriter.escribirLog(e.getMessage());
                    }
                } else if (bPosXEnemigo) {
                    int xpos = Integer.parseInt(new String(ch, start, length));
                    int y = 600 - (actual.getEnemigos().getUltimo().getHeight() + 85);
                    actual.getEnemigos().getUltimo().getEnemigo().getPosicion().setyPos(y);
                    actual.getEnemigos().getUltimo().getEnemigo().getPosicion().setxPos(xpos);
                    bPosXEnemigo = false;
                }
            } else if (bTrampa) {
                if (bRutaTrampa) {
                    String ruta = new String(ch, start, length);
                    Trampa t = new Trampa();
                    Trampa_GUI tgui = new Trampa_GUI(t);
                    tgui.setRuta(ruta);
                    tgui.cargarFrames();
                    actual.getTrampas().anadirTrampa(tgui);
                    bRutaTrampa = false;
                } else if (bPosTrampa) {
                    int pos = Integer.parseInt(new String(ch, start, length));
                    actual.getTrampas().getUltima().getTrampa().setPosX(pos);
                    bPosTrampa = false;
                } else if (bDanoTrampa) {
                    int dano = Integer.parseInt(new String(ch, start, length));
                    actual.getTrampas().getUltima().getTrampa().setDano(dano);
                    bDanoTrampa = false;
                }
            } else if (bObjeto) {
                if (bTipoObjeto) {
                    String tipo = new String(ch, start, length);
                    Objeto o = null;
                    switch (tipo) {
                        case "Pocion":
                            o = new Pocion();
                            break;
                        case "Veneno":
                            o = new Veneno();
                            break;
                    }
                    Objeto_GUI ogui = new Objeto_GUI();
                    ogui.setObjeto(o);
                    actual.getObjetos().anadirObjeto(ogui);

                    bTipoObjeto = false;
                } else if (bRutaObjeto) {
                    String ruta = new String(ch, start, length);
                    actual.getObjetos().getUltimo().setRuta(ruta);
                    actual.getObjetos().getUltimo().cargar();
                    bRutaObjeto = false;
                } else if (bPosObjeto) {
                    int x = Integer.parseInt(new String(ch, start, length));
                    actual.getObjetos().getUltimo().getPos().setxPos(x);
                    actual.getObjetos().getUltimo().getPos().setyPos(480);
                    bPosObjeto = false;
                }
            } else if (bJefe) {
                if (bTipoJefe) {
                    String tipo = new String(ch, start, length);
                    try {
                        Enemigo_GUI j = new Enemigo_GUI();
                        switch (tipo) {
                            case "jefeDragon":
                                j = FactoryEnemigos.getEnemigo(tipo);
                                break;
                        }
                        actual.setJefe(j);
                        bTipoJefe = false;
                    } catch (ExcepcionCargaEnemigo e) {
                        LogWriter.escribirLog(e.getMessage());
                    }
                } else if (bPosJefe) {
                    int x = Integer.parseInt(new String(ch, start, length));
                    int y = 600 - (actual.getJefe().getHeight() + 65);
                    actual.getJefe().getPosicion().setxPos(x);
                    actual.getJefe().getPosicion().setyPos(y);
                    bPosJefe = false;
                }
            }
        }
    }
}
