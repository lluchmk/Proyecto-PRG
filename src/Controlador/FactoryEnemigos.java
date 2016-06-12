package Controlador;

import Conexiones.LogWriter;
import Conexiones.XMLEnemigos;
import Excepciones.ExcepcionCargaEnemigo;
import Vista.Enemigo_GUI;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 * Clase encargada de la instanciación de enemigos.
 *
 * @author Kevin
 */
public class FactoryEnemigos {

    private static final String PATH = "ficheros/enemigos.xml";

    /**
     * Crea una instancia del tipo de enemigo pasado como parametro.
     *
     * @param tipo el tipo de enemigo.
     * @return una nueva instancia del enemigo deseado.
     * @throws ExcepcionCargaEnemigo si hay algunfallo al crearlo.
     */
    public static Enemigo_GUI getEnemigo(String tipo) throws ExcepcionCargaEnemigo {
        try {
            File in = new File(PATH);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            Enemigo_GUI e = new Enemigo_GUI();
            XMLEnemigos manejador = new XMLEnemigos(tipo, e);
            parser.parse(in, manejador);
            e.cargarFrames();
            return e;
        } catch (IOException e) {
            LogWriter.escribirLog("Excepcion IO: " + e.getMessage());
        } catch (ParserConfigurationException e) {
            LogWriter.escribirLog("Excepcion configuracion parser: " + e.getMessage());
        } catch (SAXException e) {
            LogWriter.escribirLog("Excepcion SAX: " + e.getMessage());
        }
        throw new ExcepcionCargaEnemigo("Trasgo");
    }

}
