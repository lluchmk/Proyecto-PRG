package Excepciones;

public class ExcepcionCargaEnemigo extends Exception {

    public ExcepcionCargaEnemigo() {
        super("Fallo al cargar un enemigo desde el archivo.");
    }
    
    public ExcepcionCargaEnemigo(String msg) {
        super("Fallo al cargar un enemigo desde el archivo. " + msg);
    }
}
