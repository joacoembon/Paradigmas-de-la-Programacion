package Modelo;

public class BloqueOpacoFijo implements Bloque {
    private final Celda celda;

    public BloqueOpacoFijo(Celda celda) {
        this.celda = celda;
    }

    @Override
    public void interactuarConLaser(Laser laser, Tablero tablero) {
        laser.detener();
    }

    @Override
    public Celda getCelda() {
        return celda;
    }
}
