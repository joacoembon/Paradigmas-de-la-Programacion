package Modelo;

import static Modelo.Laser.reboteLaser;

public class BloqueVidrio extends BloqueMovil implements Bloque{

    public BloqueVidrio(Celda celdaInicial) throws Exception {
        super(celdaInicial);
    }

    @Override
    public void interactuarConLaser(Laser laser, Tablero tablero) {
        Direccion direccion = laser.getDireccion();

        int impactoX = laser.getX();
        int impactoY = laser.getY();
        Laser nuevoLaser = new Laser(impactoX, impactoY, direccion);

        nuevoLaser.propagarCamino(tablero);
        laser.agregarCamino(nuevoLaser.getCamino());
        reboteLaser(laser, direccion, impactoX, impactoY);
    }
}
