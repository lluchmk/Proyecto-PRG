package Controlador;

import Conexiones.LogWriter;
import Conexiones.XMLNiveles;
import Excepciones.ExcepcionCargaNivel;
import Vista.Nivel;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 * Clase encargada de la instanciacion de niveles.
 *
 * @author Kevin
 */
public class FactoryNiveles {

    private static final String PATH = "ficheros/niveles.xml";

    /**
     * Crea una instancia del nivel pasado como parametro.
     *
     * @param id el identificador del nivel deseado.
     * @return una nueva instancia del nivel.
     * @throws ExcepcionCargaNivel si hay algun fallo al crear la instancia.
     */
    public static Nivel getNivel(String id) throws ExcepcionCargaNivel {
        try {
            File in = new File(PATH);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            Nivel n = new Nivel();
            XMLNiveles manejador = new XMLNiveles(id, n);
            parser.parse(in, manejador);
            return n;
        } catch (IOException e) {
            LogWriter.escribirLog("Excepcion IO: " + e.getMessage());
        } catch (ParserConfigurationException e) {
            LogWriter.escribirLog("Excepcion configuracion parser: " + e.getMessage());
        } catch (SAXException e) {
            LogWriter.escribirLog("Excepcion SAX: " + e.getMessage());
        }
        throw new ExcepcionCargaNivel("" + id);
    }

}
