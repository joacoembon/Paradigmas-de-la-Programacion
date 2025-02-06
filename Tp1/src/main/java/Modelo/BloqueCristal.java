package Modelo;

public class BloqueCristal extends BloqueMovil implements Bloque{

    public BloqueCristal(Celda celdaInicial) throws Exception {
        super(celdaInicial);
    }

    @Override
    public void interactuarConLaser(Laser laser, Tablero tablero) {
        Direccion direccion = laser.getDireccion();

        int impactoX = laser.getX();
        int impactoY = laser.getY();

        // Determinar la dirección de rebote.
        switch (direccion) {
            case NE -> {
                if (impactoX % 2 == 0 && impactoY % 2 == 1) { // Impacto en la parte Izquierda.
                    laser.cambiarDireccion(Direccion.E);// Refracta hacia Este.
                } else { // Impacto en la parte inferior.
                    laser.cambiarDireccion(Direccion.N); // Refracta hacia Norte.
                }
            }
            case NW -> {
                if (impactoX % 2 == 1 && impactoY % 2 == 0) { // Impacto en la parte inferior.
                    laser.cambiarDireccion(Direccion.N); // Refracta hacia Norte.
                } else { // Impacto en la parte derecha.
                    laser.cambiarDireccion(Direccion.W); // Refracta hacia Oeste.
                }
            }
            case SE -> {
                if (impactoX % 2 == 0 && impactoY % 2 == 1) { // Impacto en la parte izquierda.
                    laser.cambiarDireccion(Direccion.E); // Refracta hacia Este.
                } else { // Impacto en la parte superior.
                    laser.cambiarDireccion(Direccion.S); // Refracta hacia Sur.
                }
            }
            case SW -> {
                if (impactoX % 2 == 1 && impactoY % 2 == 0) { // Impacto en la parte superior.
                    laser.cambiarDireccion(Direccion.S); // Refracta hacia Sur.
                } else { // Impacto en la parte derecha.
                    laser.cambiarDireccion(Direccion.W); // Refracta hacia Oeste.
                }
            }
            default -> throw new IllegalArgumentException("Dirección no válida para el láser.");
        }
        laser.avanzarManual(2); //Avanzo hasta el otro lado del bloque.
        laser.cambiarDireccion(direccion); //Recupera su dirección.
    }
}
