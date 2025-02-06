package Modelo;

public class BloqueOpacoMovil extends BloqueMovil implements Bloque{

    public BloqueOpacoMovil(Celda celdaInicial) throws Exception {
        super(celdaInicial);
    }

    @Override
    public void interactuarConLaser(Laser laser, Tablero tablero) {
        laser.detener();
    }
}
