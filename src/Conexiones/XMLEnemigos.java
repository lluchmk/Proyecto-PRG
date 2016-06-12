package Conexiones;

import Modelo.Enemigos.Dragon;
import Modelo.Enemigos.Murcielago;
import Modelo.Enemigos.Trasgo;
import Vista.Enemigo_GUI;
import java.util.Random;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Manejador de la lectura del fichero de enemigos.
 *
 * @author Kevin
 */
public class XMLEnemigos extends DefaultHandler {

    private boolean leer = false;

    private boolean bPV = false;
    private boolean bDano = false;
    private boolean bPuntos = false;
    private boolean bVista = false;
    private boolean bRuta = false;
    private boolean bRutaHb = false;
    private boolean bNAnimaciones = false;
    private boolean bHeight = false;
    private boolean bWidth = false;
    private boolean bVelX = false;

    private final String tipo;
    private final Enemigo_GUI actual;

    /**
     * Crea un manejador para leer un tipo de enemigo.
     *
     * @param tipo el tipo de enemigo a leer.
     * @param e el enemigo en el que se guardara la informacion leida.
     */
    public XMLEnemigos(String tipo, Enemigo_GUI e) {
        super();
        this.tipo = tipo;
        actual = e;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //Datos del enemigo
        if (qName.equalsIgnoreCase("enemigo")) {
            String id = attributes.getValue("id");
            //Si se esta leyendo el enemigo deseado
            if (id.equalsIgnoreCase(tipo)) {
                leer = true;
                switch (tipo) {
                    case "Trasgo":
                        actual.setEnemigo(new Trasgo());
                        break;
                    case "Murcielago":
                        actual.setEnemigo(new Murcielago());
                        Random r = new Random();
                        int estado = r.nextInt(1) + 2;
                        actual.getEnemigo().setEstadoActual(estado);
                        break;
                    case "jefeDragon":
                        actual.setEnemigo(new Dragon());
                        break;
                }
            }
        } else if (leer) {
            if (qName.equalsIgnoreCase("pv")) {
                bPV = true;
            } else if (qName.equalsIgnoreCase("dano")) {
                bDano = true;
            } else if (qName.equalsIgnoreCase("puntos")) {
                bPuntos = true;
            } else if (qName.equalsIgnoreCase("vista")) {
                bVista = true;
            } else if (qName.equalsIgnoreCase("ruta")) {
                bRuta = true;
            } else if (qName.equalsIgnoreCase("rutahb")) {
                bRutaHb = true;
            } else if (qName.equalsIgnoreCase("nAnimaciones")) {
                bNAnimaciones = true;
            } else if (qName.equalsIgnoreCase("h")) {
                bHeight = true;
            } else if (qName.equalsIgnoreCase("w")) {
                bWidth = true;
            } else if (qName.equalsIgnoreCase("velx")) {
                bVelX = true;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("enemigo")) {
            //Se ha leido un enemigo completo
            if (leer) {
                leer = false;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //Datos de enemigo
        if (leer) {
            if (bPV) {
                int pv = Integer.parseInt(new String(ch, start, length));
                actual.getEnemigo().setPvMax(pv);
                actual.getEnemigo().setPv(pv);
                bPV = false;
            } else if (bPuntos) {
                int puntos = Integer.parseInt(new String(ch, start, length));
                actual.getEnemigo().setPuntos(puntos);
                bPuntos = false;
            } else if (bVista) {
                int vista = Integer.parseInt(new String(ch, start, length));
                actual.getEnemigo().setVista(vista);
                bVista = false;
            } else if (bDano) {
                int dano = Integer.parseInt(new String(ch, start, length));
                actual.getEnemigo().setDano(dano);
                bDano = false;
            } else if (bRuta) {
                String ruta = new String(ch, start, length);
                actual.setRuta(ruta);
                bRuta = false;
            } else if (bRutaHb) {
                String ruta = new String(ch, start, length);
                actual.setRutaHb(ruta);
                bRutaHb = false;
            } else if (bNAnimaciones) {
                int nAnimaciones = Integer.parseInt(new String(ch, start, length));
                actual.setnAnimaciones(nAnimaciones);
                bNAnimaciones = false;
            } else if (bHeight) {
                int h = Integer.parseInt(new String(ch, start, length));
                actual.setHeight(h);
                bHeight = false;
            } else if (bWidth) {
                int w = Integer.parseInt(new String(ch, start, length));
                actual.setWidth(w);
                bWidth = false;
            } else if (bVelX) {
                int velx = Integer.parseInt(new String(ch, start, length));
                actual.getEnemigo().setVX(velx);
                bVelX = false;
            }
        }
    }
}
