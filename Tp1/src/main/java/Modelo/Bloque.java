package Modelo;

public interface Bloque {
    void interactuarConLaser(Laser laser,Tablero tablero);
    Celda getCelda();
}
