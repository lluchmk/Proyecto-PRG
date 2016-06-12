package Controlador;

/**
 * Estados posibles en los que puede estar un personaje.
 *
 * @author Kevin
 */
public enum EstadosJugador {

    IDLE_R(0), IDLE_L(1), RUN_R(2), RUN_L(3), ATTACK_R(4), ATTACK_L(5), AIR_R(6), AIR_L(7), AIR_R_ATTACK(8), AIR_L_ATTACK(9);
    
    private int id;

    private EstadosJugador(int id) {
        this.id = id;
    }
    
    public int getIdEstado() {
        return id;
    }
}
