package Vista;

import java.awt.EventQueue;

class ProyectoPRG {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                VentanaPrincipal vp = new VentanaPrincipal();
                vp.setVisible(true);
            }
        });
    }
}
