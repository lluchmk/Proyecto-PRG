package Conexiones;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Clase encargada de registrar eventos en un log.
 *
 * @author Kevin
 */
public class LogWriter {

    private static final String PATH = "ficheros/log.txt"; //Rura del log

    /**
     * Escribe el mensaje pasado en el log.
     *
     * @param msg el mensaje a escribir en el log.
     */
    public static void escribirLog(String msg) {
        try {
            boolean append = true;
            File log = new File(PATH);
            if (!log.exists()) {
                log.createNewFile();
                append = false;
            }
            try (PrintWriter writer = new PrintWriter(new FileWriter(log, append))) {
                String hora = new SimpleDateFormat("dd-MM-YY -- HH:mm:ss").format(Calendar.getInstance().getTime());
                writer.println("[" + hora + "] " + msg);
            }

        } catch (IOException ex) {
            //Ignorado deliberadamente
        }
    }
}
