package Modelo;

import static Modelo.Laser.reboteLaser;

public class BloqueEspejo extends BloqueMovil implements Bloque{

    public BloqueEspejo(Celda celdaInicial) throws Exception {
        super(celdaInicial);
    }

    @Override
    public void interactuarConLaser(Laser laser, Tablero tablero) {
        Direccion direccion = laser.getDireccion();

        int impactoX = laser.getX();
        int impactoY = laser.getY();

        // Determinar la dirección de rebote.
        reboteLaser(laser, direccion, impactoX, impactoY);
    }

}