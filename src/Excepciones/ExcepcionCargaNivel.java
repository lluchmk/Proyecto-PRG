package Excepciones;

public class ExcepcionCargaNivel extends Exception {

    public ExcepcionCargaNivel() {
        super("Fallo al cargar un nivel desde el archivo.");
    }
    
    public ExcepcionCargaNivel(String msg) {
        super("Fallo al cargar un nivel desde el archivo. " + msg);
    }
}
